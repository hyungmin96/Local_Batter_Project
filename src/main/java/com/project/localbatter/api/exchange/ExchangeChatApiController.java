package com.project.localbatter.api.exchange;

import com.project.localbatter.services.ExchangeChatService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/exchange/chat")
public class ExchangeChatApiController {

    private final ExchangeChatService exchangeChatService;

    @GetMapping("/get_chat_list")
    public List<ResponseChatListDTO> getChatList(Long userId){
        return exchangeChatService.getChatList(userId);
    }

    @Setter @Getter
    public static class ResponseChatListDTO{

        private Long writerId;
        private String writerUsername;
        private String writerProfile;
        private Long clientId;
        private String clientUsername;
        private String clientProfile;
        private String message;

        public ResponseChatListDTO(Long writerId, String writerUsername, String writerProfile, Long clientId, String clientUsername, String clientProfile, String message) {
            this.writerId = writerId;
            this.writerUsername = writerUsername;
            this.writerProfile = writerProfile;
            this.clientId = clientId;
            this.clientUsername = clientUsername;
            this.clientProfile = clientProfile;
            this.message = message;
        }
    }

}
