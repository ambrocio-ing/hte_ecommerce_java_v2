package com.hteecommerce.hteapp.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.hteecommerce.hteapp.entity.Color;
import com.hteecommerce.hteapp.entity.DetalleComprobante;
import com.hteecommerce.hteapp.entity.Variedad;

public class MResumenVenta {

    private String nombreProducto;
    private Double cantidadTotal;
    private Double percioVenta;
    private LocalDate fechaEntrega;
    private LocalDateTime fechaHoraEntrega;
    private List<Variedad> variedades;

    public MResumenVenta() {

    }

    public MResumenVenta(String nombreProducto, List<DetalleComprobante> dcs,
            LocalDate fechaEntrega, LocalDateTime fechaHoraEntrega) {
        this.nombreProducto = nombreProducto;
        this.cantidadTotal = this.calcularCantidad(dcs);
        this.percioVenta = this.obtenerPrecioVenta(dcs);
        this.fechaEntrega = fechaEntrega;
        this.fechaHoraEntrega = fechaHoraEntrega;
        this.variedades = this.obtenerVariedades(dcs);
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public Double getPercioVenta() {
        return percioVenta;
    }

    public void setPercioVenta(Double percioVenta) {
        this.percioVenta = percioVenta;
    }

    public LocalDateTime getFechaHoraEntrega() {
        return fechaHoraEntrega;
    }

    public void setFechaHoraEntrega(LocalDateTime fechaHoraEntrega) {
        this.fechaHoraEntrega = fechaHoraEntrega;
    }

    public Double getCantidadTotal() {
        return cantidadTotal;
    }

    public void setCantidadTotal(Double cantidadTotal) {
        this.cantidadTotal = cantidadTotal;
    }

    public LocalDate getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(LocalDate fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public List<Variedad> getVariedades() {
        return variedades;
    }

    public void setVariedades(List<Variedad> variedades) {
        this.variedades = variedades;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    private Double calcularCantidad(List<DetalleComprobante> dcs) {
        return dcs.stream().collect(Collectors.summingDouble(dc -> dc.getCantidad().doubleValue()));
    }

    private Double obtenerPrecioVenta(List<DetalleComprobante> dcs) {
        return dcs.stream().map(dc -> dc.getPrecioUnitario()).findFirst().orElse(0.00);
    }

    private List<Variedad> obtenerVariedades(List<DetalleComprobante> dcs) {
        List<DetalleComprobante> detcs = dcs.stream()
                .filter(dc -> dc.getDetalleIngreso().getVariedades() != null)
                .collect(Collectors.toList());

        if (detcs == null || detcs.size() == 0) {

            return null;
        }

        List<Variedad> variedades = detcs.stream()
                .map(dc -> dc.getVariedades())
                .flatMap(vas -> vas.stream())
                .collect(Collectors.toList());

        Map<String, List<Variedad>> grupos = variedades.stream()
                .collect(Collectors.groupingBy(va -> va.getNombreTalla()));

        List<Variedad> listVariedad = retornarNuevaLista(grupos);

        return listVariedad;
    }

    private List<Variedad> retornarNuevaLista(Map<String, List<Variedad>> grupos) {
        List<Variedad> listVariedad = new ArrayList<>();

        for (Map.Entry<String, List<Variedad>> pair : grupos.entrySet()) {
            List<Color> colores = pair.getValue().stream().map(va -> va.getColores()).flatMap(cos -> cos.stream())
                    .collect(Collectors.toList());
            listVariedad.add(
                    new Variedad(pair.getKey(), calcularCantidadVariedad(pair.getValue()), obtenerColores(colores)));
        }

        return listVariedad;

    }

    private List<Color> obtenerColores(List<Color> colores) {

        List<Color> colors = new ArrayList<>();
        Map<String, List<Color>> result = colores.stream().collect(Collectors.groupingBy(co -> co.getNombreColor()));
        for (Map.Entry<String, List<Color>> pair : result.entrySet()) {

            colors.add(new Color(pair.getKey(), calcularCantidadColor(pair.getValue()), null));
        }

        return colors;
    }

    private Integer calcularCantidadVariedad(List<Variedad> vas) {
        return vas.stream().collect(Collectors.summingInt(Variedad::getCantidadTalla));
    }

    private Integer calcularCantidadColor(List<Color> cols) {
        return cols.stream().collect(Collectors.summingInt(Color::getCantidadColor));
    }

}
