package com.hteecommerce.hteapp.repository;

import java.util.List;

import com.hteecommerce.hteapp.entity.Producto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductoRepository extends JpaRepository<Producto,Integer> {

    public boolean existsByCodigo(String codigo);
    public boolean existsByNombre(String nombre);

    @Query("from Producto pro where pro.codigo like %?1% or upper(replace(pro.nombre, ' ', '')) like concat('%',upper(?2),'%')")
    public List<Producto> listByCodigoOrNombre(String codigo, String nombre);

    @Query("select pro from Producto pro where pro.ingresadoBarranca = false or pro.ingresadoHuacho = false")
    public Page<Producto> listAllNotIngresados(Pageable pageable);

}
