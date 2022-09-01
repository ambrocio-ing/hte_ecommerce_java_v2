package com.hteecommerce.hteapp.model;

import com.hteecommerce.hteapp.entity.CompraRapida;
import com.hteecommerce.hteapp.mapper.Mapper;

public class MCompraRapida {
    
    private Integer idcompra;

    private String condicion;
    
    private String sucursal;
    
    private Integer idcliente;   
 
    private MDetalleIngreso detalleIngreso;

    public MCompraRapida(){

    }

    public MCompraRapida(CompraRapida compraRapida){
        this.idcompra = compraRapida.getIdcompra();
        this.condicion = compraRapida.getCondicion();
        this.sucursal = compraRapida.getSucursal();
        this.idcliente = compraRapida.getIdcliente();
        this.detalleIngreso = Mapper.mapDetalleIngreso(compraRapida.getDetalleIngreso());
    }

    public Integer getIdcompra() {
        return idcompra;
    }

    public void setIdcompra(Integer idcompra) {
        this.idcompra = idcompra;
    }

    public String getCondicion() {
        return condicion;
    }

    public void setCondicion(String condicion) {
        this.condicion = condicion;
    }

    public String getSucursal() {
        return sucursal;
    }

    public void setSucursal(String sucursal) {
        this.sucursal = sucursal;
    }

    public Integer getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(Integer idcliente) {
        this.idcliente = idcliente;
    }

    public MDetalleIngreso getDetalleIngreso() {
        return detalleIngreso;
    }

    public void setDetalleIngreso(MDetalleIngreso detalleIngreso) {
        this.detalleIngreso = detalleIngreso;
    }

    

}
