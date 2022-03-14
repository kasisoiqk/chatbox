package com.chatbox.service.impl;

import com.chatbox.model.Role;
import com.chatbox.repositories.IRoleRepository;
import com.chatbox.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements IRoleService {

    @Autowired
    private IRoleRepository roleRepository;

    @Override
    public Optional<Role> findByName(String name) {
        return roleRepository.findByName(name);
    }
}
