package com.imageupload.example.services;

import com.imageupload.example.entity.GroupBoardEntity;
import com.imageupload.example.repositories.GroupBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GroupBoardService {

    private final GroupBoardRepository GroupBoardRepository;

    public Page<GroupBoardEntity> getBoardList(Long groupId, PageRequest request){
        return GroupBoardRepository.findAllBygroupId(groupId, request);
    }

}
