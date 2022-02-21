package com.example.sara.repositories;

import com.example.sara.domain.entities.VentaProducto;
import com.example.sara.domain.entities.VentaProductoID;
import org.springframework.data.repository.CrudRepository;

public interface VentaProductoRepository extends CrudRepository<VentaProducto, VentaProductoID> {
}
