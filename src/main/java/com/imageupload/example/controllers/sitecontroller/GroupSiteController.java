package com.imageupload.example.controllers.sitecontroller;

import com.imageupload.example.entity.GroupEntity;
import com.imageupload.example.entity.GroupUsersEntity;
import com.imageupload.example.entity.UserEntity;
import com.imageupload.example.repositories.GroupRepository;
import com.imageupload.example.services.GroupService;
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

    private final GroupService groupService;
    private final HttpSession session;

    @GetMapping("/group/room/{groupId}")
    public String getGrouproom(@PathVariable Long groupId){

        // 입장한 그룹의 연관 Entity
        GroupEntity groupEntity = groupService.findRoom(groupId);
        UserEntity userEntity = (UserEntity) session.getAttribute("userId");

        GroupUsersEntity groupUsersEntity = groupService.findUserEntity(groupEntity, userEntity);

        session.setAttribute("Group_room_id", groupEntity);
        session.setAttribute("Group_room_files", groupEntity.getFiles());
        session.setAttribute("group_user_entity", groupUsersEntity);
        session.setAttribute("group_users_entity", groupEntity.getUsers());
        session.setAttribute("user_id", userEntity);

        return "/group/grouproom";
    }

    @GetMapping("/product/group")
    public String getGroup(Model model){

        UserEntity userEntity = (UserEntity) session.getAttribute("userId");

        model.addAttribute("user_id", userEntity.getId());
        model.addAttribute("user_name", userEntity.getUsername());

        return "group/joingroup";
    }

    @GetMapping("/group/chat/{id}")
    public String getGroupChatRoom(@PathVariable Long id){

        session.setAttribute("roomId", id);

        return "group/groupchatroom";
    }
}
