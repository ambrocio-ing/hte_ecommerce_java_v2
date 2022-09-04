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

    //OTRAS TAREAS
    public Optional<Comprobante> findByNumero(String numero);
    public boolean existsByNumero(String numero);
    public Optional<Comprobante> findTopByOrderByIdcomprobanteDesc();

    //BUSQEDA DE VENTAS
    @Query("select com from Comprobante com join com.direccionEnvio de where com.estado = 'Entrega pendiente' and date(com.fechaPedido) = date(?1) and de.provincia = ?2")
    public List<Comprobante> listByFechaByEstadoPedido(LocalDate fecha, String sucursal);

    @Query("select com from Comprobante com join com.direccionEnvio de where com.estado = 'Entregado' and date(com.fechaPedido) = date(?1) and de.provincia = ?2")
    public List<Comprobante> listByFechaByEstadoEntregado(LocalDate fecha, String susursal);

    @Query("select com from Comprobante com join com.direccionEnvio de join de.cliente cli join cli.persona per where per.dni like %?1% or upper(replace(per.nombre, ' ', '')) like concat('%',upper(?2),'%') order by com.idcomprobante desc")
    public List<Comprobante> listByClienteByDniOrNombre(String dni, String nombre);

    @Query("select com from Comprobante com join com.direccionEnvio de join de.cliente cli where cli.idcliente = ?1 order by com.idcomprobante desc")
    public List<Comprobante> listByClienteByIdcliente(Integer idcliente);

    //PAGINACION DE VENTAS PARA SUCURSAL HUACHO
    @Query("select com from Comprobante com join com.direccionEnvio de where com.estado = 'Entregado' and de.provincia = 'Huacho' order by com.idcomprobante desc")
    public Page<Comprobante> listByEstadoEntregadoHuacho(Pageable pageable);

    @Query("select com from Comprobante com join com.direccionEnvio de where com.estado = 'Entrega pendiente' and de.provincia = 'Huacho' order by com.idcomprobante desc")
    public Page<Comprobante> listByEstadoPedidoHuacho(Pageable pageable);

    @Query("select com from Comprobante com join com.direccionEnvio de where com.estado = 'Anulado' and de.provincia = 'Huacho' order by com.idcomprobante desc")
    public Page<Comprobante> listByEstadoAnuladoHuacho(Pageable pageable);   
    
    //PAGINACION DE VENTAS PARA SUCURSAL BARRANCA
    @Query("select com from Comprobante com join com.direccionEnvio de where com.estado = 'Entregado' and de.provincia = 'Barranca' order by com.idcomprobante desc")
    public Page<Comprobante> listByEstadoEntregadoBarranca(Pageable pageable);

    @Query("select com from Comprobante com join com.direccionEnvio de where com.estado = 'Entrega pendiente' and de.provincia = 'Barranca' order by com.idcomprobante desc")      
    public Page<Comprobante> listByEstadoPedidoBarranca(Pageable pageable);

    @Query("select com from Comprobante com join com.direccionEnvio de where com.estado = 'Anulado' and de.provincia = 'Barranca' order by com.idcomprobante desc")
    public Page<Comprobante> listByEstadoAnuladoBarranca(Pageable pageable);   


    //CONSULTA PARA RESUMEN DE PRODUCTOS EN ENTREGA PENDIENTE
    @Query("select com from Comprobante com join com.direccionEnvio de where com.estado = 'Entrega pendiente' and de.provincia = ?1")
    public List<Comprobante> listarPorEntregaPendienteSucursal(String sucursal);

    //para busqueda de resumen
    @Query("select com from Comprobante com join com.direccionEnvio de where com.estado = 'Entrega pendiente' and date(com.fechaEntrega) = date(?1) and de.provincia = ?2")
    public List<Comprobante> listByFechaEntregaByEstadoPedido(LocalDate fecha, String sucursal);

    //BUSCAR PRODUCTOS CON ESTADO EN VALIDACION PENDIENTE
    @Query("select com from Comprobante com join com.direccionEnvio de where com.estado = 'Validación pendiente' and date(com.fechaPedido) = date(?1) and de.provincia = ?2")
    public List<Comprobante> listByFechaByEstadoPedidoValidar(LocalDate fecha, String sucursal);

    //PAGINAR VENTAS CON VALIDACION PENDIENTE EN SUCURSAL HUACHO
    @Query("select com from Comprobante com join com.direccionEnvio de where com.estado = 'Validación pendiente' and de.provincia = 'Huacho' order by com.idcomprobante desc")
    public Page<Comprobante> listByEstadoPedidoValidarHuacho(Pageable pageable);

    //PAGINAR VENTGAS CON VALIDACION PENDIENTE EN SUCURSAL BARRANCA
    @Query("select com from Comprobante com join com.direccionEnvio de  where com.estado = 'Validación pendiente' and de.provincia = 'Barranca' order by com.idcomprobante desc")
    public Page<Comprobante> listByEstadoPedidoValidarBarranca(Pageable pageable); 

}
