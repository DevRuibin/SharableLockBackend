package org.example.message;

import lombok.RequiredArgsConstructor;
import org.example.message.client.UserClient;
import org.example.message.client.UserModel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final RabbitTemplate rabbitTemplate;
    private final UserClient userClient;

    public void sendMessage(MessageModel message) {
        UserModel toUser = userClient.getUserById(message.getToUserId());
        if(toUser == null) {
            throw new IllegalArgumentException("User not found");
        }
        message.setRead(false);
        if(message.getType() == null) {
            message.setType(MessageType.GENERAL);

        }
        switch (message.getType()){
            case GENERAL -> {
                UserModel fromUser = userClient.getUserById(message.getSenderId());
                if(fromUser == null) {
                    throw new IllegalArgumentException("User not found");
                }
                messageRepository.save(message);
                rabbitTemplate.convertAndSend("messageQueue", message);
            }
            case NOTIFICATION -> {
                messageRepository.save(message);
                rabbitTemplate.convertAndSend("notificationQueue", message);
            }

            case STATUS -> {
                messageRepository.save(message);
                rabbitTemplate.convertAndSend("statusQueue", message);
            }
        }



    }

    public void markAsRead(Long id) {
        messageRepository.findById(id).ifPresent(message -> {
            message.setRead(true);
            messageRepository.save(message);
        });
    }


    public List<MessageUserResponse> getMessagesByUserID(Long userId) {
        if(userId != 0) {
            UserModel user = userClient.getUserById(userId);
            if (user == null) {
                throw new IllegalArgumentException("User not found");
            }
        }
        System.out.println("in get message By userId");
        List<MessageModel> messageModelBySenderId = messageRepository.findBySenderIdAndType(userId, MessageType.NOTIFICATION).orElse(new ArrayList<>());
        System.out.println(messageModelBySenderId);
        List<MessageModel> messageModelByReceiverId = messageRepository.findByToUserIdAndType(userId, MessageType.NOTIFICATION).orElse(new ArrayList<>());
        System.out.println(messageModelByReceiverId);
        Map<Long, MessageUserResponse> map = new HashMap<>();
        for(MessageModel messageModel: messageModelBySenderId){
            userId = messageModel.getToUserId();
            updateResponseMap(userId, map, messageModel);
        }
        System.out.println("update 1");
        for(MessageModel messageModel: messageModelByReceiverId){
            userId = messageModel.getSenderId();
            updateResponseMap(userId, map, messageModel);
        }
        System.out.println("update 2");
        for(MessageUserResponse messageUserResponse: map.values()){
            List<MessageModel> messages= messageUserResponse.getMessages();
            messages.sort(Comparator.comparingLong(MessageModel::getTimestamp).reversed());
        }
        List<MessageUserResponse> messageUserResponse = new ArrayList<>(map.values());
        messageUserResponse.sort(Comparator.comparingLong((MessageUserResponse mur) -> mur.getMessages().get(0).getTimestamp()).reversed());
        System.out.println("messageUserResponse:" + messageUserResponse);
        return messageUserResponse;



//        return messageRepository.findByToUserIdOrSenderId(userId, userId)
//                .orElse(List.of());
    }

    private void updateResponseMap(Long userId, Map<Long, MessageUserResponse> map, MessageModel messageModel) {
        UserModel userModel;
        if(userId != 0) {
            userModel = userClient.getUserById(userId);
        }else{
            userModel = UserModel.builder()
                    .admin(true)
                    .email("admin@admin")
                    .phone("19801358352")
                    .username("System")
                    .id(0L)
                    .build();
        }
        if(!map.containsKey(userId)){
            MessageUserResponse response = MessageUserResponse.builder()
                    .messages(new ArrayList<>())
                    .user(userModel)
                    .unreadMessageNum(0)
                    .build();
            map.put(userId, response);
        }
        map.get(userId).getMessages().add(messageModel);
        if(!messageModel.isRead()) {
            map.get(userId).increaseUnreadNum();
        }
    }

    public void deleteMessage(Long id) {
        messageRepository.deleteById(id);
    }

    public MessageModel getMessage(Long id) {
        return messageRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Message not found"));
    }
}
