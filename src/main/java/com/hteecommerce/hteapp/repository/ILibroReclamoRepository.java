package com.hteecommerce.hteapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hteecommerce.hteapp.entity.LibroReclamo;

@Repository
public interface ILibroReclamoRepository extends JpaRepository<LibroReclamo, Integer> {
    
    public Optional<LibroReclamo> findTopByOrderByIdlibroreclamoDesc();

}
