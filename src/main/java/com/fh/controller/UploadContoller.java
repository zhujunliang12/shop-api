package com.fh.controller;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.fh.common.ServerResponse;
import com.fh.model.Product;
import com.fh.utils.ExcelFanShen;
import com.fh.utils.FilterUtil;
import com.fh.utils.OssUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("upload")
@CrossOrigin
public class UploadContoller {

/*    // Endpoint以杭州为例，其它Region请按实际情况填写。
    String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
    // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
    String accessKeyId = "<yourAccessKeyId>";
    String accessKeySecret = "<yourAccessKeySecret>";*/

    private String endpoint = "http://oss-cn-beijing.aliyuncs.com";
    private String accessKeyId = "LTAI4GKZurzwinq8ecfUmcJm";
    private String accessKeySecret = "UtUpWPJ6YMb6lZzS8v7bCpAeWB3VR9";

    @RequestMapping("fileUploadOss")
    @ResponseBody
    public ServerResponse fileUpload(@RequestParam("avatar") MultipartFile file, Long typeId){
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 创建PutObjectRequest对象
        String path="";
        try {
            ossClient.putObject("zhujunliang", file.getOriginalFilename(), new ByteArrayInputStream(file.getBytes()));
            // 上传文件。
            //设置返回的url有效期为10年
            Date expiration = new Date(System.currentTimeMillis() + 10 * 365 * 24 * 60 * 60 * 1000);
            URL url = ossClient.generatePresignedUrl("zhujunliang", file.getOriginalFilename(), expiration);
            String[] split = url.toString().split("\\?");
            path= split[0];
        } catch (Throwable e) {
            e.printStackTrace();
        }finally {
            // 关闭OSSClient。
            ossClient.shutdown();
        }
        return ServerResponse.success(path);

    }

    /**
     * 老师讲的oss文件上传
     * @param filter
     * @return
     */
    @RequestMapping("ossUplaodFilter")
    @ResponseBody
    public ServerResponse ossUplaodFilter(@RequestParam("avatar") MultipartFile filter){
        InputStream in=null;
        String filterPath=null;
        try {
            in= filter.getInputStream( );
            String filterName = filter.getOriginalFilename( );
            filterPath= OssUtil.ossUploadFilter(in, filterName);
        } catch (IOException e) {
            e.printStackTrace( );
        }finally {
            if(null != in){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace( );
                }
            }
        }
        return ServerResponse.success(filterPath);
    }

    /**
     * 上传zip文件
     * @param zipFilter
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("uplaodZipFilter")
    @ResponseBody
    public ServerResponse uplaodZipFilter(MultipartFile zipFilter, HttpServletRequest request) throws Exception {
        String realPath = request.getServletContext( ).getRealPath("/uploadExcel");
        String originalFilename = zipFilter.getOriginalFilename( );
        String zhui = originalFilename.substring(originalFilename.lastIndexOf("."));
        if (!zhui.equals(".zip")) {
            return ServerResponse.error();
        }
        String newName = new SimpleDateFormat("yyyyMMdd").format(new Date( )) + RandomStringUtils.randomNumeric(6);
        File file = new File(realPath + "/" + newName + zhui);
        zipFilter.transferTo(file);

       List<Map<String, Object>> maps = FilterUtil.unZip(file,realPath);
        //增加到数据库

        return ServerResponse.success();
    }

    /**
     * 上传excel文件
     * @param
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("uplaodExcelFilter")
    public ServerResponse uplaodExcelFilter(MultipartFile excelFilter, HttpServletRequest request) throws Exception {
        String realPath = request.getServletContext( ).getRealPath("/uploadExcel");
        String originalFilename = excelFilter.getOriginalFilename( );
        String zhui = originalFilename.substring(originalFilename.lastIndexOf("."));
        if (!zhui.equals(".xls") && !zhui.equals(".xlsx")) {
            return ServerResponse.error();
        }
        String newName = new SimpleDateFormat("yyyyMMdd").format(new Date( )) + RandomStringUtils.randomNumeric(6);
        File file = new File(realPath + "/" + newName + zhui);
        Workbook workbook = null;
        InputStream in = null;
        try {
            excelFilter.transferTo(file);
            in = new FileInputStream(file);
            if (zhui.equals(".xls")) {
                workbook = new HSSFWorkbook(in);
            } else if (zhui.equals(".xlsx")) {
                workbook = new XSSFWorkbook(in);
            }
        } catch (IOException e) {
            e.printStackTrace( );
        }
        List list=null;
        try {
            list = ExcelFanShen.excelFanshe(realPath+"/"+newName+zhui, Product.class, new String[]{"productName", "title","price","stock"});
        } catch (Exception e1) {
            e1.printStackTrace( );
        }
        //然后 去贴加到数据库中
        return ServerResponse.success();
    }

}
