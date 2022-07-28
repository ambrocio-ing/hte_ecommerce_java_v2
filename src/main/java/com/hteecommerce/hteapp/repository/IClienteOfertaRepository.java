package com.hteecommerce.hteapp.repository;

import com.hteecommerce.hteapp.entity.ClienteOferta;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IClienteOfertaRepository extends JpaRepository<ClienteOferta,Integer> {
    
}
