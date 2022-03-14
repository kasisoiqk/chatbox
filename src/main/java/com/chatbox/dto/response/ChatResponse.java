package com.chatbox.dto.response;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class ChatResponse {
    private Long id;
    private String type;
    private String sendToType;
    private String content;
    private int hasRecalled; // 1: true ; 0: false
    private String time;
    private Long senderId;
    private Long recipientId;

    public ChatResponse() {
    }

    public ChatResponse(Long id, String type, String sendToType, String content, int hasRecalled, Date time, Long senderId, Long recipientId) {
        this.id = id;
        this.type = type;
        this.sendToType = sendToType;
        this.content = content;
        this.hasRecalled = hasRecalled;
        this.time = convertTimeToString(time);
        this.senderId = senderId;
        this.recipientId = recipientId;
    }

    private String convertTimeToString(Date time) {
        String pattern = "HH:mm:ss dd/MM/yyyy";
        DateFormat df = new SimpleDateFormat(pattern);
        df.setTimeZone(TimeZone.getTimeZone("GMT+0700"));

        return df.format(time);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSendToType() {
        return sendToType;
    }

    public void setSendToType(String sendToType) {
        this.sendToType = sendToType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getHasRecalled() {
        return hasRecalled;
    }

    public void setHasRecalled(int hasRecalled) {
        this.hasRecalled = hasRecalled;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public Long getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(Long recipientId) {
        this.recipientId = recipientId;
    }

    @Override
    public String toString() {
        return "ChatResponse{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", sendToType='" + sendToType + '\'' +
                ", content='" + content + '\'' +
                ", hasRecalled=" + hasRecalled +
                ", time='" + time + '\'' +
                ", senderId=" + senderId +
                ", recipientId=" + recipientId +
                '}';
    }
}
