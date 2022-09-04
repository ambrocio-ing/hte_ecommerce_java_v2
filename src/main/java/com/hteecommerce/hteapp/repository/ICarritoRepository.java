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

    @Query("select carr from Carrito carr join carr.detalleIngreso di join di.producto pro where pro.idproducto = ?1 and carr.idcliente = ?2")
    public Optional<Carrito> findByIdproductoAndIdcliente(Integer idproducto, Integer idcliente);
}
