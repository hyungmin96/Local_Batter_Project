package com.project.localbatter.ExchangeTest;

import com.project.localbatter.dto.exchangeDTO.LocalBatterServiceDTO;
import com.project.localbatter.entity.Exchange.LocalBatterServiceEntity;
import com.project.localbatter.repositories.Exchange.LocalBatterServiceRepository;
import com.project.localbatter.repositories.NotificationRepository;
import com.project.localbatter.repositories.UserRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

@SpringBootTest
@TestPropertySource("classpath:application.yml")
public class LocalBatterServiceTest {

    @Autowired private LocalBatterServiceRepository localBatterServiceRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private NotificationRepository notificationRepository;
    @Autowired private JPAQueryFactory queryFactory;
    private final Logger log = LogManager.getLogger();

    @Test
    void 로컬바터서비스_등록_테스트(){

        for(int i = 1; i <= 15; i ++){
            LocalBatterServiceDTO localBatterServiceDTO = new LocalBatterServiceDTO();
            localBatterServiceDTO.setExchangeAddr("교환장소" + i);
            localBatterServiceDTO.setExchangeDetailAddr("교환 세부장소" + i);
            localBatterServiceDTO.setExchangeLatitude("교환장소 경도");
            localBatterServiceDTO.setPrice("" + i);
            localBatterServiceDTO.setExchangeLongitude("교환장소 위도");
            localBatterServiceDTO.setReceiveAddr("물품수령 장소" + i);
            localBatterServiceDTO.setReceiveDetailAddr("물품수령 세부장소" + i);
            localBatterServiceDTO.setReceiveLatitude("물품수령 경도");
            localBatterServiceDTO.setReceiveLongitude("물품수령 위도");
            localBatterServiceDTO.setRequest("요청사항" + i);
            localBatterServiceDTO.setUserId(1L);
            localBatterServiceDTO.setWriterClientJoinId(4L);
            LocalBatterServiceEntity localBatterServiceEntity = localBatterServiceDTO.toEntity();

            localBatterServiceRepository.save(localBatterServiceEntity);
        }
    }

    @Test
    void 로컬바터서비스_조회_테스트(){

        List<LocalBatterServiceEntity> items = localBatterServiceRepository.findTop5ByOrderById();
        for(LocalBatterServiceEntity entity : items)
            log.info("등록된 서비스 목록" + entity.getId());

    }
}
