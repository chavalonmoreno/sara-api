package com.example.sara.domain.entities;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "ventas_productos")
public class VentaProducto {

    @EmbeddedId
    private VentaProductoID id;

    private Integer cantidad;

    private Double precioSinIva;

    private Double precioConIva;

}
