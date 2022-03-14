package com.chatbox.model;

import javax.persistence.*;

@Entity(name = "groups_user")
@Table(name = "groups_user")
public class GroupUser {
    public enum GroupRole {
        GROUP_ADMIN, GROUP_MEMBER
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Long groupId;

    private String role;

    public GroupUser() {
    }

    public GroupUser(Long userId, Long groupId, GroupRole role) {
        this.userId = userId;
        this.groupId = groupId;
        this.role = role.name();
    }

    public GroupUser(Long userId, Long groupId) {
        this.userId = userId;
        this.groupId = groupId;
    }

    public GroupUser(Long userId, GroupRole role) {
        this.userId = userId;
        this.role = role.name();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(GroupRole role) {
        this.role = role.name();
    }

    @Override
    public String toString() {
        return "GroupUser{" +
                "id=" + id +
                ", userId=" + userId +
                ", groupId=" + groupId +
                ", role='" + role + '\'' +
                '}';
    }
}
