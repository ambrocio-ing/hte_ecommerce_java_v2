package com.hteecommerce.hteapp.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.HashMap;

import com.hteecommerce.hteapp.service.IHoraEntregaService;
import com.hteecommerce.hteapp.entity.HoraEntrega;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;

@RestController
@RequestMapping("/hour-delivery/he")
public class HoraEntregaController {

    @Autowired
    private IHoraEntregaService horaEntregaService;

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/lista")
    public ResponseEntity<?> getAllHE(){

        Map<String, String> resp = new HashMap<>();
        List<HoraEntrega> hes = null;

        try {
            hes = horaEntregaService.getAll();
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de sistema: Inténtelo mas tarde");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(hes != null && hes.size() != 0){
            
            return new ResponseEntity<List<HoraEntrega>>(hes, HttpStatus.OK);
        }

        resp.put("mensaje", "Sin datos que mostrar");
        return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/crear")
    public ResponseEntity<?> createHE(@Valid @RequestBody HoraEntrega horaEntrega, BindingResult result){

        Map<String, String> resp = new HashMap<>();

        if(result.hasErrors()){
            String messageError = result.getFieldErrors().stream()
                                .map(err -> "El campo: "+ err.getField()+" "+err.getDefaultMessage())
                                .collect(Collectors.joining(", "));
            
            resp.put("mensaje", messageError);
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }

        try {
            horaEntregaService.saveHE(horaEntrega);
        } catch (Exception e) {
            resp.put("mensaje", "Error de sistema: Inténtelo mas tarde");
            resp.put("error", e.getMessage());
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        resp.put("mensaje", "Hora de entrega guardado con éxito");
        return new ResponseEntity<Map<String, String>>(resp, HttpStatus.CREATED);

    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/obtener/{id}")
    public ResponseEntity<?> getHE(@PathVariable(value = "id") Integer idhora){

        Map<String, String> resp = new HashMap<>();
        HoraEntrega he = null;

        try {
            he = horaEntregaService.getByIdhoraentrega(idhora);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de sistema: Inténtelo mas tarde");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(he == null){
            resp.put("mensaje", "Registro no encontrado");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<HoraEntrega>(he, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/update")
    public ResponseEntity<?> updateHE(@RequestBody HoraEntrega horaEntrega){

        Map<String, String> resp = new HashMap<>();
        HoraEntrega he = null;        

        try {
            he = horaEntregaService.getByIdhoraentrega(horaEntrega.getIdhoraentrega());
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de sistema: Inténtelo mas tarde");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(he == null){
            resp.put("mensaje", "Registro a editar no encontrado");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }

        he.setHora(horaEntrega.getHora());

        try {
            horaEntregaService.saveHE(he);
        } catch (Exception e) {
            resp.put("mensaje", "Error de sistema: Inténtelo mas tarde");
            resp.put("error", e.getMessage());
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        resp.put("mensaje", "registro actualizado con éxito");
        return new ResponseEntity<Map<String, String>>(resp, HttpStatus.OK);

    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> deleteHE(@PathVariable(value = "id") Integer idhora){

        Map<String, String> resp = new HashMap<>();
        HoraEntrega he = null;

        try {
            he = horaEntregaService.getByIdhoraentrega(idhora);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de sistema: Inténtelo mas tarde");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(he == null){
            resp.put("mensaje", "Registro no encontrado");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }

        try {
            horaEntregaService.deleteHE(he.getIdhoraentrega());
        } catch (Exception e) {
            resp.put("mensaje", "Error de sistema: Inténtelo mas tarde");
            resp.put("error", e.getMessage());
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        resp.put("mensaje", "registro eliminado con éxito");
        return new ResponseEntity<Map<String, String>>(resp, HttpStatus.OK);
    }
    
}
