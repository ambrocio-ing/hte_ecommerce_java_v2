package com.hteecommerce.hteapp.model;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.hteecommerce.hteapp.entity.Cliente;
import com.hteecommerce.hteapp.entity.ClienteCaracteristica;
import com.hteecommerce.hteapp.entity.ClienteProveedor;
import com.hteecommerce.hteapp.entity.Persona;
import com.hteecommerce.hteapp.security.model.MUsuario;

public class MCliente {
    
    private Integer idcliente;

    @NotNull
    @Size(max = 12)
    private String estado;
    
    private LocalDate fecha;  
    
    private Integer puntos;
    
    private Persona persona;    
    
    private MUsuario usuario;    
    
    private ClienteProveedor clienteProveedor;    
    
    private ClienteCaracteristica clienteCaracteristica;
    
    public MCliente(){

    }

    public MCliente(Cliente cliente){
        this.idcliente = cliente.getIdcliente();
        this.estado = cliente.getEstado();
        this.fecha = cliente.getFecha();
        this.puntos = cliente.getPuntos();
        this.persona = cliente.getPersona();
        this.usuario = new MUsuario(cliente.getUsuario());
        this.clienteProveedor = cliente.getClienteProveedor();
        this.clienteCaracteristica = cliente.getClienteCaracteristica();
    }      

    public MCliente(Integer idcliente, Persona persona) {
        this.idcliente = idcliente;
        this.persona = persona;
    }    

    public MCliente(Integer idcliente) {
        this.idcliente = idcliente;
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

    public MUsuario getUsuario() {
        return usuario;
    }

    public void setUsuario(MUsuario usuario) {
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

}
