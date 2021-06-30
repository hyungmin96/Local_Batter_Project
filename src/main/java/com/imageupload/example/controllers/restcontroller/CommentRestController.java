package com.imageupload.example.controllers.restcontroller;

import com.imageupload.example.dto.CommentDTO;
import com.imageupload.example.entity.BoardEntity;
import com.imageupload.example.entity.CommentEntity;
import com.imageupload.example.entity.UserEntity;
import com.imageupload.example.repositories.BoardRepository;
import com.imageupload.example.repositories.UserRepository;
import com.imageupload.example.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/comment/")
public class CommentRestController {
    
    @Autowired
    private CommentService commentService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BoardRepository boardRepository;

    @PostMapping("/write")
    public ResponseEntity<String> setComment(CommentDTO commentDTO){

        UserDetails user = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity writer;

        UserEntity userEntity = userRepository.findById(commentDTO.getUserId()).get();
        UserEntity sellerEntity = userRepository.findById(commentDTO.getSeller()).get();
        
        if(userEntity.getUsername().equals(((UserDetails)user).getUsername()))
            writer = sellerEntity;
        else
            writer = userEntity;

        BoardEntity boardEntity = boardRepository.findById(commentDTO.getBoardId()).get();

        CommentEntity commentEntity = CommentEntity.builder()
                                                    .boardId(boardEntity)
                                                    .writer(user.getUsername())    
                                                    .target(writer.getUsername())
                                                    .mannerScore(commentDTO.getMannerScore())
                                                    .commentValue(commentDTO.getComment())
                                                    .build();

        commentService.saveEntity(commentEntity);

        writer.getProfile().setMannerScore(commentService.getMannerScore(writer.getUsername()));
        userRepository.save(userEntity);

        return new ResponseEntity<String>("success", HttpStatus.OK);
        
    }

}
