package com.imageupload.example.controllers.restcontroller;

import com.imageupload.example.components.GenerateFile;
import com.imageupload.example.dto.BuyingChatMessageDTO;
import com.imageupload.example.dto.BuyingDTO;
import com.imageupload.example.dto.GenerateFileDTO;
import com.imageupload.example.entity.BuyingChatEntity;
import com.imageupload.example.entity.BuyingChatRoomEntity;
import com.imageupload.example.enumtype.BuyingChatRoomEnterEnumType;
import com.imageupload.example.services.BuyingChatService;
import com.imageupload.example.services.BuyingService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/buying")
public class BuyingChatRestController {

    private final BuyingService buyingService;
    private final BuyingChatService buyingChatService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImgToChatRoom(BuyingChatMessageDTO messageDTO){
        buyingService.uploadImgToBuyingChatroom(messageDTO);
        buyingChatService.sendImageToBuyingChatRoom(messageDTO);
        return new ResponseEntity<String>("Success", HttpStatus.OK);
    }

    @PostMapping("/delete")
    public ResponseEntity<String> deleteRoom(@RequestParam Long roomId, @RequestParam String username){
        buyingService.deleteRoom(roomId, username);
        return new ResponseEntity<String>("Success", HttpStatus.OK);
    }

    @GetMapping("/chats")
    public List<BuyingChatEntity> getChats(@RequestParam Long roomId){
        return buyingService.getChats(roomId);
    }

    @PostMapping("/exit")
    public ResponseEntity<String> exitRoom(@RequestParam Long roomId, @RequestParam String username){

        BuyingChatMessageDTO messageDTO = BuyingChatMessageDTO.builder()
                .roomId(roomId)
                .type("exit")
                .sender(username)
                .message(null)
                .localDate(LocalDate.now().toString())
                .build();

        buyingChatService.sendBuyingRoomToChat(messageDTO);
        buyingService.exitRoom(roomId, username);
        return new ResponseEntity<String>("Success", HttpStatus.OK);
    }

    @PostMapping("/enter")
    public ResponseEntity<String> enterRoom(@RequestParam Long roomId, @RequestParam String username){
        switch(buyingService.BuyingChatRoomEnter(roomId, username)) {
            case greeting:
                BuyingChatMessageDTO messageDTO = BuyingChatMessageDTO.builder()
                        .roomId(roomId)
                        .type("greeting")
                        .sender(username)
                        .message(null)
                        .localDate(LocalDate.now().toString())
                        .build();

                buyingChatService.sendBuyingRoomToChat(messageDTO);
                return new ResponseEntity<String>("create success", HttpStatus.OK);
            case enter:
                return new ResponseEntity<String>("enter", HttpStatus.OK);
        }
        return new ResponseEntity<String>("failed - exceed Users number", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/getlist")
    public Page<BuyingChatRoomEntity> getBuyingRoomList(@RequestParam int page, @RequestParam int display){
        PageRequest request = PageRequest.of(page, display, Sort.Direction.DESC, "id");
        return buyingService.getBuyingRooms(request);
    }

    @PostMapping("/create")
    public ResponseEntity<String> createBuyingRoom(BuyingDTO buyingDTO){
        buyingService.createBuyingRoom(buyingDTO);
        return new ResponseEntity<String>("success", HttpStatus.OK);
    }

    @GetMapping("/getRoomInfo")
    public BuyingChatRoomEntity getRoomInfo(@RequestParam Long roomId){
        return buyingService.getRoomInfo(roomId);
    }

}
