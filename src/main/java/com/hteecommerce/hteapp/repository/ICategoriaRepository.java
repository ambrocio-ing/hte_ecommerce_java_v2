package com.hteecommerce.hteapp.repository;

import java.util.Optional;

import com.hteecommerce.hteapp.entity.Categoria;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategoriaRepository extends JpaRepository<Categoria,Integer> {
    
    public Optional<Categoria> findByNombre(String nombre);
    public boolean existsByNombre(String nombre);
}
