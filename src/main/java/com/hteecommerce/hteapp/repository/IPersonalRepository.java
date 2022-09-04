package com.hteecommerce.hteapp.repository;

import java.util.Optional;

import com.hteecommerce.hteapp.entity.Personal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IPersonalRepository extends JpaRepository<Personal,Integer> {
    
    @Query("select per from Personal per join per.usuario usu where usu.username = ?1")
    public Optional<Personal> personalByUsername(String username);

}
