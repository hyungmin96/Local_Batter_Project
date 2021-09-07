package com.project.localbatter.repositories.Exchange;

import com.project.localbatter.entity.ExchangeChatEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExchangeChatRepository extends JpaRepository<ExchangeChatEntity, Long> {
    List<ExchangeChatEntity> findAllByExchangeId(Long exchangeId);
}
