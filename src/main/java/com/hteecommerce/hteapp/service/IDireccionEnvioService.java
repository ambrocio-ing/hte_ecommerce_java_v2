package com.hteecommerce.hteapp.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.hteecommerce.hteapp.entity.Destinatario;
import com.hteecommerce.hteapp.entity.DireccionEnvio;

public interface IDireccionEnvioService {
    
    public List<DireccionEnvio> getByCliente(Integer idcliente);
    public DireccionEnvio saveDE(DireccionEnvio de);
    public void deleteDE(Integer idde);
    public DireccionEnvio getByIddireccionenvio(Integer idde);

    //validar destinatario
    public boolean isExistsByDni(String dni);
    public Destinatario getByDni(String dni);
    public Destinatario getByIddestinatario(Integer iddestinatario);
    public void updateDES(Destinatario destinatario);
    public Page<Destinatario> getAllDestinatario(Pageable pageable);    
    
}
