package com.hteecommerce.hteapp.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "detalle_membresias")
public class DetalleMembresia implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer iddetallemembresia;
        
    @Size(max = 35)
    private String idtransaccion;

    @NotNull
    private String estado;

    @NotNull
    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;

    @NotNull
    @Column(name = "fecha_fin")
    private LocalDate fechaFin;

    @Size(max = 100)
    private String imagen;

    @NotNull    
    @Column(name = "monto_total")
    private Double montoTotal;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "membresia_id", nullable = false)
    private Membresia membresia;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "detallepago_id", nullable = false)
    private DetallePago detallePago;

    public DetalleMembresia() {

    }

    public Integer getIddetallemembresia() {
        return iddetallemembresia;
    }

    public void setIddetallemembresia(Integer iddetallemembresia) {
        this.iddetallemembresia = iddetallemembresia;
    }

    public String getIdtransaccion() {
        return idtransaccion;
    }

    public void setIdtransaccion(String idtransaccion) {
        this.idtransaccion = idtransaccion;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Membresia getMembresia() {
        return membresia;
    }

    public void setMembresia(Membresia membresia) {
        this.membresia = membresia;
    }    

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public DetallePago getDetallePago() {
        return detallePago;
    }

    public void setDetallePago(DetallePago detallePago) {
        this.detallePago = detallePago;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Double getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(Double montoTotal) {
        this.montoTotal = montoTotal;
    }

    private static final long serialVersionUID = 1L;
}
