package com.molu.molu.domain.entity.board;

import com.molu.molu.common.utils.Naming;
import com.molu.molu.domain.dto.board.PostBoard;
import com.querydsl.core.annotations.QueryProjection;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;

    @Column(nullable = false, length = 100)
    private String title;
    @Column(nullable = false, length = 3000)
    private String content;
    @Column(nullable = false)
    private String writer;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "commentId")
    private List<Comment> comments = new LinkedList<>();

    @Column(nullable = true, columnDefinition = "bigint default 0")
    private long heart;

    @LastModifiedDate
    private LocalDateTime modifiedAt;

    @CreatedDate
    private LocalDateTime createdAt;

    public static Board of(PostBoard request){
        return new Board(null, request.getTitle(), request.getContent(), Naming.getName(), new ArrayList<>(), 0L, null, null);
    }

    public void addHeart(){
        this.heart++;
    }
}
