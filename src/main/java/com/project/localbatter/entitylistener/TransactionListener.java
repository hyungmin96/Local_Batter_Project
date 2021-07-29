package com.project.localbatter.entitylistener;

import javax.persistence.PreUpdate;

import com.project.localbatter.entity.TransactionEntity;
import com.project.localbatter.enumtype.TransactionEnumType;

public class TransactionListener {
    
    @PreUpdate
    void setTransactionResult(TransactionEntity entity){
        if(entity.getBuyerComplete() != null && entity.getSellerComplete() != null){
            entity.setTransactionResult("complete");
            entity.setType(TransactionEnumType.complete);
        }
    }

}
