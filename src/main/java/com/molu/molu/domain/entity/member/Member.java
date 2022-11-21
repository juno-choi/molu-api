package com.molu.molu.domain.entity.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long memberId;
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "id")
    List<Sticker> stickers = new ArrayList<>();

}
