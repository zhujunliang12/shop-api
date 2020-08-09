package com.fh.member.biz;


import com.fh.common.ServerResponse;
import com.fh.member.po.Member;

public interface IMemberService {
    ServerResponse addMember(Member member);

    ServerResponse vaildateMemberName(String memberName);

    ServerResponse vaildatePhone(String phone);

    ServerResponse vaildateMail(String mail);

    ServerResponse login(String memberName, String password);

    ServerResponse updateUserPassword(Member member, String newPassword, String confirmPassword);
}
