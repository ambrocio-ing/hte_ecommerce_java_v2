package com.hteecommerce.hteapp.repository;

import java.util.List;
import java.util.Optional;

import com.hteecommerce.hteapp.entity.Proveedor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IProveedorRepository extends JpaRepository<Proveedor,Integer> {

    public boolean existsByRuc(String ruc);
    public boolean existsByRazonSocial(String razonSocial);
    public boolean existsByTelefono(String telefono);

    @Query("from Proveedor pro join pro.usuario usu where usu.username = ?1")
    public Optional<Proveedor> findByUsername(String username);

    @Query("from Proveedor pro order by pro.idproveedor desc")
    public Page<Proveedor> listProveedores(Pageable pageable);

    @Query("from Proveedor pro where upper(trim(pro.razonSocial)) like concat('%',?1,'%') or pro.ruc like concat('%',?2,'%')")
    public List<Proveedor> listByRazonOrRuc(String razon, String ruc);    

    @Query("from Proveedor pro where pro.telefono = ?1 and pro.idproveedor != ?2")
    public Optional<Proveedor> validTelefono(String telefono, Integer idproveedor);
    
}
