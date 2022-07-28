package com.hteecommerce.hteapp.repository;

import com.hteecommerce.hteapp.entity.DetalleComprobante;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IDetalleComprobanteRepository extends JpaRepository<DetalleComprobante, Integer> {

    @Query("from DetalleComprobante dc join dc.detalleIngreso di join di.producto pro join pro.tipo tip where tip.idtipo = ?1 order by pro.nventas desc")
    public List<DetalleComprobante> listMasVendidos(Integer idtipo);

    @Query(nativeQuery = true, value = "select * from detalle_comprobante dc inner join detalle_ingreso di on dc.iddetalleingreso = di.iddetalleingreso inner join productos pro on di.idproducto = pro.idproducto order by pro.nventas desc limit 10")
    public List<DetalleComprobante> listMasVendidosGeneral();

}
