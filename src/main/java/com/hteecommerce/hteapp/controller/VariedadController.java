package com.hteecommerce.hteapp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.RestController;

import com.hteecommerce.hteapp.entity.Variedad;
import com.hteecommerce.hteapp.service.IVariedadService;

@RestController
@RequestMapping("/variedad/va")
public class VariedadController {

    @Autowired
    private IVariedadService variedadService;

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/lista")
    public ResponseEntity<?> listVariedades() {

        Map<String, String> resp = new HashMap<>();
        List<Variedad> variedades = null;

        try {
            variedades = variedadService.getAll();
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de sistema: Inténtelo mas tarde");
            return new ResponseEntity<Map<String, String>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (variedades != null && variedades.size() != 0) {
            return new ResponseEntity<List<Variedad>>(variedades, HttpStatus.OK);
        }

        resp.put("mensaje", "Sin datos que mostrar");
        return new ResponseEntity<Map<String, String>>(HttpStatus.NOT_FOUND);

    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/crear")
    public ResponseEntity<?> createVariedad(@Valid @RequestBody Variedad variedad, BindingResult result) {

        Map<String, String> resp = new HashMap<>();
        if (result.hasErrors()) {
            String errors = result.getFieldErrors().stream()
                    .map(err -> "El campo : " + err.getField() + " " + err.getDefaultMessage())
                    .collect(Collectors.joining(", "));

            resp.put("mensaje", errors);
            return new ResponseEntity<Map<String, String>>(HttpStatus.BAD_REQUEST);
        }

        try {
            variedadService.saveVA(variedad);
        } catch (Exception e) {
            resp.put("mensaje", "Error de sistema: Inténtelo mas tarde");
            return new ResponseEntity<Map<String, String>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        resp.put("mensaje", "Variedad guardado con éxito");
        return new ResponseEntity<Map<String, String>>(HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/obtener/{id}")
    public ResponseEntity<?> getVariedad(@PathVariable(value = "id") Integer idvariedad){

        Map<String, String> resp = new HashMap<>();
        Variedad variedad = null;

        try {
            variedad = variedadService.getByIdvariedad(idvariedad);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de sistema: Inténtelo mas tarde");
            return new ResponseEntity<Map<String, String>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(variedad == null){
            resp.put("mensaje", "Sin datos que mostrar");
            return new ResponseEntity<Map<String, String>>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Variedad>(variedad, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/editar")
    public ResponseEntity<?> updateVariedad(@RequestBody Variedad variedad) {

        Map<String, String> resp = new HashMap<>(); 

        Variedad va = null;

        try {
            va = variedadService.getByIdvariedad(variedad.getIdvariedad());
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de sistema: Inténtelo mas tarde");
            return new ResponseEntity<Map<String, String>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(va == null){
            resp.put("mensaje", "No se encontró coincidencias");
            return new ResponseEntity<Map<String, String>>(HttpStatus.NOT_FOUND);
        }       

        va.setCantidad(variedad.getCantidad());
        va.setNombre(variedad.getNombre());

        try {
            variedadService.saveVA(va);
        } catch (Exception e) {
            resp.put("mensaje", "Error de sistema: Inténtelo mas tarde");
            return new ResponseEntity<Map<String, String>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        resp.put("mensaje", "Variedad actualizado con éxito");
        return new ResponseEntity<Map<String, String>>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> deleteVariedad(@PathVariable(value = "id") Integer idvariedad){

        Map<String, String> resp = new HashMap<>();
        Variedad variedad = null;

        try {
            variedad = variedadService.getByIdvariedad(idvariedad);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de sistema: Inténtelo mas tarde");
            return new ResponseEntity<Map<String, String>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(variedad == null){
            resp.put("mensaje", "Sin datos que mostrar");
            return new ResponseEntity<Map<String, String>>(HttpStatus.NOT_FOUND);
        }

        try {
           variedadService.deleteVA(variedad.getIdvariedad());
        } catch (Exception e) {
            resp.put("mensaje", "Error de sistema: Inténtelo mas tarde");
            return new ResponseEntity<Map<String, String>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        resp.put("mensaje", "Variedad eliminado del sistema");
        return new ResponseEntity<Map<String, String>>(HttpStatus.OK);
    }

}
