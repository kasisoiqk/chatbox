package com.chatbox.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity(name = "groups")
@Table(name = "groups")
public class Group {
    public enum GroupType {
        PUBLIC, PRIVATE
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 3, max = 100)
    @Column(unique = true)
    private String name;

    @NotBlank
    private String avatar;

    @NotBlank
    @Column(columnDefinition = "TEXT")
    private String description;

    @NotBlank
    private String code;

    private Long numberMember;

    @NotBlank
    private String type;

    public Group() {
    }

    public Group(Long id, String name, String avatar, String description, GroupType type) {
        this.id = id;
        this.name = name;
        this.avatar = avatar;
        this.description = description;
        this.type = type.name();
    }

    public Group(String name, String description) {
        this.name = name;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getNumberMember() {
        return numberMember;
    }

    public void setNumberMember(Long numberMember) {
        this.numberMember = numberMember;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(GroupType type) {
        this.type = type.name();
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", avatar='" + avatar + '\'' +
                ", description='" + description + '\'' +
                ", code='" + code + '\'' +
                ", numberMember=" + numberMember +
                ", type='" + type + '\'' +
                '}';
    }
}
