package com.molu.molu.domain.entity.board;

import com.molu.molu.common.utils.Naming;
import com.molu.molu.domain.dto.board.PostBoard;
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
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    private String title;
    @Column(nullable = false, length = 3000)
    private String content;
    @Column(nullable = false)
    private String writer;

    @OneToMany
    private List<Comment> comments = new LinkedList<>();

    @Column(nullable = true, columnDefinition = "bigint default 0")
    private long heart;

    @LastModifiedDate
    private LocalDateTime modifiedAt;

    @CreatedDate
    private LocalDateTime createdAt;

    public static Board createBoard(PostBoard request){
        return new Board(null, request.getTitle(), request.getContent(), Naming.getName(), new ArrayList<>(), 0L, null, null);
    }

    public void addHeart(){
        this.heart++;
    }
}
