package com.hteecommerce.hteapp.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "libro_reclamos")
public class LibroReclamo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idlibroreclamo;

    private LocalDate fecha;
    
    @Size(max = 20)
    private String numero;

    @NotNull
    @Column(name = "razon_social")
    private String razonSocial;

    @NotNull
    @Size(max = 50)
    private String nombres;

    @NotNull
    private String domicilio;

    @NotNull
    @Size(max = 12)
    private String documento;

    @NotNull
    @Size(max = 9)
    private String telefono;

    @NotNull
    @Email
    private String email;

    @NotNull
    @Size(max = 50)
    private String padres;

    @NotNull
    @Column(name = "bien_contratado")
    private String bienContratado;

    private Double monto;

    @Column(name = "descripcion_bien")
    private String descripcionBien;

    @NotNull
    @Size(max = 10)
    private String tipo;

    @Column(name = "detalle_disconformidad")
    private String detalleDisconformidad;

    private String pedido;

    @Column(name = "fecha_comunicacion")
    private LocalDate fechaComunicacion;

    private String respuesta;

    public LibroReclamo() {

    }

    @PrePersist
    public void generarFecha(){
        this.fecha = LocalDate.now();
    }

    public Integer getIdlibroreclamo() {
        return idlibroreclamo;
    }

    public void setIdlibroreclamo(Integer idlibroreclamo) {
        this.idlibroreclamo = idlibroreclamo;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPadres() {
        return padres;
    }

    public void setPadres(String padres) {
        this.padres = padres;
    }

    public String getBienContratado() {
        return bienContratado;
    }

    public void setBienContratado(String bienContratado) {
        this.bienContratado = bienContratado;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public String getDescripcionBien() {
        return descripcionBien;
    }

    public void setDescripcionBien(String descripcionBien) {
        this.descripcionBien = descripcionBien;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDetalleDisconformidad() {
        return detalleDisconformidad;
    }

    public void setDetalleDisconformidad(String detalleDisconformidad) {
        this.detalleDisconformidad = detalleDisconformidad;
    }

    public String getPedido() {
        return pedido;
    }

    public void setPedido(String pedido) {
        this.pedido = pedido;
    }

    public LocalDate getFechaComunicacion() {
        return fechaComunicacion;
    }

    public void setFechaComunicacion(LocalDate fechaComunicacion) {
        this.fechaComunicacion = fechaComunicacion;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    private static final long serialVersionUID = 1L;
}
/*
 * --fecha
 * --numero
 * --razonsocial
 * --consumidor --> nombres, domicilio, dni, ce, telefono, email, padre o madre
 * 
 * --bien contratado --> producto o servicio, monto reclamo, descripcion
 * 
 * --tipo(reclamo o queja)
 * --detalle
 * --pedido
 * 
 * --fecha de comunicacion
 * --respuesta
 */