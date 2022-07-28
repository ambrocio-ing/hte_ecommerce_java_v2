package com.hteecommerce.hteapp.repository;

import com.hteecommerce.hteapp.entity.ProveedorOferta;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProveedorOfertaRepository extends JpaRepository<ProveedorOferta,Integer> {
    
}
