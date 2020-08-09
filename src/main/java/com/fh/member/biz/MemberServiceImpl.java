package com.fh.member.biz;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.common.*;
import com.fh.member.mapper.MemberMapper;
import com.fh.member.po.Member;
import com.fh.mq.MqSendMailMessage;
import com.fh.utils.DateUtil;
import com.fh.utils.KeyUtils;
import com.fh.utils.RASUtils;
import com.fh.utils.RedisUtils;
import com.fh.vo.MailMessageVo;
import com.fh.vo.MemberVo;
import com.sun.org.apache.bcel.internal.generic.NEW;
import net.bytebuddy.asm.Advice;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;


@Service("memberService")
@Transactional(rollbackFor = Exception.class)
public class MemberServiceImpl implements IMemberService {

    @Resource
    private MemberMapper memberMapper;
    @Resource
    private MailUtils mailUtils;

    @Autowired
    private MqSendMailMessage mqSendMailMessage;

    @Override
    public ServerResponse addMember(Member member) {
        // 使用ras 私钥进行解密
        String memberNameRas = RASUtils.decrptCode(member.getMemberName( ));
        String passwordRas = RASUtils.decrptCode(member.getPassword());
        member.setMemberName(memberNameRas);
        member.setPassword(passwordRas);
        //进行非空验证
        if (StringUtils.isEmpty(member.getMemberName( )) || StringUtils.isEmpty(member.getPassword( ))
                || StringUtils.isEmpty(member.getMail( )) || StringUtils.isEmpty(member.getPhone( ))
        ) {
            return ServerResponse.error(ResponseEnum.REG_MEMBE_ISNULL);
        }
        // 验证是否存在
        Member memberName = (Member) memberMapper.selectOne(new QueryWrapper<Member>( ).eq("memberName", member.getMemberName( )));
        if (memberName != null) {
            return ServerResponse.error(ResponseEnum.REG_MEMBERNAME_ISEXITS);
        }
        Member phone = (Member) memberMapper.selectOne(new QueryWrapper<Member>( ).eq("phone", member.getPhone( )));
        if (phone != null) {
            return ServerResponse.error(ResponseEnum.REG_PHONE_ISEXITS);
        }
        Member mail = (Member) memberMapper.selectOne(new QueryWrapper<Member>( ).eq("mail", member.getMail( )));
        if (mail != null) {
            return ServerResponse.error(ResponseEnum.REG_EMAIL_ISEXITS);
        }
        member.setPassword(MD5Util.generate(member.getPassword( )));
        memberMapper.addMember(member);
        try {
            mailUtils.sendMail(member.getMail( ), "今天是6月28", "恭喜你注册成功");
        } catch (Exception e) {
            e.printStackTrace( );
        }
        return ServerResponse.success( );
    }

    /**
     * 验证会员名是否存在
     *
     * @param memberName
     * @return
     */
    @Override
    public ServerResponse vaildateMemberName(String memberName) {
        Member memberName1 = memberMapper.selectOne(new QueryWrapper<Member>( ).eq("memberName", memberName));
        if (memberName1 != null) {
            return ServerResponse.error(ResponseEnum.REG_MEMBERNAME_ISEXITS);
        }
        return ServerResponse.success(memberName1);
    }

    @Override
    public ServerResponse vaildatePhone(String phone) {
        Member vaildatePhone = memberMapper.selectOne(new QueryWrapper<Member>( ).eq("phone", phone));
        if (vaildatePhone != null) {
            return ServerResponse.error(ResponseEnum.REG_PHONE_ISEXITS);
        }
        return ServerResponse.success(vaildatePhone);
    }

    @Override
    public ServerResponse vaildateMail(String mail) {
        Member vaildateMail = memberMapper.selectOne(new QueryWrapper<Member>( ).eq("mail", mail));
        if (vaildateMail != null) {
            return ServerResponse.error(ResponseEnum.REG_EMAIL_ISEXITS);
        }
        return ServerResponse.success(vaildateMail);
    }

    /**
     * token 登录 逻辑
     *
     * @param memberName
     * @param password
     * @return
     */
    @Override
    public ServerResponse login(String memberName, String password) {
        //对ras 进行解密
         memberName = RASUtils.decrptCode(memberName);
         password = RASUtils.decrptCode(password);
        // ==========验证信息=========================
        if (StringUtils.isEmpty(memberName) || StringUtils.isEmpty(password)) {
            return ServerResponse.error(ResponseEnum.LOGIN_INFO_ISNULL);
        }
        Member member = memberMapper.selectOne(new QueryWrapper<Member>( ).eq("memberName", memberName));
        if (member == null) {
            return ServerResponse.error(ResponseEnum.REG_MEMBERNAME_ISEXITS);
        }
        if (!password.equals(member.getPassword( ))) {
            return ServerResponse.error(ResponseEnum.LOGIN_INFO_PASSWORD);
        }
        //将信息直接返回给客户端
        MemberVo memberVo = new MemberVo( );
        String uuid = UUID.randomUUID( ).toString( );
        long id = member.getId( );
        memberVo.setId(id);
        memberVo.setMemberName(member.getMemberName( ));
        memberVo.setRealName(member.getRealName( ));
        memberVo.setUuid(uuid);
        //将数据装换为 json数据
        String memberJson = JSONObject.toJSONString(memberVo);
        //使用base64 进行编码 增加安全性
        String base64Json = Base64.getEncoder( ).encodeToString(memberJson.getBytes( ));
        String sing = Md5Utils.sing(base64Json, Const.SECRET);
        //也对 签名 进行了 base64 可使用 不使用也没问题
        String base64Sing = Base64.getEncoder( ).encodeToString(sing.getBytes( ));
        //将 数据+ 签名 返回给前端
        String result = base64Json + "." + base64Sing;
        // 存入到 redis 中  为什么要存入redis  主要是为了 做过期时间的判断
        RedisUtils.setEx(KeyUtils.memberKey(id, uuid), "1", KeyUtils.LOG_EXPIRE);

        //登录成功后立即发送邮件
        String mail = member.getMail( );
        MailMessageVo mailMessageVo = new MailMessageVo( );
        mailMessageVo.setLoginTime(DateUtil.time(new Date( )));
        mailMessageVo.setRealName(memberVo.getRealName( ));
        mailMessageVo.setMailTo(mail);
        mailMessageVo.setUuid(UUID.randomUUID( ).toString( ));
        mqSendMailMessage.sendMailMessage(mailMessageVo);

        return ServerResponse.success(result);
    }

    @Override
    public ServerResponse updateUserPassword(Member member, String newPassword, String confirmPassword) {
        Member memberName = this.memberMapper.selectOne(new QueryWrapper<Member>( ).eq("memberName", member.getMemberName( )));
        if (StringUtils.isEmpty(memberName.getMemberName( )) || StringUtils.isEmpty(newPassword) || StringUtils.isEmpty(confirmPassword)) {
            return ServerResponse.error(ResponseEnum.LOGIN_INFO_ISNULL);
        }
        if (memberName == null) {
            return ServerResponse.error(ResponseEnum.REG_MEMBERNAME_ISEXITS);
        }
        if (!memberName.getPassword( ).equals(member.getPassword( ))) {
            return ServerResponse.error(ResponseEnum.LOGIN_INFO_PASSWORD);
        }
        if (!newPassword.equals(confirmPassword)) {
            return ServerResponse.error(ResponseEnum.UPDATE_USER_INFO_CHECKPASSWORD);
        }
        memberName.setPassword(newPassword);
        this.memberMapper.updateById(memberName);
        return ServerResponse.success( );
    }


}
