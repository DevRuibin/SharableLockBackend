package org.example.lock;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.example.lock.client.MessageClient;
import org.example.lock.client.MessageModel;
import org.example.lock.client.MessageType;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationUtil {
    private final MessageClient messageClient;

    public void notifyAllUsers(LockModel lockInfo, String text, String detail) {
        for (Long userId : lockInfo.getUsers()) {
            messageClient.createMessage(
                    MessageModel.builder()
                            .timestamp(System.currentTimeMillis())
                            .senderId(0L)
                            .toUserId(userId)
                            .read(false)
                            .type(MessageType.NOTIFICATION)
                            .text(text)
                            .detail(detail)
                            .build());
        }
    }

    public void notifyAllManagers(LockModel lockInfo, String text, String detail) {
        for (Long managerId : lockInfo.getManagers()) {
            messageClient.createMessage(
                    MessageModel.builder()
                            .senderId(0L)
                            .toUserId(managerId)
                            .read(false)
                            .type(MessageType.NOTIFICATION)
                            .text(text)
                            .detail(detail)
                            .timestamp(System.currentTimeMillis())
                            .build());
        }
    }

    public void notifyAllManagersExceptOne(LockModel lockInfo, Long managerId, String text, String detail) {
        for (Long id : lockInfo.getManagers()) {
            if (id.equals(managerId)) {
                continue;
            }
            messageClient.createMessage(
                    MessageModel.builder()
                            .senderId(0L)
                            .toUserId(id)
                            .read(false)
                            .type(MessageType.NOTIFICATION)
                            .text(text)
                            .detail(detail)
                            .timestamp(System.currentTimeMillis())
                            .build());
        }
    }

    public void notifyAllUsersExceptOne(LockModel lockInfo, Long userId, String text, String detail) {
        for (Long id : lockInfo.getUsers()) {
            if (id.equals(userId)) {
                continue;
            }
            messageClient.createMessage(
                    MessageModel.builder()
                            .senderId(0L)
                            .toUserId(id)
                            .read(false)
                            .type(MessageType.NOTIFICATION)
                            .text(text)
                            .detail(detail)
                            .timestamp(System.currentTimeMillis())
                            .build());
        }
    }

    public void notifyAllManagersAndOwner(LockModel lockInfo, String text, String detail) {
        notifyOwner(lockInfo, text, detail);
        notifyAllManagers(lockInfo, text, detail);
    }

    public void notifyAll(LockModel lockInfo, String text, String detail) {
        notifyOwner(lockInfo, text, detail);
        notifyAllManagers(lockInfo, text, detail);
        notifyAllUsers(lockInfo, text, detail);
    }

    public void notifyOwner(LockModel lockInfo, String text, String detail) {
        messageClient.createMessage(
                MessageModel.builder()
                        .senderId(0L)
                        .toUserId(lockInfo.getOwnerId())
                        .read(false)
                        .type(MessageType.NOTIFICATION)
                        .text(text)
                        .detail(detail)
                        .timestamp(System.currentTimeMillis())
                        .build());
    }

    public void notifyUser(LockModel lockInfo, Long userId, String text, String detail) {
        messageClient.createMessage(
                MessageModel.builder()
                        .senderId(0L)
                        .toUserId(userId)
                        .read(false)
                        .type(MessageType.NOTIFICATION)
                        .text(text)
                        .detail(detail)
                        .timestamp(System.currentTimeMillis())
                        .build());
    }

    public void sendStatusChangeToUser(LockModel lockInfo, Long userId, String text, String detail) {

        messageClient.createMessage(
                MessageModel.builder()
                        .senderId(0L)
                        .toUserId(userId)
                        .read(false)
                        .type(MessageType.STATUS)
                        .text(text)
                        .detail(detail)
                        .timestamp(System.currentTimeMillis())
                        .build());
    }

    public void statusChanged(LockModel lockInfo, String text, String detail) {
        MessageModel message = MessageModel.builder()
                .senderId(0L)
                .toUserId(lockInfo.getOwnerId())
                .read(false)
                .type(MessageType.STATUS)
                .text(text)
                .detail(detail)
                .timestamp(System.currentTimeMillis())
                .build();
        messageClient.createMessage(message);
        for (Long userId : lockInfo.getUsers()) {
            message.setToUserId(userId);
            messageClient.createMessage(message);
        }
        for (Long managerId : lockInfo.getManagers()) {
            message.setToUserId(managerId);
            messageClient.createMessage(message);
        }
    }

}
