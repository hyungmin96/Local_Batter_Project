package com.imageupload.example.entitylistener;

import javax.persistence.PreUpdate;
import com.imageupload.example.entity.TransactionEntity;
import com.imageupload.example.entity.TransactionEnumType;

public class TransactionListener {
    
    @PreUpdate
    void setTransactionResult(TransactionEntity entity){
        if(entity.getBuyerComplete() != null && entity.getSellerComplete() != null){
            entity.setTransactionResult("complete");
            entity.setType(TransactionEnumType.complete);
        }
    }

}
