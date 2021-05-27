package com.imageupload.example.Vo;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PageableVo {
    
    private String search;
    private String display;
    private String order;
    private String page;

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
