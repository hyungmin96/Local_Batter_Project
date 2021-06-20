package com.imageupload.example.entity;

public enum TransactionEnumType {
    
    cart("cart"),
    transaction("transaction"), 
    complete("complete");

    public final String value;

    private TransactionEnumType(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }

}
