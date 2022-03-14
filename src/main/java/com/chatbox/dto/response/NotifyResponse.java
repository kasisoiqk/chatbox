package com.chatbox.dto.response;

public class NotifyResponse {
    public enum ResponseType {
        MESSAGE, GROUP, CREATED_GROUP;
    }

    private String type;
    private Object data;

    public NotifyResponse() {
    }

    public NotifyResponse(ResponseType type, Object data) {
        this.type = type.name();
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public void setType(ResponseType type) {
        this.type = type.name();
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
