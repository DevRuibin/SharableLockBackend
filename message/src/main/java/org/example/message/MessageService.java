package org.example.message;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository repository;

    private final RabbitTemplate rabbitTemplate;

    public void sendMessage(MessageModel message) {
        rabbitTemplate.convertAndSend("messageQueue", message);
    }
    public List<MessageModel> getMessages(Long receiverId) {
        return repository.findByToUserId(receiverId);
    }

    public MessageModel createMessage(MessageModel messageModel) {
        return repository.save(messageModel);
    }

    public List<MessageModel> getMessages(Long receiverId, Long senderId) {
        return repository.findByToUserIdAndSenderId(receiverId, senderId);
    }
}

