package com.hteecommerce.hteapp.security.repository;

import java.util.Optional;

import com.hteecommerce.hteapp.enumm.NombreRole;
import com.hteecommerce.hteapp.security.entity.Role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Integer> {
    
    public boolean existsByNombreRole(NombreRole nombreRole);
    public Optional<Role> findByNombreRole(NombreRole nombreRole);

}
