package com.project.localbatter.api.group;

import com.project.localbatter.dto.GroupCommentDTO;
import com.project.localbatter.entity.GroupCommentEntity;
import com.project.localbatter.services.GroupBoardService;
import com.project.localbatter.services.GroupCommentService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/group/comment")
public class GroupCommentApiController {

    private final GroupBoardService groupBoardService;
    private final GroupCommentService groupCommentService;

    @PostMapping("/write")
    public ResponseCommentDTO commentWrite(GroupCommentDTO groupCommentDTO){
        GroupCommentEntity groupCommentEntity = groupCommentService.commentWrite(groupCommentDTO);
        return new ResponseCommentDTO(groupCommentEntity);
    }

    @PostMapping("/update")
    public ResponseEntity<GroupCommentDTO> updateComment(GroupCommentDTO groupCommentDTO){
        return groupCommentService.updateComment(groupCommentDTO);
    }

    @PostMapping("/delete")
    public ResponseEntity<GroupCommentDTO> deleteComment(GroupCommentDTO groupCommentDTO){
        return groupCommentService.deleteComment(groupCommentDTO);
    }

    @Getter @Setter
    static class ResponseCommentDTO{

        private Long groupId;
        private Long boardId;
        private Long commentId;
        private String username;
        private String profilePath;
        private String comment;

        public ResponseCommentDTO(GroupCommentEntity entity){
            this.groupId = entity.getGroupId();
            this.boardId = entity.getGroupBoard().getBoardId();
            this.commentId = entity.getCommentId();
            this.username = entity.getGroupUserJoinEntity().getUser().getUsername();
            this.profilePath = entity.getGroupUserJoinEntity().getUser().getProfilePath();
            this.comment = entity.getComment();
        }

    }

}
