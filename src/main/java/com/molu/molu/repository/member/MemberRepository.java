package com.molu.molu.repository.member;

import com.molu.molu.domain.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
