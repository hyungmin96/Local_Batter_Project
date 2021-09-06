package com.project.localbatter.GroupTest;

import com.project.localbatter.dto.Group.GroupCreateDTO;
import com.project.localbatter.entity.GroupEntity;
import com.project.localbatter.repositories.GroupRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Random;

@SpringBootTest
public class GroupServiceTest {

    @Autowired private GroupRepository groupRepository;

    @Test
    void 그룹만들기_테스트(){

        final String[] randomProfileArray = new String[]
        {
            "group_task_98px.png",
            "natural_food_98px.png",
            "potted_plant_98px.png"
        };

        for (int i = 0; i < 20; i++) {
            String randomProfile = randomProfileArray[new Random().nextInt(randomProfileArray.length - 1)];
            GroupCreateDTO groupDTO = new GroupCreateDTO();
            groupDTO.setTitle("테스트 그룹 생성" + i);
            groupDTO.setOwner("1");
            groupDTO.setTags("#test1 #test2");
            groupDTO.setType(GroupEntity.roomEnumType.공개);
            groupDTO.setChk_1("true");
            groupDTO.setChk_2("false");
            groupDTO.setChk_3("false");
            groupDTO.setLocation("대전 유성구 용산동");
            groupDTO.setDescription("테스트 그룹 생성 설명란" + i);
            groupDTO.setFilePath(randomProfile);
            GroupEntity groupEntity = groupDTO.toEntity();
            groupRepository.save(groupEntity);
        }
    }

    @Test
    void 그룹_교환게시글_작성_테스트(){

    }

}
