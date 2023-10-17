package com.example.homework.group.service;

import com.example.homework.member.model.MemberInput;

import java.util.List;

public interface GroupService {
    List<String> get(Long groupUid);
    boolean invite(MemberInput parameter, Long groupUid);
    boolean join(String invitationAuth);
}
