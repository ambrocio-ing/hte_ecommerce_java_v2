package com.hteecommerce.hteapp.repository;

import java.time.LocalDate;
import java.util.List;

import com.hteecommerce.hteapp.entity.ClienteComprobante;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IClienteComprobanteRepository extends JpaRepository<ClienteComprobante,Integer> {
    
    @Query("from ClienteComprobante cc where date(cc.fecha) between date(?1) and date(?2)")
    public List<ClienteComprobante> listByFechas(LocalDate finicio, LocalDate ffin);
    
    @Query("from ClienteComprobante cc where cc.estado = ?1 order by cc.idclientecomprobante desc")
    public Page<ClienteComprobante> listByEstado(String estado, Pageable pageable);

    @Query("from ClienteComprobante cc join cc.clienteProveedor cp where cp.idcp = ?1 order by cc.idclientecomprobante desc")
    public List<ClienteComprobante> listByCliente(Integer idcp);
    
}
