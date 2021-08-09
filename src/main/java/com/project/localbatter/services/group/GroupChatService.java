package com.project.localbatter.services.group;

import com.project.localbatter.components.GenerateFile;
import com.project.localbatter.components.PagingUtil;
import com.project.localbatter.dto.GenerateFileDTO;
import com.project.localbatter.dto.Group.GroupChatMessageDTO;
import com.project.localbatter.entity.GroupEntity;
import com.project.localbatter.repositories.GroupChatRepository;
import com.project.localbatter.repositories.GroupRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import static com.project.localbatter.entity.QGroupChatEntity.groupChatEntity;
import static com.project.localbatter.entity.QUserEntity.userEntity;

@Service
@RequiredArgsConstructor
public class GroupChatService {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final GenerateFile generateFile;
    private final GroupChatRepository groupChatRepository;
    private final GroupRepository groupRepository;
    private final JPAQueryFactory queryFactory;
    private final PagingUtil pagingUtil;

    @Transactional(readOnly = true)
    public Page<GroupChatMessageDTO> getChatList(GroupChatMessageDTO messageDTO, Pageable page){

        JPAQuery<GroupChatMessageDTO> query = queryFactory
                .select(Projections.fields(GroupChatMessageDTO.class,
                        groupChatEntity.user.as("userId"),
                        userEntity.username.as("username"),
                        userEntity.profilePath.as("profile"),
                        groupChatEntity.regTime.as("regTime"),
                        groupChatEntity.type.as("type"),
                        groupChatEntity.message.as("message")))
                .from(groupChatEntity)
                .leftJoin(userEntity).on(groupChatEntity.user.eq(userEntity.id)).fetchJoin()
                .orderBy(groupChatEntity.id.desc())
                .where(groupChatEntity.groupEntity.id.eq(messageDTO.getGroupId()));

        return pagingUtil.getPageImpl(page, query, GroupChatMessageDTO.class);
    }

    public void sendGroupRoomToChat(GroupChatMessageDTO messageDTO){
        GroupEntity groupEntity = groupRepository.getOne(messageDTO.getGroupId());
        groupChatRepository.save(messageDTO.toEntity(groupEntity));
        simpMessagingTemplate.convertAndSend("/group/chat/" + messageDTO.getGroupId(), messageDTO);
    }

    public GroupChatMessageDTO sendImageToGroupChat(GroupChatMessageDTO messageDTO){
        GroupEntity groupEntity = groupRepository.getOne(messageDTO.getGroupId());
        for(GenerateFileDTO img : generateFile.createFile(messageDTO.getImg())){
            messageDTO.setMessage(img.getName());
            messageDTO.setImg(null);
            groupChatRepository.save(messageDTO.toEntity(groupEntity));
            simpMessagingTemplate.convertAndSend("/group/chat/" + messageDTO.getGroupId(), messageDTO);
        }
        return messageDTO;
    }

}
