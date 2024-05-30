package org.example.message;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/locks")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService service;

    public ResponseEntity<MessageModel> createMessage(MessageModel messageModel) {
        return ResponseEntity.ok(service.createMessage(messageModel));
    }

    public ResponseEntity<List<MessageModel>> getMessages(Long receiverId) {
        return ResponseEntity.ok(service.getMessages(receiverId));
    }

    public ResponseEntity<List<MessageModel>> getMessages(Long receiverId, Long senderId) {
        return ResponseEntity.ok(service.getMessages(receiverId, senderId));
    }
}
