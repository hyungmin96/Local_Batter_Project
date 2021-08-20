package com.project.localbatter.entity.Exchange;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.localbatter.entity.BaseTimeEntity;
import com.project.localbatter.entity.ExchangeChatEntity;
import lombok.*;

import javax.persistence.*;
import java.util.List;

import static com.project.localbatter.entity.Exchange.WriterClientJoinEntity.status.*;

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
    @JsonIgnore
    private WriterExchangeEntity writerExchangeEntity;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "client_exchange_id")
    @JsonIgnore
    private ClientExchangeEntity clientExchangeEntity;

    @OneToMany(mappedBy = "wrtierClientJoinId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ExchangeChatEntity> chat;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private status status;

    public enum status{
        reject, wait, process, complete
    }

    public void setReject(){
        this.status = reject;
    }

    public void setWait(){
        this.status = wait;
    }

    public void setProcess(){
        this.status = process;
    }

    public void setComplete(){
        this.status = complete;
    }

}
