package com.project.localbatter.api.group;

import com.project.localbatter.dto.GroupCreateDTO;
import com.project.localbatter.dto.GroupMemberDTO;
import com.project.localbatter.dto.GroupPageDTO;
import com.project.localbatter.services.group.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/group")
public class GroupApiController {

    private final GroupService groupService;

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
    public ResponseEntity<String> checkMember(@RequestParam Long userId, @RequestParam Long groupId){
        return groupService.isMember(userId, groupId);
    }

    @GetMapping("/get_group_list")
    public List<GroupPageDTO> getGroupList(GroupPageDTO groupPageDTO){
        return groupService.getGroupList(groupPageDTO);
    }

    @PostMapping("/create")
    public ResponseEntity<GroupCreateDTO> createGroup(GroupCreateDTO groupCreateDTO){
        return groupService.createGroupRoom(groupCreateDTO);
    }


}
