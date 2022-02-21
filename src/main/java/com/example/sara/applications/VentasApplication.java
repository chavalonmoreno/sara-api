package com.example.sara.applications;

import com.example.sara.domain.entities.Producto;
import com.example.sara.exceptions.BussinessRuleException;
import com.example.sara.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class VentasApplication {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private MarcaRepository marcaRepository;

    @Autowired
    private CompraRepository compraRepository;

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private VentaProductoRepository ventaProductoRepository;

    public List<Producto> obtenerTodosLosProductos(){
        Iterable<Producto> productosIterable = productoRepository.findAll();

        return StreamSupport
                .stream(productosIterable.spliterator(), false)
                .collect(Collectors.toList());
    }

    public Producto guardarProducto(Producto producto){
        Objects.requireNonNull(producto,"El producto no debe ser nulo");
        producto.setId(null);

        productoRepository.save(producto);
        return producto;
    }

    public Producto obtenerProductoPorId(Integer idProducto){
        Objects.requireNonNull(idProducto,"El id producto no debe ser nulo");

        Optional<Producto> productoOptional = productoRepository.findById(idProducto);

        if(productoOptional.isEmpty()){
            throw new BussinessRuleException("No se encontro un producto con ese id");
        }

        return productoOptional.get();
    }
}
