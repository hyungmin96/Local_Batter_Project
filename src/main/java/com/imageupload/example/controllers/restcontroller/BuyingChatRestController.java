package com.imageupload.example.controllers.restcontroller;

import com.imageupload.example.dto.BuyingChatMessageDTO;
import com.imageupload.example.dto.BuyingDTO;
import com.imageupload.example.entity.BuyingChatRoomEntity;
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

import java.time.LocalDate;
import java.util.Date;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/buying")
public class BuyingChatRestController {

    private final BuyingService buyingService;
    private final BuyingChatService buyingChatService;

    @PostMapping("/exit")
    public ResponseEntity<String> exitRoom(@RequestParam Long roomId, @RequestParam String username){

        BuyingChatMessageDTO messageDTO = BuyingChatMessageDTO.builder()
                .roomId(roomId)
                .type("exit")
                .sender(username)
                .message(null)
                .localDate(LocalDate.now().toString())
                .build();

        buyingChatService.exitUserToRoom(messageDTO);
        buyingService.exitRoom(roomId, username);
        return new ResponseEntity<String>("Success", HttpStatus.OK);
    }

    @PostMapping("/enter")
    public ResponseEntity<String> enterRoom(@RequestParam Long roomId, @RequestParam String username){

        if(buyingService.BuyingChatRoomEnter(roomId, username)){
            BuyingChatMessageDTO messageDTO = BuyingChatMessageDTO.builder()
                    .roomId(roomId)
                    .type("greeting")
                    .sender(username)
                    .message(null)
                    .localDate(LocalDate.now().toString())
                    .build();

            buyingChatService.greetingUserToRoom(messageDTO);
            return new ResponseEntity<String>("success", HttpStatus.OK);
        }else{
            return new ResponseEntity<String>("failed - exceed Users number", HttpStatus.BAD_REQUEST);
        }

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
