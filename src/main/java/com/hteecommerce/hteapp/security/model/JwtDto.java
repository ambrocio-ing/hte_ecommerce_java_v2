package com.hteecommerce.hteapp.security.model;

//import java.io.Serializable;

public class JwtDto {

    private int id;
    private String documento;
    private String nombre;    
    private String fotoPerfil;
    private String email;
    private String sucursal;  
    private boolean esProveedor;      

    public JwtDto() {

    }         

    public JwtDto(int id, String documento, String nombre, String fotoPerfil, String email, 
        String sucursal, boolean esProveedor) {
        this.id = id;
        this.documento = documento;
        this.nombre = nombre;
        this.fotoPerfil = fotoPerfil;
        this.email = email;
        this.sucursal = sucursal;
        this.esProveedor = esProveedor;        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }    

    public String getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }   

    public String getSucursal() {
        return sucursal;
    }

    public void setSucursal(String sucursal) {
        this.sucursal = sucursal;
    }

    public boolean isEsProveedor() {
        return esProveedor;
    }

    public void setEsProveedor(boolean esProveedor) {
        this.esProveedor = esProveedor;
    }       

    //private static final long serialVersionUID = 1L;

}
