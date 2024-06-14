package org.example.message;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/messages")
public class MessageController {

    private final MessageService messageService;

    @PostMapping
    public void sendMessage(@RequestBody MessageModel message) {
        System.out.println("Sending message: " + message);
        messageService.sendMessage(message);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> markAsRead(@PathVariable Long id) {
        messageService.markAsRead(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<List<MessageUserResponse>> getMessages(@PathVariable Long userId) {
        System.out.println("Getting messages for user: " + userId);
        try {
            List<MessageUserResponse> messagesByUserID = messageService.getMessagesByUserID(userId);
            System.out.println("Get user");
            return ResponseEntity.ok(messagesByUserID);
        }catch (Exception e){
            System.out.println(e.getMessage());

        }
        return ResponseEntity.badRequest().build();


    }

    @GetMapping("/{id}")
    public ResponseEntity<MessageModel> getMessage(@PathVariable Long id) {
        return ResponseEntity.ok(messageService.getMessage(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMessage(@PathVariable Long id) {
        messageService.deleteMessage(id);
        return ResponseEntity.ok().build();
    }
}

