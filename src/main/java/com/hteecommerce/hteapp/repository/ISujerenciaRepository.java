package com.hteecommerce.hteapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hteecommerce.hteapp.entity.Sujerencia;

@Repository
public interface ISujerenciaRepository extends JpaRepository<Sujerencia,Integer> {

    public Optional<Sujerencia> findByDetalle(String detalle);
    
}
