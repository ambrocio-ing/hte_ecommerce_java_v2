package com.hteecommerce.hteapp.security.service;

import java.util.List;
import java.util.Optional;

import com.hteecommerce.hteapp.enumm.NombreRole;
import com.hteecommerce.hteapp.security.entity.Role;
import com.hteecommerce.hteapp.security.repository.IRoleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoleServiceImplements implements IRoleService {

    @Autowired
    private IRoleRepository roleRepository;

    @Override
    @Transactional
    public void saveR(Role role) {
        
        roleRepository.save(role);
        
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isExistsNombreRole(NombreRole nombreRole) {
        
        return roleRepository.existsByNombreRole(nombreRole);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Role> getByNombreRole(NombreRole nombreRole) {
        
        return roleRepository.findByNombreRole(nombreRole);
    }

    @Override
    @Transactional
    public void saveAllR(List<Role> roles) {
        
        roleRepository.saveAll(roles);
        
    }
    
}
