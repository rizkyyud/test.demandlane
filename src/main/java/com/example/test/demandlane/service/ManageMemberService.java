package com.example.test.demandlane.service;

import com.example.test.demandlane.model.dto.request.RequestMember;
import com.example.test.demandlane.model.entity.Member;

import java.util.List;

public interface ManageMemberService {
    public List<Member> getAllMember();
    public Member getMemberById(Long id);
    public Member addMember(RequestMember member);
    public Member updateMember(Long id, RequestMember member);
    public void deleteMember(Long id);
}
