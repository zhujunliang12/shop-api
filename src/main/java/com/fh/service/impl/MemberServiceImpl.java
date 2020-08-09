package com.fh.service.impl;

import com.fh.mapper.MemberMapper;
import com.fh.model.DataTableResult;
import com.fh.model.Member;
import com.fh.model.ParemMemberSelect;
import com.fh.service.IMemberService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("memberService")
public class MemberServiceImpl implements IMemberService {

    @Resource
    private MemberMapper memberMapper;

    @Override
    public Member getMemberByMember(String memberName) {
        return memberMapper.getMemberByMember(memberName);
    }

    @Override
    public void addMember(Member member) {
        memberMapper.addMember(member);
    }

    @Override
    public DataTableResult queryMemberPage(ParemMemberSelect paremMemberSelect) {
        List<Member>queryMemberList=memberMapper.queryMemberList(paremMemberSelect);
        Long queryCount=memberMapper.queryCount(paremMemberSelect);
        DataTableResult dataTableResult = new DataTableResult();
        dataTableResult.setData(queryMemberList);
        dataTableResult.setRecordsTotal(queryCount);
        dataTableResult.setRecordsFiltered(queryCount);
        return dataTableResult;
    }

    @Override
    public List<Member> queryMemberList(Member member) {
        List<Member>memberList= memberMapper.memberList(member);
        return memberList;
    }

    @Override
    public List<Member> findAllMemberList(ParemMemberSelect paremMemberSelect) {
        List<Member>findAllMemberList=this.memberMapper.findAllMemberList(paremMemberSelect);
        return findAllMemberList;
    }


}
