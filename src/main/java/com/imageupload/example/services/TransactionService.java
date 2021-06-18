package com.imageupload.example.services;

import java.security.Principal;

import com.imageupload.example.dto.SubmitTransactionDTO;
import com.imageupload.example.entity.BoardEntity;
import com.imageupload.example.entity.TransactionEntity;
import com.imageupload.example.entity.UserEntity;
import com.imageupload.example.repositories.TransactionRepository;

import org.apache.catalina.authenticator.SpnegoAuthenticator.AuthenticateAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {
    
    @Autowired
    private TransactionRepository transactionRepository;
    
    @Autowired
    private UserService userService;

    @Autowired
    private BoardService boardService;

    public Boolean updateTransactionStatus(SubmitTransactionDTO submitTransaction){

        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        UserEntity sellerEntity = userService.findById(Long.parseLong(submitTransaction.getSellerId()));
        UserEntity buyerEntity = userService.findById(Long.parseLong(submitTransaction.getBuyerId()));
        BoardEntity board = boardService.findBoard(Long.parseLong(submitTransaction.getBoardId()));

        TransactionEntity transactionEntity = transactionRepository.findByBuyerAndSellerAndBoardId(buyerEntity, sellerEntity, board);
        
        if(transactionEntity != null){
            if(sellerEntity.getUsername().equals(((UserDetails)user).getUsername()))
                transactionEntity.setSellerComplete("true");
            else
                transactionEntity.setBuyerComplete("true");
                
            transactionRepository.save(transactionEntity);

            return true;
        }
        return false;
    }

    
    public void deleteTransaction(SubmitTransactionDTO submitTransaction){

        UserEntity sellerEntity = userService.findById(Long.parseLong(submitTransaction.getSellerId()));
        UserEntity buyerEntity = userService.findById(Long.parseLong(submitTransaction.getBuyerId()));
        BoardEntity board = boardService.findBoard(Long.parseLong(submitTransaction.getBoardId()));

        TransactionEntity transactionEntity = transactionRepository.findByBuyerAndSellerAndBoardId(buyerEntity, sellerEntity, board);
        
        if(transactionEntity != null)
            transactionRepository.deleteById(transactionEntity.getId());

    }

    public boolean saveTransaction(Principal user, Long boardId, String seller){
        
        UserEntity sellerEntity = userService.findUserOne(seller);
        UserEntity buyerEntity = userService.findUserOne(user.getName());
        BoardEntity board = boardService.findBoard(boardId);
        TransactionEntity transactionEntity = transactionRepository.findByBuyerAndSellerAndBoardId(buyerEntity, sellerEntity, board);
        if(transactionEntity == null){
            transactionEntity = new TransactionEntity();
            transactionEntity.setBoardId(board);
            transactionEntity.setSeller(sellerEntity);
            transactionEntity.setBuyer(buyerEntity);
            transactionRepository.save(transactionEntity);

            return true;
        }

        return false;
    }

    public Page<TransactionEntity> getTransactionEntities(Principal user, int page, int display){
        UserEntity userEntity = userService.findUserOne(user.getName());
        PageRequest request = PageRequest.of(page, display, Sort.Direction.DESC, "id");
        Page<TransactionEntity> List = transactionRepository.findAllTransaction("complete", userEntity.getId(), request);
        return List;
    }

}
