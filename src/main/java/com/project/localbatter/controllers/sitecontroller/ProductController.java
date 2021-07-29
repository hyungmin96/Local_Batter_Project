package com.project.localbatter.controllers.sitecontroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductController {
    
    @GetMapping("/transaction/itemList")
    public String getTransactionBoardList(){
        return "/product/transaction";
    }

}
