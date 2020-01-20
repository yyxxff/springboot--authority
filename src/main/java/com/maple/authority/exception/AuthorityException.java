package com.maple.authority.exception;

import lombok.Data;

@Data
public class AuthorityException extends Exception {

    private Integer code;

    private String message;

    public AuthorityException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
