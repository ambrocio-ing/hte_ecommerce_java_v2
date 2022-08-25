package com.hteecommerce.hteapp.model;

import com.hteecommerce.hteapp.entity.DetallePago;

public class MDetallePago {

    private Integer iddetallepago;

    private String estadoPago;
    
    private String formaPago;
    
    private String tokenId;
   
    private String marcaTarjeta;
    
    private String tipoTarjeta;
   
    private String ordenId;
    
    private String numeroOrden;
   
    private String fechaCreacion;
   
    private String fechaExpiracion;    

    public MDetallePago() {

    }

    public MDetallePago(DetallePago detallePago) {
        this.iddetallepago = detallePago.getIddetallepago();
        this.estadoPago = detallePago.getEstadoPago();
        this.tokenId = detallePago.getTokenId();
        this.formaPago = detallePago.getFormaPago();        
        this.marcaTarjeta = detallePago.getMarcaTarjeta();
        this.tipoTarjeta = detallePago.getTipoTarjeta();      
        this.ordenId = detallePago.getOrdenId();
        this.numeroOrden = detallePago.getNumeroOrden();  
        this.fechaCreacion = detallePago.getFechaCreacion();
        this.fechaExpiracion = detallePago.getFechaExpiracion();
    }    

    public MDetallePago(Integer iddetallepago, String estadoPago, String formaPago, String marcaTarjeta,
            String tipoTarjeta, String fechaCreacion, String fechaExpiracion) {
        this.iddetallepago = iddetallepago;
        this.estadoPago = estadoPago;
        this.formaPago = formaPago;
        this.marcaTarjeta = marcaTarjeta;
        this.tipoTarjeta = tipoTarjeta;
        this.fechaCreacion = fechaCreacion;
        this.fechaExpiracion = fechaExpiracion;
    }

    public Integer getIddetallepago() {
        return iddetallepago;
    }

    public void setIddetallepago(Integer iddetallepago) {
        this.iddetallepago = iddetallepago;
    }

    public String getEstadoPago() {
        return estadoPago;
    }

    public void setEstadoPago(String estadoPago) {
        this.estadoPago = estadoPago;
    }

    public String getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public String getMarcaTarjeta() {
        return marcaTarjeta;
    }

    public void setMarcaTarjeta(String marcaTarjeta) {
        this.marcaTarjeta = marcaTarjeta;
    }

    public String getTipoTarjeta() {
        return tipoTarjeta;
    }

    public void setTipoTarjeta(String tipoTarjeta) {
        this.tipoTarjeta = tipoTarjeta;
    }

    public String getOrdenId() {
        return ordenId;
    }

    public void setOrdenId(String ordenId) {
        this.ordenId = ordenId;
    }

    public String getNumeroOrden() {
        return numeroOrden;
    }

    public void setNumeroOrden(String numeroOrden) {
        this.numeroOrden = numeroOrden;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getFechaExpiracion() {
        return fechaExpiracion;
    }

    public void setFechaExpiracion(String fechaExpiracion) {
        this.fechaExpiracion = fechaExpiracion;
    }

    
}
