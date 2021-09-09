package com.project.localbatter.entity.Exchange;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "tbl_exchange_review")
public class ReviewEntity {

    @Id @GeneratedValue
    @Column(name = "review_id")
    private Long reviewId;

    @Column(name = "review_writer_id")
    private Long reviewWriterId;

    @Column(name = "review_receive_id")
    private Long reviewReceiveId;

    @Column(name = "review_content")
    private String content;

    @Column(name = "review_score")
    private float score;

    @OneToOne
    @JoinColumn(name = "writer_client_join_id")
    private WriterClientJoinEntity writerClientJoinEntity;

}
