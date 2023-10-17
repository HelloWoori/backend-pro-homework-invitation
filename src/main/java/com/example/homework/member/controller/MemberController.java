package com.example.homework.member.controller;

import com.example.homework.member.entity.Member;
import com.example.homework.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members")
    @ResponseBody
    public List<Member> getMembers()
    {
        return memberService.get();
    }
}
