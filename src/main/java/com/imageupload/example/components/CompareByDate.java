package com.imageupload.example.components;

import java.util.Comparator;

import com.imageupload.example.entity.ChatEntity;
import com.imageupload.example.entity.UserJoinRoomEntity;

public class CompareByDate implements Comparator<UserJoinRoomEntity> {

    @Override
    public int compare(UserJoinRoomEntity o1, UserJoinRoomEntity o2) {

        int chatSizeofO1 = o1.getRoomEntity().getChats().size();
        int chatSizeofO2 = o2.getRoomEntity().getChats().size();
        
        ChatEntity chatEntityofO1 = o1.getRoomEntity().getChats().get(chatSizeofO1);
        ChatEntity chatEntityofO2 = o2.getRoomEntity().getChats().get(chatSizeofO2);
        
        return chatEntityofO1.getCreateTime().compareTo(chatEntityofO2.getCreateTime());
    }
    
}
