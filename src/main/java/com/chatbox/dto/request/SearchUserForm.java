package com.chatbox.dto.request;

import javax.validation.constraints.NotBlank;

public class SearchUserForm {
    @NotBlank
    private String type; // ALL, USERNAME, NAME
    @NotBlank
    private String content;

    public SearchUserForm() {
    }

    public SearchUserForm(String type, String content) {
        this.type = type;
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
