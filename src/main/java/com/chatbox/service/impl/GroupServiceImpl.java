package com.chatbox.service.impl;

import com.chatbox.dto.request.CreateGroupForm;
import com.chatbox.model.Group;
import com.chatbox.model.GroupUser;
import com.chatbox.model.User;
import com.chatbox.repositories.IGroupRepository;
import com.chatbox.repositories.IGroupUserRepository;
import com.chatbox.repositories.IUserRepository;
import com.chatbox.service.IGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class GroupServiceImpl implements IGroupService {
    @Autowired
    private IGroupRepository groupRepository;

    @Autowired
    private IGroupUserRepository groupUserRepository;

    @Autowired
    private IUserRepository userRepository;

    @Override
    public Group save(Group group) {
        if (group.getAvatar() == null) {
            group.setAvatar("group-default.jpg");
        }
        if (group.getCode() == null) {
            while (true) {
                String code = UUID.randomUUID().toString().replace("-", "").substring(0, 6).toUpperCase();
                if (!groupRepository.existsByCodeIgnoreCase(code)) {
                    group.setCode(code);
                    break;
                }
            }
        }
        if (group.getType() == null) {
            group.setType(Group.GroupType.PUBLIC);
        }
        if (group.getId() != null) {
            updateMemberGroup(group.getId());
        }
        return groupRepository.save(group);
    }

    @Override
    public Optional<Group> findById(Long id) {
        return groupRepository.findById(id);
    }

    @Override
    public List<Group> findAll() {
        return groupRepository.findAll();
    }

    @Override
    public Long getNumberMember(Long idGroup) {
        return groupUserRepository.countByGroupId(idGroup);
    }

    @Override
    public List<Group> findByNameIgnoreCaseContaining(String name) {
        return groupRepository.findByNameIgnoreCaseContaining(name);
    }

    @Override
    public List<Group> findByCode(String code) {
        return groupRepository.findByCode(code);
    }

    @Override
    public Long findIdByCode(String code) {
        return groupRepository.findIdByCode(code);
    }

    @Override
    public List<Group> findByNameIgnoreCaseContainingOrCode(String name, String code) {
        return groupRepository.findByNameIgnoreCaseContainingOrCode(name, code);
    }

    @Override
    public Group createGroup(CreateGroupForm createGroupForm) {
        final Group group = save(new Group(createGroupForm.getName(), createGroupForm.getDescription()));
        createGroupForm.getMembers().forEach(member -> {
            if (userRepository.existsById(member.getId())) {
                GroupUser groupUser = new GroupUser(member.getId(), group.getId());
                switch (member.getRole()) {
                    case "GROUP_ADMIN":
                        groupUser.setRole(GroupUser.GroupRole.GROUP_ADMIN);
                        break;
                    default:
                        groupUser.setRole(GroupUser.GroupRole.GROUP_MEMBER);
                }
                groupUserRepository.save(groupUser);
            }
        });
        return updateMemberGroup(group.getId());
    }

    @Override
    public List<User> getGroupsMember(Long groupId) {
        return userRepository.getGroupsMember(groupId);
    }

    private Group updateMemberGroup(Long groupId) {
        Optional<Group> groupOptional = findById(groupId);
        if (groupOptional.isPresent()) {
            Group group = groupOptional.get();
            group.setNumberMember(getNumberMember(group.getId()));
            return groupRepository.save(group);
        }
        return null;
    }

    @Override
    public Boolean addMember(Long groupId, Long memberId) {
        if (groupRepository.existsById(groupId) && userRepository.existsById(memberId)) {
            groupUserRepository.save(new GroupUser(memberId, groupId));
            return true;
        }
        return false;
    }

    @Override
    public Boolean removeMember(Long groupId, Long memberId) {
        groupUserRepository.deleteByGroupIdAndUserId(groupId, memberId);
        return true;
    }

    @Override
    public Boolean existsByName(String name) {
        return groupRepository.existsByName(name);
    }

    @Override
    public Boolean existsById(Long id) {
        return groupRepository.existsById(id);
    }
}
