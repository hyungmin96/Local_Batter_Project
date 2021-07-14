package com.imageupload.example.services;

import com.imageupload.example.components.DeleteFile;
import com.imageupload.example.components.GenerateFile;
import com.imageupload.example.dto.BuyingChatMessageDTO;
import com.imageupload.example.dto.BuyingDTO;
import com.imageupload.example.dto.GenerateFileDTO;
import com.imageupload.example.entity.*;
import com.imageupload.example.enumtype.BuyingChatRoomEnterEnumType;
import com.imageupload.example.enumtype.BuyingUsersEnumType;
import com.imageupload.example.repositories.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

import javax.mail.Session;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BuyingService {
    
    private final BuyingChatRepository buyingChatRepository;
    private final BuyingFileRepository buyingFileRepository;
    private final BuyingChatRoomRepository buyingChatRoomRepository;
    private final BuyingUsersRepository buyingUsersRepository;
    private final UserRepository userRepository;

    public void uploadImgToBuyingChatroom(BuyingChatMessageDTO messageDTO){

        List<GenerateFileDTO> files = new GenerateFile(messageDTO.getImg()).createFile();
        List<String> imgs = new ArrayList<>();
        BuyingChatRoomEntity buyingChatRoomEntity = buyingChatRoomRepository.findById(messageDTO.getRoomId()).get();

        for(GenerateFileDTO file : files){

            BuyingChatEntity buyingChatEntity = BuyingChatEntity.builder()
                    .roomId(buyingChatRoomEntity)
                    .sender(messageDTO.getSender())
                    .message(file.getFileName())
                    .profilePath(messageDTO.getProfilePath())
                    .type("image")
                    .userId(messageDTO.getUserId())
                    .build();

            imgs.add(file.getFileName());

            buyingChatRepository.save(buyingChatEntity);
        }
            messageDTO.setImgPath(imgs);
    }

    public void deleteRoom(HttpSession session, Long roomId, String username){

        UserEntity userEntity = (UserEntity) session.getAttribute("userId");

        if(userEntity.getUsername().equals(username)){
            BuyingChatRoomEntity buyingChatRoomEntity = buyingChatRoomRepository.findById(roomId).get();

            buyingChatRoomEntity.getChats().stream()
                    .filter(item -> item.getType().equals("image")).forEach(item -> {
                new DeleteFile(new String[] {item.getMessage()}).deleteFile();
            });

            buyingChatRoomRepository.delete(buyingChatRoomEntity);

            List<String> files = new ArrayList<>();
            buyingChatRoomEntity.getFiles().stream().map(value -> files.add(value.getName())).toArray();
            new DeleteFile(files.toArray(new String[files.size()])).deleteFile();
        }
    }

    public List<BuyingChatEntity> getChats(Long roomId){
        BuyingChatRoomEntity buyingChatRoomEntity = buyingChatRoomRepository.findById(roomId).get();
        return buyingChatRepository.findAllByRoomId(buyingChatRoomEntity);
    }

    public BuyingChatRoomEntity getRoomInfo(Long id){
        return buyingChatRoomRepository.findById(id).get();
    }

    public void saveChatEntity(BuyingChatMessageDTO messageDTO){

        BuyingChatRoomEntity buyingChatRoomEntity = buyingChatRoomRepository.findById(messageDTO.getRoomId()).get();

        BuyingChatEntity buyingChatEntity = BuyingChatEntity.builder()
                .roomId(buyingChatRoomEntity)
                .userId(messageDTO.getUserId())
                .type(messageDTO.getType())
                .profilePath(messageDTO.getProfilePath())
                .message(messageDTO.getMessage())
                .sender(messageDTO.getSender()).build();

        buyingChatRepository.save(buyingChatEntity);
    }

    public void exitRoom(HttpSession session, Long roomId){

        UserEntity userEntity = (UserEntity) session.getAttribute("userId");

        BuyingChatRoomEntity buyingChatRoomEntity = buyingChatRoomRepository.findById(roomId).get();
        BuyingUsersEntity buyingUsersEntity = buyingUsersRepository.findBybuyingChatRoomEntityAndUser(buyingChatRoomEntity, userEntity);

        buyingChatRoomRepository.exitCurrentUsers(buyingChatRoomEntity.getId());
        buyingUsersRepository.delete(buyingUsersEntity);
    }

    public BuyingChatRoomEnterEnumType BuyingChatRoomEnter(Long roomId, String username){

        BuyingChatRoomEntity buyingChatRoomEntity = buyingChatRoomRepository.findById(roomId).get();
        UserEntity userEntity = userRepository.findByUsername(username).get();
        BuyingUsersEntity buyingUsersEntity = buyingUsersRepository.findBybuyingChatRoomEntityAndUser(buyingChatRoomEntity, userEntity);

        if(buyingChatRoomEntity.getUsers().contains(buyingUsersEntity)) {
            return BuyingChatRoomEnterEnumType.enter;
        }else if(buyingChatRoomEntity.getLimitUsers() > buyingChatRoomEntity.getCurrentUsers()){
            BuyingUsersEnumType authorizationType;

            if(username.equals(buyingChatRoomEntity.getOwner()))
                authorizationType = BuyingUsersEnumType.manager;
            else
                authorizationType = BuyingUsersEnumType.member;

             buyingUsersEntity = BuyingUsersEntity.builder()
            .user(userEntity)
            .buyingChatRoomEntity(buyingChatRoomEntity)
            .authorization(authorizationType)
            .build();

            buyingChatRoomRepository.enterCurrentUsers(buyingChatRoomEntity.getId());
            buyingUsersRepository.save(buyingUsersEntity);

            return BuyingChatRoomEnterEnumType.greeting;
        }else{
            return BuyingChatRoomEnterEnumType.failed;
        }
    }

    public Page<BuyingChatRoomEntity> getBuyingRooms(PageRequest request){
        return buyingChatRoomRepository.findAll(request);
    }

    @Transactional
    public void createBuyingRoom(HttpSession session, BuyingDTO buyingDTO){

        UserEntity userEntity = (UserEntity) session.getAttribute("userId");

        BuyingChatRoomEntity buyingChatRoomEntity = BuyingChatRoomEntity.builder()
        .title(buyingDTO.getTitle())
        .description(buyingDTO.getDescription())
        .owner(userEntity.getUsername())
        .price(Integer.parseInt(buyingDTO.getPrice()))
        .roomDate(buyingDTO.getDate())
        .roomTitle(buyingDTO.getRoomTitle())
        .currentUsers(1)
        .limitUsers(Integer.parseInt(buyingDTO.getLimit()))
        .build();


        if (buyingDTO.getFiles() != null && buyingDTO.getFiles().length > 0) {
            GenerateFile generateBuyingFiles = new GenerateFile(buyingDTO.getFiles());

            List<GenerateFileDTO> files = generateBuyingFiles.createFile();

            for(GenerateFileDTO file : files){
                BuyingFileEntity fileEntity = BuyingFileEntity.builder()
                        .name(file.getFileName())
                        .path(file.getPath())
                        .buyingChatRoomEntity(buyingChatRoomEntity)
                        .build();

                buyingFileRepository.save(fileEntity);
            }
        }

        BuyingUsersEntity buyingUsersEntity = BuyingUsersEntity.builder()
        .user(userEntity)
        .buyingChatRoomEntity(buyingChatRoomEntity)
        .authorization(BuyingUsersEnumType.manager)
        .build();

        buyingUsersRepository.save(buyingUsersEntity);
        buyingChatRoomRepository.save(buyingChatRoomEntity);
    }
}
