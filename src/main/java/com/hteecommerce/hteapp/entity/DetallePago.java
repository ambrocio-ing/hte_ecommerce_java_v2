package com.hteecommerce.hteapp.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "detalle_pagos")
public class DetallePago implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer iddetallepago;

    @Column(name = "estado_pago")
    private String estadoPago;

    @Column(name = "forma_pago")
    private String formaPago;

    @Column(name = "token_id")
    private String tokenId;

    @Column(name = "marca_tarjeta")
    private String marcaTarjeta;

    @Column(name = "tipo_tarjeta")
    private String tipoTarjeta;

    @Column(name = "orden_id")
    private String ordenId;

    @Column(name = "numero_orden")
    private String numeroOrden;

    @Column(name = "fecha_creacion")
    private String fechaCreacion;

    @Column(name = "fecha_expiracion")
    private String fechaExpiracion;

    public DetallePago(){
        
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

    private static final long serialVersionUID = 1L;
}
