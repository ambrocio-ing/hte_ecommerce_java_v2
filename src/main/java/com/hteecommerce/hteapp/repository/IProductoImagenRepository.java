package com.hteecommerce.hteapp.repository;

import com.hteecommerce.hteapp.entity.ProductoImagen;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductoImagenRepository extends JpaRepository<ProductoImagen,Integer> {
    
}
