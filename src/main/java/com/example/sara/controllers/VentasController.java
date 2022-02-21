package com.example.sara.controllers;

import com.example.sara.applications.VentasApplication;
import com.example.sara.domain.entities.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/ventas")
public class VentasController {

    @Autowired
    private VentasApplication ventasApplication;

    @GetMapping("productos")
    private List<Producto> obtenerTodosLosProductos(){
        return ventasApplication.obtenerTodosLosProductos();
    }

    @PostMapping("productos")
    private Producto guardarProducto(@RequestBody Producto producto){
         return ventasApplication.guardarProducto(producto);
    }

    @GetMapping("productos/{id}")
    private Producto guardarProducto(@PathVariable("id") Integer idProducto){
        return ventasApplication.obtenerProductoPorId(idProducto);
    }

}
