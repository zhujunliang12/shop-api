package com.fh.member.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.member.po.Member;
import org.apache.ibatis.annotations.Insert;

public interface MemberMapper extends BaseMapper<Member> {

    @Insert("insert into mall_member(memberName,password,realName,phone,sex,mail,areaName,cityId,provinceId,contryId) values(#{memberName}," +
            "#{password},#{realName},#{phone},#{sex},#{mail},#{areaName},#{cityId},#{provinceId},#{contryId})")
    void addMember(Member member);


}
