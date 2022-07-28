package com.hteecommerce.hteapp.repository;

import com.hteecommerce.hteapp.entity.ProductoVestimenta;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductoVestimentaRepository extends JpaRepository<ProductoVestimenta,Integer> {
    
}
