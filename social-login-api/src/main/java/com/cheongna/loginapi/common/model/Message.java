package com.cheongna.loginapi.common.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class Message {
    private HttpStatus status;
    private int statusCode;
    private String message;
    private String subMessage;
    private Object date;

    public Message() {}

    public Message(HttpStatus status, String message, int statusCode) {
        this.status = status;
        this.message = message;
        this.statusCode = statusCode;
    }
}
