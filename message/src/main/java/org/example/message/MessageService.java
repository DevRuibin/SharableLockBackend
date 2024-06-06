package org.example.message;

import lombok.RequiredArgsConstructor;
import org.example.message.client.UserClient;
import org.example.message.client.UserModel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final RabbitTemplate rabbitTemplate;
    private final UserClient userClient;

    public void sendMessage(MessageModel message) {
        UserModel toUser = userClient.getUserById(message.getToUserId());
        if(message.getSenderId() != 0) {
            UserModel fromUser = userClient.getUserById(message.getSenderId());
            if (toUser == null || fromUser == null) {
                throw new IllegalArgumentException("User not found");
            }
        }

        message.setRead(false);

        messageRepository.save(message);
        rabbitTemplate.convertAndSend("messageQueue", message);
    }

    public void markAsRead(Long id) {
        messageRepository.findById(id).ifPresent(message -> {
            message.setRead(true);
            messageRepository.save(message);
        });
    }


    public List<MessageModel> getMessagesByUserID(Long userId) {
        if(userId != 0) {
            UserModel user = userClient.getUserById(userId);
            if (user == null) {
                throw new IllegalArgumentException("User not found");
            }
        }
        return messageRepository.findByToUserIdOrSenderId(userId, userId)
                .orElse(List.of());
    }

    public void deleteMessage(Long id) {
        messageRepository.deleteById(id);
    }
}
