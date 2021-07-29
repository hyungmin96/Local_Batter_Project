package com.project.localbatter.controllers.sitecontroller;

import com.project.localbatter.entity.GroupEntity;
import com.project.localbatter.repositories.GroupRepository;
import com.project.localbatter.services.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
@Transactional
public class GroupSiteController {

    private final GroupRepository groupRepository;

    @GetMapping("/group/{groupId}")
    public String getGrouproom(Model model, @PathVariable Long groupId){
        GroupEntity groupEntity = groupRepository.findById(groupId).get();
        model.addAttribute("groupObject", groupEntity);
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
