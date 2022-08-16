package com.hteecommerce.hteapp.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "proveedor_ofertas")
public class ProveedorOferta implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idproveedoroferta;

    @NotNull
    @Size(max = 50)
    private String nombre;

    @NotNull
    private Double precio;

    @NotNull
    private int cantidad;

    @NotNull
    @Size(max = 10)
    private String unidad;

    @NotNull
    private String calidad;

    @NotNull
    private String descripcion;

    @NotNull
    private String imagen;

    @Column(name = "proveedorcomprobante_id")
    private Integer proveedorcomprobanteId;

    public ProveedorOferta() {

    }

    public Integer getIdproveedoroferta() {
        return idproveedoroferta;
    }

    public void setIdproveedoroferta(Integer idproveedoroferta) {
        this.idproveedoroferta = idproveedoroferta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public String getCalidad() {
        return calidad;
    }

    public void setCalidad(String calidad) {
        this.calidad = calidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Integer getProveedorcomprobanteId() {
        return proveedorcomprobanteId;
    }

    public void setProveedorcomprobanteId(Integer proveedorcomprobanteId) {
        this.proveedorcomprobanteId = proveedorcomprobanteId;
    }

    private static final long serialVersionUID = 1L;

}
