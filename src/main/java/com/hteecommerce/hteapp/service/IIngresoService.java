package com.hteecommerce.hteapp.service;

import java.time.LocalDate;
import java.util.List;

import com.hteecommerce.hteapp.entity.DetalleIngreso;
import com.hteecommerce.hteapp.entity.Ingreso;
//import com.hteecommerce.hteapp.entity.Producto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IIngresoService {
    
    public Page<Ingreso> getAll(Pageable pageable);
    public Ingreso saveIN(Ingreso ingreso);
    public void updateIN(Ingreso ingreso);
    public void deleteDI(Integer iddi);  
    public void deleteIN(Integer idingreso);    
    public Ingreso getByIdngreso(Integer idingreso);
    public List<Ingreso> getByFecha(LocalDate fecha);

    public List<DetalleIngreso> getDetalleIngresoByCodigoOrNombre(String codigoOrNombre);
    public List<DetalleIngreso> getDetalleIngresoByCategoria(String nombreCategoria);

    public Page<DetalleIngreso> pageAllDetalleIngreso(Pageable pageable);
    public DetalleIngreso getByIddetalleingreso(Integer iddi);
    public DetalleIngreso getDetalleIngresoByIdproducto(Integer idproducto);
    
    //LISTA Y FILTRO DESDE VISTA DEL CLIENTE
    public DetalleIngreso getDIByIdproducto(Integer idproducto, String sucursal);
    public List<DetalleIngreso> listDIAll(String sucursal);
    public List<DetalleIngreso> listDIAllToMarca(String sucursal, String marca);

    public List<DetalleIngreso> getByNombreProducto(String nombre, String sucursal);
    public List<DetalleIngreso> getByNombreProductoToMarca(String nombre, String sucursal, String marca);

    public List<DetalleIngreso> getByTipo(Integer idtipo, String sucursal);
    public List<DetalleIngreso> getByTipoToMarca(Integer idtipo, String sucursal, String marca);

    //public List<DetalleIngreso> getLast12ByProducto(Producto producto);

    //productos mas vendidos
    public List<DetalleIngreso> getMasVendidos(Integer idtipo, String sucursal);
    public List<DetalleIngreso> getMasVendidosGeneral(String sucursal);
    public List<DetalleIngreso> getLastTwenty(String sucursal);
}
