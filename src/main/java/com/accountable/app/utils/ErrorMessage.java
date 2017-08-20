package com.accountable.app.utils;

public class ErrorMessage implements Message {

    private final String messageType = "error";
    private String content;

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public void setContent(String content) {
        this.content = content;
    }

    public String getMessageType() {
        return messageType;
    }
}
