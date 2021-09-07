package com.project.localbatter.dto.exchangeDTO;

import com.project.localbatter.entity.Exchange.LocalBatterServiceEntity;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class LocalBatterServiceDTO {

    private Long userId; // Id of user who register service
    private Long writerClientJoinId; // writer and client exchange entity's id
    private String receiveAddr;
    private String receiveDetailAddr;
    private String receiveLongitude;
    private String receiveLatitude;
    private String exchangeAddr;
    private String exchangeDetailAddr;
    private String exchangeLongitude;
    private String exchangeLatitude;
    private String request;
    private String price;
    private String time;

    public LocalBatterServiceEntity toEntity(){
        return LocalBatterServiceEntity
                .builder()
                .userId(userId)
                .writerClientJoinId(writerClientJoinId)
                .receiveAddr(receiveAddr)
                .receiveDetailAddr(receiveDetailAddr)
                .receiveLatitude(receiveLatitude)
                .receiveLongitude(receiveLongitude)
                .exchangeAddr(exchangeAddr)
                .exchangeDetailAddr(exchangeDetailAddr)
                .exchangeLongitude(exchangeLongitude)
                .exchangeLatitude(exchangeLatitude)
                .request(request)
                .price(price)
                .time(time)
                .build();
    }
}
