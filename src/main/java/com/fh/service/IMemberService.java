package com.fh.service;

import com.fh.model.DataTableResult;
import com.fh.model.Member;
import com.fh.model.ParemMemberSelect;

import java.util.List;

public interface IMemberService {
    Member getMemberByMember(String memberName);

    void addMember(Member member);

    DataTableResult queryMemberPage(ParemMemberSelect paremMemberSelect);

    List<Member> queryMemberList(Member member);

    List<Member> findAllMemberList(ParemMemberSelect paremMemberSelect);
}
