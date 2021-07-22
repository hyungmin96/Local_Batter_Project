package com.imageupload.example.services;

import com.imageupload.example.components.DeleteFile;
import com.imageupload.example.components.GenerateFile;
import com.imageupload.example.dto.*;
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
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupService {
    
    private final GroupChatRepository groupChatRepository;
    private final GroupFileRepository groupFileRepository;
    private final GroupRepository groupRepository;
    private final GroupUsersRepository groupUsersRepository;
    private final UserRepository userRepository;
    private final HttpSession session;

    public GroupEntity findRoom(Long room_id){
        return groupRepository.findById(room_id).get();
    }

    public GroupUsersEntity findUserEntity(UserEntity userEntity){
        return groupUsersRepository.findByuserId(userEntity.getId());
    }

    public void uploadImgToGroupChatroom(GroupChatMessageDTO messageDTO){

        List<GenerateFileDTO> files = new GenerateFile(messageDTO.getImg()).createFile();
        List<String> imgs = new ArrayList<>();
        GroupEntity GroupChatRoomEntity = groupRepository.findById(messageDTO.getRoomId()).get();

        for(GenerateFileDTO file : files){

            GroupChatEntity GroupChatEntity = com.imageupload.example.entity.GroupChatEntity.builder()
                    .groupEntity(GroupChatRoomEntity)
                    .sender(messageDTO.getSender())
                    .message(file.getFileName())
                    .profilePath(messageDTO.getProfilePath())
                    .type("image")
                    .userId(messageDTO.getUserId())
                    .build();

            imgs.add(file.getFileName());

            groupChatRepository.save(GroupChatEntity);
        }
            messageDTO.setImgPath(imgs);
    }

    public void deleteRoom(Long roomId, String username){

        UserEntity userEntity = (UserEntity) session.getAttribute("userId");

        if(userEntity.getUsername().equals(username)){
            GroupEntity GroupChatRoomEntity = groupRepository.findById(roomId).get();

            GroupChatRoomEntity.getChats().stream()
                    .filter(item -> item.getType().equals("image")).forEach(item -> {
                new DeleteFile(new String[] {item.getMessage()}).deleteFile();
            });

            groupRepository.delete(GroupChatRoomEntity);

            List<String> files = new ArrayList<>();
            GroupChatRoomEntity.getFiles().stream().map(value -> files.add(value.getName())).toArray();
            new DeleteFile(files.toArray(new String[files.size()])).deleteFile();
        }
    }

    public GroupEntity getRoomInfo(Long id){
        return groupRepository.findById(id).get();
    }

    public void saveChatEntity(GroupChatMessageDTO messageDTO){

        GroupEntity GroupChatRoomEntity = groupRepository.findById(messageDTO.getRoomId()).get();

        GroupChatEntity GroupChatEntity = com.imageupload.example.entity.GroupChatEntity.builder()
                .groupEntity(GroupChatRoomEntity)
                .userId(messageDTO.getUserId())
                .type(messageDTO.getType())
                .profilePath(messageDTO.getProfilePath())
                .message(messageDTO.getMessage())
                .sender(messageDTO.getSender()).build();

        groupChatRepository.save(GroupChatEntity);
    }

    public void exitRoom(Long roomId){

        UserEntity userEntity = (UserEntity) session.getAttribute("userId");

        GroupEntity groupEntity = groupRepository.findById(roomId).get();
        GroupUsersEntity GroupUsersEntity = groupUsersRepository.findByuserIdAndGroupEntity(userEntity.getId(), groupEntity);

        groupRepository.exitCurrentUsers(groupEntity.getId());
        groupUsersRepository.delete(GroupUsersEntity);
    }

    public GroupChatRoomEnterEnumType GroupChatRoomEnter(Long roomId, String username){

        GroupEntity groupEntity = groupRepository.findById(roomId).get();
        UserEntity userEntity = userRepository.findByUsername(username).get();
        GroupUsersEntity GroupUsersEntity = groupUsersRepository.findByuserIdAndGroupEntity(userEntity.getId(), groupEntity);

        if(groupEntity.getUsers().contains(GroupUsersEntity)) {
            return GroupChatRoomEnterEnumType.enter;
        }else{
            GroupUsersEnumType authorizationType;
            if(username.equals(groupEntity.getOwner()))
                authorizationType = GroupUsersEnumType.manager;
            else
                authorizationType = GroupUsersEnumType.member;

            groupRepository.enterCurrentUsers(groupEntity.getId());

            GroupUsersDTO groupUsersDTO = new GroupUsersDTO();
            groupUsersDTO.setUser_id(userEntity.getId());
            groupUsersDTO.setUser_name(userEntity.getUsername());
            groupUsersDTO.setProfilePath(userEntity.getProfile().getProfilePath());
            groupUsersDTO.setGroupEntity(groupEntity);
            groupUsersDTO.setAuthorization(authorizationType);

            groupUsersRepository.save(groupUsersDTO.toEntity());

            return GroupChatRoomEnterEnumType.greeting;
        }
    }

    public Page<GroupEntity> getGroupRooms(PageRequest request){
        return groupRepository.findAll(request);
    }

    @Transactional
    public void createGroupRoom(GroupInfoDTO GroupDTO){

        UserEntity userEntity = (UserEntity) session.getAttribute("userId");

        GroupEntity groupEntity = GroupDTO.toEntity();

        if (GroupDTO.getFiles() != null && GroupDTO.getFiles().length > 0) {
            GenerateFile generateGroupFiles = new GenerateFile(GroupDTO.getFiles());

            groupRepository.save(groupEntity);

            List<GenerateFileDTO> files = generateGroupFiles.createFile();

            for(GenerateFileDTO file : files){
                GroupFileDTO groupFileDTO = new GroupFileDTO();
                groupFileDTO.setName(file.getFileName());
                groupFileDTO.setPath(file.getPath());
                groupFileDTO.setGroupEntity(groupEntity);

                groupFileRepository.save(groupFileDTO.toEntity());
            }
        }

        GroupUsersDTO groupUsersDTO = new GroupUsersDTO();
        groupUsersDTO.setUser_id(userEntity.getId());
        groupUsersDTO.setUser_name(userEntity.getUsername());
        groupUsersDTO.setProfilePath(userEntity.getProfile().getProfilePath());
        groupUsersDTO.setGroupEntity(groupEntity);
        groupUsersDTO.setAuthorization(GroupUsersEnumType.manager);

        groupUsersRepository.save(groupUsersDTO.toEntity());
    }
}
