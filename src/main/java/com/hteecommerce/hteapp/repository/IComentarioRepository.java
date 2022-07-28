package com.hteecommerce.hteapp.repository;

import java.util.List;

import com.hteecommerce.hteapp.entity.Comentario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IComentarioRepository extends JpaRepository<Comentario,Integer> {
    
    @Query("from Comentario com join com.detalleIngreso di where di.iddetalleingreso = ?1")
    List<Comentario> listByIddetalleingreso(Integer iddi);

    @Query("from Comentario com join com.detalleIngreso di join di.producto pro where pro.idproducto = ?1")
    List<Comentario> listByIdproducto(Integer idproducto);
}
