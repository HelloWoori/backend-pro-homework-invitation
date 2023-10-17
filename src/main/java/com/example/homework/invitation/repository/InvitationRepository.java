package com.example.homework.invitation.repository;

import com.example.homework.invitation.entity.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InvitationRepository extends JpaRepository<Invitation, Long> {

    Optional<Invitation> findBySenderUidAndReceiverUidAndGroupUid(Long senderUid, Long receiverUid, Long groupUid);
    Optional<Invitation> findByInvitationAuth(String invitationAuth);
    List<Invitation> findByGroupUid(Long groupUid);
}
