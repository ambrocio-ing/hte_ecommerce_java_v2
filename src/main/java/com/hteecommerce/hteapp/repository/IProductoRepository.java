package com.hteecommerce.hteapp.repository;

import java.util.List;

import com.hteecommerce.hteapp.entity.Producto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductoRepository extends JpaRepository<Producto,Integer> {

    public boolean existsByCodigo(String codigo);
    public boolean existsByNombre(String nombre);

    @Query("from Producto pro where pro.codigo like %?1% or upper(trim(pro.nombre)) like concat('%',upper(?2),'%')")
    public List<Producto> listByCodigoOrNombre(String codigo, String nombre);

}
