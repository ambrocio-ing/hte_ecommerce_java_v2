package com.hteecommerce.hteapp.repository;

import com.hteecommerce.hteapp.entity.Persona;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IPersonaRepository extends JpaRepository<Persona,String> {

    public boolean existsByDni(String dni);
    public boolean existsByTelefono(String telefono);

    @Query("from Persona per where per.telefono = ?1 and per.dni != ?1")
    public Optional<Persona> validTelefono(String telefono, String dni);
    
}
