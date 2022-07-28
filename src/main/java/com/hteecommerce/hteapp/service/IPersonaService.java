package com.hteecommerce.hteapp.service;

import com.hteecommerce.hteapp.entity.Persona;

public interface IPersonaService {
    
    public Persona saveP(Persona persona);
    public Persona getByDni(String dni);
    public boolean isExistsDni(String dni);
    public boolean isExistsTelefono(String telefono);

    public boolean isExistsTelefonoAndDni(String telefono, String dni);

}
