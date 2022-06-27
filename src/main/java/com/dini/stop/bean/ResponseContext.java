package com.dini.stop.bean;

import java.util.List;
import java.util.Map;

public class ResponseContext<T> {

    private int code;

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
}
