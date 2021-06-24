package com.imageupload.example.controllers.restcontroller;

import java.security.Principal;

import com.imageupload.example.dto.SubmitTransactionDTO;
import com.imageupload.example.entity.TransactionEntity;
import com.imageupload.example.entity.TransactionEnumType;
import com.imageupload.example.services.TransactionService;
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

    @PostMapping("/cart/move")
    public ResponseEntity<String> cartToTransaction(SubmitTransactionDTO submitTransaction){

        if (transactionService.cartToTransaction(submitTransaction))
            return new ResponseEntity<String>("success", HttpStatus.OK);
        else
            return new ResponseEntity<String>("failed", HttpStatus.OK);
    }

    @PostMapping("/cart/delete")
    public ResponseEntity<String> deleteCartBoard(SubmitTransactionDTO submitTransaction){

        if (transactionService.deleteTransaction(submitTransaction))
            return new ResponseEntity<String>("success", HttpStatus.OK);
        else
            return new ResponseEntity<String>("failed", HttpStatus.OK);
    }

    @PostMapping("/product")
    @Transactional
    public ResponseEntity<String> dealWithSeller(SubmitTransactionDTO transactionDTO){
        if (transactionService.saveTransaction(transactionDTO)){
            return new ResponseEntity<String>("success", HttpStatus.OK);
        }else{
            return new ResponseEntity<String>("failed", HttpStatus.OK);
        }
    }

    @GetMapping("/dealList")
    public Page<TransactionEntity> getTransactionList(Principal user,
    @RequestParam TransactionEnumType type, @RequestParam int page, @RequestParam int display){
        return transactionService.getTransactionEntities(user, type, page, display);
    }

    @PostMapping("/submit")
    public ResponseEntity<String> submitTransaction(SubmitTransactionDTO submitTransaction){
        boolean result = transactionService.updateTransactionStatus(submitTransaction);  
        if(result)
            return new ResponseEntity<String>("success", HttpStatus.OK);
        else
            return new ResponseEntity<String>("falied", HttpStatus.OK);
    }

    @PostMapping("/delete")
    public ResponseEntity<String> deleteTransaction(SubmitTransactionDTO submitTransaction){
        transactionService.deleteTransaction(submitTransaction);
        return new ResponseEntity<String>("success", HttpStatus.OK);
    }

}
