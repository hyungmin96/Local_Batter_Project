package com.imageupload.example.controllers.sitecontroller;

import com.imageupload.example.entity.GroupChatRoomEntity;
import com.imageupload.example.entity.GroupFileEntity;
import com.imageupload.example.entity.GroupUsersEntity;
import com.imageupload.example.entity.UserEntity;
import com.imageupload.example.repositories.UserRepository;
import com.imageupload.example.services.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class GroupSiteController {

    private final GroupService GroupService;

    @GetMapping("/group/room/{room_id}")
    public String getGrouproom(HttpSession session, @PathVariable Long room_id){

        GroupChatRoomEntity GroupChatRoomEntity = GroupService.findRoom(room_id);
        UserEntity userEntity = (UserEntity) session.getAttribute("userId");

        GroupUsersEntity groupUsersEntity = GroupService.findUserEntity(userEntity);

        session.setAttribute("Group_room_id", GroupChatRoomEntity);
        session.setAttribute("Group_room_files", GroupChatRoomEntity.getFiles());
        session.setAttribute("group_user_entity", groupUsersEntity);
        session.setAttribute("user_id", userEntity);

        return "/group/grouproom";
    }

    @GetMapping("/product/group")
    public String getGroup(HttpSession session, Model model){

        UserEntity userEntity = (UserEntity) session.getAttribute("userId");

        model.addAttribute("user_id", userEntity.getId());
        model.addAttribute("user_name", userEntity.getUsername());

        return "group/joingroup";
    }

    @GetMapping("/group/chat/{id}")
    public String getGroupChatRoom(HttpSession session, Model model, @PathVariable Long id){

        UserEntity userEntity = (UserEntity) session.getAttribute("userId");

        model.addAttribute("roomId", id);
        model.addAttribute("username", userEntity.getUsername());
        model.addAttribute("userId", userEntity.getId());
        return "group/groupchatroom";
    }
}
