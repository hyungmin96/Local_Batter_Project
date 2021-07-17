package com.imageupload.example.controllers.sitecontroller;

import com.imageupload.example.entity.BuyingChatRoomEntity;
import com.imageupload.example.entity.BuyingFileEntity;
import com.imageupload.example.entity.UserEntity;
import com.imageupload.example.repositories.UserRepository;
import com.imageupload.example.services.BuyingService;
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
public class BuyingChatController {

    private final BuyingService buyingService;

    @GetMapping("/buying/room/{room_id}")
    public String getBuyingroom(HttpSession session, @PathVariable Long room_id){

        BuyingChatRoomEntity buyingChatRoomEntity = buyingService.findRoom(room_id);
        UserEntity userEntity = (UserEntity) session.getAttribute("userId");

        session.setAttribute("buying_room_id", buyingChatRoomEntity);
        session.setAttribute("buying_room_files", buyingChatRoomEntity.getFiles());
        session.setAttribute("user_id", userEntity);

        return "/buying/buyingroom";
    }

    @GetMapping("/product/buying")
    public String getBuying(HttpSession session, Model model){

        UserEntity userEntity = (UserEntity) session.getAttribute("userId");

        model.addAttribute("user_id", userEntity.getId());
        model.addAttribute("user_name", userEntity.getUsername());

        return "buying/joinbuying";
    }

    @GetMapping("/buying/chat/{id}")
    public String getBuyingChatRoom(HttpSession session, Model model, @PathVariable Long id){

        UserEntity userEntity = (UserEntity) session.getAttribute("userId");

        model.addAttribute("roomId", id);
        model.addAttribute("username", userEntity.getUsername());
        model.addAttribute("userId", userEntity.getId());
        return "buying/buyingchatroom";
    }
}
