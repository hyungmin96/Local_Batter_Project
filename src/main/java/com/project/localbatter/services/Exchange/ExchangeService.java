package com.project.localbatter.services.Exchange;

import com.project.localbatter.components.ExchangeQueryComponent;
import com.project.localbatter.components.GenerateFile;
import com.project.localbatter.dto.GenerateFileDTO;
import com.project.localbatter.dto.Group.GroupBoardDTO;
import com.project.localbatter.dto.TransactionDTO;
import com.project.localbatter.dto.exchangeDTO.*;
import com.project.localbatter.entity.Exchange.*;
import com.project.localbatter.entity.GroupBoardEntity;
import com.project.localbatter.entity.ProfileEntity;
import com.project.localbatter.repositories.Exchange.*;
import com.project.localbatter.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.project.localbatter.api.exchange.GroupExchangeApiController.*;

@Service
@RequiredArgsConstructor
public class ExchangeService {

    private final ClientExchangeRepository clientExchangeRepository;
    private final ClientExchangeFileRepository clientExchangeFileRepository;
    private final WriterExchangeRepository writerExchangeRepository;
    private final WriterClientJoinRepository writerClientJoinRepository;
    private final ReviewRepository reviewRepository;
    private final LocalBatterServiceRepository localBatterServiceRepository;
    private final UserRepository userRepository;
    private final GenerateFile generateFile;
    private final ExchangeQueryComponent exchangeQueryComponent; // Using @Component annotaion to get exchange data
    private final ExchangeChatService exchangeChatService;
    /*  post method service */

    // 교환완료시 상대방 사용자의 reivew 작성
    // Write review of target profile
    public ReviewDTO writeReview(ReviewDTO reviewDTO){
        WriterClientJoinEntity writerClientJoinEntity = writerClientJoinRepository.findByExchangeId(reviewDTO.getWriterClientJoinId());
        ProfileEntity profileEntity = userRepository.getById(reviewDTO.getReviewReceiveId()).getProfile();
        profileEntity.updateMannerScore(reviewDTO.getScore());
        reviewRepository.save(reviewDTO.toEntity(writerClientJoinEntity));
        return reviewDTO;
    }

    // 교환확정
    // Confirm exchange
    @Transactional
    public void confirmExchange(Long userId, Long exchangeId){
        WriterClientJoinEntity writerClientJoinEntity = writerClientJoinRepository.findByExchangeId(exchangeId);
        writerClientJoinEntity.confirmExchangeUpdate(userId);
        if(writerClientJoinEntity.getWriterStatus() == WriterClientJoinEntity.status.complete
                && writerClientJoinEntity.getClientStatus() == WriterClientJoinEntity.status.complete){
            writerClientJoinEntity.getWriterExchangeEntity().exchangeComplete();
            writerClientJoinEntity.setComplete();
        }
        writerClientJoinRepository.save(writerClientJoinEntity);
    }

    // 사용자가 신청한 Batter Service 게시글을 삭제
    // delete user's LocalBatter Service
    public ResponseEntity<String> deleteLocalBatterService(LocalBatterServiceDTO localBatterServiceDTO){
        localBatterServiceRepository.deleteBywriterClientJoinId(localBatterServiceDTO.getWriterClientJoinId());
        return new ResponseEntity<String>("해당 요청을 삭제하였습니다.", HttpStatus.OK);
    }

    // 사용자가 신청한 localBatter Service 등록
    // Save users's LocalBatter Service
    public LocalBatterServiceDTO saveLocalBatterService(LocalBatterServiceDTO localBatterServiceDTO){
        localBatterServiceRepository.save(localBatterServiceDTO.toEntity());
        return localBatterServiceDTO;
    }

    // writer 유저가 client 유저의 교환요청 수락
    // Accept to Request for exchange writer's user from client user
    @Transactional
    public ResponseRequestExchangeDTO accpetClientsRequest(TransactionDTO transactionDTO){
        ExchangeChatMessageDTO exchangeChatMessageDTO = exchangeQueryComponent.getWriterClientExchangeEntity(transactionDTO);
        WriterClientJoinEntity writerClientJoinEntity = writerClientJoinRepository.findByExchangeId(exchangeChatMessageDTO.getExchangeId());
        if(writerClientJoinEntity != null){
            // update exchange status
            writerClientJoinEntity.setProcess();
            // create MeesageDTo to send the message when create exchange chat room between writer and client
            ExchangeChatMessageDTO message = new ExchangeChatMessageDTO();
            message.setMessage("새로운 채팅방이 개설되었습니다.");
            message.setExchangeId(exchangeChatMessageDTO.getExchangeId());
            message.setType(ExchangeChatMessageDTO.ExchangeMessageType.enter);
            exchangeChatService.sendMessage(message);
            return new ResponseRequestExchangeDTO(writerClientJoinEntity);
        }
        return null;
    }

