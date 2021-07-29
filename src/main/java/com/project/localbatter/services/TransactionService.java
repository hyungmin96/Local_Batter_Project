package com.project.localbatter.services;

import java.time.LocalDate;

import com.project.localbatter.dto.NotificationDTO;
import com.project.localbatter.enumtype.NotificationEnumType;
import com.project.localbatter.dto.SubmitTransactionDTO;
import com.project.localbatter.entity.UserEntity;
import com.project.localbatter.entity.BoardEntity;
import com.project.localbatter.entity.TransactionEntity;
import com.project.localbatter.enumtype.TransactionEnumType;
import com.project.localbatter.repositories.NotificationRepository;
import com.project.localbatter.repositories.TransactionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpSession;

@Service
@RequiredArgsConstructor
public class TransactionService {
    
    private final TransactionRepository transactionRepository;
    private final UserService userService;
    private final BoardService boardService;
    private final NotificationRepository notificationRepository;
    private final ChatService chatService;

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

    public Boolean updateTransactionStatus(HttpSession session, SubmitTransactionDTO submitTransaction){

        UserEntity userEntity = (UserEntity) session.getAttribute("userId");

        UserEntity sellerEntity = userService.findById(Long.parseLong(submitTransaction.getSellerId()));
        UserEntity buyerEntity = userService.findById(Long.parseLong(submitTransaction.getBuyerId()));
        BoardEntity board = boardService.findBoard(submitTransaction.getBoardId());

        TransactionEntity transactionEntity = transactionRepository.findByBuyerAndSellerAndBoardId(buyerEntity, sellerEntity, board);
        
        if(transactionEntity != null){
            if(sellerEntity.getUsername().equals(userEntity.getUsername()))
                transactionEntity.setSellerComplete("true");
            else
                transactionEntity.setBuyerComplete("true");
                
            try{
                if(transactionEntity.getSellerComplete().equals("true") && 
                    transactionEntity.getBuyerComplete().equals("true")){
                        notificationRepository.completeTransaction(sellerEntity.getNotification().getId(), buyerEntity.getNotification().getId());
                }
            }catch(Exception ex){}

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
            
            if(!transactionEntity.getType().equals(TransactionEnumType.cart))
                notificationRepository.completeTransaction(sellerEntity.getNotification().getId(), buyerEntity.getNotification().getId());

            return true;
        }
        return false;
    }


    public boolean saveTransaction(HttpSession session, SubmitTransactionDTO transactionDTO){
        
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

            NotificationDTO notificationDTO = NotificationDTO.builder()
            .result("success")
            .notificationType(NotificationEnumType.transaction)
            .message("transaction")
            .sender(buyerEntity.getUsername())
            .target(sellerEntity.getUsername())
            .date(LocalDate.now().toString())
            .build();

            if(!transactionDTO.getType().equals(TransactionEnumType.cart))
                chatService.sendNotification(session, notificationDTO);

            return true;
        }

        return false;
    }

    public Page<TransactionEntity> getTransactionEntities(HttpSession session, TransactionEnumType type, int page, int display){

        UserEntity userEntity = (UserEntity) session.getAttribute("userId");
        PageRequest request = PageRequest.of(page, display, Sort.Direction.DESC, "id");
        Page<TransactionEntity> List;

        if(type.equals(TransactionEnumType.cart))
            List = transactionRepository.findAllByBuyerAndType(userEntity, TransactionEnumType.cart, request);
        else
            List = transactionRepository.findAllTransaction(type.getValue(), userEntity.getId(), request);

        return List;
    }

}
