package com.hteecommerce.hteapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hteecommerce.hteapp.entity.HoraEntrega;
import com.hteecommerce.hteapp.repository.IHoraEntregaRepository;

@Service
public class HoraEntregaServiceImplements implements IHoraEntregaService {

    @Autowired
    private IHoraEntregaRepository horaEntregaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<HoraEntrega> getAll() {
        
        return horaEntregaRepository.findAll();
    }

    @Override
    @Transactional
    public void saveHE(HoraEntrega horaEntrega) {
        
        horaEntregaRepository.save(horaEntrega);
        
    }

    @Override
    @Transactional
    public void deleteHE(Integer idhora) {
        
        horaEntregaRepository.deleteById(idhora);
        
    }

    @Override
    @Transactional(readOnly = true)
    public HoraEntrega getByIdhoraentrega(Integer idhora) {
        
        return horaEntregaRepository.findById(idhora).orElse(null);
    }
    

}
