package com.jquispeluyo.productos.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jquispeluyo.productos.dto.ActualizarSaldoDTO;
import com.jquispeluyo.productos.dto.ProductoDTO;
import com.jquispeluyo.productos.error.ProductoBadRequestException;
import com.jquispeluyo.productos.error.ProductoNotFoundException;
import com.jquispeluyo.productos.helpers.KafkaProducer;
import com.jquispeluyo.productos.models.Producto;
import com.jquispeluyo.productos.repositories.ProductoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private KafkaProducer kafkaProducer;

    public List<Producto> findAll() {
        return productoRepository.findAll();
    }

    public Producto findById(String id) {
        return productoRepository.findById(id)
                .orElseGet(() -> {
                    throw new ProductoNotFoundException(id);
                });
    }

    public Producto create(Producto producto) {
        return productoRepository.save(producto);
    }

    public Producto updateCantidad(String idCliente, String idProducto, Integer cantidad) {
        return productoRepository.findById(idProducto)
                .map((x) -> {

                    if ((x.getStock() - cantidad) < 0)
                        throw new ProductoBadRequestException("Stock no disponible: " + x.getStock());

                    x.setStock(x.getStock() - cantidad);
                    productoRepository.save(x);
                    ActualizarSaldoDTO actualizarSaldoDTO = new ActualizarSaldoDTO();
                    actualizarSaldoDTO.setIdCliente(idCliente);
                    ProductoDTO productoDTO = new ProductoDTO();
                    productoDTO.set_id(x.get_id());
                    productoDTO.setCantidad(cantidad);
                    productoDTO.setPrecio(x.getPrecio());
                    actualizarSaldoDTO.setProductoDto(productoDTO);
                    kafkaProducer.send(actualizarSaldoDTO);
                    return x;
                })
                .orElseGet(() -> {
                    throw new ProductoNotFoundException(idProducto);
                });
    }

    public Producto updateCantidadFallback(String id, Integer cantidad) {
        return productoRepository.findById(id)
                .map((x) -> {
                    x.setStock(x.getStock() + cantidad);
                    return productoRepository.save(x);
                })
                .orElseGet(() -> {
                    throw new ProductoNotFoundException(id);
                });
    }

    public void delete(String id) {
        productoRepository.deleteById(id);
    }

}
