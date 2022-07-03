package com.dini.stop.bean;

import org.springframework.http.HttpStatus;

import java.util.Map;

public class ResponseContext<T> {

    private int code;

    private HttpStatus httpStatus;

    private Map<String, String> messages;

    private T context;

    public ResponseContext() {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Map<String, String> getMessages() {
        return messages;
    }

    public void setMessages(Map<String, String> messages) {
        this.messages = messages;
    }

    public T getContext() {
        return context;
    }

    public void setContext(T context) {
        this.context = context;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
}
