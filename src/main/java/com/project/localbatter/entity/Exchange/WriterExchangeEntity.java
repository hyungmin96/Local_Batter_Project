package com.project.localbatter.entity.Exchange;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.localbatter.dto.Group.GroupBoardDTO;
import com.project.localbatter.entity.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "tbl_group_exchange")
public class WriterExchangeEntity extends BaseTimeEntity {

    @Column(name = "writer_id")
    @Id @GeneratedValue
    private Long id;

    @Column(name = "writer_user_id")
    private Long userId;

    @Column(name = "writer_price")
    private String price;

    @Column(name = "writer_exchange_on")
    @Enumerated(EnumType.STRING)
    private ExchageOnOff exchangeOn;

    @Column(name = "writer_longtitude")
    private String longitude;

    @Column(name = "writer_latitude")
    private String latitude;

    @Column(name = "writer_location") // 거래지역
    private String location;

    @Column(name = "writer_locationDetail") // 거래지역 세부주소
    private String locationDetail;

    @Column(name = "writer_prefer_time")
    private String preferTime;

    @Column(name = "request_count")
    private int requestCount = 0;

    @Column(name = "exchange_status")
    @Enumerated(EnumType.STRING)
    private exchangeStatus status;

    @OneToMany(mappedBy = "writerExchangeEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<WriterClientJoinEntity> writerClientJoinEntity;

    public void exchangeComplete(){ this.status = exchangeStatus.complete; }

    public void updateCount(int value){
        this.requestCount += value;
    }

    public void update(GroupBoardDTO groupBoardDTO){
        this.price = groupBoardDTO.getPrice();
        this.exchangeOn = groupBoardDTO.getExchangeOn();
        this.longitude = groupBoardDTO.getLongitude();
        this.latitude = groupBoardDTO.getLatitude();
        this.location = groupBoardDTO.getLocation();
        this.locationDetail = groupBoardDTO.getLocationDetail();
        this.preferTime = groupBoardDTO.getPreferTime();
    }

    public enum exchangeStatus{ wait, complete }

    public enum ExchageOnOff{ on, off }

}
