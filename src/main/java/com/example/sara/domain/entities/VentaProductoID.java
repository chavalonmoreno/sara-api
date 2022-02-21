package com.example.sara.domain.entities;

import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
public class VentaProductoID implements Serializable {

    private static final long serialVersionUID = 7586939658048019343L;
    private Integer idVenta;

    private Integer idProducto;
}
