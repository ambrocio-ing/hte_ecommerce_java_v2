package com.hteecommerce.hteapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hteecommerce.hteapp.entity.CompraRapida;

@Repository
public interface ICompraRapidaRepository extends JpaRepository<CompraRapida, Integer> {
    
    public List<CompraRapida> findByIdcliente(Integer idcliente);

    @Query("from CompraRapida cr join cr.detalleIngreso di where cr.idcliente = ?1 and di.iddetalleingreso = ?2")
    public Optional<CompraRapida> getByIddetalleingreso(Integer idcliente, Integer iddetalleingreso);
    
}
