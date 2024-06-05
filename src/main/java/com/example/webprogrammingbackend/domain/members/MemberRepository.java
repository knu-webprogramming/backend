package com.example.webprogrammingbackend.domain.members;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface MemberRepository extends JpaRepository<Member, UUID> {
    Optional<Member> findMemberByAttributeIdAndAuth(String attributeId, AuthType auth);
}
