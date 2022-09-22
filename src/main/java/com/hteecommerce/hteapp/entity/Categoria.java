package com.hteecommerce.hteapp.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "categorias")
public class Categoria implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idcategoria;

    @NotNull
    @Size(max = 20)
    @Column(unique = true)
    private String nombre;

    private String descripcion;

    @JsonIgnoreProperties(value = { "categoria", "hibernateLazyInitializer", "handler" }, allowSetters = true)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "categoria")
    private List<Tipo> tipos = null;

    public Categoria() {
        this.tipos = new ArrayList<>();
    }

    public Integer getIdcategoria() {
        return idcategoria;
    }

    public void setIdcategoria(Integer idcategoria) {
        this.idcategoria = idcategoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Tipo> getTipos() {
        return tipos;
    }

    public void setTipos(List<Tipo> tipos) {
        this.tipos = tipos;
    }

    private static final long serialVersionUID = 1L;
}
