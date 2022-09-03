package com.hteecommerce.hteapp.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "comprobantes")
public class Comprobante implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idcomprobante;

    @Size(max = 12)
    private String ruc;

    @Size(max = 100)
    @Column(name = "razon_social")
    private String razonSocial;

    @Column(unique = true)
    @Size(max = 10, min = 10)
    private String numero;

    @Size(max = 35)
    private String idtransaccion;

    @NotNull
    @Size(max = 15)
    @Column(name = "tipo_comprobante")
    private String tipoComprobante;

    @Column(name = "fecha_pedido")
    private LocalDateTime fechaPedido;

    @NotNull
    @Size(max = 30)
    private String estado;

    @NotNull
    private Double igv;

    @NotNull
    @Column(name = "monto_envio")
    private Double montoEnvio;

    @NotNull
    @Column(name = "sub_total")
    private Double subTotal;

    @NotNull
    private Double total;

    private Double descuento;

    @NotNull
    @Column(name = "fecha_entrega")
    private LocalDateTime fechaEntrega;

    @Size(max = 20)
    private String nbolsa;

    @NotNull
    @Column(name = "forma_envio")
    @Size(max = 50)
    private String formaEnvio;    
    
    private String imagen;

    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "direccion_id", nullable = false)
    private DireccionEnvio direccionEnvio;    

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "detallepago_id", nullable = true)
    private DetallePago detallePago;

    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "comprobante_id", referencedColumnName = "idcomprobante")
    public List<DetalleComprobante> detalleComprobantes = null;

    public Comprobante() {
        this.detalleComprobantes = new ArrayList<>();
    }

    @PrePersist
    public void cargarFecha() {
        this.fechaPedido = LocalDateTime.now();
    }    

    public Integer getIdcomprobante() {
        return idcomprobante;
    }

    public void setIdcomprobante(Integer idcomprobante) {
        this.idcomprobante = idcomprobante;
    }

    public String getTipoComprobante() {
        return tipoComprobante;
    }

    public void setTipoComprobante(String tipoComprobante) {
        this.tipoComprobante = tipoComprobante;
    }

    public LocalDateTime getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(LocalDateTime fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public Double getIgv() {
        return igv;
    }

    public void setIgv(Double igv) {
        this.igv = igv;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getIdtransaccion() {
        return idtransaccion;
    }

    public void setIdtransaccion(String idtransaccion) {
        this.idtransaccion = idtransaccion;
    }

    public List<DetalleComprobante> getDetalleComprobantes() {
        return detalleComprobantes;
    }

    public void setDetalleComprobantes(List<DetalleComprobante> detalleComprobantes) {
        this.detalleComprobantes = detalleComprobantes;
    }

    public Double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public DireccionEnvio getDireccionEnvio() {
        return direccionEnvio;
    }

    public void setDireccionEnvio(DireccionEnvio direccionEnvio) {
        this.direccionEnvio = direccionEnvio;
    }

    public Double getMontoEnvio() {
        return montoEnvio;
    }

    public void setMontoEnvio(Double montoEnvio) {
        this.montoEnvio = montoEnvio;
    }

    public Double getDescuento() {
        return descuento;
    }

    public void setDescuento(Double descuento) {
        this.descuento = descuento;
    }

    public String getNbolsa() {
        return nbolsa;
    }

    public void setNbolsa(String nbolsa) {
        this.nbolsa = nbolsa;
    }

    public DetallePago getDetallePago() {
        return detallePago;
    }

    public void setDetallePago(DetallePago detallePago) {
        this.detallePago = detallePago;
    }

    public LocalDateTime getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(LocalDateTime fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public String getFormaEnvio() {
        return formaEnvio;
    }

    public void setFormaEnvio(String formaEnvio) {
        this.formaEnvio = formaEnvio;
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

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    private static final long serialVersionUID = 1L;

}
