package com.imageupload.example.Controllers.siteController;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class siteController {
    
    @GetMapping("/")
    public String home(Model model){
        model.addAttribute("msg", "hi");
        return "index";
    }

    @GetMapping("/article/write")
    public String articleWrite(){
        return "articleWrite";
    }

}
