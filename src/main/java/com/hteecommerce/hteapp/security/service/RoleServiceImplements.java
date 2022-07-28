package com.hteecommerce.hteapp.security.service;

import java.util.Optional;

import com.hteecommerce.hteapp.enumm.NombreRole;
import com.hteecommerce.hteapp.security.entity.Role;
import com.hteecommerce.hteapp.security.repository.IRoleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImplements implements IRoleService {

    @Autowired
    private IRoleRepository roleRepository;

    @Override
    public void saveR(Role role) {
        
        roleRepository.save(role);
        
    }

    @Override
    public boolean isExistsNombreRole(NombreRole nombreRole) {
        
        return roleRepository.existsByNombreRole(nombreRole);
    }

    @Override
    public Optional<Role> getByNombreRole(NombreRole nombreRole) {
        
        return roleRepository.findByNombreRole(nombreRole);
    }
    
}
