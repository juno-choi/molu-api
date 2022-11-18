package com.molu.molu.domain.dto.board;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BoardRequest {
    private String title;
    private String content;
    private String writer;

    public void makeWriter(){
        String writer =
        this.writer = "";
    }
}
