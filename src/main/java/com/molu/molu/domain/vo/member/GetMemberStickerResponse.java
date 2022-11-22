package com.molu.molu.domain.vo.member;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GetMemberStickerResponse {
    private Long memberId;
    private String name;
    private int totalStickerEa;
    private List<StickerVo> sticker;
}
