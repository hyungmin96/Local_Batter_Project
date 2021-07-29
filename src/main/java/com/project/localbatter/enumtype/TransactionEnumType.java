package com.project.localbatter.enumtype;

public enum TransactionEnumType {
    
    cart("cart"),
    delete("delete"),
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
