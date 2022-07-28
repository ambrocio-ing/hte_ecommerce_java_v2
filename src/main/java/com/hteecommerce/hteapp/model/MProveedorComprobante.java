package com.hteecommerce.hteapp.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.hteecommerce.hteapp.entity.ProveedorComprobante;
import com.hteecommerce.hteapp.entity.ProveedorOferta;

public class MProveedorComprobante {

    private Integer idproveedorcomprobante;

    private LocalDateTime fecha;

    private String estado;

    private Double igv;

    private Double subTotal;

    private Double total;

    private MProveedor proveedor;

    private List<ProveedorOferta> proveedorOfertas = new ArrayList<>();

    public MProveedorComprobante(ProveedorComprobante pc) {
        this.idproveedorcomprobante = pc.getIdproveedorcomprobante();
        this.fecha = pc.getFecha();
        this.estado = pc.getEstado();
        this.igv = pc.getIgv();
        this.subTotal = pc.getSubTotal();
        this.total = pc.getTotal();
        this.proveedor = new MProveedor(
            pc.getProveedor().getIdproveedor(),pc.getProveedor().getRuc(),
            pc.getProveedor().getRazonSocial(), pc.getProveedor().getTelefono(),
            pc.getProveedor().getDireccion(), pc.getProveedor().getUsuario().getEmail());
        this.proveedorOfertas = pc.getProveedorOfertas();
    }

    public Integer getIdproveedorcomprobante() {
        return idproveedorcomprobante;
    }

    public void setIdproveedorcomprobante(Integer idproveedorcomprobante) {
        this.idproveedorcomprobante = idproveedorcomprobante;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Double getIgv() {
        return igv;
    }

    public void setIgv(Double igv) {
        this.igv = igv;
    }

    public Double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public MProveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(MProveedor proveedor) {
        this.proveedor = proveedor;
    }

    public List<ProveedorOferta> getProveedorOfertas() {
        return proveedorOfertas;
    }

    public void setProveedorOfertas(List<ProveedorOferta> proveedorOfertas) {
        this.proveedorOfertas = proveedorOfertas;
    }

}
