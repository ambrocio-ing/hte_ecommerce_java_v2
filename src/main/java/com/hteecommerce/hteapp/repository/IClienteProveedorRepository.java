package com.hteecommerce.hteapp.repository;

import com.hteecommerce.hteapp.entity.ClienteProveedor;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IClienteProveedorRepository extends JpaRepository<ClienteProveedor, Integer> {
    
    public boolean existsByRuc(String ruc);
    public boolean existsByRazonSocial(String razonSocial);

    @Query("from ClienteProveedor cp where cp.ruc = ?1 and cp.idcp != ?2")
    public Optional<ClienteProveedor> validRuc(String ruc, Integer idcp);

    @Query("from ClienteProveedor cp where cp.razonSocial = ?1 and cp.idcp != ?2")
    public Optional<ClienteProveedor> validRazonSocial(String razonSocial);

}
