package com.chatbox.repositories;

import com.chatbox.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IMessageRepository extends JpaRepository<Message, Long> {
    @Query("select m from message m where ((m.senderId = ?1 and m.recipientId = ?2) " +
            "or (m.senderId = ?2 and m.recipientId = ?1))  and m.sendToType = 'ONLY'")
    List<Message> getMessage(Long senderId, Long recipientId);

    @Query("select m from message m where m.recipientId = ?1 and m.sendToType = ?2")
    List<Message> getMessage(Long groupId, String sendTo);

    @Query("select m from message m where ((m.senderId = ?1 or m.recipientId = ?1) and m.sendToType = 'ONLY') " +
            "or (m.recipientId in ?2 and m.sendToType = 'GROUP') order by m.id desc")
    List<Message> getAllMessageOfUser(Long id, List<Long> groupIds);
}
