package com.hteecommerce.hteapp.repository;

import java.util.Optional;

import com.hteecommerce.hteapp.entity.Destinatario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDestinatarioRepository extends JpaRepository<Destinatario,Integer> {

    public boolean existsByDni(String dni);
    public Optional<Destinatario> findByDni(String dni);   
    
}
