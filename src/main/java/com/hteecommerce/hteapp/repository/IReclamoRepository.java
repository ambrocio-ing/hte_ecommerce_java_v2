package com.hteecommerce.hteapp.repository;

import java.time.LocalDate;
import java.util.List;

import com.hteecommerce.hteapp.entity.Reclamo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IReclamoRepository extends JpaRepository<Reclamo,Integer> {

    @Query("from Reclamo re join re.cliente cli where cli.idcliente = ?1 order by re.idreclamo desc")
    public List<Reclamo> listByIdcliente(Integer idcliente);

    @Query("from Reclamo re join re.proveedor pro where pro.idproveedor = ?1 order by re.idreclamo desc")
    public List<Reclamo> listByIdproveedor(Integer idproveedor);

    public List<Reclamo> findByFecha(LocalDate fecha);

    @Query("from Reclamo re order by re.idreclamo desc")
    public Page<Reclamo> paginateReclamos(Pageable pageable);
    
}
