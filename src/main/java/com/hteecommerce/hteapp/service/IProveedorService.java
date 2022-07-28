package com.hteecommerce.hteapp.service;

import java.util.List;

import com.hteecommerce.hteapp.entity.Proveedor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IProveedorService {
    
    public Page<Proveedor> getAll(Pageable pageable);
    public List<Proveedor> getByRazonOrRuc(String razonOrRuc);

    public Proveedor savePro(Proveedor proveedor);    

    public void deletePro(Integer idproveedor);
    public Proveedor getByIdproveedor(Integer idproveedor);

    public boolean isExistsByRuc(String ruc);
    public boolean isExistsByRazonSocial(String razonSocial);
    public boolean isExistsByTelefono(String telefono);

    public Proveedor getByUsername(String username);

    public boolean isExistsByTelefonoAndIdproveedor(String telefono, Integer idproveedor);

}
