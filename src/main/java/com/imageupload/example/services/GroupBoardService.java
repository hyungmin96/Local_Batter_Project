package com.imageupload.example.services;

import com.imageupload.example.components.GenerateFile;
import com.imageupload.example.dto.GenerateFileDTO;
import com.imageupload.example.dto.GroupBoardDTO;
import com.imageupload.example.entity.GroupBoardEntity;
import com.imageupload.example.entity.GroupBoardFileEntity;
import com.imageupload.example.entity.GroupCommentEntity;
import com.imageupload.example.entity.GroupUsersEntity;
import com.imageupload.example.repositories.GroupBoardRepository;
import com.imageupload.example.repositories.GroupCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupBoardService {

    private final GroupBoardRepository groupBoardRepository;
    private final GroupCommentRepository groupCommentRepository;

    public GroupCommentEntity commentWrite(GroupBoardEntity groupBoardEntity, GroupUsersEntity groupUsersEntity, String comment){

        GroupCommentEntity groupCommentEntity = GroupCommentEntity.builder()
                .comment(comment)
                .writer(groupUsersEntity)
                .groupBoard(groupBoardEntity)
                .build();

        groupCommentRepository.save(groupCommentEntity);

        return groupCommentEntity;
    }

    public void delete(Long boardId, String username){
        groupBoardRepository.deleteById(boardId);
    }

    public void post(GroupUsersEntity groupUserEntity, GroupBoardDTO groupBoardDTO){

        GroupBoardEntity groupBoardEntity = GroupBoardEntity.builder()
                .groupUsersEntity(groupUserEntity)
                .content(groupBoardDTO.getContent())
                .groupId(groupBoardDTO.getGroupId())
                .build();

        if(groupBoardDTO.getBoard_img() != null){
            List<GenerateFileDTO> fileList = new GenerateFile(groupBoardDTO.getBoard_img()).createFile();
            List<GroupBoardFileEntity> boardFileList = new ArrayList<>();

            for(GenerateFileDTO item : fileList){
                GroupBoardFileEntity groupBoardFileEntity = GroupBoardFileEntity.builder()
                        .name(item.getFileName())
                        .groupBoard(groupBoardEntity)
                        .path(item.getPath())
                        .build();
                boardFileList.add(groupBoardFileEntity);
            }

//            groupBoardEntity.setFiles(boardFileList);
        }

        groupBoardRepository.save(groupBoardEntity);
    }

    public Page<GroupBoardEntity> getBoardList(Long groupId, PageRequest request){
        return groupBoardRepository.findAllBygroupId(groupId, request);
    }

}
