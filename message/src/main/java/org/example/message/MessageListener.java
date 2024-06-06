package org.example.message;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MessageListener {

    private final MyWebSocketHandler myWebSocketHandler;

    public MessageListener(MyWebSocketHandler myWebSocketHandler) {
        this.myWebSocketHandler = myWebSocketHandler;
    }

    @RabbitListener(queues = {"messageQueue", "notificationQueue", "statusQueue"})
    public void handleMessage(MessageModel message) {
        System.out.println("Received message: " + message);
        myWebSocketHandler.sendMessageToUser(message.getToUserId(), message);
    }
}



