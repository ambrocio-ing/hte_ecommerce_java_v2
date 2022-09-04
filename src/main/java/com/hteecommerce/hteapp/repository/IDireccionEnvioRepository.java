package com.hteecommerce.hteapp.repository;

import java.util.List;

import com.hteecommerce.hteapp.entity.DireccionEnvio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IDireccionEnvioRepository extends JpaRepository<DireccionEnvio,Integer> {
    
    @Query("select de from DireccionEnvio de join de.cliente cli where cli.idcliente = ?1")
    public List<DireccionEnvio> listByIdcliente(Integer idcliente);
}
