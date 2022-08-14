package com.hteecommerce.hteapp.repository;

import com.hteecommerce.hteapp.entity.HoraEntrega;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface IHoraEntregaRepository extends JpaRepository<HoraEntrega,Integer> {
    
}
