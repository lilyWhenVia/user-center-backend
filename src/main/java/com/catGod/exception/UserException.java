package com.catGod.exception;

import lombok.Data;

/**
 * Created by lily via on 2023/10/7 17:36
 */

public class UserException extends RuntimeException{

    private final String code;

    private  String message;

    private final String description;

    public UserException(String code, String message, String description) {
        super(message);
        this.code = code;
        this.description = description;
    }

    public UserException(String description) {
        this.description = description;
        this.code = "";
    }

    public String getCode() {
        return code;
    }

public String getDescription() {
        return description;
    }

}
