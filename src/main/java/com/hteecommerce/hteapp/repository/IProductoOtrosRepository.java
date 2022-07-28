package com.hteecommerce.hteapp.repository;

import com.hteecommerce.hteapp.entity.ProductoOtros;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductoOtrosRepository extends JpaRepository<ProductoOtros,Integer> {
    
}
