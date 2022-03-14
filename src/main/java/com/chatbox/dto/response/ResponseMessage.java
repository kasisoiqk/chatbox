package com.chatbox.dto.response;

public class ResponseMessage {

    public enum ResponseType {
        OK,
        FAILED
    }

    private ResponseType type;
    private String message;
    private Object data;

    public ResponseMessage() {
    }

    public ResponseMessage(ResponseType type, String message, Object data) {
        this.type = type;
        this.message = message;
        this.data = data;
    }

    public ResponseType getType() {
        return type;
    }

    public void setType(ResponseType type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
