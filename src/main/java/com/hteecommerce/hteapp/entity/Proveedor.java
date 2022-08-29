package com.hteecommerce.hteapp.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.hteecommerce.hteapp.security.entity.Usuario;

@Entity
@Table(name = "proveedores")
public class Proveedor implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idproveedor;

    @NotNull
    @Size(min = 10, max = 11)
    @Column(unique = true)
    private String ruc;

    @NotNull
    @Size(max = 100)
    @Column(unique = true)
    private String razonSocial;

    @NotNull
    @Size(max = 9, min = 7)
    @Column(unique = true)
    private String telefono;

    @NotNull
    @Size(max = 100)
    private String direccion;    

    @NotNull
    @Size(max = 12)
    private String estado;

    private String fotoPerfil;

    @NotNull
    private LocalDate fecha;

    @NotNull
    private String sucursal;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    public Proveedor(){

    }          

    public Proveedor(@NotNull @Size(min = 10, max = 11) String ruc, @NotNull @Size(max = 100) String razonSocial,
            @NotNull @Size(max = 9, min = 7) String telefono, @NotNull @Size(max = 100) String direccion,
            @NotNull String sucursal, Usuario usuario) {
        this.ruc = ruc;
        this.razonSocial = razonSocial;
        this.telefono = telefono;
        this.direccion = direccion;        
        this.sucursal = sucursal;
        this.usuario = usuario;
    }

    @PrePersist
    public void generateDate(){
        this.fecha = LocalDate.now();
        this.estado = "Activo";
    }

    public Integer getIdproveedor() {
        return idproveedor;
    }

    public void setIdproveedor(Integer idproveedor) {
        this.idproveedor = idproveedor;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }   

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }    

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getSucursal() {
        return sucursal;
    }

    public void setSucursal(String sucursal) {
        this.sucursal = sucursal;
    }

    private static final long serialVersionUID = 1L;
}
