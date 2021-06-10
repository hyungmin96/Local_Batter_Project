package com.imageupload.example.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Role {
    ROLE_USER("ROLE_MEMBER"),
    ROLE_ADMIN("ROLE_ADMIN");

    private String value;
}
