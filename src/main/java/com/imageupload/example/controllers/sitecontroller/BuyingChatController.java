package com.imageupload.example.controllers.sitecontroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class BuyingChatController {
    
    @GetMapping("/product/buying")
    public String getBuying(){
        return "buying/joinbuying";
    }

    @GetMapping("/buying/chat/roomid={id}")
    public String getBuyingChatRoom(@PathVariable Long id){

        return "buying/buyingchatroom";
    }
}
