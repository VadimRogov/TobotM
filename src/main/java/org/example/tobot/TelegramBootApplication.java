package org.example.tobot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class TelegramBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(TelegramBootApplication.class, args);
    }
}
