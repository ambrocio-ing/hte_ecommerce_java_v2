package com.hteecommerce.hteapp.model;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.hteecommerce.hteapp.entity.Ingreso;
import com.hteecommerce.hteapp.entity.Persona;
import com.hteecommerce.hteapp.entity.Personal;
import com.hteecommerce.hteapp.security.model.MUsuario;

public class MPersonal {
    
    private Integer idpersonal;

    @NotNull
    @Size(max = 12)
    private String estado;
    
    private LocalDate fecha;

    @NotNull
    private Persona persona;    
    
    @NotNull
    private MUsuario usuario;

    List<MIngreso> ingresos;

    public MPersonal(){

    }

    public MPersonal(Personal personal){
        this.idpersonal = personal.getIdpersonal();
        this.estado = personal.getEstado();
        this.fecha = personal.getFecha();
        this.persona = personal.getPersona();
        this.usuario = new MUsuario(personal.getUsuario());        
    }  
         
    public MPersonal(Integer idpersonal, List<Ingreso> ingresos) {
        this.idpersonal = idpersonal;
        this.ingresos = ingresos.stream()
            .map(in -> new MIngreso(in)).collect(Collectors.toList());
    }

    public MPersonal(Integer idpersonal, @NotNull Persona persona) {
        this.idpersonal = idpersonal;       
        this.persona = new Persona(persona.getDni(), persona.getNombre(), persona.getApellidos(), persona.getFotoPerfil());        
    }

    public Integer getIdpersonal() {
        return idpersonal;
    }

    public void setIdpersonal(Integer idpersonal) {
        this.idpersonal = idpersonal;
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

    public List<MIngreso> getIngresos() {
        return ingresos;
    }

    public void setIngresos(List<MIngreso> ingresos) {
        this.ingresos = ingresos;
    }
    
}
