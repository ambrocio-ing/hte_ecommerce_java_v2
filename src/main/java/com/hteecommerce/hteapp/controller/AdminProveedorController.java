package com.hteecommerce.hteapp.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hteecommerce.hteapp.entity.Proveedor;
import com.hteecommerce.hteapp.file_manager.IFileService;
import com.hteecommerce.hteapp.model.MProveedor;
import com.hteecommerce.hteapp.service.IProveedorService;
import com.hteecommerce.hteapp.util.RutaActual;

@RestController
@RequestMapping("/pro/admin")
public class AdminProveedorController {
    
    @Autowired
    private IProveedorService proveedorService;      
    
    @Autowired
    private IFileService fileService;

    @PreAuthorize("hasRole('SUPPLIER')")
    @GetMapping("/obtener/{id}")
    public ResponseEntity<?> getCliente(@PathVariable(value = "id") Integer idproveedor){

        Map<String,String> resp = new HashMap<>();
        Proveedor proveedor = null;        

        try {
            proveedor = proveedorService.getByIdproveedor(idproveedor);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity<Map<String,String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(proveedor == null){
            resp.put("mensaje", "No se encontraron coincidencias que eliminar");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }       

        MProveedor mpro = new MProveedor(proveedor);
        mpro.getUsuario().setRoles(new ArrayList<>());
        return new ResponseEntity<MProveedor>(mpro, HttpStatus.OK);
    }    

    @PreAuthorize("hasRole('SUPPLIER')")
    @PostMapping("/editar")
    public ResponseEntity<?> createProveedor(@RequestBody MProveedor proveedor){

        Map<String,Object> resp = new HashMap<>();
        Proveedor pro = null;   
        
        try {
            pro = proveedorService.getByIdproveedor(proveedor.getIdproveedor());
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error al guardar datos, por favor revise sus datos e intentelo denuevo");
            return new ResponseEntity<Map<String,Object>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(pro == null){
            resp.put("mensaje", "No fue posible cotejar sus datos");
            return new ResponseEntity<Map<String,Object>>(resp, HttpStatus.NOT_FOUND);
        }        

        if(proveedorService.isExistsByTelefonoAndIdproveedor(proveedor.getTelefono(), proveedor.getIdproveedor())){
            resp.put("mensaje", "El teléfono ya existe en el sistema");
            return new ResponseEntity<Map<String,Object>>(resp, HttpStatus.BAD_REQUEST);
        }    
            
        pro.setTelefono(proveedor.getTelefono());
        pro.setDireccion(proveedor.getDireccion());
        
        try {
            proveedorService.savePro(pro);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error al guardar datos, por favor revise sus datos e intentelo denuevo");
            return new ResponseEntity<Map<String,Object>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        resp.put("mensaje", "Datos guardados con éxito");
        resp.put("id", pro.getIdproveedor().toString());
        return new ResponseEntity<Map<String,Object>>(resp, HttpStatus.CREATED);

    }

    @PreAuthorize("hasRole('SUPPLIER')")
    @PostMapping("/imagen")
    public ResponseEntity<?> saveImage(@RequestParam(name = "id") Integer idproveedor,
            @RequestParam(name = "imagen") MultipartFile archivo) {

        Map<String,String> resp = new HashMap<>();
        Proveedor proveedor = null;

        String ruta = RutaActual.RUTA_PROVEEDOR;

        if(archivo.isEmpty()){
            resp.put("mensaje", "La imagen no es válido");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }

        try {
            proveedor = proveedorService.getByIdproveedor(idproveedor);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity<Map<String,String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(proveedor == null){
            resp.put("mensaje", "La imagen no se pudo guardar");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }

        String nombreImagen = null;

        try {
            nombreImagen = fileService.copiar(ruta, archivo);
        } catch (IOException e) {
            resp.put("mensaje", "La imagen no se pudo guardar");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }

        if(nombreImagen == null){
            resp.put("mensaje", "La imagen no se pudo guardar");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }

        fileService.eliminar(ruta, proveedor.getFotoPerfil());

        try {
           proveedor.setFotoPerfil(nombreImagen);
           proveedorService.savePro(proveedor);
        } catch (DataAccessException e) {
            resp.put("mensaje", "No es posible guardar la imagen, por favor asegurece que la imagen es válido e intentelo mas denuevo");
            return new ResponseEntity<Map<String,String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        resp.put("mensaje", "Felicidades,sus datos fueron guardados con éxito, ya puede iniciar sesión");
        return new ResponseEntity<Map<String, String>>(resp, HttpStatus.OK);        
    }

}
