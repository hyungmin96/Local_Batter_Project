package com.project.localbatter.entity.Exchange;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.localbatter.entity.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Table(name = "tbl_client_exchange")
public class ClientExchangeEntity extends BaseTimeEntity implements Serializable {

    @Column(name = "client_id")
    @Id @GeneratedValue
    private Long id;

    @Column(name = "client_user_id")
    private Long userId;

    @Column(name = "client_title")
    private String title;

    @Column(name = "client_cotent")
    private String content;

    @Column(name = "client_price")
    private String price;

    @Column(name = "client_request")
    private String request;

    @Column(name = "client_addr")
    private String address;

    @Column(name = "client_longtitude")
    private String longtitude;

    @Column(name = "client_latitude")
    private String latitude;

    @Column(name = "board_Id")
    private Long boardId;

    @Column(name = "client_thumbnail")
    private String thumbnail;

    @OneToMany(mappedBy = "client", fetch = LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ExchangeFileEntity> files;

    @OneToMany(mappedBy = "clientExchangeEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<WriterClientJoinEntity> writerClientJoinEntity;

}
