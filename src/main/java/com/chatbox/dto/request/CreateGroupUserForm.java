package com.chatbox.dto.request;

import com.chatbox.model.GroupUser;

public class CreateGroupUserForm {
    private Long id;
    private String role;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(GroupUser.GroupRole role) {
        this.role = role.name();
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", role=" + role +
                '}';
    }
}
