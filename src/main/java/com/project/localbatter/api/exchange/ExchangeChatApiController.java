package com.project.localbatter.api.exchange;

import com.project.localbatter.dto.exchangeDTO.ExchangeChatMessageDTO;
import com.project.localbatter.services.ExchangeChatService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

    @Setter @Getter
    public static class ResponseChatListDTO{

        private Long userId; // 채팅방의 로그인유저 정보
        private String username;
        private String userProfile;

        private Long targetId; // 채팅방의 상대유저 정보
        private String targetUsername;
        private String targetProfile;

        private Long exchangeId;
        private ExchangeChatMessageDTO.ExchangeMessageType type;
        private String message;
        private String coordinate;
        private LocalDateTime regTime;

        public ResponseChatListDTO(Long userId, String username, String userProfile, Long targetId, String targetUsername, String targetProfile, Long exchangeId, ExchangeChatMessageDTO.ExchangeMessageType type, String message, String coordinate, LocalDateTime regTime) {
            this.userId = userId;
            this.username = username;
            this.userProfile = userProfile;
            this.targetId = targetId;
            this.targetUsername = targetUsername;
            this.targetProfile = targetProfile;
            this.exchangeId = exchangeId;
            this.type = type;
            this.message = message;
            this.coordinate = coordinate;
            this.regTime = regTime;
        }
    }
}
