package com.hteecommerce.hteapp.repository;

import java.time.LocalDate;
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

    @Query("from DetalleIngreso di join di.producto pro where pro.idproducto = ?1 and di.createAt > ?2 order by di.createAt asc")
    public List<DetalleIngreso> listByIdproductoByFecha(Integer idproducto, LocalDate createAt);

}
