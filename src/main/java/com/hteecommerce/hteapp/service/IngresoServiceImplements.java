package com.hteecommerce.hteapp.service;

import java.time.LocalDate;
import java.util.List;

import com.hteecommerce.hteapp.entity.DetalleIngreso;
import com.hteecommerce.hteapp.entity.Ingreso;
import com.hteecommerce.hteapp.entity.Producto;
import com.hteecommerce.hteapp.repository.IDetalleIngresoRepository;
import com.hteecommerce.hteapp.repository.IIngresoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class IngresoServiceImplements implements IIngresoService {

    @Autowired
    private IIngresoRepository ingresoRepository;

    @Autowired
    private IDetalleIngresoRepository detalleIngresoRepository;   

    @Override
    @Transactional(readOnly = true)
    public Page<Ingreso> getAll(Pageable pageable) {
       
        return ingresoRepository.listAll(pageable);
    }

    @Override
    @Transactional
    public Ingreso saveIN(Ingreso ingreso, List<DetalleIngreso> dis) {

        if(dis.size() != 0){
            detalleIngresoRepository.saveAll(dis);
        }
        
        return ingresoRepository.save(ingreso);
    }    

    @Override
    @Transactional
    public void deleteIN(Integer idingreso, List<Producto> productos) {
        
        ingresoRepository.deleteById(idingreso);
        for(Producto pro : productos){
            DetalleIngreso di = detalleIngresoRepository.findTopByProductoOrderByIddetalleingresoDesc(pro).orElse(null);
            if(di != null){
                di.setEstado(true);
                detalleIngresoRepository.save(di);
            }
        }
        
    }

    @Override
    @Transactional
    public void deleteDI(Integer iddi, Producto producto) {
        
        detalleIngresoRepository.deleteById(iddi);
        DetalleIngreso di = detalleIngresoRepository.findTopByProductoOrderByIddetalleingresoDesc(producto).orElse(null);
        if(di != null){
            di.setEstado(true);
            detalleIngresoRepository.save(di);
        }        
        
    }  

    @Override
    @Transactional(readOnly = true)
    public DetalleIngreso getTopByProductoOrderByIddetalleingresoDesc(Producto producto) {
        
        return detalleIngresoRepository.findTopByProductoOrderByIddetalleingresoDesc(producto).orElse(null);
    } 

    @Override
    @Transactional(readOnly = true)
    public Ingreso getByIdngreso(Integer idingreso) {
        
        return ingresoRepository.findById(idingreso).orElse(null);
    }    

    @Override
    @Transactional(readOnly = true)
    public List<Ingreso> getByFecha(LocalDate fecha) {
        
        return ingresoRepository.findByFecha(fecha);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DetalleIngreso> getDetalleIngresoByCodigoOrNombre(String codigoOrNombre) {
        
        return detalleIngresoRepository.listByCodigoOrNombreOfProducto(codigoOrNombre, codigoOrNombre);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DetalleIngreso> pageAllDetalleIngreso(Pageable pageable) {
        
        return detalleIngresoRepository.pageAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public DetalleIngreso getByIddetalleingreso(Integer iddi) {
        
        return detalleIngresoRepository.findById(iddi).orElse(null);
    }    

    @Override
    @Transactional(readOnly = true)
    public DetalleIngreso getDIByIdproducto(Integer idproducto) {
        
        return detalleIngresoRepository.findByIdproducto(idproducto).orElse(null);
    }

    //LISTA Y FILTRO EN VISTA DEL CLIENTE
    @Override
    @Transactional(readOnly = true)
    public List<DetalleIngreso> listDIAll() {
        
        return detalleIngresoRepository.listAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<DetalleIngreso> getByNombreProducto(String nombre) {
        
        return detalleIngresoRepository.listByNombreProducto(nombre);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DetalleIngreso> getByTipo(Integer idtipo) {
        
        return detalleIngresoRepository.listByTipo(idtipo);
    }    

    @Override
    @Transactional(readOnly = true)
    public List<DetalleIngreso> getLast12ByProducto(Producto producto) {
        
        return detalleIngresoRepository.findTop12ByProductoByOrderByIddetalleingresoDesc(producto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DetalleIngreso> getMasVendidos(Integer idtipo) {
        
        return detalleIngresoRepository.listMasVendidos(idtipo);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DetalleIngreso> getMasVendidosGeneral() {
        
        return detalleIngresoRepository.listMasVendidosGeneral();
    }

    @Override
    @Transactional(readOnly = true)
    public List<DetalleIngreso> getLastTwenty() {
        
        return detalleIngresoRepository.listLastTwenty();
    }         
    
}
