package com.hteecommerce.hteapp.security.service;

import java.util.List;
import java.util.Optional;

import com.hteecommerce.hteapp.enumm.NombreRole;
import com.hteecommerce.hteapp.security.entity.Role;

public interface IRoleService {
    
    public void saveR(Role role);
    public void saveAllR(List<Role> roles);
    public boolean isExistsNombreRole(NombreRole nombreRole);
    public Optional<Role> getByNombreRole(NombreRole nombreRole);

}
