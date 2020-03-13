package com.ascencio.api_cuentas.controller;

import com.ascencio.api_cuentas.model.ActualizarSaldoDTO;
import com.ascencio.api_cuentas.model.Cuenta;
import com.ascencio.api_cuentas.model.ProductoDTO;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import com.ascencio.api_cuentas.repository.CuentaRepository;
import org.springframework.web.client.RestTemplate;



import java.util.Optional;

@Service
@RestController
@RequestMapping("/api")
public class CuentaC {

    @Autowired
    CuentaRepository cuentaRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @KafkaListener(topics = "cuentaservice")
    public void consume(String message) throws JsonProcessingException {
        ActualizarSaldoDTO actualizarSaldoDTO = objectMapper.readValue(message, ActualizarSaldoDTO.class);
        System.out.println(actualizarSaldoDTO);
        Optional<Cuenta> cuenta = cuentaRepository.findByCliente(actualizarSaldoDTO.getIdCliente());
        System.out.println(cuenta);
        Double descuento = (actualizarSaldoDTO.getProductoDto().getCantidad() * actualizarSaldoDTO.getProductoDto().getPrecio());
        if (cuenta.isPresent() && !(cuenta.get().getSaldo() <= descuento)) {
            cuenta.get().setSaldo(cuenta.get().getSaldo() - descuento);
            cuentaRepository.save(cuenta.get());
            System.out.println("Transacción Completada satisfactoriamente :D");
        } else {
            System.out.println("El cliente no existe o No cuentas con saldo suficiente :(");
            send(actualizarSaldoDTO.getProductoDto());
        }
    }


    public void send(ProductoDTO productoDTO) throws JsonProcessingException {
        kafkaTemplate.send("productoservice", objectMapper.writeValueAsString(productoDTO));
    }

}
