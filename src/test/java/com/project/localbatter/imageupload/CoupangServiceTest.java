package com.project.localbatter.imageupload;

import com.project.localbatter.dto.Group.GroupBoardDTO;
import com.project.localbatter.dto.Group.GroupBoardFileDTO;
import com.project.localbatter.entity.Exchange.WriterExchangeEntity;
import com.project.localbatter.entity.GroupBoardEntity;
import com.project.localbatter.entity.GroupUserJoinEntity;
import com.project.localbatter.repositories.GroupBoardFileRepository;
import com.project.localbatter.repositories.GroupBoardRepository;
import com.project.localbatter.repositories.GroupUserJoinRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.io.*;
import java.net.URL;
import java.util.UUID;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestPropertySource("classpath:application.yml")
public class CoupangServiceTest {

    private static Logger log = LogManager.getLogger();
    private final String root = "D:\\Spring projects\\SpringBoot LocalBatter\\src\\main\\downloads\\";;
    @Autowired private GroupBoardRepository groupBoardRepository;
    @Autowired private GroupBoardFileRepository groupBoardFileRepository;
    @Autowired private GroupUserJoinRepository groupUserJoinRepository;

    @Test
    @Transactional
    @Rollback(false)
    void 쿠팡_게시글_가져오기_테스트() throws IOException {

        int page = 10;
        int boardNums = 600;
        String category = "일반";

        for (int i = 1; i <= page; i++) {

            String url = "https://www.coupang.com/np/categories/178255?page=" + i;

            Document doc = Jsoup.connect(url).get();
            String content = doc.select("#searchOptionForm").html();

            String[] items = content.split("baby-product-wrap");
            items[0] = null;

            for (String item : items) {
                if (item != null) {
                    String title = item.split("<div class=\"name\">")[1].split("</div>")[0].trim().replaceAll("\\s+", " ");
                    String price = item.split("-value\">")[1].split("<")[0].replaceAll(",", "");
                    String src = "https://thumbnai" + item.split("src=\"//thumbnai")[1].split("\"")[0];

                    String filename = UUID.randomUUID().toString();
                    urlImageDownload(filename, src);

                    GroupUserJoinEntity groupUserJoinEntity = groupUserJoinRepository.findById(3L).get();

                    WriterExchangeEntity writerExchangeEntity = WriterExchangeEntity.builder()
                            .price(price)
                            .exchangeOn(WriterExchangeEntity.ExchageOnOff.on)
                            .location("대전 유성구 용산동")
                            .locationDetail("대덕테크노벨리 11단지")
                            .longitude("36.419001602579314")
                            .latitude("127.39287710981833")
                            .preferTime("상관없음")
                            .build();

                    GroupBoardDTO groupBoardDTO = new GroupBoardDTO();
                    groupBoardDTO.setThumnbnailPath(filename + ".jpg");
                    groupBoardDTO.setBoardLike(0);
                    groupBoardDTO.setGroupId(2L);
                    groupBoardDTO.setCategory("exchange");
                    groupBoardDTO.setTitle(title);
                    groupBoardDTO.setContent(title);
                    groupBoardDTO.setPrice(price);
                    groupBoardDTO.setWriter(1L);
                    groupBoardDTO.setLocation("대전 유성구 용산동");
                    groupBoardDTO.setLocationDetail("대덕테크노벨리 11단지");
                    groupBoardDTO.setExchangeOn(WriterExchangeEntity.ExchageOnOff.on);

                    GroupBoardEntity groupBoardEntity = groupBoardDTO.toEntity(groupUserJoinEntity, writerExchangeEntity);

                    groupBoardRepository.save(groupBoardEntity);

                    GroupBoardFileDTO groupBoardFileDTO = new GroupBoardFileDTO();
                    groupBoardFileDTO.setGroupId(2L);
                    groupBoardFileDTO.setName(filename + ".jpg");
                    groupBoardFileDTO.setPath(root + filename + ".jpg");
                    groupBoardFileDTO.setSize(0);

                    groupBoardFileRepository.save(groupBoardFileDTO.toEntity(groupBoardEntity));

                    log.info(title + " / " + price);

                    if(boardNums <= i) return; else boardNums ++;
                }
            }
        }
    }

    void urlImageDownload(String filename, String src) throws IOException {
        URL imageSrc = new URL(src);
        InputStream in = new BufferedInputStream(imageSrc.openStream());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        int n = 0;
        while (-1 != (n = in.read(buf))) {
            out.write(buf, 0, n);
        }
        out.close();
        in.close();
        byte[] response = out.toByteArray();

        FileOutputStream fos = new FileOutputStream(root + filename + ".jpg");
        fos.write(response);
        Long filesize = fos.getChannel().size();
        fos.close();
    }
}
