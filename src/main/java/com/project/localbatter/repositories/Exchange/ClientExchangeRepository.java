package com.project.localbatter.repositories.Exchange;

import com.project.localbatter.entity.Exchange.ClientExchangeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientExchangeRepository extends JpaRepository<ClientExchangeEntity, Long> {
}
