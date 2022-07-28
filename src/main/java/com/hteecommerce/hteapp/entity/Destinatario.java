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
@Table(name = "destinatarios")
public class Destinatario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer iddestinatario;

    @NotNull
    @Size(max = 50)
    private String nombre;

    @NotNull
    @Size(max = 50)
    private String apellidos;

    @NotNull
    @Size(max = 12)
    @Column(unique = true)
    private String dni;

    @NotNull
    @Size(max = 12)
    private String estado;

    public Destinatario(){
        
    }
    
    public Integer getIddestinatario() {
        return iddestinatario;
    }

    public void setIddestinatario(Integer iddestinatario) {
        this.iddestinatario = iddestinatario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }    

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {        
        return dni+","+nombre+","+apellidos+","+estado;
    }

    private static final long serialVersionUID = 1L;
}
