package com.project.localbatter.api.group;

import com.project.localbatter.dto.Group.GroupBoardDTO;
import com.project.localbatter.entity.GroupBoardEntity;
import com.project.localbatter.entity.GroupBoardFileEntity;
import com.project.localbatter.entity.GroupCommentEntity;
import com.project.localbatter.services.group.GroupBoardService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/group/board")
public class GroupBoardApiController {

    private final GroupBoardService groupBoardService;

    @PostMapping("/update")
    public ResponseBoardDTO updateGroupBoard(GroupBoardDTO groupBoardDTO){
          GroupBoardEntity groupBoardEntity = groupBoardService.update(groupBoardDTO);
          return new ResponseBoardDTO(groupBoardEntity);
    }

    @PostMapping("/getInfo")
    public ResponseBoardDTO getBoardInfo(GroupBoardDTO groupBoardDTO){
        GroupBoardEntity grouBoardEntity = groupBoardService.getBoardInfo(groupBoardDTO).get(0);
        return new ResponseBoardDTO(grouBoardEntity);
    }

    @PostMapping("/update/notice")
    public ResponseBoardDTO updateNotice(GroupBoardDTO groupBoardDTO){
        return new ResponseBoardDTO(groupBoardService.updateNotice(groupBoardDTO));
    }

    @GetMapping("/get_notice_list")
    public List<GroupBoardDTO> getNoticeList(GroupBoardDTO groupBoardDTO){
        return groupBoardService.getNoticeList(groupBoardDTO);
    }

    @PostMapping("/delete")
    public ResponseEntity<String> deletePost(GroupBoardDTO groupBoardDTO){
        groupBoardService.delete(groupBoardDTO);
        return new ResponseEntity<String>("Success", HttpStatus.OK);
    }

    @PostMapping("/post")
    public ResponseBoardDTO postGroupBoard(GroupBoardDTO groupBoardDTO){
            GroupBoardEntity boardItem = groupBoardService.post(groupBoardDTO);
            return new ResponseBoardDTO(boardItem);
    }

    @GetMapping("/get_board_list")
    public Page<ResponseBoardDTO> getBoardList(GroupBoardDTO groupBoardDTO){
        PageRequest request = PageRequest.of(groupBoardDTO.getPage(), groupBoardDTO.getDisplay(), Sort.Direction.DESC, "boardId");
        Page<GroupBoardEntity> items = groupBoardService.getBoardList(groupBoardDTO.getGroupId(), request);
        List<ResponseBoardDTO> response = items.stream().map(ResponseBoardDTO::new).collect(Collectors.toList());
        return new PageImpl<>(response, request, items.getTotalElements());
    }


    @Setter @Getter
    public static class ResponseBoardDTO {
        // 게시글 DTO
        private String result;
        private Long userId;
        private String username;
        private String profilePath;
        private LocalDateTime regTime;
        private Long boardId;
        private int boardLike;
        private GroupBoardEntity.BoardCategory BoardCategory;
        private String title;
        private String content;
        private GroupBoardEntity.BoardType type; // 그룹 공지, 일반 글
        private List<String> boardFiles;
        private List<ResponseCommentDTO> comments;

        // 게시글 거래위치 DTO
        private String residence;
        private String detailAddr;
        private String buildingcode;
        private String location;
        private String locationDetail;
        private String longitude;
        private String latitude;
        private String preferTime;

        public ResponseBoardDTO(GroupBoardEntity entity) {
            this.userId = entity.getGroupUserJoinEntity().getUser().getId();
            this.username = entity.getGroupUserJoinEntity().getUser().getUsername();
            this.profilePath = entity.getGroupUserJoinEntity().getUser().getProfilePath();
            this.regTime = entity.getRegTime();
            this.boardId = entity.getBoardId();
            this.boardLike = entity.getBoardLike();
            this.BoardCategory = entity.getBoardCategory();
            this.type = entity.getType();
            this.title = entity.getTitle();
            this.content = entity.getContent();
            this.boardFiles = entity.getFiles().stream().map(GroupBoardFileEntity::getName).collect(Collectors.toList());
            this.comments = (entity.getComments() != null) ? entity.getComments().stream().map(ResponseCommentDTO::new).collect(Collectors.toList()) : new ArrayList<>();
            if (entity.getWriterExchangeEntity() != null) {
                this.residence = entity.getWriterExchangeEntity().getResidence();
                this.detailAddr = entity.getWriterExchangeEntity().getDetailAddr();
                this.buildingcode = entity.getWriterExchangeEntity().getBuildingcode();
                this.longitude = entity.getWriterExchangeEntity().getLongitude();
                this.latitude = entity.getWriterExchangeEntity().getLatitude();
                this.location = entity.getWriterExchangeEntity().getLocation();
                this.locationDetail = entity.getWriterExchangeEntity().getLocationDetail();
                this.preferTime = entity.getWriterExchangeEntity().getExchangeTime();
            }
        }
    }

    @Getter @Setter
    static class ResponseCommentDTO{
        private String username;
        private String profilePath;
        private String comment;
        private Long commentId;
        private Long boardId;
        private Long userId;

        public ResponseCommentDTO(GroupCommentEntity entity) {
            this.comment = entity.getComment();
            this.commentId = entity.getCommentId();
            this.userId = entity.getGroupUserJoinEntity().getUser().getId();
            this.boardId = entity.getGroupBoard().getBoardId();
            this.username = entity.getGroupUserJoinEntity().getUser().getUsername();
            this.profilePath = entity.getGroupUserJoinEntity().getUser().getProfilePath();
        }
    }

}
















