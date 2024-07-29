package com.cheongna.loginapi.common.controller;

import com.cheongna.loginapi.common.model.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ControllerAdvice {

    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Message> internalServerError(Exception e) {
        log.error("========== INTERNAL_SERVER_ERROR !! {}", e);

        Message message = new Message(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류", HttpStatus.INTERNAL_SERVER_ERROR.value());

        return new ResponseEntity<>(message, message.getStatus());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<Message> badRequest(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        FieldError fe = bindingResult.getFieldError();
        log.warn("========== BAD_REQUEST !! {}", e);

        String msg = "";
        if (fe != null) {
            msg = "Request Error" + " " + fe.getField() + "=" + fe.getRejectedValue() + " (" + fe.getDefaultMessage() + ")";
        }

        Message message = new Message(HttpStatus.BAD_REQUEST, msg, HttpStatus.BAD_REQUEST.value());

        return new ResponseEntity<>(message, message.getStatus());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResponseEntity<Message> required(HttpMessageNotReadableException e) {

        log.warn("========== BAD_REQUEST !! {}", e);

        Message message = new Message(HttpStatus.BAD_REQUEST, "", HttpStatus.BAD_REQUEST.value());

        return new ResponseEntity<>(message, message.getStatus());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public ResponseEntity<Message> requiredParameter(MissingServletRequestParameterException e) {

        Message message = new Message(HttpStatus.BAD_REQUEST, "Request Error : [" + e.getParameterName() + "] 는 필수 입니다.", HttpStatus.BAD_REQUEST.value());

        return new ResponseEntity<>(message, message.getStatus());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Message> httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {

        log.warn("========== METHOD_NOT_ALLOWED !! {}", e);

        Message message = new Message(HttpStatus.METHOD_NOT_ALLOWED, "", HttpStatus.METHOD_NOT_ALLOWED.value());

        return new ResponseEntity<>(message, message.getStatus());
    }
}
