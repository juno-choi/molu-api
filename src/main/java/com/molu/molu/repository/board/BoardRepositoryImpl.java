package com.molu.molu.repository.board;

import com.molu.molu.common.querydsl.QueryDslConfig;
import com.molu.molu.domain.dto.board.BoardDto;
import com.molu.molu.domain.entity.board.Comment;
import com.molu.molu.domain.entity.board.QComment;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.molu.molu.domain.entity.board.QBoard.board;
import static com.molu.molu.domain.entity.board.QComment.comment1;

@Repository
@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardRepositoryCustom{
    private final QueryDslConfig qd;

    @Override
    public Page<BoardDto> findBoardPage(Pageable pageable) {

        List<BoardDto> content = qd.query()
                .select(Projections.constructor(BoardDto.class,
                        board.boardId,
                        board.title,
                        board.content,
                        board.writer,
                        ExpressionUtils.as(
                                JPAExpressions.select(comment1.board.count())
                                .from(comment1)
                                .where(comment1.board.boardId.eq(board.boardId)),"commentCount"
                        ),
                        board.heart,
                        board.modifiedAt,
                        board.createdAt
                ))
                .from(board)
                .orderBy(board.boardId.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        for(BoardDto dto : content){
            List<Comment> comments = qd.query()
                    .selectFrom(comment1)
                    .limit(5L)
                    .where(comment1.board.boardId.eq(dto.getBoardId()))
                    .fetch();
            dto.setComments(comments);
        }

        Long total = qd.query()
                .from(board)
                .stream().count();
        return new PageImpl<>(content, pageable, total);
    }
}
