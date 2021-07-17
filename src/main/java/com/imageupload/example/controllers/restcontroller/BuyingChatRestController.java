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

import javax.servlet.http.HttpSession;
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
    public ResponseEntity<String> deleteRoom(HttpSession session, @RequestParam Long roomId, @RequestParam String username){
        buyingService.deleteRoom(session, roomId, username);
        return new ResponseEntity<String>("Success", HttpStatus.OK);
    }

    @PostMapping("/exit")
    public ResponseEntity<String> exitRoom(HttpSession session, @RequestParam Long roomId, @RequestParam String username){

        BuyingChatMessageDTO messageDTO = BuyingChatMessageDTO.builder()
                .roomId(roomId)
                .type("exit")
                .sender(username)
                .message(null)
                .localDate(LocalDate.now().toString())
                .build();

        buyingChatService.sendBuyingRoomToChat(messageDTO);
        buyingService.exitRoom(session, roomId);
        return new ResponseEntity<String>("Success", HttpStatus.OK);
    }

    @PostMapping("/enter")
    public ResponseEntity<String> enterRoom(@RequestParam Long room_Id, @RequestParam Long user_id, @RequestParam String user_name){
        switch(buyingService.BuyingChatRoomEnter(room_Id, user_name)) {
            case greeting:
                BuyingChatMessageDTO messageDTO = BuyingChatMessageDTO.builder()
                        .roomId(room_Id)
                        .type("greeting")
                        .sender(user_name)
                        .message(null)
                        .localDate(LocalDate.now().toString())
                        .build();

                buyingChatService.sendBuyingRoomToChat(messageDTO);
                return new ResponseEntity<String>("Success", HttpStatus.OK);
            case enter:
                return new ResponseEntity<String>("Enter", HttpStatus.OK);
        }
        return new ResponseEntity<String>("Failed - exceed Users number", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/getlist")
    public Page<BuyingChatRoomEntity> getBuyingRoomList(@RequestParam int page, @RequestParam int display){
        PageRequest request = PageRequest.of(page, display, Sort.Direction.DESC, "id");
        return buyingService.getBuyingRooms(request);
    }

    @PostMapping("/create")
    public ResponseEntity<String> createBuyingRoom(HttpSession session, BuyingDTO buyingDTO){
        buyingService.createBuyingRoom(session, buyingDTO);
        return new ResponseEntity<String>("success", HttpStatus.OK);
    }

    @GetMapping("/getRoomInfo")
    public BuyingChatRoomEntity getRoomInfo(@RequestParam Long roomId){
        return buyingService.getRoomInfo(roomId);
    }

}
