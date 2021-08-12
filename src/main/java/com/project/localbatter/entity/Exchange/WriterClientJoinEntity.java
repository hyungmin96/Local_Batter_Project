package com.project.localbatter.entity.Exchange;

import com.project.localbatter.entity.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "tbl_writer_client_join")
public class WriterClientJoinEntity extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "writer_id")
    private Long writerId;

    @Column(name = "client_id")
    private Long clientId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_exchange_id")
    private WriterExchangeEntity writerExchangeEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_exchange_id")
    private ClientExchangeEntity clientExchangeEntity;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private status status;

    public enum status{
        reject, wait, process, complete
    }

    public void updateStatus(status status){
        this.status = status;
    }

}
