package com.jquispeluyo.productos.error;

public class ProductoNotFoundException extends RuntimeException {

    public ProductoNotFoundException(String id){
         super("Producto no encontrado : " + id);
    }

}
