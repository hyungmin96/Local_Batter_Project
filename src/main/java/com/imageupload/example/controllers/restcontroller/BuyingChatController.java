package com.imageupload.example.controllers.restcontroller;

import com.imageupload.example.dto.BuyingDTO;
import com.imageupload.example.entity.BuyingChatRoomEntity;
import com.imageupload.example.services.BuyingService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/buying")
public class BuyingChatController {
    
    private final BuyingService buyingService;

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

}
