package com.project.localbatter.entity.Exchange;

import com.project.localbatter.entity.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Table(name = "tbl_exchange_file")
public class ExchangeFileEntity extends BaseTimeEntity implements Serializable {

    @Column(name = "file_id")
    @Id @GeneratedValue
    private Long id;

    @Column(name = "file_name")
    private String name;

    @Column(name = "file_path")
    private String path;

    @Column(name = "file_size")
    private int size;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "client_id")
    private ClientExchangeEntity client;

}
