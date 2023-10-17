package com.example.homework.group.service.impl;

import com.example.homework.Temp;
import com.example.homework.group.service.GroupService;
import com.example.homework.invitation.entity.Invitation;
import com.example.homework.invitation.repository.InvitationRepository;
import com.example.homework.member.entity.Member;
import com.example.homework.member.model.MemberInput;
import com.example.homework.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class GroupServiceImpl implements GroupService {

    private static final int EXPIRE_DAY = 3;
    private final MemberRepository memberRepository;
    private final InvitationRepository invitationRepository;

    @Override
    public List<String> get(Long groupUid) {
        List<String> links = new ArrayList<>();
        for (Invitation invitation : invitationRepository.findByGroupUid(groupUid)) {
            links.add(Temp.DOMAIN + "/groups/" + groupUid + "/join?id=" + invitation.getInvitationAuth());
        }
        return links;
    }

    @Transactional
    @Override
    public boolean invite(MemberInput parameter, Long groupUid) {

        Optional<Member> optionalMember = memberRepository.findByEmail(parameter.getEmail());
        if (optionalMember.isPresent())
        {
            return false;
        }

        // Member 테이블에 행 삽입을 위한 준비
        Member member = Member.builder()
                .email(parameter.getEmail())
                .name(parameter.getName())
                .phone(parameter.getPhone())
                .datetimeAdd(LocalDateTime.now())
                .datetimeMod(LocalDateTime.now())
                .build();

        // Invitation 테이블에 행이 이미 있는 것은 아닌지 확인
        Long senderUid = Temp.SENDER_UID; // TODO: 아직 로그인 기능이 없으므로 임시값을 넣는다
        Long receiverUid = member.getMemberUid();

        Optional<Invitation> optionalInvitation
                = invitationRepository.findBySenderUidAndReceiverUidAndGroupUid(senderUid, receiverUid, groupUid);
        if (optionalInvitation.isPresent())
        {
            return false;
        }

        // Member 테이블에 행 삽입
        Member savedMember = memberRepository.save(member);

        // Invitation 테이블에 행 삽입
        String uuid = UUID.randomUUID().toString();
        Invitation invitation = Invitation.builder()
                .senderUid(senderUid)
                .receiverUid(savedMember.getMemberUid())
                .groupUid(groupUid)
                .invitationAuth(uuid)
                .datetimeAdd(LocalDateTime.now())
                .datetimeExpired(LocalDateTime.now().plusDays(EXPIRE_DAY))
                .build();
        invitationRepository.save(invitation);

        return true;
    }

    @Transactional
    @Override
    public boolean join(String invitationAuth) {
        Optional<Invitation> optionalInvitation = invitationRepository.findByInvitationAuth(invitationAuth);
        if (optionalInvitation.isEmpty())
        {
            return false;
        }

        if (optionalInvitation.get().isInvited())
        {
            return false;
        }

        LocalDateTime datetimeExpired = optionalInvitation.get().getDatetimeExpired();
        if (datetimeExpired.isBefore(LocalDateTime.now()))
        {
            return false;
        }

        Optional<Member> optionalMember = memberRepository.findById(optionalInvitation.get().getReceiverUid());
        if (optionalMember.isEmpty())
        {
            return false;
        }

        // invitation 테이블 갱신
        optionalInvitation.get().setInvited(true);
        optionalInvitation.get().setDatetimeInvited(LocalDateTime.now());
        invitationRepository.save(optionalInvitation.get());

        // member 테이블 갱신
        optionalMember.get().setStatus(true);
        optionalMember.get().setDatetimeMod(LocalDateTime.now());
        memberRepository.save(optionalMember.get());

        return true;
    }
}
