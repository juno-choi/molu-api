package com.molu.molu.repository.board;

import com.molu.molu.domain.entity.board.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
