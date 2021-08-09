package com.project.localbatter.repositories.Exchange;

import com.project.localbatter.entity.Exchange.ExchangeFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientExchangeFileRepository extends JpaRepository<ExchangeFileEntity, Long> {
}
