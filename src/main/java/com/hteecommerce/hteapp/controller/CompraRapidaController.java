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

import com.hteecommerce.hteapp.entity.CompraRapida;
import com.hteecommerce.hteapp.entity.DetalleIngreso;
import com.hteecommerce.hteapp.model.MCompraRapida;
import com.hteecommerce.hteapp.service.ICompraRapidaService;
import com.hteecommerce.hteapp.service.IIngresoService;

@RestController
@RequestMapping("/compra-rapida/cr")
public class CompraRapidaController {

    @Autowired
    private ICompraRapidaService compraRapidaService;

    @Autowired
    private IIngresoService ingresoService;

    @PreAuthorize("hasRole('CLIENT')")
    @GetMapping("/lista/{id}")
    public ResponseEntity<?> listCompraRapida(@PathVariable(value = "id") Integer idcliente) {

        Map<String, String> resp = new HashMap<>();
        List<CompraRapida> crs = null;

        try {
            crs = compraRapidaService.getByIdcliente(idcliente);
        } catch (DataAccessException e) {
            resp.put(("mensaje"), "Error de servicio");
            return new ResponseEntity<Map<String, String>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (crs != null && crs.size() != 0) {  
            for(CompraRapida cr : crs)  {
                if(cr.getDetalleIngreso().getStockActual() == 0){                    
                    cr.setCondicion("Agotado");                                 
                }         
            }       

            List<MCompraRapida> mlista = crs.stream()
                                    .map(cor -> new MCompraRapida(cor))
                                    .collect(Collectors.toList());

            return new ResponseEntity<List<MCompraRapida>>(mlista, HttpStatus.OK);
        }

        resp.put(("mensaje"), "Sin datos que mostrar");
        return new ResponseEntity<Map<String, String>>(HttpStatus.NOT_FOUND);
    }

    @PreAuthorize("hasRole('CLIENT')")
    @PostMapping("/crear")
    public ResponseEntity<?> crCreate(@Valid @RequestBody CompraRapida compraRapida, BindingResult result) {

        Map<String, String> resp = new HashMap<>();
        CompraRapida comra = null;
        DetalleIngreso dIngreso = null;
        
        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors().stream()
                    .map(err -> "El campo: " + err.getField() + " " + err.getDefaultMessage())
                    .collect(Collectors.toList());

            resp.put("mensaje", errors.toString());
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.BAD_REQUEST);
        }

        try {
            comra = compraRapidaService.getByIddetalleingreso(compraRapida.getIdcliente(), compraRapida.getDetalleIngreso().getIddetalleingreso());
        } catch (DataAccessException e) {
            resp.put(("mensaje"), "Error de consulta");
            return new ResponseEntity<Map<String, String>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (comra != null) {
            resp.put("mensaje", "El producto ya existe en su lista de compras");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }

        try {
            dIngreso = ingresoService.getByIddetalleingreso(compraRapida.getDetalleIngreso().getIddetalleingreso());
        } catch (DataAccessException e) {
            resp.put(("mensaje"), "Error de consulta");
            return new ResponseEntity<Map<String, String>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (dIngreso == null) {
            resp.put("mensaje", "No fue posible contejar datos del producto");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }

        compraRapida.setCondicion("Disponible");
        compraRapida.setDetalleIngreso(dIngreso);

        try {
            compraRapidaService.saveCR(compraRapida);
        } catch (Exception e) {
            resp.put(("mensaje"), "Error de consulta");
            resp.put("error", e.getMessage());
            return new ResponseEntity<Map<String, String>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        resp.put("mensaje", "El producto fue agregado a su lista de compras");
        return new ResponseEntity<Map<String, String>>(resp, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('CLIENT')")
    @GetMapping("/obtener/{id}")
    public ResponseEntity<?> crObtener(@PathVariable(value = "id") Integer idcr) {

        Map<String, String> resp = new HashMap<>();
        CompraRapida cr = null;
        
        try {
            cr = compraRapidaService.getByIdcompra(idcr);
        } catch (DataAccessException e) {
            resp.put(("mensaje"), "No fue posible encontrar coincidencias");
            return new ResponseEntity<Map<String, String>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (cr != null) {

            MCompraRapida mcompra = new MCompraRapida(cr);
            return new ResponseEntity<MCompraRapida>(mcompra, HttpStatus.OK);
        }

        resp.put(("mensaje"), "Sin datos que mostrar");
        return new ResponseEntity<Map<String, String>>(HttpStatus.NOT_FOUND);
    }   

    @PreAuthorize("hasRole('CLIENT')")
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> crDelete(@PathVariable(value = "id") Integer idcompra) {

        Map<String, String> resp = new HashMap<>();
        CompraRapida comra = null;

        try {
            comra = compraRapidaService.getByIdcompra(idcompra);
        } catch (DataAccessException e) {
            resp.put(("mensaje"), "Error de consulta");
            return new ResponseEntity<Map<String, String>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (comra == null) {
            resp.put("mensaje", "No se encontró conincidencias");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }

        try {
            compraRapidaService.deleteCR(comra.getIdcompra());
        } catch (Exception e) {
            resp.put(("mensaje"), "Error, no fue posible eliminar producto de sus lista");
            resp.put("error", e.getMessage());
            return new ResponseEntity<Map<String, String>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        resp.put("mensaje", "Producto quitado de la lista de compras");
        return new ResponseEntity<Map<String, String>>(resp, HttpStatus.OK);
    }

}
