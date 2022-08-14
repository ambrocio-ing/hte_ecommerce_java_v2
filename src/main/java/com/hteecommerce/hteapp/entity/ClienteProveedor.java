package com.hteecommerce.hteapp.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "cliente_proveedores")
public class ClienteProveedor implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idcp;

    @NotNull
    @Size(min = 10, max = 11)
    @Column(unique = true)
    private String ruc;

    @NotNull
    @Size(max = 100)
    @Column(unique = true)
    private String razonSocial;

    @Size(max = 9, min = 7)
    @Column(unique = true)
    private String telefono;

    @Size(min = 5, max = 100)
    private String direccion;

    @Email
    @Column(unique = true)
    @Size(min = 5, max = 50)
    private String email;

    public ClienteProveedor() {

    }

    public ClienteProveedor(@Size(min = 10, max = 11) String ruc, String razonSocial,
            @Size(max = 9, min = 7) String telefono, String direccion, String email) {
        this.ruc = ruc;
        this.razonSocial = razonSocial;
        this.telefono = telefono;
        this.direccion = direccion;
        this.email = email;
    }

    public Integer getIdcp() {
        return idcp;
    }

    public void setIdcp(Integer idcp) {
        this.idcp = idcp;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // -id, ruc, razón social, numero de contacto, dirección, email,dnicliente
    private static final long serialVersionUID = 1L;
}
