package com.hteecommerce.hteapp.service;

import java.util.List;

import com.hteecommerce.hteapp.entity.HoraEntrega;

public interface IHoraEntregaService {
    
    public List<HoraEntrega> getAll();
    public void saveHE(HoraEntrega horaEntrega);
    public void deleteHE(Integer idhora);
    public HoraEntrega getByIdhoraentrega(Integer idhora);


}
