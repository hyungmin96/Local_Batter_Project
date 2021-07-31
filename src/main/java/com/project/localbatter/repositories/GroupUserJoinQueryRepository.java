package com.project.localbatter.repositories;

import com.project.localbatter.entity.GroupUserJoinEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.project.localbatter.entity.QGroupUserJoinEntity.groupUserJoinEntity;

@Repository
@RequiredArgsConstructor
public class GroupUserJoinQueryRepository {

    private final JPAQueryFactory queryFactory;

    public GroupUserJoinEntity findGroupUserJoinEntity(Long userId, Long groupId){
        return queryFactory
            .selectFrom(groupUserJoinEntity)
            .where(groupUserJoinEntity.user.id.eq(userId)
                    .and(groupUserJoinEntity.group.id.eq(groupId)))
            .fetchOne();
    }

    public List<GroupUserJoinEntity> findAllUsers(Long groupId){
        return queryFactory
                .selectFrom(groupUserJoinEntity)
                .join(groupUserJoinEntity.user)
                .fetchJoin()
                .where(groupUserJoinEntity.group.id.eq(groupId))
                .fetch();
    }

}
