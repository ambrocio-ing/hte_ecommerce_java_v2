package com.hteecommerce.hteapp.service;

import java.util.List;

import com.hteecommerce.hteapp.entity.Personal;

public interface IPersonalService {
    
    public List<Personal> getAll();
    public void savePe(Personal personal);
    public void deletePe(Integer idpersonal);
    public Personal getByIdpersonal(Integer idpersonal);

    public Personal getByUsername(String username);

}
