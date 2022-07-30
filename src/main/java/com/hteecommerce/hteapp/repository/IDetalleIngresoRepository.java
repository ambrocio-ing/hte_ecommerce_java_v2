package com.hteecommerce.hteapp.repository;

import java.util.List;
import java.util.Optional;

import com.hteecommerce.hteapp.entity.DetalleIngreso;
import com.hteecommerce.hteapp.entity.Producto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IDetalleIngresoRepository extends JpaRepository<DetalleIngreso, Integer> {   
        
    @Query("from DetalleIngreso di join di.producto pro where di.estado = true and di.stockActual > 0 and pro.codigo like %?1% or upper(trim(pro.nombre)) like concat('%',upper(?2),'%')")
    public List<DetalleIngreso> listByCodigoOrNombreOfProducto(String codigo, String nombre);

    @Query("from DetalleIngreso di join di.producto pro where di.estado = true and pro.idproducto = ?1")
    public Optional<DetalleIngreso> findByIdproducto(Integer idproducto);

    public Optional<DetalleIngreso> findTopByProductoOrderByIddetalleingresoDesc(Producto producto);

    @Query("from DetalleIngreso di where di.estado = true and di.stockActual > 0 order by di.iddetalleingreso desc")
    public Page<DetalleIngreso> pageAll(Pageable pageable);

    //LISTA Y FILTRO DESDE VISTA CLIENTE
    @Query("from DetalleIngreso di join di.producto pro where di.estado = true and di.stockActual > 0 and upper(trim(pro.nombre)) like concat('%',upper(?2),'%')")
    public List<DetalleIngreso> listByNombreProducto(String nombre);

    @Query("from DetalleIngreso di join di.producto pro join pro.tipo ti where di.estado = true and di.stockActual > 0 and ti.idtipo = ?1")
    public List<DetalleIngreso> listByTipo(Integer idtipo);

    @Query("from DetalleIngreso di where di.estado = true and di.stockActual > 0 order by di.iddetalleingreso desc")
    public List<DetalleIngreso> listAll();
   
    //listar los 12 ultimos ingresos de cada producto
    public List<DetalleIngreso> findTop12ByProductoByOrderByIddetalleingresoDesc(Producto producto);

    //lista de productos mas populares
    @Query("from DetalleIngreso di join di.producto pro join pro.tipo tip where di.stockActual > 0 and di.estado = true and tip.idtipo = ?1 order by pro.nventas desc")
    public List<DetalleIngreso> listMasVendidos(Integer idtipo);

    @Query(nativeQuery = true, value = "select * from detalle_ingreso di inner join productos pro on di.idproducto = pro.idproducto where di.stock_actual > 0 and di.estado = true order by pro.nventas desc limit 50")
    public List<DetalleIngreso> listMasVendidosGeneral();

    @Query(nativeQuery = true, value = "select * from detalle_ingreso di where di.estado = true and di.stockActual > 0 order by di.iddetalleingreso asc limit 20")
    public List<DetalleIngreso> listLastTwenty();

}
