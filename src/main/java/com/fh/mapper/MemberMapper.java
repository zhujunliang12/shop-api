package com.fh.mapper;

import com.fh.model.Member;
import com.fh.model.ParemMemberSelect;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface MemberMapper {
    @Select("select * from mall_member where memberName=#{memberName}")
    Member getMemberByMember(String memberName);
    @Insert("insert into mall_member(memberName,password,realName,phone,mail) values(#{memberName},#{password},#{realName},#{phone},#{mail})")
    void addMember(Member member);

    List<Member> queryMemberList(ParemMemberSelect paremMemberSelect);

    Long queryCount(ParemMemberSelect paremMemberSelect);

    @Select("select * from mall_member")
    List<Member> memberList(Member member);

    List<Member> findAllMemberList(ParemMemberSelect paremMemberSelect);
}
