package com.imageupload.example.controllers.sitecontroller;

import com.imageupload.example.entity.GroupEntity;
import com.imageupload.example.entity.GroupUsersEntity;
import com.imageupload.example.entity.ProfileEntity;
import com.imageupload.example.entity.UserEntity;
import com.imageupload.example.services.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Transactional
public class GroupSiteController {

    private final GroupService groupService;
    private final HttpSession session;

    @GetMapping("/group/room/{room_id}")
    public String getGrouproom(@PathVariable Long room_id){

        // 입장한 그룹의 연관 Entity
        GroupEntity groupEntity = groupService.findRoom(room_id);
        UserEntity userEntity = (UserEntity) session.getAttribute("userId");

        GroupUsersEntity groupUsersEntity = groupService.findUserEntity(userEntity);

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
    public String getGroupChatRoom(Model model, @PathVariable Long id){

        UserEntity userEntity = (UserEntity) session.getAttribute("userId");

        model.addAttribute("roomId", id);
        model.addAttribute("username", userEntity.getUsername());
        model.addAttribute("userId", userEntity.getId());
        return "group/groupchatroom";
    }
}
