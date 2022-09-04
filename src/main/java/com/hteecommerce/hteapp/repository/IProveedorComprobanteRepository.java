package com.hteecommerce.hteapp.repository;

import java.time.LocalDate;
import java.util.List;

import com.hteecommerce.hteapp.entity.ProveedorComprobante;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IProveedorComprobanteRepository extends JpaRepository<ProveedorComprobante,Integer> {
    
    @Query("select pc from ProveedorComprobante pc where pc.estado = 'Pendiente' order by pc.idproveedorcomprobante desc")
    public Page<ProveedorComprobante> listByEstadoOfertado(Pageable pageable);

    @Query("select pc from ProveedorComprobante pc where pc.estado = 'Rechazado' order by pc.idproveedorcomprobante desc")
    public Page<ProveedorComprobante> listByEstadoRechazado(Pageable pageable);

    @Query("select pc from ProveedorComprobante pc where pc.estado = 'Aceptado' order by pc.idproveedorcomprobante desc")
    public Page<ProveedorComprobante> listByEstadoAceptado(Pageable pageable);

    @Query("select pc from ProveedorComprobante pc where date(pc.fecha) between date(?1) and date(?2)")
    public List<ProveedorComprobante> listByFechas(LocalDate finicio, LocalDate ffin);

    @Query("select pc from ProveedorComprobante pc order by pc.idproveedorcomprobante desc")
    public Page<ProveedorComprobante> listAll(Pageable pageable);

}
