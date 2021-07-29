package com.project.localbatter.controllers.restcontroller;
import com.project.localbatter.dto.SubmitTransactionDTO;
import com.project.localbatter.entity.TransactionEntity;
import com.project.localbatter.enumtype.TransactionEnumType;
import com.project.localbatter.services.BoardService;
import com.project.localbatter.services.TransactionService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/transaction")
@RequiredArgsConstructor
public class TransactionController {
    
    private final TransactionService transactionService;
    private final BoardService boardService;

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
    public ResponseEntity<String> dealWithSeller(HttpSession session, SubmitTransactionDTO transactionDTO){

        if(transactionDTO.getType().equals(TransactionEnumType.cart))
            boardService.updateCartCount(transactionDTO.getBoardId());

        if (transactionService.saveTransaction(session, transactionDTO)){
            return new ResponseEntity<String>("success", HttpStatus.OK);
        }else{
            return new ResponseEntity<String>("failed", HttpStatus.OK);
        }
    }

    @GetMapping("/dealList")
    public Page<TransactionEntity> getTransactionList(HttpSession session,
    @RequestParam TransactionEnumType type, @RequestParam int page, @RequestParam int display){
        return transactionService.getTransactionEntities(session, type, page, display);
    }

    @PostMapping("/submit")
    public ResponseEntity<String> submitTransaction(HttpSession session, SubmitTransactionDTO submitTransaction){
        boolean result = transactionService.updateTransactionStatus(session, submitTransaction);
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
