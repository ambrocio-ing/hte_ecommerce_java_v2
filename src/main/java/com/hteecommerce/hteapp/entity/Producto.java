package com.hteecommerce.hteapp.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
    @Size(max = 100)
    @Column(unique = true)
    private String nombre;

    private Integer puntos;

    private String marca;

    private Integer nventas;

    private Integer nestrellas;   

    private String descripcion;

    private Boolean ingresado;

    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_id", nullable = false)
    private Tipo tipo;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name = "producto_id")
    private List<ProductoImagen> productoImagenes = null;

    @OneToOne(cascade = CascadeType.ALL )
    @JoinColumn(name = "pdnutricional_id", nullable = true)
    private ProductoDatoNutricional productoDatoNutricional;

    @OneToOne(cascade = CascadeType.ALL )
    @JoinColumn(name = "pvestimenta_id", nullable = true)
    private ProductoVestimenta productoVestimenta;

    @OneToOne(cascade = CascadeType.ALL )
    @JoinColumn(name = "potros_id", nullable = true)
    private ProductoOtros productoOtros;

    public Producto() {
        this.productoImagenes = new ArrayList<>();
    }

    @PrePersist
    public void asignarNumeroEstrellas() {
        this.nestrellas = 5;
        this.nventas = 0;
        this.ingresado = false;
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

    public List<ProductoImagen> getProductoImagenes() {
        return productoImagenes;
    }

    public void setProductoImagenes(List<ProductoImagen> productoImagenes) {
        this.productoImagenes = productoImagenes;
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

    public Boolean getIngresado() {
        return ingresado;
    }

    public void setIngresado(Boolean ingresado) {
        this.ingresado = ingresado;
    }

    private static final long serialVersionUID = 1L;
    // -Id, código producto, nombre, categoría, tipo de producto, descripción breve
}
