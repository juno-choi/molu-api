package com.molu.molu.domain.entity.board;

import com.molu.molu.common.Naming;
import com.molu.molu.domain.dto.board.BoardRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = true)
    private Long memberId;
    @Column(nullable = false, length = 30)
    private String title;
    @Column(nullable = false, length = 500)
    private String content;
    @Column(nullable = false)
    private String writer;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "id")
    private List<Comment> comments = new ArrayList<>();

    @Column(nullable = true, columnDefinition = "bigint default 0")
    private long heart;

    @LastModifiedDate
    private LocalDateTime modifiedAt;

    @CreatedDate
    private LocalDateTime createdAt;

    public void createBoard(BoardRequest request){
        this.title = request.getTitle();
        this.content = request.getContent();
    }

    public void makeWriter(){
        this.writer = Naming.getName();
    }
}
