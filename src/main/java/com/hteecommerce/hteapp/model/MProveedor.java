package com.hteecommerce.hteapp.model;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.hteecommerce.hteapp.entity.Proveedor;
import com.hteecommerce.hteapp.security.model.MUsuario;

public class MProveedor {
    
    private Integer idproveedor;

    @NotNull
    @Size(min = 10, max = 11)    
    private String ruc;

    @NotNull
    @Size(max = 100)    
    private String razonSocial;

    @NotNull
    @Size(max = 9, min = 7)    
    private String telefono;

    @NotNull
    @Size(max = 100)
    private String direccion;    

    @NotNull
    @Size(max = 12)
    private String estado;

    private String fotoPerfil;

    private LocalDate fecha;

    @NotNull
    private String sucursal;

    @NotNull
    private MUsuario usuario;    

    public MProveedor(){

    }

    public MProveedor(Proveedor proveedor){
        this.idproveedor = proveedor.getIdproveedor();
        this.ruc = proveedor.getRuc();
        this.razonSocial = proveedor.getRazonSocial();
        this.telefono = proveedor.getTelefono();
        this.direccion = proveedor.getDireccion();        
        this.estado = proveedor.getEstado();
        this.fotoPerfil = proveedor.getFotoPerfil();
        this.fecha = proveedor.getFecha();
        this.sucursal = proveedor.getSucursal();
        this.usuario = new MUsuario(proveedor.getUsuario());
    }        

    public MProveedor(Integer idproveedor, @NotNull @Size(min = 10, max = 11) String ruc,
            @NotNull @Size(max = 100) String razonSocial, @NotNull @Size(max = 9, min = 7) String telefono,
            @NotNull @Size(max = 100) String direccion, String fotoPerfil) {
        this.idproveedor = idproveedor;
        this.ruc = ruc;
        this.razonSocial = razonSocial;
        this.telefono = telefono;
        this.direccion = direccion;
        this.fotoPerfil = fotoPerfil;
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

    public MUsuario getUsuario() {
        return usuario;
    }

    public void setUsuario(MUsuario usuario) {
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
   
}
