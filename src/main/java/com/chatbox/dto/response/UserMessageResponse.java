package com.chatbox.dto.response;

public class UserMessageResponse {
    private Long id;
    private String name;
    private String avatar;
    private Boolean isNew;
    private Boolean isSender;
    private Boolean isFavourite;
    private Boolean isGroup;
    private String content;
    private String time;

    private String code;
    private String nameSender;

    public UserMessageResponse() {
    }

    public UserMessageResponse(Long id, String name, String avatar, Boolean isNew, Boolean isSender,
                               Boolean isFavourite, Boolean isGroup, String content, String time) {
        this.id = id;
        this.name = name;
        this.avatar = avatar;
        this.isNew = isNew;
        this.isSender = isSender;
        this.isFavourite = isFavourite;
        this.isGroup = isGroup;
        this.content = content;
        this.time = time;
    }

    public UserMessageResponse(Long id, String name, String avatar, Boolean isNew, Boolean isSender, Boolean isFavourite,
                               Boolean isGroup, String content, String time, String code, String nameSender) {
        this.id = id;
        this.name = name;
        this.avatar = avatar;
        this.isNew = isNew;
        this.isSender = isSender;
        this.isFavourite = isFavourite;
        this.isGroup = isGroup;
        this.content = content;
        this.time = time;
        this.code = code;
        this.nameSender = nameSender;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Boolean getNew() {
        return isNew;
    }

    public void setNew(Boolean aNew) {
        isNew = aNew;
    }

    public Boolean getSender() {
        return isSender;
    }

    public void setSender(Boolean sender) {
        isSender = sender;
    }

    public Boolean getGroup() {
        return isGroup;
    }

    public void setGroup(Boolean group) {
        isGroup = group;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Boolean getFavourite() {
        return isFavourite;
    }

    public void setFavourite(Boolean favourite) {
        isFavourite = favourite;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNameSender() {
        return nameSender;
    }

    public void setNameSender(String nameSender) {
        this.nameSender = nameSender;
    }
}
