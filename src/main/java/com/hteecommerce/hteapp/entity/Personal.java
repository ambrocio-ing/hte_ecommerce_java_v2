package com.hteecommerce.hteapp.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hteecommerce.hteapp.security.entity.Usuario;

@Entity
@Table(name = "personal")
public class Personal implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idpersonal;

    @NotNull
    @Size(max = 12)
    private String estado;
    
    private LocalDate fecha;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "dni", nullable = false)
    private Persona persona;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idusuario", nullable = false)
    private Usuario usuario;

    @JsonIgnoreProperties(value = {"personal", "hibernateLazyInitializer", "handler"}, allowSetters = true)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "personal", cascade = CascadeType.ALL)
    public List<Ingreso> ingresos;

    public Personal() {
        this.ingresos = new ArrayList<>();
    }

    @PrePersist
    public void uploadDate(){
        this.fecha = LocalDate.now();
    }

    public Personal(@NotNull @Size(max = 12) String estado, @NotNull LocalDate fecha,
            Persona persona, @NotNull Usuario usuario) {        
        this.estado = estado;
        this.fecha = fecha;
        this.persona = persona;
        this.usuario = usuario;
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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Ingreso> getIngresos() {
        return ingresos;
    }

    public void setIngresos(List<Ingreso> ingresos) {
        this.ingresos = ingresos;
    }

    private static final long serialVersionUID = 1L;
}
