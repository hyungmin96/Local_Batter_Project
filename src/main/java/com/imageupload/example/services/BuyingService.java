package com.imageupload.example.services;

import com.imageupload.example.repositories.BuyingChatRepository;
import com.imageupload.example.repositories.BuyingChatRoomRepository;
import com.imageupload.example.repositories.BuyingFileRepository;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BuyingService {
    
    private final BuyingChatRepository buyingChatRepository;
    private final BuyingFileRepository buyingFileRepository;
    private final BuyingChatRoomRepository buyingChatRoomRepository;

}
