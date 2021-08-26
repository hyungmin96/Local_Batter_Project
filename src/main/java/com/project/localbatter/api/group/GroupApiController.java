package com.project.localbatter.api.group;

import com.project.localbatter.dto.Group.GroupCreateDTO;
import com.project.localbatter.dto.Group.GroupMemberDTO;
import com.project.localbatter.dto.Group.GroupPageDTO;
import com.project.localbatter.entity.GroupEntity;
import com.project.localbatter.entity.GroupUserJoinEntity;
import com.project.localbatter.services.group.GroupService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/group")
public class GroupApiController {

    private final GroupService groupService;

    @PostMapping("/update")
    public ResponseUpdateDTO updateGroup(GroupCreateDTO groupCreateDTO){
        GroupEntity groupEntity = groupService.updateGroup(groupCreateDTO);
        return new ResponseUpdateDTO(groupEntity);
    }

    @PostMapping("/delete")
    public ResponseDeleteDTO deleteGroup(GroupPageDTO groupPageDTO){
        GroupEntity groupEntity = groupService.deleteGroup(groupPageDTO);
        return new ResponseDeleteDTO(groupEntity);
    }

    @GetMapping("/get_member_list")
    public Page<GroupMemberDTO> getMemberList(GroupPageDTO groupPageDTO){
        return groupService.getMemberList(groupPageDTO);
    }

    @PostMapping("/enter")
    public ResponseEntity<String> enterGroup(GroupPageDTO groupPageDTO){
        return groupService.enterGroup(groupPageDTO);
    }

    @PostMapping("/exit")
    public ResponseEntity<String> exitGroup(GroupPageDTO groupPageDTO){
        return groupService.exitGroup(groupPageDTO);
    }

    @GetMapping("/check/isMember")
    public ResponseMemberCheckDTO checkMember(@RequestParam Long userId, @RequestParam Long groupId){
        return groupService.isMember(userId, groupId);
    }

    @GetMapping("/join/get_group_list")
    public Page<GroupEntity> getGroupList(GroupPageDTO groupPageDTO){
        return groupService.getGroupList(groupPageDTO);
    }

    @GetMapping("/random/get_group_list")
    public List<GroupEntity> getRandomGroupList(GroupPageDTO groupPageDTO){
        return groupService.getRandomGroupList(groupPageDTO);
    }

    @PostMapping("/create")
    public ResponseEntity<GroupCreateDTO> createGroup(GroupCreateDTO groupCreateDTO){
        return groupService.createGroupRoom(groupCreateDTO);
    }

    @Getter @Setter
    static class ResponseUpdateDTO{
        private String result;
        private Long groupId;
        public ResponseUpdateDTO(GroupEntity entity){
            this.result = "그룹 정보를 수정 하였습니다.";
            this.groupId = entity.getId();
        }
    }

    @Getter @Setter
    @NoArgsConstructor
    public static class ResponseMemberCheckDTO{

        private Long userId;
        private Long groupId;
        private String username;
        private String profilePath;
        private GroupUserJoinEntity.userAuthority type = GroupUserJoinEntity.userAuthority.none;
    }

    @Getter @Setter
    static class ResponseDeleteDTO{
        private String result;
        private Long groupId;
        public ResponseDeleteDTO(GroupEntity entity){
            this.result = "그룹을 삭제하였습니다.";
            this.groupId = entity.getId();
        }
    }

}
