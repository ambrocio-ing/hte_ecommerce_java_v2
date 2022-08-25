package com.hteecommerce.hteapp.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "personas")
public class Persona implements Serializable {

    @Id
    @Size(min = 8, max = 12)
    @Column(unique = true)
    private String dni;

    @NotNull
    @Size(max = 25)
    private String nombre;

    @NotNull
    @Size(max = 50)
    private String apellidos;

    @NotNull
    @Column(length = 1)
    private String genero;

    @NotNull
    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @NotNull
    @Column(unique = true)
    @Size(max = 9, min = 7)
    private String telefono;

    @Size(max = 100)
    private String direccion;

    @Column(name = "foto_perfil")
    private String fotoPerfil;

    public Persona() {

    }

    public Persona(@Size(min = 8, max = 12) String dni, @NotNull @Size(max = 25) String nombre,
            @NotNull @Size(max = 50) String apellidos, @NotNull String genero, @NotNull LocalDate fechaNacimiento,
            @NotNull @Size(max = 9, min = 7) String telefono) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.genero = genero;
        this.fechaNacimiento = fechaNacimiento;
        this.telefono = telefono;
    }

    public Persona(@Size(min = 8, max = 12) String dni, @NotNull @Size(max = 25) String nombre,
            @NotNull @Size(max = 50) String apellidos, String fotoPerfil) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.fotoPerfil = fotoPerfil;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    private static final long serialVersionUID = 1L;

}
