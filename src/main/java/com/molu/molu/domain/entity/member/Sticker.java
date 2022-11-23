package com.molu.molu.domain.entity.member;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Sticker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sticker_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private Long fromMemberId;
    private String fromMemberName;
    private String reason;
    private int ea;

    @CreatedDate
    private LocalDateTime createdAt;

    public static Sticker createSticker(Member toMember, Member fromMember, String reason, int ea){
        return new Sticker(null, toMember, fromMember.getMemberId(), fromMember.getName(), reason, ea, null);
    }
}
