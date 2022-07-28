package com.hteecommerce.hteapp.repository;

import java.time.LocalDate;
import java.util.List;

import com.hteecommerce.hteapp.entity.Ingreso;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IIngresoRepository extends JpaRepository<Ingreso,Integer> {

    @Query("from Ingreso ing order by ing.idingreso desc")
    public Page<Ingreso> listAll(Pageable pageable);

    public List<Ingreso> findByFecha(LocalDate fecha);
}
