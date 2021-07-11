package com.imageupload.example.services;

import com.imageupload.example.components.GenerateBuyingFiles;
import com.imageupload.example.dto.BuyingChatMessageDTO;
import com.imageupload.example.dto.BuyingDTO;
import com.imageupload.example.entity.*;
import com.imageupload.example.repositories.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BuyingService {
    
    private final BuyingChatRepository buyingChatRepository;
    private final BuyingFileRepository buyingFileRepository;
    private final BuyingChatRoomRepository buyingChatRoomRepository;
    private final BuyingUsersRepository buyingUsersRepository;
    private final UserRepository userRepository;

    public BuyingChatRoomEntity getRoomInfo(Long id){
        return buyingChatRoomRepository.findById(id).get();
    }

    public void saveChatEntity(BuyingChatMessageDTO messageDTO){

        BuyingChatRoomEntity buyingChatRoomEntity = buyingChatRoomRepository.findById(messageDTO.getRoomId()).get();

        BuyingChatEntity buyingChatEntity = BuyingChatEntity.builder()
                .buyingChatRoomEntity(buyingChatRoomEntity)
                .localDate(messageDTO.getLocalDate())
                .type(messageDTO.getType())
                .profilePath(messageDTO.getProfilePath())
                .message(messageDTO.getMessage())
                .sender(messageDTO.getSender()).build();

        buyingChatRepository.save(buyingChatEntity);
    }

    public void exitRoom(Long roomId, String username){

        UserEntity userEntity = userRepository.findByUsername(username).get();

        BuyingChatRoomEntity buyingChatRoomEntity = buyingChatRoomRepository.findById(roomId).get();
        BuyingUsersEntity buyingUsersEntity = buyingUsersRepository.findBybuyingChatRoomEntityAndUser(buyingChatRoomEntity, userEntity);

        buyingUsersRepository.delete(buyingUsersEntity);
    }

    public boolean BuyingChatRoomEnter(Long roomId, String username){

        BuyingChatRoomEntity buyingChatRoomEntity = buyingChatRoomRepository.findById(roomId).get();

        if(buyingChatRoomEntity.getLimitUsers() > buyingChatRoomEntity.getCurrentUsers()){
            UserEntity userEntity = userRepository.findByUsername(username).get();
            BuyingUsersEntity buyingUsersEntity = buyingUsersRepository.findBybuyingChatRoomEntityAndUser(buyingChatRoomEntity, userEntity);

            if(buyingUsersEntity == null){
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

                buyingUsersRepository.save(buyingUsersEntity);
                buyingChatRoomRepository.updateCurrentUsers(buyingChatRoomEntity.getId());

                return true;
            }
        }
        return false;
    }

    public Page<BuyingChatRoomEntity> getBuyingRooms(PageRequest request){
        return buyingChatRoomRepository.findAll(request);
    }

    @Transactional
    public void createBuyingRoom(BuyingDTO buyingDTO){

        UserDetails user = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        UserEntity userEntity = userRepository.findByUsername(user.getUsername()).get();

        BuyingChatRoomEntity buyingChatRoomEntity = BuyingChatRoomEntity.builder()
        .title(buyingDTO.getTitle())
        .description(buyingDTO.getDescription())
        .owner(user.getUsername())
        .price(Integer.parseInt(buyingDTO.getPrice()))
        .roomDate(buyingDTO.getDate())
        .roomTitle(buyingDTO.getRoomTitle())
        .currentUsers(1)
        .limitUsers(Integer.parseInt(buyingDTO.getLimit()))
        .build();


        if (buyingDTO.getFiles() != null && buyingDTO.getFiles().length > 0) {
            GenerateBuyingFiles generateBuyingFiles = new GenerateBuyingFiles(buyingChatRoomEntity, buyingDTO.getFiles());
            buyingFileRepository.saveAll(generateBuyingFiles.generateFileVoList());
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
