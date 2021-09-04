package com.project.localbatter.entity.Exchange;

import com.project.localbatter.entity.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
@Table(name = "tbl_localbatter_service")
public class LocalBatterServiceEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "service_id")
    private Long id;

    @Column(name = "regist_user_id")
    private Long userId;

    @Column(name = "receive_addr")
    private String receiveAddr;

    @Column(name = "receive_detail_addr")
    private String receiveDetailAddr;

    @Column(name = "receive_longitude")
    private String receiveLongitude;

    @Column(name = "receive_latitude")
    private String receiveLatitude;

    @Column(name = "writer_client_join_id")
    private Long writerClientJoinId;

    @Column(name = "exchange_addr")
    private String exchangeAddr;

    @Column(name = "exchange_detail_addr")
    private String exchangeDetailAddr;

    @Column(name = "exchange_longitude")
    private String exchangeLongitude;

    @Column(name = "exchange_latitude")
    private String exchangeLatitude;

    @Column(name = "request_content")
    private String request;

    @Column(name = "price")
    private String price;

}