    // client 요청을 취소 및 삭제
    // reject the client's exchange request
    public void cancelRequest(ClientExchangeDTO clientExchangeDTO){
        WriterClientJoinEntity writerClientJoinEntity = writerClientJoinRepository.findByClientExchangeEntity(clientExchangeDTO.getClientExchangeId());
        writerClientJoinEntity.getWriterExchangeEntity().updateCount(-1);
        writerClientJoinRepository.delete(writerClientJoinEntity);
    }

    // Client request exchange to writer's board
    // client가 writer 게시글에 교환을 요청
    @Transactional
    public ClientExchangeEntity clientPost(ClientExchangeDTO clientExchangeDTO){
        WriterExchangeEntity writerExchangeEntity = writerExchangeRepository.findWriterExchangeEntityByboardId(clientExchangeDTO.getBoardId());
        List<GenerateFileDTO> clientExchangeFiles = generateFile.createFile(clientExchangeDTO.getImages());
        if(clientExchangeFiles.size() > 0) clientExchangeDTO.setThumbnail(clientExchangeFiles.get(0).getName());
        ClientExchangeEntity clientExchangeEntity = clientExchangeDTO.toEntity();
        clientExchangeRepository.save(clientExchangeEntity);
        WriterClientJoinDTO writerClientJoinEntity = new WriterClientJoinDTO(writerExchangeEntity, clientExchangeEntity);
        clientExchangeFiles.stream().map(ExchagneFileDTO::new).collect(Collectors.toList())
                .forEach(item -> {
                    ExchangeFileEntity file = item.toEntity(clientExchangeEntity);
                    clientExchangeDTO.addFile(file);
                    clientExchangeFileRepository.save(file);
                });
        writerExchangeEntity.updateCount(1);
        writerClientJoinRepository.save(writerClientJoinEntity.toEntity());
        return clientExchangeEntity;
    }

    /* Get method service */

    // 로컬바터서비스에 등록된 TOP10 Entities를 조회
    // View top 10 entities in localbatterservice Repository
    @Transactional(readOnly = true)
    public List<LocalBatterServiceEntity> getServiceList(){
        return localBatterServiceRepository.findTop5ByOrderById();
    }

    // View the client's reqeust for exchange info
    // client가 교환요청한 게시글의 정보를 조회
    public ResponseClientDTO getClientRequestExchangeInfo(Long exchangeId){
        return exchangeQueryComponent.getClientRequestExchangeInfo(exchangeId);
    }
    // View the client'request board and writer's board infomation
    // client와 writer 게시글 정보를 조회
    public ResponseClientAndWriterBoard getClientAndWriterBoard(TransactionDTO transactionDTO){
        return exchangeQueryComponent.getClientAndWriterBoard(transactionDTO);
    }
    // View the list of clients requested for exchange to Writer's aritcle
    // 해당 게시글에 교환요청한 client 요청 게시글 조회
    public Page<ResponseClientRequestDTO> getBoardClientRequestList(ClientExchangeDTO clientExchangeDTO, Pageable page){
        return exchangeQueryComponent.getBoardClientRequestList(clientExchangeDTO, page);
    }
    // view writer's exchanged board List
    public Page<ResponseWrtierExchangeDTO> getCompleteBoards(TransactionDTO transactionDTO, Pageable page){
        return exchangeQueryComponent.getCompleteBoards(transactionDTO, page);
    }
    // view writer's board List
    public Page<ResponseWrtierExchangeDTO> getWriterBoards(TransactionDTO transactionDTO, Pageable page){
        return exchangeQueryComponent.getWriterBoards(transactionDTO, page);
    }
    // view client's(login user) request exchange for board
    @Transactional(readOnly = true)
    public Page<ResponseRequestListDTO> getRequestList(TransactionDTO transactionDTO, Pageable page) {
        return exchangeQueryComponent.getRequestList(transactionDTO, page);
    }

    public GroupBoardEntity getExchangeInfo(GroupBoardDTO groupBoardDTO){
        return exchangeQueryComponent.getExchangeInfo(groupBoardDTO);
    }

}
