package com.project.localbatter.repositories.Exchange;

import com.project.localbatter.entity.Exchange.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {
}
