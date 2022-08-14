package com.hteecommerce.hteapp.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "hora_entregas")
public class HoraEntrega implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idhoraentrega;

    @NotNull
    @Size(max = 12)
    private String hora;

    @Column(name = "create_at")
    private LocalDate createAt;

    public Integer getIdhoraentrega() {
        return idhoraentrega;
    }

    public HoraEntrega() {

    }

    @PrePersist
    public void generateDate() {
        this.createAt = LocalDate.now();
    }

    public void setIdhoraentrega(Integer idhoraentrega) {
        this.idhoraentrega = idhoraentrega;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public LocalDate getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDate createAt) {
        this.createAt = createAt;
    }

    private static final long serialVersionUID = 1L;
}
