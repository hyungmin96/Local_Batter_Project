package com.imageupload.example.controllers.restcontroller;

import java.security.Principal;

import com.imageupload.example.dto.TransactionDTO;
import com.imageupload.example.entity.BoardEntity;
import com.imageupload.example.entity.TransactionEntity;
import com.imageupload.example.entity.UserEntity;
import com.imageupload.example.services.BoardService;
import com.imageupload.example.services.TransactionService;
import com.imageupload.example.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {
    
    @Autowired
    private TransactionService transactionService;

    @PostMapping("/deal")
    @Transactional
    public ResponseEntity<String> dealWithSeller(Principal user, @RequestParam long boardId, @RequestParam String seller){
        transactionService.saveTransaction(user, boardId, seller);
        return new ResponseEntity<String>("성공", HttpStatus.OK);
    }

    @GetMapping("/dealList")
    public Page<TransactionEntity> getTransactionList(Principal user,
    @RequestParam int page, @RequestParam int display){
        return transactionService.getTransactionEntities(user, page, display);
    }

}
