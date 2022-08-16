package com.hteecommerce.hteapp.security.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.hteecommerce.hteapp.enumm.NombreRole;

@Entity
@Table(name = "roles")
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idrol;

    @NotNull
    @Column(name = "nombre_role", unique = true)
    @Enumerated(EnumType.STRING)
    private NombreRole nombreRole;

    public Role() {

    }

    public Role(@NotNull NombreRole nombreRole) {
        this.nombreRole = nombreRole;
    }

    public int getIdrol() {
        return idrol;
    }

    public void setIdrol(int idrol) {
        this.idrol = idrol;
    }

    public NombreRole getNombreRole() {
        return nombreRole;
    }

    public void setNombreRole(NombreRole nombreRole) {
        this.nombreRole = nombreRole;
    }

    private static final long serialVersionUID = 1L;

}
