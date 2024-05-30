package org.example.message;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageListener {

    @Autowired
    private MyWebSocketHandler webSocketHandler;

    @RabbitListener(queues = "messageQueue")
    public void handleMessage(MessageModel message) {
        // Handle the incoming message
        System.out.println("Received message from RabbitMQ: " + message.getText());
        // Send the message to WebSocket clients
        webSocketHandler.broadcast(message);
    }
}

