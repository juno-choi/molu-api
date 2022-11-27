package com.molu.molu.repository.board;

import com.molu.molu.common.querydsl.QueryDslConfig;
import com.molu.molu.domain.dto.board.BoardDto;
import com.molu.molu.domain.dto.board.CommentDto;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

        ArrayList<Long> boardIdList = new ArrayList<>();

        for(BoardDto dto : content){
            boardIdList.add(dto.getBoardId());
        }

        List<CommentDto> findComment = qd.query().select(Projections.constructor(CommentDto.class,
                    comment1.commentId,
                    comment1.memberId,
                    comment1.board.boardId,
                    comment1.comment,
                    comment1.writer,
                    comment1.modifiedAt,
                    comment1.createdAt
                ))
                .from(comment1)
                .where(comment1.board.boardId.in(boardIdList))
                .fetch();

        for(BoardDto dto : content){
            Stream<CommentDto> commentStream = findComment.stream().filter(c -> c.getBoardId() == dto.getBoardId());
            List<CommentDto> comments = commentStream.collect(Collectors.toList());
            Collections.reverse(comments);
            List<CommentDto> newComments = new LinkedList<>();
            if(comments.size() > 5){
                for(int i=0; i<5; i++){
                    newComments.add(comments.get(i));
                }
            }

            dto.setComments(comments);
        }

        Long total = qd.query()
                .from(board)
                .stream().count();
        return new PageImpl<>(content, pageable, total);
    }
}
