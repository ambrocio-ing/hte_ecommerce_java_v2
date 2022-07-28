package com.hteecommerce.hteapp.security.service;

import java.util.Optional;

import com.hteecommerce.hteapp.enumm.NombreRole;
import com.hteecommerce.hteapp.security.entity.Role;

public interface IRoleService {
    
    public void saveR(Role role);
    public boolean isExistsNombreRole(NombreRole nombreRole);

    public Optional<Role> getByNombreRole(NombreRole nombreRole);

}
