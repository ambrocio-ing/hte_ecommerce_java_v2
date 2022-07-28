package com.hteecommerce.hteapp.repository;

import java.util.Optional;
import java.util.List;

import com.hteecommerce.hteapp.entity.Tipo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ITipoRepository extends JpaRepository<Tipo,Integer> {
    
    public Optional<Tipo> findByNombre(String nombre);
    public boolean existsByNombre(String nombre);

    @Query("from Tipo ti join ti.categoria cat where cat.idcategoria = ?1")
    List<Tipo> listByIdcategoria(Integer idcategoria);
}
