package com.hteecommerce.hteapp.repository;

import com.hteecommerce.hteapp.entity.Publicacion;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPublicacionRepository extends JpaRepository<Publicacion,Integer> {
    
    public List<Publicacion> findByEstado(String estado);

}
