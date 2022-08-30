package com.hteecommerce.hteapp.repository;

import com.hteecommerce.hteapp.entity.Publicacion;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IPublicacionRepository extends JpaRepository<Publicacion,Integer> {
        
    public List<Publicacion> findByEstado(String estado);

    @Query("from Publicacion pu where pu.estado = 'Vigente' and ?1 between pu.fecha and pu.fechaFin ")
    public List<Publicacion> listByEstadoAndDates(LocalDate fechaFin);

}
