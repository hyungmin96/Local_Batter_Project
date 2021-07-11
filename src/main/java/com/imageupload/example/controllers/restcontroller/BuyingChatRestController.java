package com.imageupload.example.controllers.restcontroller;

import com.imageupload.example.dto.BuyingDTO;
import com.imageupload.example.entity.BuyingChatRoomEntity;
import com.imageupload.example.services.BuyingService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/buying")
public class BuyingChatRestController {

    private final BuyingService buyingService;

    @PostMapping("/exit")
    public ResponseEntity<String> exitRoom(@RequestParam Long roomId, @RequestParam String username){
        buyingService.exitRoom(roomId, username);
        return new ResponseEntity<String>("Success", HttpStatus.OK);
    }

    @PostMapping("/enter")
    public ResponseEntity<String> enterRoom(@RequestParam String roomId, @RequestParam String username){
        buyingService.BuyingChatRoomEnter(Long.parseLong(roomId), username);
        return new ResponseEntity<String>("success", HttpStatus.OK);
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
