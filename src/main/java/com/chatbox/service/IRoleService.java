package com.chatbox.service;

import com.chatbox.model.Role;

import java.util.Optional;

public interface IRoleService {
    Optional<Role> findByName(String name);
}
