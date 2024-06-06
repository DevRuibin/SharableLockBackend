package org.example.message.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "users-service", url = "${application.config.users-url}")
public interface UserClient {
    @GetMapping("/users/{user-id}")
    UserModel getUserById(@PathVariable("user-id") Long userId);
}

