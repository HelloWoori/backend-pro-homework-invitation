package com.example.homework.group.controller;

import com.example.homework.group.service.GroupService;
import com.example.homework.member.model.MemberInput;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class GroupController {

    private final GroupService groupService;

    @GetMapping("groups/{groupUid}/invitations")
    @ResponseBody
    public List<String> getInvitations(@PathVariable("groupUid") Long groupUid)
    {
        return groupService.get(groupUid);
    }

    @GetMapping("/groups/{groupUid}/invitation")
    @ResponseBody
    public Long createInvitation(@PathVariable("groupUid") Long groupUid) {
        return groupUid;
    }

    @PostMapping("/groups/{groupUid}/invitation")
    @ResponseBody
    public boolean createInvitationSubmit(
            MemberInput parameter,
            @PathVariable("groupUid") Long groupUid) {
        return groupService.invite(parameter, groupUid);
    }

    @GetMapping("/groups/{groupUid}/join")
    @ResponseBody
    public boolean joinGroup(HttpServletRequest request) {
        String invitationAuth = request.getParameter("id");
        return groupService.join(invitationAuth);
    }
}
