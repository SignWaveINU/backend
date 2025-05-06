package com.signwave.signwave.repository;

import com.signwave.signwave.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String mail);
    Optional<Member> findByNickname(String nickname);
}
