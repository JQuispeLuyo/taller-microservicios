package com.jquispeluyo.kafka.helpers;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaTestListener {

    @KafkaListener(topics = "test", groupId = "0")
    public void processMessage(String content) {
        System.out.println(content);
    }

}
