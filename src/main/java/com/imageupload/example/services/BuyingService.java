package com.imageupload.example.services;

import com.imageupload.example.components.GenerateBuyingFiles;
import com.imageupload.example.dto.BuyingDTO;
import com.imageupload.example.entity.BuyingChatEntity;
import com.imageupload.example.entity.BuyingChatRoomEntity;
import com.imageupload.example.entity.BuyingUsersEntity;
import com.imageupload.example.repositories.BuyingChatRepository;
import com.imageupload.example.repositories.BuyingChatRoomRepository;
import com.imageupload.example.repositories.BuyingFileRepository;
import com.imageupload.example.repositories.BuyingUsersRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    public Page<BuyingChatRoomEntity> getBuyingRooms(PageRequest request){
        return buyingChatRoomRepository.findAll(request);
    }

    @Transactional
    public void createBuyingRoom(BuyingDTO buyingDTO){

        UserDetails user = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        BuyingChatRoomEntity buyingChatRoomEntity = BuyingChatRoomEntity.builder()
        .title(buyingDTO.getTitle())
        .description(buyingDTO.getDescription())
        .owner(user.getUsername())
        .price(Integer.parseInt(buyingDTO.getPrice()))
        .roomDate(buyingDTO.getDate())
        .roomTitle(buyingDTO.getRoomTitle())
        .limitUsers(Integer.parseInt(buyingDTO.getLimit()))
        .build();
        

        if (buyingDTO.getFiles() != null && buyingDTO.getFiles().length > 0) {
            GenerateBuyingFiles generateBuyingFiles = new GenerateBuyingFiles(buyingChatRoomEntity, buyingDTO.getFiles());
            buyingFileRepository.saveAll(generateBuyingFiles.generateFileVoList());
        }

        BuyingUsersEntity buyingUsersEntity = BuyingUsersEntity.builder()
        .user(user.getUsername())
        .buyingChatRoomEntity(buyingChatRoomEntity)
        .build();

        BuyingChatEntity buyingChatEntity = BuyingChatEntity.builder()
        .buyingChatRoomEntity(buyingChatRoomEntity).build();
        
        buyingUsersRepository.save(buyingUsersEntity);

        buyingChatRepository.save(buyingChatEntity);

        buyingChatRoomRepository.save(buyingChatRoomEntity);

    }

}
