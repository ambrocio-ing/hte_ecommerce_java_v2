package com.hteecommerce.hteapp.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "regimenes_alimenticios")
public class RegimenAlimenticio implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idregimenalimenticio;

    @NotNull
    @Size(max = 100)
    private String alimento;

    @NotNull
    @Size(max = 20)
    private String dia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "regimen_potajes", 
                joinColumns = @JoinColumn(name = "regimen_id"),
                inverseJoinColumns = @JoinColumn(name = "potaje_id"))
    private List<Potaje> potajes = null;

    public RegimenAlimenticio(){
        
    }

    public Integer getIdregimenalimenticio() {
        return idregimenalimenticio;
    }

    public void setIdregimenalimenticio(Integer idregimenalimenticio) {
        this.idregimenalimenticio = idregimenalimenticio;
    }

    public String getAlimento() {
        return alimento;
    }

    public void setAlimento(String alimento) {
        this.alimento = alimento;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<Potaje> getPotajes() {
        return potajes;
    }

    public void setPotajes(List<Potaje> potajes) {
        this.potajes = potajes;
    }

    private static final long serialVersionUID = 1L;

}
