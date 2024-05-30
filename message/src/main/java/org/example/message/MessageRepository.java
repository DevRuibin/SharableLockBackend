package org.example.message;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<MessageModel, Long> {
    List<MessageModel> findBySenderId(Long userId);

    List<MessageModel> findByToUserId(Long receiverId);

    List<MessageModel> findByToUserIdAndSenderId(Long receiverId, Long senderId);
}
