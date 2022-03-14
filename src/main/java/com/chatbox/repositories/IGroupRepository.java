package com.chatbox.repositories;

import com.chatbox.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IGroupRepository extends JpaRepository<Group, Long> {
    List<Group> findByNameIgnoreCaseContaining(String name);
    List<Group> findByCode(String code);

    @Query("select g.id from groups g where code = ?1")
    Long findIdByCode(String code);
    List<Group> findByNameIgnoreCaseContainingOrCode(String name, String code);
    Boolean existsByCodeIgnoreCase(String code);
    Boolean existsByName(String name);
}
