package com.hteecommerce.hteapp.service;

import java.util.List;

import com.hteecommerce.hteapp.entity.Proveedor;
import com.hteecommerce.hteapp.repository.IProveedorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProveedorServiceImplements implements IProveedorService {

    @Autowired
    private IProveedorRepository proveedorRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<Proveedor> getAll(Pageable pageable) {
        
        return proveedorRepository.listProveedores(pageable);
    }

    @Override
    @Transactional
    public Proveedor savePro(Proveedor proveedor) {
        
        return proveedorRepository.save(proveedor);
    }

    @Override
    @Transactional
    public void deletePro(Integer idproveedor) {
        
        proveedorRepository.deleteById(idproveedor);
        
    }

    @Override
    @Transactional(readOnly = true)
    public Proveedor getByIdproveedor(Integer idproveedor) {
        
        return proveedorRepository.findById(idproveedor).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isExistsByRuc(String ruc) {
        
        return proveedorRepository.existsByRuc(ruc);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isExistsByRazonSocial(String razonSocial) {
        
        return proveedorRepository.existsByRazonSocial(razonSocial);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isExistsByTelefono(String telefono) {
        
        return proveedorRepository.existsByTelefono(telefono);
    }

    @Override
    @Transactional(readOnly = true)
    public Proveedor getByUsername(String username) {
        
        return proveedorRepository.findByUsername(username).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Proveedor> getByRazonOrRuc(String razonOrRuc) {
        
        return proveedorRepository.listByRazonOrRuc(razonOrRuc, razonOrRuc);
    }    

    @Override
    @Transactional(readOnly = true)
    public boolean isExistsByTelefonoAndIdproveedor(String telefono, Integer idproveedor) {
        Proveedor pro = proveedorRepository.validTelefono(telefono, idproveedor).orElse(null);
        if(pro != null){
            return true;
        }
        return false;
    }    
    
}
