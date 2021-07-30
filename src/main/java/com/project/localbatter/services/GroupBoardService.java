package com.project.localbatter.services;

import com.project.localbatter.components.DeleteFile;
import com.project.localbatter.components.GenerateFile;
import com.project.localbatter.dto.GenerateFileDTO;
import com.project.localbatter.dto.GroupBoardDTO;
import com.project.localbatter.dto.GroupBoardFileDTO;
import com.project.localbatter.entity.*;
import com.project.localbatter.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class GroupBoardService {

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final GroupBoardRepository groupBoardRepository;
    private final GroupCommentRepository groupCommentRepository;
    private final GroupBoardFileRepository groupBoardFileRepository;
    private final GroupUserJoinRepository groupUserJoinRepository;

    @Transactional(readOnly = true)
    public Page<GroupBoardEntity> getBoardList(Long groupId, PageRequest request){
        return groupBoardRepository.findAllBygroupId(groupId, request);
    }

    public Page<GroupCommentEntity> getLatestComments(GroupBoardDTO groupBoardDTO){
        PageRequest request = PageRequest.of(groupBoardDTO.getPage(), groupBoardDTO.getDisplay(), Sort.Direction.DESC, "commentId");
        return groupCommentRepository.findTop5ByGroupId(groupBoardDTO.getGroupId(), request);
    }

    public Page<GroupBoardFileEntity> getLatestImages(GroupBoardDTO groupBoardDTO){
        PageRequest request = PageRequest.of(groupBoardDTO.getPage(), groupBoardDTO.getDisplay(), Sort.Direction.DESC, "id");
        return groupBoardFileRepository.findTop9BygroupId(groupBoardDTO.getGroupId(), request);
    }

    public ResponseEntity<GroupBoardDTO> updateNotice(GroupBoardDTO groupBoardDTO){
        // Check Authorization to enter user

//        if(isUserHasAuthority){
//            GroupBoardEntity groupBoardEntity = groupBoardRepository.getOne(groupBoardDTO.getBoardId());
//            groupBoardDTO.setBoardId(groupBoardEntity.getBoardId());
//            groupBoardEntity.updateNotice(groupBoardDTO.getType());
//            groupBoardRepository.save(groupBoardEntity);
//            groupBoardDTO.setResult("Success");
//
//            return new ResponseEntity<>(groupBoardDTO, HttpStatus.OK);
//        }
        groupBoardDTO.setResult("Failed - No request authority");
        return new ResponseEntity<>(groupBoardDTO, HttpStatus.BAD_REQUEST);
    }

    public Page<GroupBoardEntity> getNoticeList(GroupBoardDTO groupBoardDTO){
        PageRequest request = PageRequest.of(groupBoardDTO.getPage(), groupBoardDTO.getDisplay(), Sort.Direction.DESC, "boardId");
        return groupBoardRepository.findAllBygroupIdAndType(groupBoardDTO.getGroupId(), GroupBoardEntity.BoardType.notice, request);
    }

    public void delete(GroupBoardDTO groupBoardDTO){
        GroupBoardEntity groupBoardEntity = groupBoardRepository.getOne(groupBoardDTO.getBoardId());
        String[] filePathArray = groupBoardEntity.getFiles().stream().map(GroupBoardFileEntity::getName).toArray(String[]::new);
        new DeleteFile(filePathArray).deleteFile();
        groupBoardRepository.deleteById(groupBoardDTO.getBoardId());
    }

    public void post(GroupBoardDTO groupBoardDTO){

        GroupEntity groupEntity = groupRepository.getOne(groupBoardDTO.getGroupId());
        UserEntity userEntity = userRepository.getOne(groupBoardDTO.getUserId());

        GroupUserJoinEntity groupUserJoinEntity = groupUserJoinRepository.findByGroupAndUser(groupEntity, userEntity);

        groupBoardDTO.setGroupUserJoin(groupUserJoinEntity);
        GroupBoardEntity groupBoardEntity = groupBoardDTO.toEntity();
        groupBoardRepository.save(groupBoardEntity);

        if(groupBoardDTO.getBoard_img() != null){
            List<GenerateFileDTO> fileList = new GenerateFile(groupBoardDTO.getBoard_img()).createFile();
            List<GroupBoardFileEntity> boardFileList = new ArrayList<>();

            for(GenerateFileDTO item : fileList){
                GroupBoardFileDTO groupBoardFileDTO = new GroupBoardFileDTO();
                groupBoardFileDTO.setName(item.getFileName());
                groupBoardFileDTO.setBoardId(groupBoardEntity);
                groupBoardFileDTO.setGroupId(groupBoardDTO.getGroupId());
                groupBoardFileDTO.setPath(item.getPath());
                boardFileList.add(groupBoardFileDTO.toEntity());

                groupBoardFileRepository.saveAll(boardFileList);
            }

            groupBoardDTO.setFiles(boardFileList);
            groupBoardDTO.setBoard_img(null);
        }

        groupBoardDTO.setBoardId(groupBoardEntity.getBoardId());
    }

}
