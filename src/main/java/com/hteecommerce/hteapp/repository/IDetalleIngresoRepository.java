package com.hteecommerce.hteapp.repository;

import java.util.List;
import java.util.Optional;

import com.hteecommerce.hteapp.entity.DetalleIngreso;
//import com.hteecommerce.hteapp.entity.Producto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IDetalleIngresoRepository extends JpaRepository<DetalleIngreso, Integer> {

  @Query("select di from DetalleIngreso di join di.producto pro where pro.codigo like %?1% or upper(trim(pro.nombre)) like concat('%',upper(?2),'%')")
  public List<DetalleIngreso> listByCodigoOrNombreOfProducto(String codigo, String nombre);

  @Query("select di from DetalleIngreso di join di.producto pro join pro.tipo ti join ti.categoria cat where upper(replace(cat.nombre, ' ', '')) like concat('%', upper(?1), '%')")
  public List<DetalleIngreso> listByCategoriaProducto(String nombreCategoria);

  @Query("select di from DetalleIngreso di order by di.iddetalleingreso desc")
  public Page<DetalleIngreso> pageAll(Pageable pageable);

  @Query("select di from DetalleIngreso di join di.producto pro where pro.idproducto = ?1")
  public Optional<DetalleIngreso> find_ByIdproducto(Integer idproducto);

  // LISTA Y FILTRO DESDE VISTA CLIENTE
  @Query("select di from DetalleIngreso di join di.producto pro where di.estado = true and pro.idproducto = ?1 and di.sucursal = ?2")
  public Optional<DetalleIngreso> findByIdproducto(Integer idproducto, String sucursal);

  @Query("select di from DetalleIngreso di join di.producto pro where di.estado = true and di.stockActual > 0 and upper(trim(pro.nombre)) like concat('%',upper(?1),'%') and di.sucursal = ?2")
  public List<DetalleIngreso> listByNombreProducto(String nombre, String sucursal);

  @Query("select di from DetalleIngreso di join di.producto pro where di.estado = true and di.stockActual > 0 and upper(trim(pro.nombre)) like concat('%',upper(?1),'%') and di.sucursal = ?2 and pro.marca = ?3")
  public List<DetalleIngreso> listByNombreProductoToMarca(String nombre, String sucursal, String marca);

  @Query("select di from DetalleIngreso di join di.producto pro join pro.tipo ti where di.estado = true and di.stockActual > 0 and ti.idtipo = ?1 and di.sucursal = ?2")
  public List<DetalleIngreso> listByTipo(Integer idtipo, String sucursal);

  @Query("select di from DetalleIngreso di join di.producto pro join pro.tipo ti where di.estado = true and di.stockActual > 0 and ti.idtipo = ?1 and di.sucursal = ?2 and pro.marca = ?3")
  public List<DetalleIngreso> listByTipoToMarca(Integer idtipo, String sucursal, String marca);

  @Query("select di from DetalleIngreso di where di.estado = true and di.stockActual > 0 and di.sucursal = ?1 order by di.iddetalleingreso desc")
  public List<DetalleIngreso> listAll(String sucursal);

  @Query("select di from DetalleIngreso di join di.producto pro where di.estado = true and di.stockActual > 0 and di.sucursal = ?1 and pro.marca = ?2 order by di.iddetalleingreso desc")
  public List<DetalleIngreso> listAllToMarca(String sucursal, String marca);  
  
  // lista de productos mas populares
  @Query("select di from DetalleIngreso di join di.producto pro join pro.tipo tip where di.stockActual > 0 and di.estado = true and tip.idtipo = ?1 and di.sucursal = ?2 order by pro.nventas desc")
  public List<DetalleIngreso> listMasVendidos(Integer idtipo, String sucursal);

  @Query(nativeQuery = true, value = "select * from detalle_ingresos di inner join productos pro on di.producto_id = pro.idproducto where di.stock_actual > 0 and di.sucursal = ?1 and di.estado = true order by pro.nventas desc limit 50")
  public List<DetalleIngreso> listMasVendidosGeneral(String sucursal);

  public List<DetalleIngreso> findTop20BySucursalOrderByIddetalleingresoDesc(String sucursal);

}
