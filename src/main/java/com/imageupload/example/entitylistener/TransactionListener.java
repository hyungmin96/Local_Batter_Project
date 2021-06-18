package com.imageupload.example.entitylistener;

import javax.persistence.PreUpdate;
import com.imageupload.example.entity.TransactionEntity;

public class TransactionListener {
    
    @PreUpdate
    void setTransactionResult(TransactionEntity entity){
        if(entity.getBuyerComplete() != null && entity.getSellerComplete() != null){
            System.out.println("postupdate");
            entity.setTransactionResult("complete");
        }
    }

}
