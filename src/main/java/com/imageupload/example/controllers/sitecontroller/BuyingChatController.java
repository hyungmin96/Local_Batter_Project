package com.imageupload.example.controllers.sitecontroller;

import com.imageupload.example.entity.UserEntity;
import com.imageupload.example.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class BuyingChatController {

    private final UserRepository userRepository;

    @GetMapping("/product/buying")
    public String getBuying(){
        return "buying/joinbuying";
    }

    @GetMapping("/buying/chat/{id}")
    public String getBuyingChatRoom(Model model, @PathVariable Long id){

        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity userEntity = userRepository.findByUsername(user.getUsername()).get();

        model.addAttribute("roomId", id);
        model.addAttribute("username", user.getUsername());
        model.addAttribute("userProfile", userEntity.getProfile().getProfilePath());
        return "buying/buyingchatroom";
    }
}
