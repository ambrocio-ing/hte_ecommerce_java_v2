package com.hteecommerce.hteapp.security.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.hteecommerce.hteapp.security.entity.Usuario;

public class MUsuario {
    
    private Integer idusuario;

    @NotNull
    @Size(min = 4, max = 15)
    @Column(unique = true)
    private String username;

    @NotNull
    @Email
    @Column(unique = true)
    private String email;

    @NotNull
    @Size(max = 12)
    private String estado;

    @NotNull
    @Size(min = 8, max = 15)
    @Column(unique = true)
    private String password;

    private List<String> roles = new ArrayList<>();

    public MUsuario(){

    }

    public MUsuario(Usuario usuario) {
        this.idusuario = usuario.getIdusuario();
        this.username = usuario.getUsername();
        this.email = usuario.getEmail();
        this.estado = usuario.getEstado();

        this.roles = usuario.getRoles().stream()
            .map(rol -> rol.getNombreRole().toString())
            .collect(Collectors.toList());    
       
    }

    public Integer getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(Integer idusuario) {
        this.idusuario = idusuario;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }      

}
