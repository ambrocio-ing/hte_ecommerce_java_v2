package com.hteecommerce.hteapp.security.model;

public class JwtDto {

    private int id;
    private String documento;
    private String nombre;    
    private String fotoPerfil;
    private String email;
    private boolean isProveedor;    

    public JwtDto() {

    }      

    public JwtDto(int id, String documento, String nombre, String fotoPerfil, String email,
            boolean isProveedor) {
        this.id = id;
        this.documento = documento;
        this.nombre = nombre;        
        this.fotoPerfil = fotoPerfil;
        this.email = email;
        this.isProveedor = isProveedor;
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

    public boolean isProveedor() {
        return isProveedor;
    }

    public void setProveedor(boolean isProveedor) {
        this.isProveedor = isProveedor;
    }

}
