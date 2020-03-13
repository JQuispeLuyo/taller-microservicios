package com.jquispeluyo.productos.error;

public class ProductoBadRequestException extends RuntimeException {

    public ProductoBadRequestException(String message){
         super(message);
    }

}
