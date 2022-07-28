package com.hteecommerce.hteapp.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.hteecommerce.hteapp.entity.Cliente;
import com.hteecommerce.hteapp.entity.Proveedor;
import com.hteecommerce.hteapp.entity.Reclamo;
import com.hteecommerce.hteapp.model.MReclamo;
import com.hteecommerce.hteapp.service.IClienteService;
import com.hteecommerce.hteapp.service.IProveedorService;
import com.hteecommerce.hteapp.service.IReclamoService;

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

@RestController
@RequestMapping("/reclamo/re")
public class ReclamoController {

    @Autowired
    private IReclamoService reclamoService;

    @Autowired
    private IClienteService clienteService;

    @Autowired
    private IProveedorService proveedorService;

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/cli/{idcliente}")
    public ResponseEntity<?> getByCliente(@PathVariable(value = "idcliente") Integer idcliente){

        Map<String,String> resp = new HashMap<>();
        List<Reclamo> lista = null;
        
        try {
            lista = reclamoService.getByCliente(idcliente);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity<Map<String,String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(lista != null && lista.size() != 0){
            List<MReclamo> mList = lista.stream()
                .map(re -> new MReclamo(re))
                .collect(Collectors.toList());
            return new ResponseEntity<List<MReclamo>>(mList, HttpStatus.OK);
        }
        else{
            resp.put("mensaje", "Sin datos que mostrar");
            return new ResponseEntity<Map<String,String>>(resp, HttpStatus.NOT_FOUND);
        }

    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/pro/{idproveedor}")
    public ResponseEntity<?> getByProveedor(@PathVariable(value = "idproveedor") Integer idproveedor){

        Map<String,String> resp = new HashMap<>();
        List<Reclamo> lista = null;
        
        try {
            lista = reclamoService.getByProveedor(idproveedor);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity<Map<String,String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(lista != null && lista.size() != 0){
            List<MReclamo> mList = lista.stream()
                .map(re -> new MReclamo(re))
                .collect(Collectors.toList());
            return new ResponseEntity<List<MReclamo>>(mList, HttpStatus.OK);
        }
        else{
            resp.put("mensaje", "Sin datos que mostrar");
            return new ResponseEntity<Map<String,String>>(resp, HttpStatus.NOT_FOUND);
        }

    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/fecha/{fecha}")
    public ResponseEntity<?> getByFecha(@PathVariable(value = "fecha") String fecha){

        Map<String,String> resp = new HashMap<>();
        List<Reclamo> lista = null;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate _fecha = LocalDate.parse(fecha, formatter);
        
        try {
            lista = reclamoService.getByFecha(_fecha);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity<Map<String,String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(lista != null && lista.size() != 0){
            List<MReclamo> mList = lista.stream()
                .map(re -> new MReclamo(re))
                .collect(Collectors.toList());
            return new ResponseEntity<List<MReclamo>>(mList, HttpStatus.OK);
        }
        else{
            resp.put("mensaje", "Sin datos que mostrar");
            return new ResponseEntity<Map<String,String>>(resp, HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/all/{page}")
    public ResponseEntity<?> getAll(@PathVariable(value = "page") int page){

        Map<String,String> resp = new HashMap<>();
        Page<Reclamo> lista = null;

        Pageable pageable = PageRequest.of(page, 10);
        
        try {
            lista = reclamoService.getAll(pageable);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity<Map<String,String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(lista != null && lista.getContent().size() != 0){
            Page<MReclamo> mList = lista.map(re -> new MReclamo(re));

            return new ResponseEntity<Page<MReclamo>>(mList, HttpStatus.OK);
        }
        else{
            resp.put("mensaje", "Sin datos que mostrar");
            return new ResponseEntity<Map<String,String>>(resp, HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('SUPPLIER') or hasRole('ROLE_CLIENT')")
    @PostMapping("/crear")
    public ResponseEntity<?> createReclamo(@Valid @RequestBody Reclamo reclamo, BindingResult result){

        Map<String,Object> resp = new HashMap<>();
        Cliente cliente = null;
        Proveedor proveedor = null;
        
        if(result.hasErrors()){
            List<String> errors = result.getFieldErrors().stream()
                    .map(err -> "El campo: " + err.getField() + " " + err.getDefaultMessage())
                    .collect(Collectors.toList());

            resp.put("mensaje", errors);
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.BAD_REQUEST);
        }

        if(reclamo.getCliente().getIdcliente() != null){
            try {
                cliente = clienteService.getByIdcliente(reclamo.getCliente().getIdcliente());
            } catch (DataAccessException e) {
                resp.put("mensaje", "Error de consulta a la base de datos, intentelo mas tarde");
                return new ResponseEntity<Map<String,Object>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
            }

            if(cliente == null){
                resp.put("mensaje", "Error de consulta a la base de datos, intentelo mas tarde");
                return new ResponseEntity<Map<String,Object>>(resp, HttpStatus.NOT_FOUND);
            }

        }
        else if(reclamo.getProveedor().getIdproveedor() != null){
            try {
                proveedor = proveedorService.getByIdproveedor(reclamo.getProveedor().getIdproveedor());
            } catch (DataAccessException e) {
                resp.put("mensaje", "Error de consulta a la base de datos, intentelo mas tarde");
                return new ResponseEntity<Map<String,Object>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
            }

            if(proveedor == null){
                resp.put("mensaje", "Error de consulta a la base de datos, intentelo mas tarde");
                return new ResponseEntity<Map<String,Object>>(resp, HttpStatus.NOT_FOUND);
            }
        }

        reclamo.setCliente(cliente);
        reclamo.setProveedor(proveedor);
        
        try {
            reclamoService.saveR(reclamo);
        } catch (Exception e) {
            resp.put("mensaje", "Error al crear reclamo, intentelo mas tarde");
            return new ResponseEntity<Map<String,Object>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        resp.put("mensaje", "Reclamo enviado con éxito");
        return new ResponseEntity<Map<String,Object>>(resp, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/obtener/{idreclamo}")
    public ResponseEntity<?> getReclamo(@PathVariable(value = "idreclamo") Integer idreclamo){

        Map<String,String> resp = new HashMap<>();
        Reclamo reclamo = null;        
        
        try {
            reclamo = reclamoService.getByIdreclamo(idreclamo);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity<Map<String,String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(reclamo == null){
            resp.put("mensaje", "Sin datos que mostrar, no se encontraron coincidencias");
            return new ResponseEntity<Map<String,String>>(resp, HttpStatus.NOT_FOUND);
        }

        MReclamo mre = new MReclamo(reclamo);
        return new ResponseEntity<MReclamo>(mre, HttpStatus.OK);
        
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/eliminar/{idreclamo}")
    public ResponseEntity<?> getByFecha(@PathVariable(value = "idreclamo") Integer idreclamo){

        Map<String,String> resp = new HashMap<>();
        Reclamo reclamo = null;        
        
        try {
            reclamo = reclamoService.getByIdreclamo(idreclamo);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity<Map<String,String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(reclamo == null){
            resp.put("mensaje", "Sin datos que mostrar, no se encontraron coincidencias");
            return new ResponseEntity<Map<String,String>>(resp, HttpStatus.NOT_FOUND);
        }

        try {
            reclamoService.deleteR(reclamo.getIdreclamo());
        } catch (Exception e) {
            resp.put("mensaje", "Error, no fue posible eliminar registro");
            return new ResponseEntity<Map<String,String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        resp.put("mensaje", "Reclamo eliminado con éxito");
        return new ResponseEntity<Map<String,String>>(resp, HttpStatus.OK);
        
    }
    
}
