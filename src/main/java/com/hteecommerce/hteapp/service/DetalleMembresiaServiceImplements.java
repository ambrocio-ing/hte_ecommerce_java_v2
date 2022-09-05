package com.hteecommerce.hteapp.service;

import java.time.LocalDate;
import java.util.List;

import com.hteecommerce.hteapp.entity.DetalleMembresia;
import com.hteecommerce.hteapp.repository.IDetalleMembresiaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DetalleMembresiaServiceImplements implements IDetalleMembresiaService {

    @Autowired
    private IDetalleMembresiaRepository detalleMembresiaRepository;

    @Override
    @Transactional(readOnly = true)
    public DetalleMembresia getByIdcliente(Integer idcliente, LocalDate fecha) {
        
        return detalleMembresiaRepository.findByIdcliente(idcliente, fecha).orElse(null);
    }

    @Override
    @Transactional
    public DetalleMembresia saveDM(DetalleMembresia detalleMembresia) {
        
        return detalleMembresiaRepository.save(detalleMembresia);
    }

    @Override
    @Transactional
    public void deleteDM(Integer iddm) {
        
        detalleMembresiaRepository.deleteById(iddm);
    }

    @Override
    @Transactional(readOnly = true)
    public DetalleMembresia getByIddetallemembresia(Integer iddm) {
        
        return detalleMembresiaRepository.findById(iddm).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DetalleMembresia> getAllByEstado(String estado) {
        
        return detalleMembresiaRepository.findByEstado(estado);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DetalleMembresia> getByClienteDniOrNombre(String dniOrNombre) {
        
        return detalleMembresiaRepository.listByCliente(dniOrNombre);
    }
    
}
