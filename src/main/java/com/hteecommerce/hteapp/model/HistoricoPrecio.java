package com.hteecommerce.hteapp.model;

public class HistoricoPrecio {

    private Double precioVenta;
    private Double precioVentaAnterior;
    private int numero;
    private String annio;

    public HistoricoPrecio() {

    }

    public HistoricoPrecio(Double precioVenta, Double precioVentaAnterior, int numero, String annio) {
        this.precioVenta = precioVenta;
        this.precioVentaAnterior = precioVentaAnterior;
        this.numero = numero;
        this.annio = annio;
    }

    public Double getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(Double precioVenta) {
        this.precioVenta = precioVenta;
    }

    public Double getPrecioVentaAnterior() {
        return precioVentaAnterior;
    }

    public void setPrecioVentaAnterior(Double precioVentaAnterior) {
        this.precioVentaAnterior = precioVentaAnterior;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getAnnio() {
        return annio;
    }

    public void setAnnio(String annio) {
        this.annio = annio;
    }

}
