package com.hteecommerce.hteapp.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.hteecommerce.hteapp.entity.Comprobante;
import com.hteecommerce.hteapp.entity.DetalleComprobante;
import com.hteecommerce.hteapp.entity.DetallePago;
import com.hteecommerce.hteapp.entity.DireccionEnvio;
import com.hteecommerce.hteapp.mapper.Mapper;

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

    private LocalDateTime fechaEntrega;  

    private String nbolsa;  

    private String formaEnvio;

    private MDireccionEnvio direccionEnvio;    

    private MDetallePago detallePago;

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
        this.nbolsa = comprobante.getNbolsa();
        this.formaEnvio = comprobante.getFormaEnvio();      
        this.detallePago = Mapper.mapDetallePago(comprobante.getDetallePago());
        this.direccionEnvio = new MDireccionEnvio(comprobante.getDireccionEnvio()); 
        this.detalleComprobantes = comprobante.getDetalleComprobantes().stream()
                .map(dc -> {
                    return new MDetalleComprobante(dc.getIddetallecomprobante(), dc.getVariedades() ,dc.getCantidad(), dc.getDescuento(),
                            dc.getSubTotal(), dc.getDetalleIngreso(), dc.getComprobanteId());
                }).collect(Collectors.toList());
    }

    

    public MComprobante(Integer idcomprobante, String numero, String idtransaccion, String tipoComprobante,
            LocalDateTime fechaPedido, String estado, double igv, Double montoEnvio, Double subTotal, Double total,
            Double descuento, LocalDateTime fechaEntrega, String nbolsa, String formaEnvio,
            DireccionEnvio direccionEnvio, DetallePago detallePago, List<DetalleComprobante> detalleComprobantes) {
        this.idcomprobante = idcomprobante;
        this.numero = numero;
        this.idtransaccion = idtransaccion;
        this.tipoComprobante = tipoComprobante;
        this.fechaPedido = fechaPedido;
        this.estado = estado;
        this.igv = igv;
        this.montoEnvio = montoEnvio;
        this.subTotal = subTotal;
        this.total = total;
        this.descuento = descuento;
        this.fechaEntrega = fechaEntrega;
        this.nbolsa = nbolsa;
        this.formaEnvio = formaEnvio;
        this.detallePago = new MDetallePago(detallePago);
        this.direccionEnvio = new MDireccionEnvio(direccionEnvio); 
        this.detalleComprobantes = detalleComprobantes.stream()
                .map(dc -> {
                    return new MDetalleComprobante(dc.getIddetallecomprobante(), dc.getVariedades() ,dc.getCantidad(), dc.getDescuento(),
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

    public String getNbolsa() {
        return nbolsa;
    }

    public void setNbolsa(String nbolsa) {
        this.nbolsa = nbolsa;
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

    public MDetallePago getDetallePago() {
        return detallePago;
    }

    public void setDetallePago(MDetallePago detallePago) {
        this.detallePago = detallePago;
    }
    

}
