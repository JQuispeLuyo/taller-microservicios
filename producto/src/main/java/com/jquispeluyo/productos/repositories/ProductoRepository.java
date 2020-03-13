package com.jquispeluyo.productos.repositories;

import com.jquispeluyo.productos.models.Producto;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductoRepository extends MongoRepository<Producto, String> {
}
