package com.project.localbatter.controllers;

import com.project.localbatter.entity.GroupBoardFileEntity;
import com.project.localbatter.entity.GroupCommentEntity;
import com.project.localbatter.entity.GroupEntity;
import com.project.localbatter.repositories.GroupBoardFileRepository;
import com.project.localbatter.repositories.GroupCommentRepository;
import com.project.localbatter.repositories.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Transactional
public class GroupController {

    private final GroupRepository groupRepository;
    private final GroupBoardFileRepository groupBoardFileRepository;
    private final GroupCommentRepository groupCommentRepository;

    @GetMapping("/group/{groupId}")
    public String getGrouproom(Model model, @PathVariable Long groupId){
        GroupEntity groupEntity = groupRepository.getOne(groupId);
        List<GroupCommentEntity> comment = groupCommentRepository.findTop5ByGroupId(groupEntity.getId());
        List<GroupBoardFileEntity> files = groupBoardFileRepository.findTop9BygroupId(groupEntity.getId());
        model.addAttribute("groupObject", groupEntity);
        model.addAttribute("group_files", files);
        model.addAttribute("group_comments", comment);
        return "group/group";
    }

    @GetMapping("/product/group")
    public String getGroup(){
        return "group/joingroup";
    }

    @GetMapping("/group/chat/{id}")
    public String getGroupChatRoom(@PathVariable Long id){
        return "group/groupchatroom";
    }
}
