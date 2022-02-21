package com.example.sara.repositories;

import com.example.sara.domain.entities.Producto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ProductoRepository extends CrudRepository<Producto, Integer> {
}
