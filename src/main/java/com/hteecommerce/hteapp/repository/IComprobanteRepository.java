package com.hteecommerce.hteapp.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.hteecommerce.hteapp.entity.Comprobante;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IComprobanteRepository extends JpaRepository<Comprobante, Integer> {

    @Query("from Comprobante com where com.estado = 'Entrega pendiente' and date(com.fechaPedido) = date(?1)")
    public List<Comprobante> listByFechaByEstadoPedido(LocalDate fecha);

    @Query("from Comprobante com where com.estado = 'Entregado' and date(com.fechaPedido) = date(?1)")
    public List<Comprobante> listByFechaByEstadoEntregado(LocalDate fecha);

    @Query("from Comprobante com join com.direccionEnvio de join de.cliente cli join cli.persona per where per.dni like %?1% or upper(replace(per.nombre, ' ', '')) like concat('%',upper(?2),'%') order by com.idcomprobante desc")
    public List<Comprobante> listByClienteByDniOrNombre(String dni, String nombre);

    @Query("from Comprobante com join com.direccionEnvio de join de.cliente cli where cli.idcliente = ?1 order by com.idcomprobante desc")
    public List<Comprobante> listByClienteByIdcliente(Integer idcliente);

    @Query("from Comprobante com where com.estado = 'Entregado' order by com.idcomprobante desc")
    public Page<Comprobante> listByEstadoEntregado(Pageable pageable);

    @Query("from Comprobante com where com.estado = 'Entrega pendiente' order by com.idcomprobante desc")
    public Page<Comprobante> listByEstadoPedido(Pageable pageable);

    @Query("from Comprobante com where com.estado = 'Anulado' order by com.idcomprobante desc")
    public Page<Comprobante> listByEstadoAnulado(Pageable pageable);

    public Optional<Comprobante> findByNumero(String numero);

    public boolean existsByNumero(String numero);

    public Optional<Comprobante> findTopByOrderByIdcomprobanteDesc();

    //consulta para resumen de pedido
    public List<Comprobante> findByEstado(String estado);

}
