package com.project.localbatter.repositories.Exchange;

import com.project.localbatter.entity.Exchange.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {

    @Query(value = "select avg(r.score) from ReviewEntity r where r.reviewReceiveId = :userId")
    double getReviewScore(Long userId);

}
