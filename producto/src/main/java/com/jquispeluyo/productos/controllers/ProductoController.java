package com.jquispeluyo.productos.controllers;

import com.jquispeluyo.productos.models.Producto;
import com.jquispeluyo.productos.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/producto")
public class ProductoController {

    @Autowired
    ProductoService productoService;

    @GetMapping()
    public List<Producto> findAll (){
        return productoService.findAll();
    }

    @GetMapping("/{id}")
    public Producto findById (@PathVariable("id") String id){
        return productoService.findById(id);
    }

    @PostMapping("/create")
    public Producto create(@RequestBody Producto producto){
        return productoService.create(producto);
    }

    @PutMapping("/restar-cantidad")
    public Producto updateCantidad (@RequestParam("idCliente") String idCliente,
                                    @RequestParam("idProducto") String idProducto,
                                    @RequestParam("cantidad") Integer cantidad){
        return productoService.updateCantidad(idCliente,idProducto, cantidad);
    }

    @PutMapping("/sumar-cantidad")
    public Producto updateCantidadFallback (@RequestParam("idProducto") String id, @RequestParam("cantidad") Integer cantidad){
        return productoService.updateCantidadFallback(id, cantidad);
    }

    @DeleteMapping("/delete/{id}")
    public void create(@PathVariable("id") String id){
        productoService.delete(id);
    }
}
