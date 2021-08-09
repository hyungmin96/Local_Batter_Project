package com.project.localbatter.api;

import com.project.localbatter.dto.Group.GroupBoardDTO;
import com.project.localbatter.dto.exchangeDTO.ClientExchangeDTO;
import com.project.localbatter.entity.Exchange.ClientExchangeEntity;
import com.project.localbatter.entity.Exchange.WriterClientJoinEntity;
import com.project.localbatter.entity.GroupBoardEntity;
import com.project.localbatter.services.ExchangeService;
import com.project.localbatter.api.group.GroupBoardApiController.ResponseBoardDTO;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/exchange")
public class GroupExchangeApiController {

    private final ExchangeService groupExchangeService;

    @PostMapping("/cancel/request")
    public void cancelRequestEntity(ClientExchangeDTO clientExchangeDTO){
        groupExchangeService.cancelRequest(clientExchangeDTO);
    }

    @PostMapping("/select/request")
    public ResponseRequestExchangeDTO selectRequestEntity(ClientExchangeDTO clientExchangeDTO){
        return groupExchangeService.selectRequest(clientExchangeDTO);
    }

    @GetMapping("/get_request_list")
    public Page<ResponseClientDTO> getClientRequestList(GroupBoardDTO groupBoardDTO) {
        Pageable page = PageRequest.of(groupBoardDTO.getPage(), groupBoardDTO.getDisplay(), Sort.Direction.ASC, "id");
        JPAQuery<ResponseClientDTO> query = groupExchangeService.getClientRequestList(groupBoardDTO, page);
        return new PageImpl<>(query.fetch(), page, query.fetchCount());
    }

    @PostMapping("/client/post")
    public ResponseClientDTO clientPost(ClientExchangeDTO clientExchangeDTO){
        ClientExchangeEntity clientExchangeEntity = groupExchangeService.clientPost(clientExchangeDTO);
        return new ResponseClientDTO(clientExchangeEntity);
    }

    @GetMapping("/view/board")
    public ResponseBoardDTO getExchangeInfo(GroupBoardDTO groupBoardDTO){
        GroupBoardEntity groupBoardEntity = groupExchangeService.getExchangeInfo(groupBoardDTO);
        return new ResponseBoardDTO(groupBoardEntity);
    }

    @GetMapping("/get_board_list")
    public Page<ResponseBoardDTO> getBoardList(GroupBoardDTO groupBoardDTO){
        Pageable page = PageRequest.of(groupBoardDTO.getPage(), groupBoardDTO.getDisplay(), Sort.Direction.ASC, "id");
        JPAQuery<ResponseBoardDTO> query = groupExchangeService.getBoardList(groupBoardDTO, page);
        return new PageImpl<>(query.fetch(), page, query.fetchCount());
    }

    @Setter @Getter
    public static class ResponseRequestExchangeDTO{

        private Long userId; // writer id
        private String username;
        private String userProfile;
        private Long clientUserId;
        private String clilentUsername;
        private String clientProfile;
        private Long boardId;
        private Long clientId;

        public ResponseRequestExchangeDTO(WriterClientJoinEntity entity){
            this.userId = entity.getWriterId();
//            this.username = entity.getContent();
//            this.userProfile = entity.getPrice();
            this.clientUserId = entity.getClientId();
//            this.clilentUsername = entity.getUserId();
//            this.clientProfile = entity.getContent();
            this.boardId = entity.getWriterExchangeEntity().getId();
            this.clientId = entity.getClientExchangeEntity().getId();
        }
    }

    @Setter @Getter
    @NoArgsConstructor
    public static class ResponseClientDTO{
        private Long clientId;
        private Long userId;
        private Long writerId;
        private String username;
        private String userProfile;
        private String content; // client's product description
        private String price; // add price with product or none product
        private String request; // client's request content
        private WriterClientJoinEntity.status status; // To check exchange was progressing
        private List<String> file;

        public ResponseClientDTO(ClientExchangeEntity entity) {
            this.userId = entity.getUserId();
            this.content = entity.getContent();
            this.price = entity.getPrice();
            this.request = entity.getRequest();
        }
    }

}
