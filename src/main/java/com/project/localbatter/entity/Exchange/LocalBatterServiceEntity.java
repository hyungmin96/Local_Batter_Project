package com.project.localbatter.entity.Exchange;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class LocalBatterServiceEntity {

    @Id
    @GeneratedValue
    @Column(name = "service_id")
    private Long id;

    @Column(name = "receive_longitude")
    private String receiveLongitude;

    @Column(name = "receive_latitude")
    private String receiveLatitude;

    @Column(name = "exchange_longitude")
    private String exchangeLongitude;

    @Column(name = "exchange_latitude")
    private String exchangeLatitude;

    @Column(name = "writer_client_join_id")
    private Long writerClientJoinId;

    @Column(name = "request_content")
    private String request;

    @Column(name = "price")
    private String price;

}
