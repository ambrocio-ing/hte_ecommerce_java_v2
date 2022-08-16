package com.hteecommerce.hteapp.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "productos")
public class Producto implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idproducto;

    @NotNull
    @Size(max = 12)
    @Column(unique = true)
    private String codigo;

    @NotNull
    @Size(max = 50)
    @Column(unique = true)
    private String nombre;

    private Integer puntos;

    private String marca;

    private Integer nventas;

    private Integer nestrellas;

    @NotNull
    private String descripcion;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_id", nullable = false)
    private Tipo tipo;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pimagen_id", nullable = false)
    private ProductoImagen productoImagen;

    @OneToOne(cascade = { CascadeType.MERGE, CascadeType.REMOVE })
    @JoinColumn(name = "pdnutricional_id", nullable = true)
    private ProductoDatoNutricional productoDatoNutricional;

    @OneToOne(cascade = { CascadeType.MERGE, CascadeType.REMOVE })
    @JoinColumn(name = "pvestimenta_id", nullable = true)
    private ProductoVestimenta productoVestimenta;

    @OneToOne(cascade = { CascadeType.MERGE, CascadeType.REMOVE })
    @JoinColumn(name = "potros_id", nullable = true)
    private ProductoOtros productoOtros;

    public Producto() {
        
    }

    @PrePersist
    public void asignarNumeroEstrellas() {
        this.nestrellas = 5;
        this.nventas = 0;
    }

    public Integer getIdproducto() {
        return idproducto;
    }

    public void setIdproducto(Integer idproducto) {
        this.idproducto = idproducto;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public ProductoImagen getProductoImagen() {
        return productoImagen;
    }

    public void setProductoImagen(ProductoImagen productoImagen) {
        this.productoImagen = productoImagen;
    }

    public ProductoDatoNutricional getProductoDatoNutricional() {
        return productoDatoNutricional;
    }

    public void setProductoDatoNutricional(ProductoDatoNutricional productoDatoNutricional) {
        this.productoDatoNutricional = productoDatoNutricional;
    }

    public ProductoVestimenta getProductoVestimenta() {
        return productoVestimenta;
    }

    public void setProductoVestimenta(ProductoVestimenta productoVestimenta) {
        this.productoVestimenta = productoVestimenta;
    }

    public ProductoOtros getProductoOtros() {
        return productoOtros;
    }

    public void setProductoOtros(ProductoOtros productoOtros) {
        this.productoOtros = productoOtros;
    }

    public Integer getPuntos() {
        return puntos;
    }

    public void setPuntos(Integer puntos) {
        this.puntos = puntos;
    }

    public Integer getNventas() {
        return nventas;
    }

    public void setNventas(Integer nventas) {
        this.nventas = nventas;
    }

    public Integer getNestrellas() {
        return nestrellas;
    }

    public void setNestrellas(Integer nestrellas) {
        this.nestrellas = nestrellas;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    private static final long serialVersionUID = 1L;
    // -Id, código producto, nombre, categoría, tipo de producto, descripción breve
}
