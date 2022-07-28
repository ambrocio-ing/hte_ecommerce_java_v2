package com.hteecommerce.hteapp.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.hteecommerce.hteapp.entity.Publicacion;
import com.hteecommerce.hteapp.file_manager.IFileService;
import com.hteecommerce.hteapp.service.IPublicacionService;
import com.hteecommerce.hteapp.util.RutaActual;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/publicacion/pu")
public class PublicacionController {

    @Autowired
    private IPublicacionService publicacionService;

    @Autowired
    private IFileService fileService;

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/lista")
    public ResponseEntity<?> getAll(){

        Map<String,String> resp = new HashMap<>();
        List<Publicacion> lista = null;
        
        try {
            lista = publicacionService.getAll();
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity<Map<String,String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(lista != null && lista.size() != 0){
            
            return new ResponseEntity<List<Publicacion>>(lista, HttpStatus.OK);
        }
        else{
            resp.put("mensaje", "Sin datos que mostrar");
            return new ResponseEntity<Map<String,String>>(resp, HttpStatus.NOT_FOUND);
        }
    }    

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/crear")
    public ResponseEntity<?> createPublicacion(@Valid @RequestBody Publicacion publicacion, BindingResult result){

        Map<String,Object> resp = new HashMap<>();        
        
        if(result.hasErrors()){
            List<String> errors = result.getFieldErrors().stream()
                    .map(err -> "El campo: " + err.getField() + " " + err.getDefaultMessage())
                    .collect(Collectors.toList());

            resp.put("mensaje", errors);
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.BAD_REQUEST);
        }
        
        Publicacion pu = null;
        
        try {
            pu = publicacionService.saveP(publicacion);
        } catch (Exception e) {
            resp.put("mensaje", "Error al crear publicación, intentelo mas tarde");
            return new ResponseEntity<Map<String,Object>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        resp.put("mensaje", "Publicación enviado con éxito");
        resp.put("id", pu.getIdpublicacion());
        return new ResponseEntity<Map<String,Object>>(resp, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/imagen")
    public ResponseEntity<?> saveImage(@RequestParam(name = "id") Integer id,
            @RequestParam(name = "imagen") MultipartFile imagen) {

        Map<String, String> resp = new HashMap<>();
        String ruta = RutaActual.RUTA_PUBLICACION;
        Publicacion publicacion = null;

        if (imagen.isEmpty()) {
            resp.put("mensaje", "La imagen no es válida");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }

        try {
            publicacion = publicacionService.getByIdpublicacion(id);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (publicacion == null) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }        

        String nombreImagen = null;

        try {
            nombreImagen = fileService.copiar(ruta, imagen);
        } catch (IOException e) {
            resp.put("mensaje", "No fue posible guardar imagenes del producto");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }

        if (nombreImagen == null) {
            resp.put("mensaje", "No fue posible guardar imagen");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }

        fileService.eliminar(ruta, publicacion.getImagen());
        publicacion.setImagen(nombreImagen);

        try {            
            publicacionService.saveP(publicacion);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error al guardar imagen");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        resp.put("mensaje", "Publicación guardado con éxito");
        return new ResponseEntity<Map<String, String>>(resp, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/obtener/{idpublicacion}")
    public ResponseEntity<?> getPublicacion(@PathVariable(value = "idpublicacion") Integer idpublicacion){

        Map<String,String> resp = new HashMap<>();
        Publicacion publicacion = null;
        
        try {
            publicacion = publicacionService.getByIdpublicacion(idpublicacion);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity<Map<String,String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(publicacion == null){
            resp.put("mensaje", "Sin datos que mostrar");
            return new ResponseEntity<Map<String,String>>(resp, HttpStatus.NOT_FOUND);            
        }   
        
        return new ResponseEntity<Publicacion>(publicacion, HttpStatus.OK);

    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/editar")
    public ResponseEntity<?> updatePublicacion(@RequestBody Publicacion publicacion){

        Map<String,Object> resp = new HashMap<>();        
        Publicacion pu = null;
        
        try {
            pu = publicacionService.getByIdpublicacion(publicacion.getIdpublicacion());
        } catch (Exception e) {
            resp.put("mensaje", "Error de consulta a la base de datos, intentelo mas tarde");
            return new ResponseEntity<Map<String,Object>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(pu == null){
            resp.put("mensaje", "Error de consulta a la base de datos, intentelo mas tarde");
            return new ResponseEntity<Map<String,Object>>(resp, HttpStatus.NOT_FOUND);  
        }

        pu.setDetalle(publicacion.getDetalle());
        pu.setEstado(publicacion.getEstado());
        pu.setUrl(publicacion.getUrl());
        pu.setFecha(publicacion.getFecha());
        pu.setFechaFin(publicacion.getFechaFin());        
        
        try {
            publicacionService.saveP(pu);
        } catch (Exception e) {
            resp.put("mensaje", "Error al actualizar publicación, intentelo mas tarde");
            return new ResponseEntity<Map<String,Object>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        resp.put("mensaje", "Publicación actualizado con éxito");
        return new ResponseEntity<Map<String,Object>>(resp, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/eliminar/{idpublicacion}")
    public ResponseEntity<?> deletePublicacion(@PathVariable(value = "idpublicacion") Integer idpublicacion){

        Map<String,String> resp = new HashMap<>();
        Publicacion publicacion = null;
        
        try {
            publicacion = publicacionService.getByIdpublicacion(idpublicacion);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity<Map<String,String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(publicacion == null){
            resp.put("mensaje", "Sin datos que mostrar");
            return new ResponseEntity<Map<String,String>>(resp, HttpStatus.NOT_FOUND);            
        }   

        try {
            publicacionService.deleteP(publicacion.getIdpublicacion());
        } catch (Exception e) {
            resp.put("mensaje", "Error al eliminar registro, intentelo mas tarde");
            return new ResponseEntity<Map<String,String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        resp.put("mensaje", "Publicación eliminado con éxito");
        return new ResponseEntity<Map<String,String>>(resp, HttpStatus.OK);
    }
    
}
