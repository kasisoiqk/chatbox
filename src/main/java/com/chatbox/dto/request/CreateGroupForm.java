package com.chatbox.dto.request;

import com.chatbox.model.GroupUser;

import java.util.List;

public class CreateGroupForm {
    private String name;
    private String description;
    private List<CreateGroupUserForm> members;

    public CreateGroupForm() {
    }

    public CreateGroupForm(String name, String description, List<CreateGroupUserForm> members) {
        this.name = name;
        this.description = description;
        this.members = members;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<CreateGroupUserForm> getMembers() {
        return members;
    }

    public void setMembers(List<CreateGroupUserForm> members) {
        this.members = members;
    }

    @Override
    public String toString() {
        return "CreateGroupForm{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", members=" + members +
                '}';
    }
}
