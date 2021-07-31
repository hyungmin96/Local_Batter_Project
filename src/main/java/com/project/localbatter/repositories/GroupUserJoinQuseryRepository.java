package com.project.localbatter.repositories;

import com.project.localbatter.entity.GroupUserJoinEntity;
import com.project.localbatter.entity.QGroupUserJoinEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class GroupUserJoinQuseryRepository {

    private final JPAQueryFactory queryFactory;

    public GroupUserJoinEntity findGroupUserJoinEntity(Long userId, Long groupId){
        return queryFactory
            .selectFrom(QGroupUserJoinEntity.groupUserJoinEntity)
            .where(QGroupUserJoinEntity.groupUserJoinEntity.user.id.eq(userId)
                    .and(QGroupUserJoinEntity.groupUserJoinEntity.group.id.eq(groupId)))
            .fetchOne();
    }

}
