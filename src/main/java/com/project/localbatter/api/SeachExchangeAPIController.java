package com.project.localbatter.api;

import com.project.localbatter.api.group.GroupBoardApiController.ResponseGroupExchangeDTO;
import com.project.localbatter.dto.Group.GroupPageDTO;
import com.project.localbatter.services.SearchExchangeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SeachExchangeAPIController {

    private final SearchExchangeService searchExchangeService;

    @GetMapping("/api/search/get_exchange_list/")
    public Page<ResponseGroupExchangeDTO> getSearchExchangeList(@RequestParam String search, @RequestParam int page, @RequestParam int display) {
        Pageable pageRequest = PageRequest.of(page, display);
        return searchExchangeService.getSearchExchangeList(search, pageRequest);
    }

    @GetMapping("/api/search/get_group_list/")
    public Page<GroupPageDTO> getSearchGroupList(@RequestParam String search, @RequestParam int page, @RequestParam int display){
        Pageable pageRequest = PageRequest.of(page, display);
        return searchExchangeService.getSearchGroupList(search, pageRequest);
    }

}