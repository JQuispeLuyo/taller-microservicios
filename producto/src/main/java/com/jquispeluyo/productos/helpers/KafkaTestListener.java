package com.jquispeluyo.productos.helpers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jquispeluyo.productos.dto.ProductoDTO;
import com.jquispeluyo.productos.repositories.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaTestListener {

    @Autowired
    ProductoRepository productoRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @KafkaListener(topics = "productoservice", groupId = "0")
    public void processMessage(String content) throws JsonProcessingException {
        ProductoDTO productoDTO = objectMapper.readValue(content, ProductoDTO.class);
        productoRepository.findById(productoDTO.get_id())
                .map((x)->{
                    System.out.println("Rollback");
                    x.setStock((x.getStock() + productoDTO.getCantidad()));
                   productoRepository.save(x);
                   return x;
                })
                .orElseGet(()->{
                    return null;
                });
    }

}
