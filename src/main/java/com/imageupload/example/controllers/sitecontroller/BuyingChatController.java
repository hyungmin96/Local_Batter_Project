package com.imageupload.example.controllers.sitecontroller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class BuyingChatController {

    @GetMapping("/product/buying")
    public String getBuying(){
        return "buying/joinbuying";
    }

    @GetMapping("/buying/chat/{id}")
    public String getBuyingChatRoom(Model model, @PathVariable Long id){

        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        model.addAttribute("roomId", id);
        model.addAttribute("username", user.getUsername());
        return "buying/buyingchatroom";
    }
}
