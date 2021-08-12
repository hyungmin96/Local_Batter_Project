package com.project.localbatter.controllers.exchange;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/view/transaction")
public class TransactionController {

    @GetMapping("/my/boards")
    public String getTransactionBoards(){
        return "/transaction/transaction";
    }

}
