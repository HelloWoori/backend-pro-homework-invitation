package com.example.homework.group.controller;

import com.example.homework.group.service.GroupService;
import com.example.homework.member.model.MemberInput;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class GroupController {

    private final GroupService groupService;

    @GetMapping("groups/{groupUid}/invitations")
    public String getInvitations(Model model, @PathVariable("groupUid") Long groupUid)
    {
        List<String> links = groupService.get(groupUid);
        model.addAttribute("links", links);
        return "group/get_invitations";
    }

    @GetMapping("/groups/{groupUid}/invitation")
    public String createInvitation(Model model, @PathVariable("groupUid") Long groupUid) {
        model.addAttribute("groupUid", groupUid);
        return "group/create_invitation";
    }

    @PostMapping("/groups/{groupUid}/invitation")
    public String createInvitationSubmit(
            Model model,
            MemberInput parameter,
            @PathVariable("groupUid") Long groupUid) {
        boolean result = groupService.invite(parameter, groupUid);
        model.addAttribute("result", result);

        return "group/create_invitation_result";
    }

    @GetMapping("/groups/{groupUid}/join")
    public String joinGroup(Model model, HttpServletRequest request) {
        String invitationAuth = request.getParameter("id");
        boolean result = groupService.join(invitationAuth);
        model.addAttribute("result", result);

        return "/group/join_result";
    }
}
