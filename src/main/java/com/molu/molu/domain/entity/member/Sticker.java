package com.molu.molu.domain.entity.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Sticker {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "sticker_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private long fromMemberId;
    private String fromMemberName;
    private String reason;
    private int ea;

    @CreatedDate
    private LocalDateTime createdAt;
}
