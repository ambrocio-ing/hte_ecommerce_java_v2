package com.hteecommerce.hteapp.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.hteecommerce.hteapp.entity.Comprobante;
import com.hteecommerce.hteapp.entity.HoraEntrega;

public class MComprobante {

    private Integer idcomprobante;

    private String numero;

    private String idtransaccion;

    private String tipoComprobante;

    private LocalDateTime fechaPedido;

    private String estado;

    private double igv;

    private Double montoEnvio;

    private Double subTotal;

    private Double total;

    private Double descuento;

    private LocalDate fechaEntrega;    

    private MDireccionEnvio direccionEnvio;

    private HoraEntrega horaEntrega;

    private List<MDetalleComprobante> detalleComprobantes;

    public MComprobante() {

    }

    public MComprobante(Comprobante comprobante) {
        this.idcomprobante = comprobante.getIdcomprobante();
        this.numero = comprobante.getNumero();
        this.idtransaccion = comprobante.getIdtransaccion();
        this.tipoComprobante = comprobante.getTipoComprobante();
        this.fechaPedido = comprobante.getFechaPedido();
        this.estado = comprobante.getEstado();
        this.igv = comprobante.getIgv();
        this.montoEnvio = comprobante.getMontoEnvio();
        this.subTotal = comprobante.getSubTotal();
        this.total = comprobante.getTotal();
        this.descuento = comprobante.getDescuento();
        this.fechaEntrega = comprobante.getFechaEntrega();
        this.horaEntrega = comprobante.getHoraEntrega();
        this.direccionEnvio = new MDireccionEnvio(comprobante.getDireccionEnvio()); 
        this.detalleComprobantes = comprobante.getDetalleComprobantes().stream()
                .map(dc -> {
                    return new MDetalleComprobante(dc.getIddetallecomprobante(), dc.getVariedad() ,dc.getCantidad(), dc.getDescuento(),
                            dc.getSubTotal(), dc.getDetalleIngreso(), dc.getComprobanteId());
                }).collect(Collectors.toList());
    }

    public Integer getIdcomprobante() {
        return idcomprobante;
    }

    public void setIdcomprobante(Integer idcomprobante) {
        this.idcomprobante = idcomprobante;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public double getIgv() {
        return igv;
    }

    public void setIgv(double igv) {
        this.igv = igv;
    }

    public Double getMontoEnvio() {
        return montoEnvio;
    }

    public void setMontoEnvio(Double montoEnvio) {
        this.montoEnvio = montoEnvio;
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

    public LocalDate getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(LocalDate fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }    

    public MDireccionEnvio getDireccionEnvio() {
        return direccionEnvio;
    }

    public void setDireccionEnvio(MDireccionEnvio direccionEnvio) {
        this.direccionEnvio = direccionEnvio;
    }

    public List<MDetalleComprobante> getDetalleComprobantes() {
        return detalleComprobantes;
    }

    public void setDetalleComprobantes(List<MDetalleComprobante> detalleComprobantes) {
        this.detalleComprobantes = detalleComprobantes;
    }

    public Double getDescuento() {
        return descuento;
    }

    public void setDescuento(Double descuento) {
        this.descuento = descuento;
    }

    public HoraEntrega getHoraEntrega() {
        return horaEntrega;
    }

    public void setHoraEntrega(HoraEntrega horaEntrega) {
        this.horaEntrega = horaEntrega;
    }

}
