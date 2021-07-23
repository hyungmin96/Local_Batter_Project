package com.imageupload.example.services;

import com.imageupload.example.components.DeleteFile;
import com.imageupload.example.components.GenerateFile;
import com.imageupload.example.dto.GenerateFileDTO;
import com.imageupload.example.dto.GroupBoardDTO;
import com.imageupload.example.dto.GroupBoardFileDTO;
import com.imageupload.example.dto.GroupCommentDTO;
import com.imageupload.example.entity.GroupBoardEntity;
import com.imageupload.example.entity.GroupBoardFileEntity;
import com.imageupload.example.entity.GroupCommentEntity;
import com.imageupload.example.entity.GroupUsersEntity;
import com.imageupload.example.repositories.GroupBoardFileRepository;
import com.imageupload.example.repositories.GroupBoardRepository;
import com.imageupload.example.repositories.GroupCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupBoardService {

    private final GroupBoardRepository groupBoardRepository;
    private final GroupCommentRepository groupCommentRepository;
    private final GroupBoardFileRepository groupBoardFileRepository;
    private final HttpSession session;

    public void updateNotice(GroupBoardDTO groupBoardDTO){

        GroupBoardEntity groupBoardEntity = groupBoardRepository.getOne(groupBoardDTO.getBoardId());
        groupBoardDTO.setBoardId(groupBoardEntity.getBoardId());
        groupBoardEntity.updateNotice(groupBoardDTO.getType());
        groupBoardRepository.save(groupBoardEntity);
    }

    public Page<GroupBoardEntity> getNoticeList(GroupBoardDTO groupBoardDTO){
        PageRequest request = PageRequest.of(groupBoardDTO.getPage(), groupBoardDTO.getDisplay(), Sort.Direction.DESC, "boardId");
        return groupBoardRepository.findAllBygroupIdAndType(groupBoardDTO.getGroupId(), GroupBoardEntity.BoardType.notice, request);
    }

    public void commentWrite(GroupCommentDTO groupCommentDTO){

        GroupUsersEntity groupUsersEntity = (GroupUsersEntity) session.getAttribute("group_user_entity");
        GroupBoardEntity groupBoardEntity = groupBoardRepository.getOne(groupCommentDTO.getBoardId());

        groupCommentDTO.setGroupBoard(groupBoardEntity);
        groupCommentDTO.setWriter(groupUsersEntity);

        GroupCommentEntity groupCommentEntity = groupCommentDTO.toEntity();

        groupCommentRepository.save(groupCommentEntity);

    }

    public void delete(GroupBoardDTO groupBoardDTO){

        GroupBoardEntity groupBoardEntity = groupBoardRepository.getOne(groupBoardDTO.getBoardId());

        String[] filePathArray = groupBoardEntity.getFiles().stream().map(item -> item.getName()).toArray(String[]::new);

        new DeleteFile(filePathArray).deleteFile();
        groupBoardRepository.deleteById(groupBoardDTO.getBoardId());
    }

    public void post(GroupBoardDTO groupBoardDTO){

        GroupBoardEntity groupBoardEntity = groupBoardDTO.toEntity();
        groupBoardRepository.save(groupBoardEntity);

        if(groupBoardDTO.getBoard_img() != null){
            List<GenerateFileDTO> fileList = new GenerateFile(groupBoardDTO.getBoard_img()).createFile();
            List<GroupBoardFileEntity> boardFileList = new ArrayList<>();

            for(GenerateFileDTO item : fileList){
                GroupBoardFileDTO groupBoardFileEntity = new GroupBoardFileDTO();
                groupBoardFileEntity.setName(item.getFileName());
                groupBoardFileEntity.setBoardId(groupBoardEntity);
                groupBoardFileEntity.setPath(item.getPath());
                boardFileList.add(groupBoardFileEntity.toEntity());

                groupBoardFileRepository.saveAll(boardFileList);
            }
            groupBoardDTO.setFiles(boardFileList);
            groupBoardDTO.setBoard_img(null);
        }

        groupBoardDTO.setBoardId(groupBoardEntity.getBoardId());
    }

    public Page<GroupBoardEntity> getBoardList(Long groupId, PageRequest request){
        return groupBoardRepository.findAllBygroupId(groupId, request);
    }

}
