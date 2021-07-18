package com.imageupload.example.dto;

import com.imageupload.example.entity.GroupChatRoomEntity;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class GroupDTO {
    
    private String title;
    private String description;
    private String roomTitle;
    private String limit;
    private String chk_1; // 전체공개
    private String chk_2; // 일부공개
    private String chk_3; // 비공개
    private MultipartFile[] files;

    public GroupChatRoomEntity.roomEnumType getType(){
        if(chk_1.equals("1")) return GroupChatRoomEntity.roomEnumType.공개;
        else if(chk_2.equals("2")) return GroupChatRoomEntity.roomEnumType.일부공개;
        else return GroupChatRoomEntity.roomEnumType.비공개;
    }

}
