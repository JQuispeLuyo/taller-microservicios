package com.jquispeluyo.productos.helpers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jquispeluyo.productos.dto.ActualizarSaldoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducer {

    private final KafkaTemplate kafkaTemplate;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    public KafkaProducer(KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    private final String kafkaTopic = "cuentaservice";

    public void send(ActualizarSaldoDTO actualizarSaldoDTO) {
        try {
            kafkaTemplate.send(kafkaTopic, objectMapper.writeValueAsString(actualizarSaldoDTO));
        } catch (JsonProcessingException e) {
            throw new ExceptionInInitializerError();
        }
    }

}

