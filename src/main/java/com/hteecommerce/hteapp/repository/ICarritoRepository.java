package com.hteecommerce.hteapp.repository;

import java.util.List;
import java.util.Optional;

import com.hteecommerce.hteapp.entity.Carrito;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ICarritoRepository extends JpaRepository<Carrito,Integer> {
    
    public List<Carrito> findByIdcliente(Integer idcliente);

    @Query("from Carrito carr join carr.detalleIngreso di where di.iddetalleingreso = ?1 and carr.idcliente = ?2")
    public Optional<Carrito> findByIddetalleingresoAndIdcliente(Integer iddi, Integer idcliente);
}
