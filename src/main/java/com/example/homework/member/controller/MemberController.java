package com.example.homework.member.controller;

import com.example.homework.member.entity.Member;
import com.example.homework.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members")
    public String getMembers(Model model, HttpServletRequest request)
    {
        List<Member> members = memberService.get();
        model.addAttribute("members", members);

        return "member/get_members";
    }
}
