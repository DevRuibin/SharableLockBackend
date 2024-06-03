package org.example.lock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class LockApplication {

    public static void main(String[] args) {
        SpringApplication.run(LockApplication.class, args);
    }

}
