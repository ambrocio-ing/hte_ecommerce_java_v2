package com.hteecommerce.hteapp.security.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "usuarios")
public class Usuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @Column(unique = true, length = 70)
    private String password;

    @Column(name = "token_password", length = 70)
    private String tokenPassword;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "usuario_roles", 
            joinColumns = @JoinColumn(name = "usuario_id"), 
            inverseJoinColumns = @JoinColumn(name = "role_id"), 
            uniqueConstraints = { @UniqueConstraint(columnNames = { "usuario_id", "role_id" }) })
    private Set<Role> roles;

    public Usuario() {
        this.roles = new HashSet<>();
    }

    public Usuario(@NotNull @Size(min = 4, max = 15) String username, @NotNull @Email String email,
            @NotNull @Size(max = 12) String estado, @NotNull @Size(min = 8, max = 15) String password,
            Set<Role> roles) {
        this.username = username;
        this.email = email;
        this.estado = estado;
        this.password = password;
        this.roles = roles;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getTokenPassword() {
        return tokenPassword;
    }

    public void setTokenPassword(String tokenPassword) {
        this.tokenPassword = tokenPassword;
    }

    private static final long serialVersionUID = 1L;
}
