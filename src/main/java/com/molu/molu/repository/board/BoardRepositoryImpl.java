package com.molu.molu.repository.board;

import com.molu.molu.common.querydsl.QueryDslConfig;
import com.molu.molu.domain.dto.board.BoardDto;
import com.molu.molu.domain.entity.board.QComment;
import com.querydsl.core.types.Projections;
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
                        board.comments,
                        board.heart,
                        board.modifiedAt,
                        board.createdAt
                ))
                .from(board)
//                .leftJoin(board.comments, comment1)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = qd.query()
                .from(board)
                .stream().count();
        return new PageImpl<BoardDto>(content, pageable, total);
    }
}
