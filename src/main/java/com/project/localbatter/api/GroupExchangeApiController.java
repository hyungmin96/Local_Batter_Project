package com.project.localbatter.api;

import com.project.localbatter.api.group.GroupBoardApiController.ResponseBoardDTO;
import com.project.localbatter.dto.Group.GroupBoardDTO;
import com.project.localbatter.dto.exchangeDTO.ClientExchangeDTO;
import com.project.localbatter.entity.Exchange.ClientExchangeEntity;
import com.project.localbatter.entity.Exchange.WriterClientJoinEntity;
import com.project.localbatter.entity.GroupBoardEntity;
import com.project.localbatter.entity.GroupBoardFileEntity;
import com.project.localbatter.services.ExchangeService;
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

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
    public ResponseExchagneInfo getExchangeInfo(GroupBoardDTO groupBoardDTO){
        return groupExchangeService.getExchangeInfo(groupBoardDTO);
    }

    @GetMapping("/get_board_list")
    public Page<ResponseBoardDTO> getBoardList(GroupBoardDTO groupBoardDTO){
        Pageable page = PageRequest.of(groupBoardDTO.getPage(), groupBoardDTO.getDisplay(), Sort.Direction.ASC, "id");
        JPAQuery<ResponseBoardDTO> query = groupExchangeService.getBoardList(groupBoardDTO, page);
        return new PageImpl<>(query.fetch(), page, query.fetchCount());
    }

    @Setter @Getter
    @NoArgsConstructor
    public static class ResponseExchagneInfo{
        private Long id;
        private String username;
        private String profilePath;
        private LocalDateTime regTime;
        private Long boardId;
        private String content;
        private List<String> boardFiles;

        // 게시글 거래위치 DTO
        private String residence;
        private String detailAddr;
        private String buildingcode;
        private String location;
        private String locationDetail;
        private String longitude;
        private String latitude;
        private String preferTime;

        public ResponseExchagneInfo(GroupBoardEntity entity) {
            this.id = entity.getGroupUserJoinEntity().getUser().getId();
            this.username = entity.getGroupUserJoinEntity().getUser().getUsername();
            this.profilePath = entity.getGroupUserJoinEntity().getUser().getProfilePath();
            this.regTime = entity.getRegTime();
            this.boardId = entity.getBoardId();
            this.content = entity.getContent();
            this.boardFiles = entity.getFiles().stream().map(GroupBoardFileEntity::getName).collect(Collectors.toList());
            if (entity.getWriterExchangeEntity() != null) {
                this.residence = entity.getWriterExchangeEntity().getResidence();
                this.detailAddr = entity.getWriterExchangeEntity().getDetailAddr();
                this.buildingcode = entity.getWriterExchangeEntity().getBuildingcode();
                this.longitude = entity.getWriterExchangeEntity().getLongitude();
                this.latitude = entity.getWriterExchangeEntity().getLatitude();
                this.location = entity.getWriterExchangeEntity().getLocation();
                this.locationDetail = entity.getWriterExchangeEntity().getLocationDetail();
                this.preferTime = entity.getWriterExchangeEntity().getExchangeTime();
            }
        }
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
            this.clientUserId = entity.getClientId();
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
