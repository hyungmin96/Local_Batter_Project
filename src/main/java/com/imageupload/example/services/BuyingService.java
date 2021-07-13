package com.imageupload.example.services;

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

import java.util.List;

@Service
@RequiredArgsConstructor
public class BuyingService {
    
    private final BuyingChatRepository buyingChatRepository;
    private final BuyingFileRepository buyingFileRepository;
    private final BuyingChatRoomRepository buyingChatRoomRepository;
    private final BuyingUsersRepository buyingUsersRepository;
    private final UserRepository userRepository;

    public List<BuyingChatEntity> getChats(Long roomId){
        return buyingChatRepository.findAllByRoomId(roomId);
    }

    public BuyingChatRoomEntity getRoomInfo(Long id){
        return buyingChatRoomRepository.findById(id).get();
    }

    public void saveChatEntity(BuyingChatMessageDTO messageDTO){

        BuyingChatEntity buyingChatEntity = BuyingChatEntity.builder()
                .roomId(messageDTO.getRoomId())
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
            GenerateFile generateBuyingFiles = new GenerateFile(buyingDTO.getFiles());

            List<GenerateFileDTO> files = generateBuyingFiles.createFile();

            for(GenerateFileDTO file : files){
                BuyingFileEntity fileEntity = BuyingFileEntity.builder()
                        .name(file.getName())
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
