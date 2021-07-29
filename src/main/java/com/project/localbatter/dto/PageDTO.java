package com.project.localbatter.dto;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PageDTO {
    
    private String search;
    private String page;
    private String display;
    private String order;

    public PageRequest getPageRequest(){
        return PageRequest.of(Integer.parseInt(page), Integer.parseInt(display), Sort.Direction.DESC, convertOrderToColumn());
    }

    private String convertOrderToColumn(){
        if(this.order.equals("accuracy"))
            this.order = "title";
        else if(this.order.equals("Bestprice"))
            this.order = "price";
        else
            this.order = "createTime";

        return this.order;
    }

}
