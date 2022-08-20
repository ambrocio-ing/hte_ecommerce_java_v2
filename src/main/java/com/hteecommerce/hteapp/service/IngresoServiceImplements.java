package com.hteecommerce.hteapp.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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
    public DetalleIngreso getDIByIdproducto(Integer idproducto, String sucursal) {
        
        return detalleIngresoRepository.findByIdproducto(idproducto, sucursal).orElse(null);
    }

    //LISTA Y FILTRO EN VISTA DEL CLIENTE
    @Override
    @Transactional(readOnly = true)
    public List<DetalleIngreso> listDIAll(String sucrsal) {
        
        return detalleIngresoRepository.listAll(sucrsal);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DetalleIngreso> listDIAllToMarca(String sucursal, String marca) {
        
        return detalleIngresoRepository.listAllToMarca(sucursal, marca);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DetalleIngreso> getByNombreProducto(String nombre, String sucursal) {
        
        return detalleIngresoRepository.listByNombreProducto(nombre, sucursal);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DetalleIngreso> getByNombreProductoToMarca(String nombre, String sucursal, String marca) {
        
        return detalleIngresoRepository.listByNombreProductoToMarca(nombre, sucursal, marca);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DetalleIngreso> getByTipo(Integer idtipo, String sucursal) {
        
        return detalleIngresoRepository.listByTipo(idtipo, sucursal);
    }    

    @Override
    @Transactional(readOnly = true)
    public List<DetalleIngreso> getByTipoToMarca(Integer idtipo, String sucursal, String marca) {
        
        return detalleIngresoRepository.listByTipoToMarca(idtipo, sucursal, marca);
    }   

    @Override
    @Transactional(readOnly = true)
    public List<DetalleIngreso> getLast12ByProducto(Producto producto) {
        
        List<DetalleIngreso> lista = detalleIngresoRepository.list12Ultimos(producto.getIdproducto());
        return lista.stream().limit(12).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<DetalleIngreso> getMasVendidos(Integer idtipo, String sucursal) {
        
        return detalleIngresoRepository.listMasVendidos(idtipo, sucursal);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DetalleIngreso> getMasVendidosGeneral(String sucursal) {
        
        return detalleIngresoRepository.listMasVendidosGeneral(sucursal);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DetalleIngreso> getLastTwenty(String sucursal) {
        
        return detalleIngresoRepository.findTop20BySucursalOrderByIddetalleingresoDesc(sucursal);
    }

    @Override
    public DetalleIngreso getDetalleIngresoByIdproducto(Integer idproducto) {
        
        return detalleIngresoRepository.find_ByIdproducto(idproducto).orElse(null);
    }         
    
}
