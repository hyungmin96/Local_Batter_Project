package com.project.localbatter.dto.exchangeDTO;

import com.project.localbatter.entity.Exchange.ReviewEntity;
import com.project.localbatter.entity.Exchange.WriterClientJoinEntity;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class ReviewDTO {

    private Long reviewWriterId;
    private Long reviewReceiveId;
    private Long writerClientJoinId;
    private String content;
    private float score;

    public ReviewEntity toEntity(WriterClientJoinEntity writerClientJoinEntity){
        return ReviewEntity.builder()
                .reviewWriterId(reviewWriterId)
                .reviewReceiveId(reviewReceiveId)
                .score(score)
                .content(content)
                .writerClientJoinEntity(writerClientJoinEntity)
                .build();
    }
}
