package com.project.localbatter.entity.Exchange;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.localbatter.entity.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

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

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private status status;

    @Enumerated(EnumType.STRING)
    @Column(name = "writer_status")
    private status writerStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "client_status")
    private status clientStatus;

    public enum status{ reject, wait, process, complete }

    public void exitChatRoom(Long userId){
        if(writerId != null && writerId.equals(userId)) this.writerId = null; else this.clientId = null;
    }
    /* 사용자의 교환 확정을 업데이트 하는 로직 */
    public void confirmExchangeUpdate(Long userId){
        if(userId.equals(this.writerId)) writerStatus = complete; else clientStatus = complete;
    }
    /* 교환상태를 업데이트 하는 로직 */
    public void setReject(){ this.status = reject; }

    public void setWait(){ this.status = wait; }

    public void setProcess(){ this.status = process; }

    public void setComplete(){ this.status = complete; }
}
