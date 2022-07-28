package com.hteecommerce.hteapp.model;

public class HistoricoPrecio {

    private Double precioPromedio;
    private String mes;
    private String annio;

    public HistoricoPrecio() {

    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getAnnio() {
        return annio;
    }

    public void setAnnio(String annio) {
        this.annio = annio;
    }

    public Double getPrecioPromedio() {
        return precioPromedio;
    }

    public void setPrecioPromedio(Double precioPromedio) {
        this.precioPromedio = precioPromedio;
    }

}
