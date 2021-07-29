package com.project.localbatter.entity;

import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass // 상속받는 Entity는 아래 variables를 column으로 인식한다.
@EntityListeners(AuditingEntityListener.class) // Auditing(자동 값 매핑)
public abstract class BaseTimeEntity {

    @CreationTimestamp
    private LocalDateTime regTime;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

}
