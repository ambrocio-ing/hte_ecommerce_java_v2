package com.hteecommerce.hteapp.security.repository;

import java.util.Optional;

import com.hteecommerce.hteapp.security.entity.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUsuarioRepository extends JpaRepository<Usuario, Integer> {

    public boolean existsByUsername(String username);
    public boolean existsByEmail(String email);
    
    public Optional<Usuario> findByUsernameOrEmail(String username, String email);
    public Optional<Usuario> findByUsername(String username);
    public Optional<Usuario> findByTokenPassword(String tokenPassword);
    
}
