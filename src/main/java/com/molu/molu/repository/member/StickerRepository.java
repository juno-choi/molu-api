package com.molu.molu.repository.member;

import com.molu.molu.domain.entity.member.Member;
import com.molu.molu.domain.entity.member.Sticker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StickerRepository extends JpaRepository<Sticker, Long> {
    List<Sticker> findAllByMember(Member findMember);
}
