package com.hteecommerce.hteapp.model;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hteecommerce.hteapp.entity.Comprobante;
import com.hteecommerce.hteapp.entity.Destinatario;
import com.hteecommerce.hteapp.entity.DireccionEnvio;

public class MDireccionEnvio {

    private Integer iddireccion;

    private String direccion;

    private String telefono;

    private String referencia;

    private String codigoPostal;

    private String pais;

    private String region;

    private String provincia;

    private String distrito;

    private String principal;

    private MCliente cliente;

    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    private Destinatario destinatario;

    List<MComprobante> comprobantes;

    public MDireccionEnvio() {

    }

    public MDireccionEnvio(DireccionEnvio de) {
        this.iddireccion = de.getIddireccion();
        this.direccion = de.getDireccion();
        this.telefono = de.getTelefono();
        this.referencia = de.getReferencia();
        this.codigoPostal = de.getCodigoPostal();
        this.pais = de.getPais();
        this.region = de.getRegion();
        this.provincia = de.getProvincia();
        this.distrito = de.getDistrito();
        this.principal = de.getPrincipal();       
        if(de.getCliente() != null){
            this.cliente = new MCliente(de.getCliente().getIdcliente(), de.getCliente().getPersona());
        }

        if(de.getDestinatario() != null){
            this.destinatario = de.getDestinatario();
        }        
        
    }

    public MDireccionEnvio(Integer iddireccion, String direccion, String telefono, String referencia,
            String codigoPostal, String pais, String region, String provincia, String distrito, String principal,
            Destinatario destinatario) {
        this.iddireccion = iddireccion;
        this.direccion = direccion;
        this.telefono = telefono;
        this.referencia = referencia;
        this.codigoPostal = codigoPostal;
        this.pais = pais;
        this.region = region;
        this.provincia = provincia;
        this.distrito = distrito;
        this.principal = principal;       
        this.destinatario = destinatario;
    }

    public MDireccionEnvio(Integer iddireccion, List<Comprobante> comprobantes) {
        this.iddireccion = iddireccion;
        this.comprobantes = comprobantes.stream()
                .map(com -> new MComprobante(com))
                .collect(Collectors.toList());
    }

    public Integer getIddireccion() {
        return iddireccion;
    }

    public void setIddireccion(Integer iddireccion) {
        this.iddireccion = iddireccion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public MCliente getCliente() {
        return cliente;
    }

    public void setCliente(MCliente cliente) {
        this.cliente = cliente;
    }

    public Destinatario getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(Destinatario destinatario) {
        this.destinatario = destinatario;
    }

    public List<MComprobante> getComprobantes() {
        return comprobantes;
    }

    public void setComprobantes(List<MComprobante> comprobantes) {
        this.comprobantes = comprobantes;
    }    

}
