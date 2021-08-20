package com.project.localbatter.repositories.Exchange;

import com.project.localbatter.entity.ExchangeChatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExchangeChatRepository extends JpaRepository<ExchangeChatEntity, Long> {
}
