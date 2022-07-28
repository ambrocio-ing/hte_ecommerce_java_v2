package com.hteecommerce.hteapp.repository;

import com.hteecommerce.hteapp.entity.ClienteCaracteristica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IClienteCaracteristicaRepository extends JpaRepository<ClienteCaracteristica, Integer> {
    
}
