package com.hteecommerce.hteapp.repository;

import com.hteecommerce.hteapp.entity.Membresia;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMembresiaRepository extends JpaRepository<Membresia,Integer> {
    
    public List<Membresia> findByEstadoOrderByIdmembresiaDesc(String estado); 
    
}
