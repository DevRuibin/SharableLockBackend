package org.example.lock.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "messages-service", url = "${application.config.messages-url}")
public interface MessageClient {
    @PostMapping("/messages")
    MessageModel createMessage(MessageModel messageModel);
}

