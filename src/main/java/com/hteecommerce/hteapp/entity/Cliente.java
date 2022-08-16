package com.hteecommerce.hteapp.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.CascadeType;
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
@Table(name = "clientes")
public class Cliente implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idcliente;

    @NotNull
    @Size(max = 12)
    private String estado;

    @NotNull
    private LocalDate fecha;

    private Integer puntos;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "dni", nullable = false)
    private Persona persona;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @OneToOne(cascade = { CascadeType.MERGE, CascadeType.REMOVE })
    @JoinColumn(name = "cp_id", nullable = true)
    private ClienteProveedor clienteProveedor;

    @OneToOne(cascade = { CascadeType.MERGE, CascadeType.REMOVE })
    @JoinColumn(name = "caracteristica_id", nullable = true)
    private ClienteCaracteristica clienteCaracteristica;

    public Cliente() {

    }

    @PrePersist
    public void cargarFecha() {
        this.fecha = LocalDate.now();
    }

    public Integer getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(Integer idcliente) {
        this.idcliente = idcliente;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public ClienteProveedor getClienteProveedor() {
        return clienteProveedor;
    }

    public void setClienteProveedor(ClienteProveedor clienteProveedor) {
        this.clienteProveedor = clienteProveedor;
    }

    public ClienteCaracteristica getClienteCaracteristica() {
        return clienteCaracteristica;
    }

    public void setClienteCaracteristica(ClienteCaracteristica clienteCaracteristica) {
        this.clienteCaracteristica = clienteCaracteristica;
    }

    public Integer getPuntos() {
        return puntos;
    }

    public void setPuntos(Integer puntos) {
        this.puntos = puntos;
    }

    private static final long serialVersionUID = 1L;

}
