package com.project.localbatter.services;

import com.project.localbatter.api.exchange.GroupExchangeApiController.ResponseClientAndWriterBoard;
import com.project.localbatter.api.exchange.GroupExchangeApiController.ResponseClientRequestDTO;
import com.project.localbatter.api.exchange.GroupExchangeApiController.ResponseRequestListDTO;
import com.project.localbatter.api.exchange.GroupExchangeApiController.ResponseWrtierExchangeDTO;
import com.project.localbatter.components.ExchangeQueryComponent;
import com.project.localbatter.components.GenerateFile;
import com.project.localbatter.dto.GenerateFileDTO;
import com.project.localbatter.dto.Group.GroupBoardDTO;
import com.project.localbatter.dto.TransactionDTO;
import com.project.localbatter.dto.exchangeDTO.ClientExchangeDTO;
import com.project.localbatter.dto.exchangeDTO.ExchagneFileDTO;
import com.project.localbatter.dto.exchangeDTO.ExchangeChatMessageDTO;
import com.project.localbatter.dto.exchangeDTO.WriterClientJoinDTO;
import com.project.localbatter.entity.Exchange.ClientExchangeEntity;
import com.project.localbatter.entity.Exchange.ExchangeFileEntity;
import com.project.localbatter.entity.Exchange.WriterClientJoinEntity;
import com.project.localbatter.entity.Exchange.WriterExchangeEntity;
import com.project.localbatter.entity.GroupBoardEntity;
import com.project.localbatter.repositories.Exchange.ClientExchangeFileRepository;
import com.project.localbatter.repositories.Exchange.ClientExchangeRepository;
import com.project.localbatter.repositories.Exchange.WriterClientJoinRepository;
import com.project.localbatter.repositories.Exchange.WriterExchangeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.project.localbatter.api.exchange.GroupExchangeApiController.ResponseRequestExchangeDTO;

@Service
@RequiredArgsConstructor
public class ExchangeService {

    private final ClientExchangeRepository clientExchangeRepository;
    private final ClientExchangeFileRepository clientExchangeFileRepository;
    private final WriterExchangeRepository writerExchangeRepository;
    private final WriterClientJoinRepository writerClientJoinRepository;
    private final GenerateFile generateFile;
    private final ExchangeQueryComponent exchangeQueryComponent; // Using @Component annotaion to get exchange data
    private final ExchangeChatService exchangeChatService;

    /*  post method service */

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
            message.setUserId(writerClientJoinEntity.getWriterId());
            message.setTargetId(writerClientJoinEntity.getClientId());
            message.setTargetUsername(exchangeChatMessageDTO.getTargetUsername());
            message.setTargetProfile(exchangeChatMessageDTO.getTargetProfile());
            message.setMessage("새로운 채팅방이 개설되었습니다.");
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
        ClientExchangeEntity clientExchangeEntity = writerClientJoinEntity.getClientExchangeEntity();
        clientExchangeRepository.delete(clientExchangeEntity);
    }

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

    // View the client'request board and writer's board infomation
    // client와 writer 게시글 정보를 조회
    public ResponseClientAndWriterBoard getClientAndWriterBoard(TransactionDTO transactionDTO){
        return exchangeQueryComponent.getClientAndWriterBoard(transactionDTO);
    }
    
    // View the list of clients requested for exchange to Writer's aritcle
    // 해당 게시글에 교환요청한 client 요청 게시글 조회
    @Transactional(readOnly = true)
    public Page<ResponseClientRequestDTO> getBoardClientRequestList(ClientExchangeDTO clientExchangeDTO, Pageable page){
        return exchangeQueryComponent.getBoardClientRequestList(clientExchangeDTO, page);
    }

    // view writer's board List
    @Transactional(readOnly = true)
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
