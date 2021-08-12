package com.project.localbatter.imageupload;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestPropertySource("classpath:application.yml")
public class CoupangServiceTest {

    private static Logger log = LogManager.getLogger();
    private final String root = "D:\\Spring projects\\SpringBoot LocalBatter\\src\\main\\downloads\\";;

//    @Test
//    void 쿠팡_게시글_가져오기_테스트() throws IOException {
//
//        int page = 10;
//        int boardNums = 600;
//        String category = "일반";
//
//        for (int i = 1; i <= page; i++) {
//
//            String url = "https://www.coupang.com/np/categories/178255?page=" + i;
//
//            Document doc = Jsoup.connect(url).get();
//            String content = doc.select("#searchOptionForm").html();
//
//            String[] items = content.split("baby-product-wrap");
//            items[0] = null;
//
//            for (String item : items) {
//                if (item != null) {
//                    String title = item.split("<div class=\"name\">")[1].split("</div>")[0];
//                    String price = item.split("-value\">")[1].split("<")[0];
//                    String src = "https://thumbnai" + item.split("src=\"//thumbnai")[1].split("\"")[0];
//
//                    urlImageDownload(vo, src);
//
//                    log.info(title + " / " + price);
//
//                    if(boardNums <= i){
//                        return;
//                    }else{
//                        boardNums ++;
//                    }
//                }
//            }
//
//        }
//
//    }
//
//    void urlImageDownload(BoardEntity vo, String src) throws IOException {
//
//        String filename = UUID.randomUUID().toString();
//
//        URL imageSrc = new URL(src);
//        InputStream in = new BufferedInputStream(imageSrc.openStream());
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//        byte[] buf = new byte[1024];
//        int n = 0;
//        while (-1 != (n = in.read(buf))) {
//            out.write(buf, 0, n);
//        }
//        out.close();
//        in.close();
//        byte[] response = out.toByteArray();
//
//        FileOutputStream fos = new FileOutputStream(root + filename + ".jpg");
//        fos.write(response);
//        Long filesize = fos.getChannel().size();
//        fos.close();
//
//        FileEntity filevo = FileEntity.builder().tempName(filename + ".jpg").filePath(root + filename + ".jpg")
//                .originName(filename).fileSize(filesize).board(vo).build();
//
//        fileRep.save(filevo);
//
//    }

}
