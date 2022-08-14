package com.hteecommerce.hteapp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hteecommerce.hteapp.entity.LibroReclamo;
import com.hteecommerce.hteapp.entity.Sujerencia;
import com.hteecommerce.hteapp.mapper.Mapper;
import com.hteecommerce.hteapp.service.ILibroReclamoService;

@RestController
@RequestMapping("/lib-re/lr")
public class LibroReclamoController {

    @Autowired
    private ILibroReclamoService libroReclamoService;

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/lista/{page}")
    public ResponseEntity<?> listLR(@PathVariable(value = "page") int page){

        Map<String,String> resp = new HashMap<>();
        Page<LibroReclamo> lrs = null;

        Pageable pageable = PageRequest.of(page, 10);

        try {
            lrs = libroReclamoService.getAll(pageable);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta");
            return new ResponseEntity<Map<String,String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(lrs != null && lrs.getContent().size() != 0){
            return new ResponseEntity<Page<LibroReclamo>>(lrs, HttpStatus.OK);
        }

        resp.put("mensaje", "Sin datos que mostrar");
        return new ResponseEntity<Map<String,String>>(resp, HttpStatus.NOT_FOUND);
    }

    @PreAuthorize("hasRole('CLIENT')")
    @PostMapping("/crear")
    public ResponseEntity<?> createLR(@Valid @RequestBody LibroReclamo libroReclamo, BindingResult result){

        Map<String,String> resp = new HashMap<>();
        String numero = null;

        if(result.hasErrors()){
            List<String> errors = result.getFieldErrors().stream()
                .map(err -> "El campo: "+err.getField()+" "+err.getDefaultMessage())
                .collect(Collectors.toList());
            resp.put("mensaje", errors.toString());
            return new ResponseEntity<Map<String,String>>(resp, HttpStatus.BAD_REQUEST);
        }

        try {
            numero = libroReclamoService.getLatestNumero();
        } catch (Exception e) {
            resp.put("mensaje", "Error, no fue posible guardar reclamo");
            return new ResponseEntity<Map<String,String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(numero != null){
            String arrayNumero[] = numero.split("-");
            String serie = arrayNumero[0];
            String correlativo = arrayNumero[1];
            if(Integer.parseInt(correlativo) < 999999){
                numero = serie+"-"+Mapper.generateNumero(correlativo, 6);
            }
            else if(Integer.parseInt(correlativo) == 999999){
                numero = Mapper.generateNumero(serie, 3)+"-000001";
            }
        }
        else{
            numero = "001-000001";
        }

        libroReclamo.setNumero(numero);

        try {
            libroReclamoService.saveLR(libroReclamo);
        } catch (Exception e) {
            resp.put("mensaje", "Error, no fue posible guardar reclamo");
            return new ResponseEntity<Map<String,String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        resp.put("mensaje", "Su reclamo ha sido enviado con éxito");
        return new ResponseEntity<Map<String,String>>(resp, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/obtener/{id}")
    public ResponseEntity<?> getLR(@PathVariable(value = "id") Integer idlibro){

        Map<String,String> resp = new HashMap<>();
        LibroReclamo lr = null;        

        try {
            lr = libroReclamoService.getByIdlibro(idlibro);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta");
            return new ResponseEntity<Map<String,String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(lr != null){
            return new ResponseEntity<LibroReclamo>(lr, HttpStatus.OK);
        }

        resp.put("mensaje", "Sin datos que mostrar");
        return new ResponseEntity<Map<String,String>>(resp, HttpStatus.NOT_FOUND);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/editar")
    public ResponseEntity<?> updateLR(@RequestBody LibroReclamo libroReclamo){

        Map<String,String> resp = new HashMap<>();        
        LibroReclamo lr = null;

        try {
            lr = libroReclamoService.getByIdlibro(libroReclamo.getIdlibroreclamo());
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta");
            return new ResponseEntity<Map<String,String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(lr == null){
            resp.put("mensaje", "No se encontraron coincidencias");
            return new ResponseEntity<Map<String,String>>(resp, HttpStatus.NOT_FOUND);
        }

        lr.setFechaComunicacion(libroReclamo.getFechaComunicacion());
        lr.setRespuesta(libroReclamo.getRespuesta());

        try {
            libroReclamoService.saveLR(lr);
        } catch (Exception e) {
            resp.put("mensaje", "Error, no fue posible guardar respuesta");
            return new ResponseEntity<Map<String,String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        resp.put("mensaje", "Su reclamo ha sido enviado con éxito");
        return new ResponseEntity<Map<String,String>>(resp, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/eliminar/{id}")
    public ResponseEntity<?> deleteLR(@PathVariable(value = "id") Integer idlibro){

        Map<String,String> resp = new HashMap<>();
        LibroReclamo lr = null;        

        try {
            lr = libroReclamoService.getByIdlibro(idlibro);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta");
            return new ResponseEntity<Map<String,String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(lr == null){
            resp.put("mensaje", "No se encontraron coincidencias");
            return new ResponseEntity<Map<String,String>>(resp, HttpStatus.NOT_FOUND);
        }

        try {
            lr = libroReclamoService.getByIdlibro(idlibro);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta");
            return new ResponseEntity<Map<String,String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        resp.put("mensaje", "Sin datos que mostrar");
        return new ResponseEntity<Map<String,String>>(resp, HttpStatus.NOT_FOUND);
    }

    //METODOS PARA SUJERENCIA
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/suje-list/{page}")
    public ResponseEntity<?> listaSU(@PathVariable(value = "page") int page){

        Map<String,String> resp = new HashMap<>();
        Page<Sujerencia> sus = null;

        Pageable pageable = PageRequest.of(page, 20);

        try {
            sus = libroReclamoService.getAllSujerencia(pageable);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta");
            return new ResponseEntity<Map<String,String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(sus != null && sus.getContent().size() != 0){
            return new ResponseEntity<Page<Sujerencia>>(sus, HttpStatus.OK);
        }

        resp.put("mensaje", "Sin datos que mostrar");
        return new ResponseEntity<Map<String,String>>(resp, HttpStatus.NOT_FOUND);
    }    

}
