package com.hteecommerce.hteapp.model;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.hteecommerce.hteapp.entity.Ingreso;

public class MIngreso {

    private Integer idingreso;

    private LocalDate fecha;

    private String tipoComprobante;

    private Double igv;

    private String estado;

    private MPersonal personal;

    private List<MDetalleIngreso> detalleIngresos;

    private String ruc;

    public MIngreso(){

    }

    public MIngreso(Ingreso ingreso) {
        this.idingreso = ingreso.getIdingreso();
        this.fecha = ingreso.getFecha();
        this.tipoComprobante = ingreso.getTipoComprobante();
        this.igv = ingreso.getIgv();
        this.estado = ingreso.getEstado();
        this.personal = new MPersonal(ingreso.getPersonal().getIdpersonal(), ingreso.getPersonal().getPersona());
        this.detalleIngresos = ingreso.getDetalleIngresos().stream()
            .map(di -> new MDetalleIngreso(di)).collect(Collectors.toList());
        this.ruc = ingreso.getRuc();
    }

    public Integer getIdingreso() {
        return idingreso;
    }

    public void setIdingreso(Integer idingreso) {
        this.idingreso = idingreso;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getTipoComprobante() {
        return tipoComprobante;
    }

    public void setTipoComprobante(String tipoComprobante) {
        this.tipoComprobante = tipoComprobante;
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

    public MPersonal getPersonal() {
        return personal;
    }

    public void setPersonal(MPersonal personal) {
        this.personal = personal;
    }

    public List<MDetalleIngreso> getDetalleIngresos() {
        return detalleIngresos;
    }

    public void setDetalleIngresos(List<MDetalleIngreso> detalleIngresos) {
        this.detalleIngresos = detalleIngresos;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }
    
}
