package com.hteecommerce.hteapp.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.hteecommerce.hteapp.entity.Membresia;
import com.hteecommerce.hteapp.file_manager.IFileService;
import com.hteecommerce.hteapp.service.IMembresiaService;
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
@RequestMapping("/membresia/mem")
public class MembresiaController {

    @Autowired
    private IMembresiaService membresiaService;

    @Autowired
    private IFileService fileService;

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/lista")
    public ResponseEntity<?> listMembresia(){

        Map<String,String> resp = new HashMap<>();
        List<Membresia> lista = null;
        
        try {
            lista = membresiaService.getAll();
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity<Map<String,String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(lista != null && lista.size() != 0){
            return new ResponseEntity<List<Membresia>>(lista, HttpStatus.OK);
        }
        else{
            resp.put("mensaje", "Sin datos que mostrar");
            return new ResponseEntity<Map<String,String>>(resp, HttpStatus.NOT_FOUND);
        }

    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/crear")
    public ResponseEntity<?> createMembresia(@Valid @RequestBody Membresia membresia, BindingResult result){

        Map<String,Object> resp = new HashMap<>();
        Membresia mem = null;
        
        if(result.hasErrors()){
            List<String> errors = result.getFieldErrors().stream()
                    .map(err -> "El campo: " + err.getField() + " " + err.getDefaultMessage())
                    .collect(Collectors.toList());

            resp.put("mensaje", errors);
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.BAD_REQUEST);
        }
        
        try {
            mem = membresiaService.saveM(membresia);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error al crear membresia, intentelo mas tarde");
            return new ResponseEntity<Map<String,Object>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        resp.put("mensaje", "Membresia creado con éxito");
        resp.put("id", mem.getIdmembresia());
        return new ResponseEntity<Map<String,Object>>(resp, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/imagen")
    public ResponseEntity<?> saveImagen(@RequestParam(name = "id") Integer id,
        @RequestParam(name = "imagen") MultipartFile imagen){

        Map<String,Object> resp = new HashMap<>();
        String ruta = RutaActual.RUTA_MEMBRESIA;
        Membresia membresia = null;
        
        if(imagen.isEmpty()){
            resp.put("mensaje", "La imagen no es válido");
            return new ResponseEntity<Map<String,Object>>(resp, HttpStatus.NOT_FOUND);
        }

        String nombreImagen = null;

        try {
            nombreImagen = fileService.copiar(ruta, imagen);
        } catch (IOException e1) {
            resp.put("mensaje", "No fue posible guardar la imagen");
            return new ResponseEntity<Map<String,Object>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        try {
            membresia = membresiaService.getByIdmembresia(id);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error al crear membresia, intentelo mas tarde");
            return new ResponseEntity<Map<String,Object>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(nombreImagen == null || membresia == null){
            resp.put("mensaje", "No fue posible guardar la imagen");
            return new ResponseEntity<Map<String,Object>>(resp, HttpStatus.NOT_FOUND);
        }

        fileService.eliminar(ruta, membresia.getImagen());
        membresia.setImagen(nombreImagen);
        
        try {
            membresiaService.saveM(membresia);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error al crear membresia, intentelo mas tarde");
            return new ResponseEntity<Map<String,Object>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        resp.put("mensaje", "Membresia creado con éxito");
        return new ResponseEntity<Map<String,Object>>(resp, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/obtener/{id}")
    public ResponseEntity<?> getMembresia(@PathVariable(value = "id") Integer id){

        Map<String,String> resp = new HashMap<>();
        Membresia membresia = null;
        
        try {
            membresia = membresiaService.getByIdmembresia(id);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity<Map<String,String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(membresia == null){
            resp.put("mensaje", "Sin datos que mostrar, no se encontraron coincidencias");
            return new ResponseEntity<Map<String,String>>(resp, HttpStatus.NOT_FOUND);
        }
        
        return new ResponseEntity<Membresia>(membresia, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/editar")
    public ResponseEntity<?> updateMembresia(@Valid @RequestBody Membresia membresia, BindingResult result){

        Map<String,Object> resp = new HashMap<>();
        Membresia mem = null;
        
        if(result.hasErrors()){
            List<String> errors = result.getFieldErrors().stream()
                    .map(err -> "El campo: " + err.getField() + " " + err.getDefaultMessage())
                    .collect(Collectors.toList());

            resp.put("mensaje", errors);
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.BAD_REQUEST);
        }
        
        try {
            mem = membresiaService.getByIdmembresia(membresia.getIdmembresia());
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity<Map<String,Object>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(mem == null){
            resp.put("mensaje", "No se encontraron coincidencias");
            return new ResponseEntity<Map<String,Object>>(resp, HttpStatus.NOT_FOUND);
        }

        mem.setMonto(membresia.getMonto());
        mem.setTipo(membresia.getTipo());
        mem.setValor(membresia.getValor());
        mem.setEstado(membresia.getEstado());
        mem.setDetalle(membresia.getDetalle());
        mem.setDuracion(membresia.getDuracion());

        try {
            membresiaService.saveM(mem);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error al actualizar membresia, intentelo mas tarde");
            return new ResponseEntity<Map<String,Object>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        resp.put("mensaje", "Membresia actualizado con éxito");
        return new ResponseEntity<Map<String,Object>>(resp, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> deleteMembresia(@PathVariable(value = "id") Integer id){

        Map<String,String> resp = new HashMap<>();
        Membresia membresia = null;
        
        try {
            membresia = membresiaService.getByIdmembresia(id);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity<Map<String,String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(membresia == null){
            resp.put("mensaje", "Sin datos que mostrar, no se encontraron coincidencias");
            return new ResponseEntity<Map<String,String>>(resp, HttpStatus.NOT_FOUND);
        }

        try {
            membresiaService.deleteM(membresia.getIdmembresia());
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity<Map<String,String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }        
        
        resp.put("mensaje", "Membresia eliminado con éxito");
        return new ResponseEntity<Map<String,String>>(resp, HttpStatus.OK);
    }
    
}
