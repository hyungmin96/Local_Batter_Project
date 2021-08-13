package com.project.localbatter.dto.exchangeDTO;

import com.project.localbatter.entity.Exchange.ClientExchangeEntity;
import com.project.localbatter.entity.Exchange.ExchangeFileEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Setter @Getter
public class ClientExchangeDTO implements Serializable {

    private int page;
    private int display;

    private Long clientId; // exchange entity unique id
    private Long clientExchangeId;
    private Long userId; // client user id
    private Long writerId; // writer user id
    private String username;
    private String userProfile;
    private Long boardId; // 교환을 요청한 게시글 번호
    private String title;
    private String content;
    private String price;
    private String request;
    private String address;
    private String longtitude;
    private String latitude;
    private MultipartFile[] images;
    private List<ExchangeFileEntity> files = new ArrayList<>();

    public void addFile(ExchangeFileEntity file){
        this.files.add(file);
    }

    public ClientExchangeEntity toEntity(){
            return ClientExchangeEntity
                    .builder()
                    .title(title)
                    .content(content)
                    .boardId(boardId)
                    .request(request)
                    .address(address)
                    .longtitude(longtitude)
                    .latitude(latitude)
                    .price(price)
                    .userId(userId)
                    .build();
    }
}
