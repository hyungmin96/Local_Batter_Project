package com.imageupload.example.services;

import java.security.Principal;

import com.imageupload.example.dto.SubmitTransactionDTO;
import com.imageupload.example.entity.BoardEntity;
import com.imageupload.example.entity.TransactionEntity;
import com.imageupload.example.entity.TransactionEnumType;
import com.imageupload.example.entity.UserEntity;
import com.imageupload.example.repositories.TransactionRepository;
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

    public Boolean cartToTransaction(SubmitTransactionDTO submitTransaction){

        UserEntity sellerEntity = userService.findById(Long.parseLong(submitTransaction.getSellerId()));
        UserEntity buyerEntity = userService.findById(Long.parseLong(submitTransaction.getBuyerId()));
        BoardEntity board = boardService.findBoard(submitTransaction.getBoardId());

        TransactionEntity transactionEntity = transactionRepository.findByBuyerAndSellerAndBoardId(buyerEntity, sellerEntity, board);
        
        if(transactionEntity != null){
            transactionEntity.setType(TransactionEnumType.transaction);
                
            transactionRepository.save(transactionEntity);  

            return true;
        }
        return false;
    }

    public Boolean updateTransactionStatus(SubmitTransactionDTO submitTransaction){

        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        UserEntity sellerEntity = userService.findById(Long.parseLong(submitTransaction.getSellerId()));
        UserEntity buyerEntity = userService.findById(Long.parseLong(submitTransaction.getBuyerId()));
        BoardEntity board = boardService.findBoard(submitTransaction.getBoardId());

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

    
    public boolean deleteTransaction(SubmitTransactionDTO submitTransaction){

        UserEntity sellerEntity = userService.findById(Long.parseLong(submitTransaction.getSellerId()));
        UserEntity buyerEntity = userService.findById(Long.parseLong(submitTransaction.getBuyerId()));
        BoardEntity board = boardService.findBoard(submitTransaction.getBoardId());

        TransactionEntity transactionEntity = transactionRepository.findByBuyerAndSellerAndBoardId(buyerEntity, sellerEntity, board);
        
        if(transactionEntity != null){
            transactionRepository.deleteById(transactionEntity.getId());
            return true;
        }
        return false;
    }


    public boolean saveTransaction(SubmitTransactionDTO transactionDTO){
        
        UserEntity sellerEntity = userService.findUserOne(transactionDTO.getSellerId());
        UserEntity buyerEntity = userService.findUserOne(transactionDTO.getBuyerId());
        BoardEntity board = boardService.findBoard(transactionDTO.getBoardId());

        TransactionEntity transactionEntity = transactionRepository.findByBuyerAndSellerAndBoardId(buyerEntity, sellerEntity, board);
        if(transactionEntity == null){
            transactionEntity = new TransactionEntity();
            transactionEntity.setType(transactionDTO.getType());
            transactionEntity.setBoardId(board);
            transactionEntity.setSeller(sellerEntity);
            transactionEntity.setBuyer(buyerEntity);
            transactionRepository.save(transactionEntity);

            return true;
        }

        return false;
    }

    public Page<TransactionEntity> getTransactionEntities(Principal user, TransactionEnumType type, int page, int display){
        UserEntity userEntity = userService.findUserOne(user.getName());
        PageRequest request = PageRequest.of(page, display, Sort.Direction.DESC, "id");
        Page<TransactionEntity> List = transactionRepository.findAllTransaction(type.getValue(), userEntity.getId(), request);
        return List;
    }

}
