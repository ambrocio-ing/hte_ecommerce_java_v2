package com.hteecommerce.hteapp.repository;

import com.hteecommerce.hteapp.entity.Variedad;

import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface IVariedadRepository extends JpaRepository<Variedad, Integer> {
    
}
