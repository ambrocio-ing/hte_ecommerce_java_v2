package com.hteecommerce.hteapp.service;

import java.util.List;

import com.hteecommerce.hteapp.entity.Variedad;

public interface IVariedadService {
    
    public List<Variedad> getAll();
    public void saveVA(Variedad variedad);
    public void deleteVA(Integer idvariedad);
    public Variedad getByIdvariedad(Integer idvariedad);

}
