package com.imageupload.example.imageupload;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.UUID;

import com.imageupload.example.entity.FileEntity;
import com.imageupload.example.entity.BoardEntity;
import com.imageupload.example.repositories.BoardRepository;
import com.imageupload.example.repositories.FileRepository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestPropertySource("classpath:application.yml")
public class CoupangServiceTest {

    private static Logger log = LogManager.getLogger();
    final String root = "D:\\Spring projects\\SpringBoot LocalBatter\\src\\main\\downloads\\";

    @Autowired
    private BoardRepository boardRep;

    @Autowired
    private FileRepository fileRep;

    @Test
    void 쿠팡_게시글_가져오기_테스트() throws IOException {

        int page = 1;
        int boardNums = 10;
        String category = "긴급";

        for (int i = 1; i <= page; i++) {

            String url = "https://www.coupang.com/np/categories/178255?page=" + i;

            Document doc = Jsoup.connect(url).get();
            String content = doc.select("#searchOptionForm").html();

            String[] items = content.split("baby-product-wrap");
            items[0] = null;

            for (String item : items) {
                if (item != null) {
                    String title = item.split("<div class=\"name\">")[1].split("</div>")[0];
                    String price = item.split("-value\">")[1].split("<")[0];
                    String src = "https://thumbnai" + item.split("src=\"//thumbnai")[1].split("\"")[0];

                    BoardEntity vo = BoardEntity.builder().category(category).descryption(title).location("대전").price(price)
                            .title(title).writer("2").build();

                    boardRep.save(vo);

                    urlImageDownload(vo, src);

                    log.info(title + " / " + price);

                    if(boardNums <= i){
                        return;
                    }else{
                        boardNums ++;
                    }
                }
            }

        }

    }

    void urlImageDownload(BoardEntity vo, String src) throws IOException {

        String filename = UUID.randomUUID().toString();

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

        FileEntity filevo = FileEntity.builder().tempName(filename + ".jpg").filePath(root + filename + ".jpg")
                .originName(filename).fileSize(filesize).board(vo).build();

        fileRep.save(filevo);

    }

}
