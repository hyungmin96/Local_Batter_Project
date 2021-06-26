package com.imageupload.example.repositories;

public enum UserJoinRommEnumType {
    
    disconnected("disconnected"),
    connected("connected");

    public final String value;

    private UserJoinRommEnumType(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    } 

}
