package com.imageupload.example.services;

import com.imageupload.example.components.DeleteFile;
import com.imageupload.example.components.GenerateFile;
import com.imageupload.example.dto.GroupChatMessageDTO;
import com.imageupload.example.dto.GroupDTO;
import com.imageupload.example.dto.GenerateFileDTO;
import com.imageupload.example.entity.*;
import com.imageupload.example.entity.GroupChatEntity;
import com.imageupload.example.enumtype.GroupChatRoomEnterEnumType;
import com.imageupload.example.enumtype.GroupUsersEnumType;
import com.imageupload.example.repositories.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupService {
    
    private final GroupChatRepository GroupChatRepository;
    private final GroupFileRepository GroupFileRepository;
    private final GroupChatRoomRepository groupChatRoomRepository;
    private final GroupUsersRepository GroupUsersRepository;
    private final UserRepository userRepository;

    public GroupUsersEntity findUserEntity(UserEntity userEntity){
        return GroupUsersRepository.findByuser(userEntity);
    }

    public GroupChatRoomEntity findRoom(Long room_id){
        return groupChatRoomRepository.findById(room_id).get();
    }

    public void uploadImgToGroupChatroom(GroupChatMessageDTO messageDTO){

        List<GenerateFileDTO> files = new GenerateFile(messageDTO.getImg()).createFile();
        List<String> imgs = new ArrayList<>();
        GroupChatRoomEntity GroupChatRoomEntity = groupChatRoomRepository.findById(messageDTO.getRoomId()).get();

        for(GenerateFileDTO file : files){

            GroupChatEntity GroupChatEntity = com.imageupload.example.entity.GroupChatEntity.builder()
                    .roomId(GroupChatRoomEntity)
                    .sender(messageDTO.getSender())
                    .message(file.getFileName())
                    .profilePath(messageDTO.getProfilePath())
                    .type("image")
                    .userId(messageDTO.getUserId())
                    .build();

            imgs.add(file.getFileName());

            GroupChatRepository.save(GroupChatEntity);
        }
            messageDTO.setImgPath(imgs);
    }

    public void deleteRoom(HttpSession session, Long roomId, String username){

        UserEntity userEntity = (UserEntity) session.getAttribute("userId");

        if(userEntity.getUsername().equals(username)){
            GroupChatRoomEntity GroupChatRoomEntity = groupChatRoomRepository.findById(roomId).get();

            GroupChatRoomEntity.getChats().stream()
                    .filter(item -> item.getType().equals("image")).forEach(item -> {
                new DeleteFile(new String[] {item.getMessage()}).deleteFile();
            });

            groupChatRoomRepository.delete(GroupChatRoomEntity);

            List<String> files = new ArrayList<>();
            GroupChatRoomEntity.getFiles().stream().map(value -> files.add(value.getName())).toArray();
            new DeleteFile(files.toArray(new String[files.size()])).deleteFile();
        }
    }

    public GroupChatRoomEntity getRoomInfo(Long id){
        return groupChatRoomRepository.findById(id).get();
    }

    public void saveChatEntity(GroupChatMessageDTO messageDTO){

        GroupChatRoomEntity GroupChatRoomEntity = groupChatRoomRepository.findById(messageDTO.getRoomId()).get();

        GroupChatEntity GroupChatEntity = com.imageupload.example.entity.GroupChatEntity.builder()
                .roomId(GroupChatRoomEntity)
                .userId(messageDTO.getUserId())
                .type(messageDTO.getType())
                .profilePath(messageDTO.getProfilePath())
                .message(messageDTO.getMessage())
                .sender(messageDTO.getSender()).build();

        GroupChatRepository.save(GroupChatEntity);
    }

    public void exitRoom(HttpSession session, Long roomId){

        UserEntity userEntity = (UserEntity) session.getAttribute("userId");

        GroupChatRoomEntity GroupChatRoomEntity = groupChatRoomRepository.findById(roomId).get();
        GroupUsersEntity GroupUsersEntity = GroupUsersRepository.findByGroupChatRoomEntityAndUser(GroupChatRoomEntity, userEntity);

        groupChatRoomRepository.exitCurrentUsers(GroupChatRoomEntity.getId());
        GroupUsersRepository.delete(GroupUsersEntity);
    }

    public GroupChatRoomEnterEnumType GroupChatRoomEnter(Long roomId, String username){

        GroupChatRoomEntity groupChatRoomEntity = groupChatRoomRepository.findById(roomId).get();
        UserEntity userEntity = userRepository.findByUsername(username).get();
        GroupUsersEntity GroupUsersEntity = GroupUsersRepository.findByGroupChatRoomEntityAndUser(groupChatRoomEntity, userEntity);

        if(groupChatRoomEntity.getUsers().contains(GroupUsersEntity)) {
            return GroupChatRoomEnterEnumType.enter;
        }else if(groupChatRoomEntity.getLimitUsers() > groupChatRoomEntity.getCurrentUsers()){
            GroupUsersEnumType authorizationType;

            if(username.equals(groupChatRoomEntity.getOwner()))
                authorizationType = GroupUsersEnumType.manager;
            else
                authorizationType = GroupUsersEnumType.member;

            GroupUsersEntity groupUsersEntity = com.imageupload.example.entity.GroupUsersEntity.builder()
                    .user(userEntity)
                    .groupChatRoomEntity(groupChatRoomEntity)
                    .authorization(authorizationType)
                    .build();

            groupChatRoomRepository.enterCurrentUsers(groupChatRoomEntity.getId());
            GroupUsersRepository.save(groupUsersEntity);

            return GroupChatRoomEnterEnumType.greeting;
        }else{
            return GroupChatRoomEnterEnumType.failed;
        }
    }

    public Page<GroupChatRoomEntity> getGroupRooms(PageRequest request){
        return groupChatRoomRepository.findAll(request);
    }

    @Transactional
    public void createGroupRoom(HttpSession session, GroupDTO GroupDTO){

        UserEntity userEntity = (UserEntity) session.getAttribute("userId");

        GroupChatRoomEntity GroupChatRoomEntity = com.imageupload.example.entity.GroupChatRoomEntity.builder()
        .description(GroupDTO.getDescription())
        .owner(userEntity.getUsername())
        .type(GroupDTO.getType())
        .roomTitle(GroupDTO.getRoomTitle())
        .currentUsers(1)
        .limitUsers(Integer.parseInt(GroupDTO.getLimit()))
        .build();


        if (GroupDTO.getFiles() != null && GroupDTO.getFiles().length > 0) {
            GenerateFile generateGroupFiles = new GenerateFile(GroupDTO.getFiles());

            List<GenerateFileDTO> files = generateGroupFiles.createFile();

            for(GenerateFileDTO file : files){
                GroupFileEntity fileEntity = GroupFileEntity.builder()
                        .name(file.getFileName())
                        .path(file.getPath())
                        .groupChatRoomEntity(GroupChatRoomEntity)
                        .build();

                GroupFileRepository.save(fileEntity);
            }
        }

        GroupUsersEntity GroupUsersEntity = com.imageupload.example.entity.GroupUsersEntity.builder()
        .user(userEntity)
        .groupChatRoomEntity(GroupChatRoomEntity)
        .authorization(GroupUsersEnumType.manager)
        .build();

        GroupUsersRepository.save(GroupUsersEntity);
        groupChatRoomRepository.save(GroupChatRoomEntity);
    }
}
