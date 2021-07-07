package com.imageupload.example.controllers.restcontroller;

import com.imageupload.example.dto.BuyingDTO;
import com.imageupload.example.services.BuyingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/buying")
public class BuyingChatController {
    
    private final BuyingService buyingService;

    @PostMapping("/create")
    public ResponseEntity<String> createBuyingRoom(BuyingDTO buyingDTO){



        return new ResponseEntity<String>("success", HttpStatus.OK);

    }

}
