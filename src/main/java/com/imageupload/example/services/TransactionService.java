package com.imageupload.example.services;

import java.security.Principal;

import com.imageupload.example.dto.TransactionDTO;
import com.imageupload.example.entity.BoardEntity;
import com.imageupload.example.entity.TransactionEntity;
import com.imageupload.example.entity.UserEntity;
import com.imageupload.example.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class TransactionService {
    
    @Autowired
    private TransactionRepository transactionRepository;
    
    @Autowired
    private UserService userService;

    @Autowired
    private BoardService boardService;

    public void saveTransaction(Principal user, Long boardId, String seller){

        BoardEntity board = boardService.findBoard(boardId);

        UserEntity selletEntity = userService.findUserOne(seller);
        UserEntity buyerEntity = userService.findUserOne(user.getName());

        TransactionEntity transactionEntity = new TransactionEntity();
        // transactionEntity.setBoardId(board);
        transactionEntity.setSeller(selletEntity);
        transactionEntity.setBuyer(buyerEntity);

        transactionRepository.save(transactionEntity);
    }

    @Transactional
    public Page<TransactionEntity> getTransactionEntities(Principal user, int page, int display){

        UserEntity userEntity = userService.findUserOne(user.getName());

        PageRequest request = PageRequest.of(page, display, Sort.Direction.DESC, "id");
        Page<TransactionEntity> List = transactionRepository.findAllBySellerOrBuyer(userEntity, userEntity, request);

        return List;

    }

}
