package com.example.homework.member.service.impl;

import com.example.homework.member.entity.Member;
import com.example.homework.member.repository.MemberRepository;
import com.example.homework.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

    private static final int LIMIT = 5;
    private final MemberRepository memberRepository;

    @Override
    public List<Member> get() {
        return memberRepository.findAll();
    }
}
