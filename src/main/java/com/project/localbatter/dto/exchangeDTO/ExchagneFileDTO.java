package com.project.localbatter.dto.exchangeDTO;

import com.project.localbatter.dto.GenerateFileDTO;
import com.project.localbatter.entity.Exchange.ClientExchangeEntity;
import com.project.localbatter.entity.Exchange.ExchangeFileEntity;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ExchagneFileDTO {
    private String name;
    private String path;
    private Long groupId;
    private int size;

    public ExchagneFileDTO(GenerateFileDTO groupFileDTO) {
        this.name = groupFileDTO.getName();
        this.path = groupFileDTO.getPath();
    }

    public ExchangeFileEntity toEntity(ClientExchangeEntity entity){
        return ExchangeFileEntity.builder()
                .name(name)
                .path(path)
                .client(entity)
                .build();
    }
}
