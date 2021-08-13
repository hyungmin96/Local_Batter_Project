package com.project.localbatter.api.exchange;

import com.project.localbatter.dto.Group.GroupBoardDTO;
import com.project.localbatter.dto.TransactionDTO;
import com.project.localbatter.dto.exchangeDTO.ClientExchangeDTO;
import com.project.localbatter.entity.Exchange.ClientExchangeEntity;
import com.project.localbatter.entity.Exchange.WriterClientJoinEntity;
import com.project.localbatter.entity.GroupBoardEntity;
import com.project.localbatter.entity.GroupBoardFileEntity;
import com.project.localbatter.services.ExchangeService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @GetMapping("/view/board/client_reqeust_list")
    public Page<ResponseClientRequestDTO> getBoardClientReqeust(ClientExchangeDTO clientExchangeDTO){
        PageRequest page = PageRequest.of(clientExchangeDTO.getPage(), clientExchangeDTO.getDisplay());
        return groupExchangeService.getBoardClientRequestList(clientExchangeDTO, page);
    }

    @GetMapping("/my/request_list")
    public Page<ResponseRequestListDTO> getRequestList(TransactionDTO transactionDTO){
        Pageable page = PageRequest.of(transactionDTO.getPage(), transactionDTO.getDisplay());
        return groupExchangeService.getRequestList(transactionDTO, page);
    }

    @PostMapping("/cancel/request")
    public void cancelRequestEntity(ClientExchangeDTO clientExchangeDTO){
        groupExchangeService.cancelRequest(clientExchangeDTO);
    }

    @PostMapping("/select/request")
    public ResponseRequestExchangeDTO selectRequestEntity(ClientExchangeDTO clientExchangeDTO){
        return groupExchangeService.selectRequest(clientExchangeDTO);
    }

    @GetMapping("/my/get_write_list")
    public Page<ResponseWrtierExchangeDTO> getWriterBoards(TransactionDTO transactionDTO) {
        Pageable page = PageRequest.of(transactionDTO.getPage(), transactionDTO.getDisplay());
        return groupExchangeService.getWriterBoards(transactionDTO, page);
    }

    @PostMapping("/client/post")
    public ResponseClientDTO clientPost(ClientExchangeDTO clientExchangeDTO){
        ClientExchangeEntity clientExchangeEntity = groupExchangeService.clientPost(clientExchangeDTO);
        return new ResponseClientDTO(clientExchangeEntity);
    }

    @GetMapping("/view/board")
    public ResponseExchagneInfo getExchangeInfo(GroupBoardDTO groupBoardDTO){
        return new ResponseExchagneInfo(groupExchangeService.getExchangeInfo(groupBoardDTO));
    }

    @Setter @Getter
    public static class ResponseClientRequestDTO{

        private Long userId;
        private String username;
        private String userProfile;
        private String filename;
        private ClientExchangeEntity clientExchange;

        public ResponseClientRequestDTO(Long clientId, String username, String userProfile, String filename, ClientExchangeEntity clientExchange) {
            this.userId = clientId;
            this.username = username;
            this.userProfile = userProfile;
            this.filename = filename;
            this.clientExchange = clientExchange;
        }
    }

    @Setter @Getter
    public static class ResponseWrtierExchangeDTO{
        private Long writerId; // writer user id
        private Long writerExchangeId; // writerExchange Entity id
        private Long boardId;
        private int reqeustCount;
        private String title;
        private String content;
        private LocalDateTime regTime;
        private String files;

        public ResponseWrtierExchangeDTO(Long writerId, Long writerExchangeId, Long boardId, int reqeustCount, String title, String content, LocalDateTime regTime, String files) {
            this.writerId = writerId;
            this.writerExchangeId = writerExchangeId;
            this.boardId = boardId;
            this.reqeustCount = reqeustCount;
            this.title = title;
            this.content = content;
            this.regTime = regTime;
            this.files = files;
        }
    }

    @Setter @Getter
    public static class ResponseRequestListDTO{
        private Long clientId; // user Id
        private Long clientExchangeId; // client's request board id
        private String clientUsername;
        private String clientProfile;
        private Long writerId;
        private Long boardId;
        private String title;
        private String content;
        private WriterClientJoinEntity.status status;
        private LocalDateTime regTime;
        private String files;

        public ResponseRequestListDTO(Long clientId, Long clientExchangeId, String clientUsername, String clientProfile, Long writerId, Long boardId, String title, String content, WriterClientJoinEntity.status status, LocalDateTime regTime, String files) {
            this.clientId = clientId;
            this.clientExchangeId = clientExchangeId;
            this.clientUsername = clientUsername;
            this.clientProfile = clientProfile;
            this.writerId = writerId;
            this.boardId = boardId;
            this.title = title;
            this.content = content;
            this.status = status;
            this.regTime = regTime;
            this.files = files;
        }
    }

    @Setter @Getter
    public static class ResponseExchagneInfo{
        private Long id;
        private String username;
        private String profilePath;
        private LocalDateTime regTime;
        private Long boardId;
        private String title;
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
            this.title = entity.getTitle();
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
        private Long writerId;      // writer user id
        private String username;    // writer user name
        private String profilePath; // wrtier profile image path
        private String title;
        private String content;     // client's product description
        private String price;       // add price with product or none product
        private String request;     // client's request content
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
