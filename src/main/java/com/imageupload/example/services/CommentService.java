package com.imageupload.example.services;

import com.imageupload.example.entity.BoardEntity;
import com.imageupload.example.entity.CommentEntity;
import com.imageupload.example.entity.TransactionEntity;
import com.imageupload.example.repositories.BoardRepository;
import com.imageupload.example.repositories.CommentRepository;
import com.imageupload.example.repositories.TransactionRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import com.imageupload.example.dto.CommentDTO;
import com.imageupload.example.entity.UserEntity;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import com.imageupload.example.repositories.UserRepository;
import com.imageupload.example.services.CommentService;

@Service
@RequiredArgsConstructor
public class CommentService {
    
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;
    private final BoardRepository boardRepsoitory;

    public ResponseEntity<String> saveEntity(CommentDTO commentDTO){
        
        UserEntity writer;

        UserDetails user = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity userEntity = userRepository.findById(commentDTO.getUserId()).get();
        UserEntity sellerEntity = userRepository.findById(commentDTO.getSeller()).get();
        
        BoardEntity boardEntity = boardRepsoitory.findById(commentDTO.getBoardId()).get();

        TransactionEntity transactionEntity = transactionRepository.findByBuyerAndSellerAndBoardId(userEntity, sellerEntity, boardEntity);
        
        if(userEntity.getUsername().equals(((UserDetails)user).getUsername())){
            writer = sellerEntity;
            transactionEntity.setBuyerCommentId(userEntity.getUsername());
        }
        else{
            writer = userEntity;
            transactionEntity.setSellerCommentId(sellerEntity.getUsername());
        }
        transactionRepository.save(transactionEntity);

        CommentEntity commentEntity = CommentEntity.builder()
                                                    .writer(user.getUsername())    
                                                    .target(writer.getUsername())
                                                    .mannerScore(commentDTO.getMannerScore())
                                                    .commentValue(commentDTO.getComment())
                                                    .build();

        commentRepository.save(commentEntity);

        writer.getProfile().setMannerScore(getMannerScore(writer.getUsername()));
        userRepository.save(userEntity);

        return new ResponseEntity<String>("success", HttpStatus.OK);

    }

    public float getMannerScore(String target){
        return commentRepository.findAllBymannerScore(target);
    }

}
