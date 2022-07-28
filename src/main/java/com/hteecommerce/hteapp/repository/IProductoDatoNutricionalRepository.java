package com.hteecommerce.hteapp.repository;

import com.hteecommerce.hteapp.entity.ProductoDatoNutricional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductoDatoNutricionalRepository extends JpaRepository<ProductoDatoNutricional,Integer> {
    
}
