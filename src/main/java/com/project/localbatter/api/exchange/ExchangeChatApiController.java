package com.project.localbatter.api.exchange;

import com.project.localbatter.dto.exchangeDTO.ExchangeChatMessageDTO;
import com.project.localbatter.entity.Exchange.LocalBatterServiceEntity;
import com.project.localbatter.services.Exchange.ExchangeChatService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/exchange/chat")
public class ExchangeChatApiController {

    private final ExchangeChatService exchangeChatService;

    @GetMapping("/get_chat_list")
    public List<ResponseChatListDTO> getChatList(Long userId){
        return exchangeChatService.getExchageChatList(userId);
    }

    @GetMapping("/get_chat_item")
    public Page<ResponseChatListDTO> getChatItems(int page, int display, Long exchangeId){
        Pageable pageRequest = PageRequest.of(page, display);
        return exchangeChatService.getChatItems(exchangeId, pageRequest);
    }

    @PostMapping("/upload/image")
    public ExchangeChatMessageDTO uploadImageToChat(ExchangeChatMessageDTO messageDTO, MultipartFile[] files){
        return exchangeChatService.uploadImageToChat(messageDTO, files);
    }

    @GetMapping("/get/service/item")
    public ResponseServiceDTO getService(@RequestParam("userId") Long userId, @RequestParam("exchangeId") Long exchangeId){
        return exchangeChatService.getService(userId, exchangeId);
    }

    @Setter @Getter
    public static class ResponseServiceDTO{
        private Long userId;
        private String receiveAddr;
        private String receiveDetailAddr;
        private String receiveLongitude;
        private String receiveLatitude;
        private Long writerClientJoinId;
        private String exchangeAddr;
        private String exchangeDetailAddr;
        private String exchangeLongitude;
        private String exchangeLatitude;
        private String request;
        private String price;

        public ResponseServiceDTO(LocalBatterServiceEntity entity){
            this.userId = entity.getUserId();
            this.receiveAddr = entity.getReceiveAddr();
            this.receiveDetailAddr = entity.getReceiveDetailAddr();
            this.receiveLongitude = entity.getReceiveLongitude();
            this.receiveLatitude = entity.getReceiveLatitude();
            this.writerClientJoinId = entity.getWriterClientJoinId();
            this.exchangeAddr = entity.getExchangeAddr();
            this.exchangeDetailAddr = entity.getExchangeDetailAddr();
            this.exchangeLongitude = entity.getExchangeLongitude();
            this.exchangeLatitude = entity.getExchangeLatitude();
            this.request = entity.getRequest();
            this.price = entity.getPrice();
        }
    }

    @Setter @Getter
    public static class ResponseChatListDTO{

        private Long senderId; // 채팅방의 로그인유저 정보
        private String senderUsername;
        private String senderProfile;

        private Long receiveId; // 채팅방의 상대유저 정보
        private String receiveUsername;
        private String receiveProfile;

        private Long exchangeId;
        private ExchangeChatMessageDTO.ExchangeMessageType type;
        private Long messageId;
        private String message;
        private String coordinate;
        private LocalDateTime regTime;

        public ResponseChatListDTO(Long userId, String username, String userProfile, Long targetId, String targetUsername, String targetProfile, Long exchangeId, ExchangeChatMessageDTO.ExchangeMessageType type, String message, String coordinate, LocalDateTime regTime) {
            this.senderId = userId;
            this.senderUsername = username;
            this.senderProfile = userProfile;
            this.receiveId = targetId;
            this.receiveUsername = targetUsername;
            this.receiveProfile = targetProfile;
            this.exchangeId = exchangeId;
            this.type = type;
            this.message = message;
            this.coordinate = coordinate;
            this.regTime = regTime;
        }

        public ResponseChatListDTO(Long userId, String username, String userProfile, Long targetId, String targetUsername, String targetProfile, Long exchangeId, Long messageId, ExchangeChatMessageDTO.ExchangeMessageType type, String message, String coordinate, LocalDateTime regTime) {
            this.senderId = userId;
            this.senderUsername = username;
            this.senderProfile = userProfile;
            this.receiveId = targetId;
            this.receiveUsername = targetUsername;
            this.receiveProfile = targetProfile;
            this.exchangeId = exchangeId;
            this.messageId = messageId;
            this.type = type;
            this.message = message;
            this.coordinate = coordinate;
            this.regTime = regTime;
        }
    }
}
