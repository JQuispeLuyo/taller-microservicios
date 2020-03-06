package com.jquispeluyo.kafkacassandra.controllers;


import com.jquispeluyo.kafkacassandra.helps.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kafka")
public class KafkaController {

    @Autowired
    KafkaProducer kafkaProducer;

    @GetMapping("message/{message}")
    public void producerMessage(@PathVariable("message") String message){
        kafkaProducer.send(message);
    }

}
