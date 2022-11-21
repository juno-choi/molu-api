package com.molu.molu.domain.entity.member;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "id")
    List<Sticker> stickers = new ArrayList<>();

    public static Member createMember(String name){
        return new Member(null, name, new ArrayList<>());
    }

    public void changeSticker(Sticker sticker){
        this.stickers.add(sticker);
    }
}
