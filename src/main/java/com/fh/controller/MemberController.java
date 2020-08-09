package com.fh.controller;

import com.fh.common.FileUtil;
import com.fh.common.FreemarkerUtil;
import com.fh.common.MD5Util;
import com.fh.common.ServerResponse;
import com.fh.model.DataTableResult;
import com.fh.model.Member;
import com.fh.model.ParemMemberSelect;
import com.fh.service.IMemberService;
import com.fh.utils.FilterUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("member")
public class MemberController {

    @Resource(name = "memberService")
    private IMemberService memberService;

    @RequestMapping("init")
    @ResponseBody
    public String init(){
        return "111";
    }

    @RequestMapping("login")
    @ResponseBody
    public ServerResponse toLogin(String memberName, String password,String checkCode, HttpSession session){
        Member member=memberService.getMemberByMember(memberName);
        if(member == null){
            return ServerResponse.error(1,"用户名不存在");
        }
        if(StringUtils.isBlank(password)){
            return ServerResponse.error(1,"密码不能为空");
        }
        if(StringUtils.isBlank(checkCode)){
            return ServerResponse.error(1,"验证码不能为空");
        }
        if(!checkCode.equals(session.getAttribute("checkCode"))){
            return ServerResponse.error(1,"验证码不一致空");
        }
        if(!MD5Util.verify(password, member.getPassword())){
            return ServerResponse.error(1,"密码错误");
        }else{
            session.setAttribute("member",member);
            return ServerResponse.success(member);
        }

    }

    /**
     * 初始化会员展示页面
     * @return
     */
    @RequestMapping("toMember")
    public String toMember(){
        return "member/queryMemberList";
    }

    @RequestMapping("toZhuCe")
    public String toZhuCe(){
        return "member/zhuCe";
    }

    /**
     * 增加会员
     * @param member
     * @return
     */
    @RequestMapping("addMember")
    @ResponseBody
    public ServerResponse addMember(Member member) {
        Member mem=memberService.getMemberByMember(member.getMemberName( ));
        if (mem != null) {
            return ServerResponse.error(1, "用户名已存在");
        }else{
            memberService.addMember(member);
            return ServerResponse.success( );
        }
    }

    /**
     * 分页查询+条件
     * @param paremMemberSelect
     * @return
     */
    @RequestMapping("queryMemberPage")
    @ResponseBody
    public DataTableResult queryMemberPage(ParemMemberSelect paremMemberSelect){
        return memberService.queryMemberPage(paremMemberSelect);
    }


    /**
     * 将查询的数据导出成pdf
     * @param member
     * @param response
     * @param request
     */
    @RequestMapping("exportPdf")
    public void exportPdf(Member member, HttpServletResponse response, HttpServletRequest request){
        try {
            //1.根据查询条件查询出符合条件的角色集合
            List<Member> memberList = memberService.queryMemberList(member);
            Map<String,Object> dataMap = new HashMap<>();
            dataMap.put("memberList",memberList);
            //2.调用FreemarkerUtil工具类的生成pdf文件方法得到一个pdf文件
            File file = FreemarkerUtil.generatePdf(dataMap, "role-pdf.ftl", "/template", request);
            //3.调用FileUtil工具类中的下载文件方法
            FileUtil.downloadFile(file,"会员列表.pdf",request,response);
            //4.将服务器上生成的word文件删除掉
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 将查询的数据导出成excel
     * @param paremMemberSelect
     * @param response
     */
    @RequestMapping("exportExcel")
    public void exportExcel(ParemMemberSelect paremMemberSelect, HttpServletResponse response){
        //根据条件查询 数据
        List<Member>findAllMemberList=this.memberService.findAllMemberList(paremMemberSelect);
        // 创建 wookbook
        XSSFWorkbook workbook = bulidSheet(findAllMemberList);
        //下载
        FilterUtil.xlsDownloadFile(response,workbook);
    }

    private XSSFWorkbook bulidSheet(List<Member> findAllMemberList) {
        XSSFWorkbook workbook = new XSSFWorkbook( );
        XSSFSheet sheet = workbook.createSheet( );
        //构建 大标题
        XSSFRow row = sheet.createRow(3);
        XSSFCell cell = row.createCell(7);
        cell.setCellValue("会员列表");
        CellRangeAddress cellRangeAddress = new CellRangeAddress(3, 5, 7, 9);
        sheet.addMergedRegion(cellRangeAddress);
        buildExcelUtil(sheet);
        buildExcelBody(findAllMemberList, sheet);
        return workbook;
    }

    private void buildExcelBody(List<Member> findAllMemberList, XSSFSheet sheet) {
        for(int i=0;i<findAllMemberList.size();i++){
            Member member = findAllMemberList.get(i);
            XSSFRow row = sheet.createRow(i+7);
            row.createCell(0+7).setCellValue(member.getMemberName());
            row.createCell(1+7).setCellValue(member.getRealName());
        }
    }

    private void buildExcelUtil(XSSFSheet sheet) {
        XSSFRow row = sheet.createRow(0);
        String [] title={"会员名","会员真实名"};
        for(int i=0;i<title.length;i++){
            row.createCell(i).setCellValue(title[i]);
        }
    }


}
