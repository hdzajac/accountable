package com.accountable.app.utils;

public class InfoMessage implements Message {

    private final String messageType = "info";
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
