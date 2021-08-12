package com.project.localbatter.api.exchange;

import com.project.localbatter.dto.TransactionDTO;
import com.project.localbatter.entity.Exchange.WriterClientJoinEntity;
import com.project.localbatter.entity.GroupBoardEntity;
import com.project.localbatter.entity.GroupBoardFileEntity;
import com.project.localbatter.entity.UserEntity;
import com.project.localbatter.services.TransactionService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/view/transaction")
public class TransactionApiController {

    private final TransactionService transactionService;

    @GetMapping("/my/request_list")
    public Page<ResponseRequestListDTO> getRequestList(TransactionDTO transactionDTO){
        Pageable page = PageRequest.of(transactionDTO.getPageNum(), transactionDTO.getPageSize());
        return transactionService.getRequestList(transactionDTO, page);
    }

    @Setter @Getter
    public static class ResponseRequestListDTO{
        private Long clientId; // user Id
        private String clientUsername;
        private String clientProfile;
        private Long writerId;
        private Long exchangeId;
        private Long boardId;
        private String title;
        private String content;
        private WriterClientJoinEntity.status status;
        private LocalDateTime regTime;
        private List<String> files;

        public ResponseRequestListDTO(WriterClientJoinEntity entity, GroupBoardEntity board, UserEntity user) {
            this.clientId = entity.getClientId();
            this.writerId = entity.getWriterId();
            this.status = entity.getStatus();
            this.boardId = board.getBoardId();
            this.title = board.getTitle();
            this.content = board.getContent();
            this.clientUsername = user.getUsername();
            this.clientProfile = user.getProfilePath();
            this.regTime = entity.getRegTime();
            this.files = board.getFiles().stream().map(GroupBoardFileEntity::getName).collect(Collectors.toList());
        }
    }

}
