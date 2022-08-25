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

import com.hteecommerce.hteapp.entity.Delivery;
import com.hteecommerce.hteapp.service.IDeliveryService;

@RestController
@RequestMapping("/deli/de")
public class DeliveryController {

    @Autowired
    private IDeliveryService deliveryService;

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/lista")
    public ResponseEntity<?> listDelivery() {

        Map<String, String> resp = new HashMap<>();
        List<Delivery> ds = null;

        try {
            ds = deliveryService.getAll();
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (ds != null && ds.size() != 0) {
            return new ResponseEntity<List<Delivery>>(ds, HttpStatus.OK);
        }

        resp.put("mensaje", "Sin datos que mostrar");
        return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/crear")
    public ResponseEntity<?> deCreate(@Valid @RequestBody Delivery delivery, BindingResult result) {

        Map<String, String> resp = new HashMap<>();

        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors().stream()
                    .map(err -> "El campo: " + err.getField() + " " + err.getDefaultMessage())
                    .collect(Collectors.toList());
            resp.put("mensaje", errors.toString());
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.BAD_REQUEST);
        }

        if (deliveryService.isExistsByEmpresaAndSucursal(delivery.getEmpresa(), delivery.getSucursal())) {
            resp.put("mensaje", "El nombre de la empresa ya se encuentra registrado en el sistema");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.BAD_REQUEST);
        }

        try {
            deliveryService.saveDEL(delivery);
        } catch (Exception e) {
            resp.put("mensaje", "Error, no fue posible guardar registro");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        resp.put("mensaje", "Registro guardado con éxito");
        return new ResponseEntity<Map<String, String>>(resp, HttpStatus.CREATED);

    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/obtener/{id}")
    public ResponseEntity<?> getDelivery(@PathVariable(value = "id") Integer iddelivery) {

        Map<String, String> resp = new HashMap<>();
        Delivery del = null;

        try {
            del = deliveryService.getByIddelivery(iddelivery);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (del == null) {
            resp.put("mensaje", "Sin datos que mostrar");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Delivery>(del, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/editar")
    public ResponseEntity<?> deUpdate(@RequestBody Delivery delivery) {

        Map<String, String> resp = new HashMap<>();
        Delivery del = null;

        if (deliveryService.isExistsByEmpresaAndSucursalAndIddelivery(delivery.getEmpresa(), delivery.getSucursal(), delivery.getIddelivery())) {
            resp.put("mensaje", "El nombre de la empresa ya se encuentra registrado en el sistema");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.BAD_REQUEST);
        }

        try {
            del = deliveryService.getByIddelivery(delivery.getIddelivery());
        } catch (Exception e) {
            resp.put("mensaje", "Error, no fue posible guardar registro");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (del == null) {
            resp.put("mensaje", "No se encontraron coincidencias");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }

        del.setCosto(delivery.getCosto());
        del.setDetalle(delivery.getDetalle());
        del.setEmpresa(delivery.getEmpresa());

        try {
            deliveryService.saveDEL(del);
        } catch (Exception e) {
            resp.put("mensaje", "Error, no fue posible actualizar registro");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        resp.put("mensaje", "Registro actualizado con éxito");
        return new ResponseEntity<Map<String, String>>(resp, HttpStatus.OK);

    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> deleteDelivery(@PathVariable(value = "id") Integer iddelivery) {

        Map<String, String> resp = new HashMap<>();
        Delivery del = null;

        try {
            del = deliveryService.getByIddelivery(iddelivery);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (del == null) {
            resp.put("mensaje", "No se encontró coincidencias");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }

        try {
            deliveryService.deleteDEL(del.getIddelivery());
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        resp.put("mensaje", "Registro eliminado con éxito");
        return new ResponseEntity<Map<String, String>>(resp, HttpStatus.OK);
    }

}
