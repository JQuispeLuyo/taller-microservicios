package com.jquispeluyo.productos.models;

import lombok.Data;
import org.springframework.data.annotation.Id;


@Data
public class Producto {
    @Id
    private String _id;
    private String descripcion;
    private Double precio;
    private Integer stock;

}
