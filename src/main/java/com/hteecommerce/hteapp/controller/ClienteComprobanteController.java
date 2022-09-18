package com.hteecommerce.hteapp.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.hteecommerce.hteapp.entity.Cliente;
import com.hteecommerce.hteapp.entity.ClienteComprobante;
import com.hteecommerce.hteapp.entity.ClienteOferta;
import com.hteecommerce.hteapp.file_manager.IFileService;
import com.hteecommerce.hteapp.mapper.Mapper;
import com.hteecommerce.hteapp.service.IClienteComprobanteService;
import com.hteecommerce.hteapp.service.IClienteService;
import com.hteecommerce.hteapp.util.RutaActual;

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
@RequestMapping("/cli-oferta/co")
public class ClienteComprobanteController {

    @Autowired
    private IClienteComprobanteService clienteComprobanteService;

    @Autowired
    private IFileService fileService;

    @Autowired
    private IClienteService clienteService;

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/entre/fechas/{finicio}/{ffin}")
    public ResponseEntity<?> ccListByFechas(@PathVariable(value = "finicio") String finicio,
            @PathVariable(value = "ffin") String ffin) {

        Map<String, String> resp = new HashMap<>();
        List<ClienteComprobante> ccs = null;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate f_inicio = LocalDate.parse(finicio, formatter);
        LocalDate f_fin = LocalDate.parse(ffin, formatter);

        try {
            ccs = clienteComprobanteService.getByFechas(f_inicio, f_fin);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (ccs != null && ccs.size() != 0) {
            return new ResponseEntity<List<ClienteComprobante>>(ccs, HttpStatus.OK);
        }

        resp.put("mensaje", "Sin datos que mostrar");
        return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);

    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/pores/{estado}/{page}")
    public ResponseEntity<?> ccListByEstado(@PathVariable(value = "estado") String estado,
            @PathVariable(value = "page") int page) {

        Map<String, String> resp = new HashMap<>();
        Page<ClienteComprobante> ccs = null;

        Pageable pageable = PageRequest.of(page, 50);

        try {
            ccs = clienteComprobanteService.getByEstado(estado, pageable);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (ccs != null && ccs.getContent().size() != 0) {
            return new ResponseEntity<Page<ClienteComprobante>>(ccs, HttpStatus.OK);
        }

        resp.put("mensaje", "Sin datos que mostrar");
        return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);

    }

    @PreAuthorize("hasRole('CLIENT')")
    @GetMapping("/por/cli/{idcliente}")
    public ResponseEntity<?> ccByCliente(@PathVariable(value = "idcliente") Integer idcliente) {

        Map<String, String> resp = new HashMap<>();
        Cliente cliente = null;
        List<ClienteComprobante> ccs = null;       

        try {
            cliente = clienteService.getByIdcliente(idcliente);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(cliente == null){
            resp.put("mensaje", "No fue posible validar sus datos");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }

        try {
            ccs = clienteComprobanteService.getByClientes(cliente.getClienteProveedor().getIdcp());
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (ccs != null && ccs.size() != 0) {
            List<ClienteComprobante> newList = ccs.stream()
                .map(cc -> {
                    cc.setClienteProveedor(null);
                    return cc;
                })
                .collect(Collectors.toList());                
            return new ResponseEntity<List<ClienteComprobante>>(newList, HttpStatus.OK);
        }

        resp.put("mensaje", "Sin datos que mostrar");
        return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);

    }

    @PreAuthorize("hasRole('CLIENT')")
    @PostMapping("/crear")
    public ResponseEntity<?> ccCreate(@Valid @RequestBody ClienteComprobante cc, BindingResult result) {

        Map<String, Object> resp = new HashMap<>();
        Cliente cli = null;
        String ruta = RutaActual.RUTA_CLIENTE_OFERTA;

        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors().stream()
                    .map(err -> "El campo: " + err.getField() + " " + err.getDefaultMessage())
                    .collect(Collectors.toList());

            resp.put("mensaje", errors);
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.BAD_REQUEST);
        }

        List<String> errors = cc.getClienteOfertas().stream()
                .flatMap(co -> Mapper.isValidClienteOferta(co))
                .collect(Collectors.toList());

        if (errors.size() != 0) {
            resp.put("mensaje", errors);
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.BAD_REQUEST);
        }

        try {
            cli = clienteService.getByIdcliente(cc.getClienteProveedor().getIdcp());
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(cli.getClienteProveedor() == null){
            resp.put("mensaje", "No fue posible validar sus datos");
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.NOT_FOUND);
        }

        List<ClienteOferta> cos = new ArrayList<>();
        for(ClienteOferta co : cc.getClienteOfertas()){
            String nombreImagen = null;

            try {
                nombreImagen = fileService.convertirBase64(co.getImagen(), ruta, co.getNombre());
            } catch (IOException e) {
                nombreImagen = "error";
            }

            if(nombreImagen != null && !nombreImagen.equals("error")){
                co.setImagen(nombreImagen);
                cos.add(co);
            }

        }

        if(cos.size() != cc.getClienteOfertas().size()){
            resp.put("mensaje", "No fue posible guardar una de las imagenes");
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.NOT_FOUND);
        }

        cc.setClienteOfertas(cos);
        cc.setClienteProveedor(cli.getClienteProveedor());

        try {
            clienteComprobanteService.saveCLICOM(cc);
        } catch (Exception e) {
            resp.put("mensaje", "Error.. No fue posible enviar sus productos, por favor intentelo mas tarde");
            resp.put("error", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        resp.put("mensaje", "Sus productos en venta se enviaron con éxito");
        return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/editar")
    public ResponseEntity<?> ccUpdate(@RequestBody ClienteComprobante cc) {

        Map<String, Object> resp = new HashMap<>();
        ClienteComprobante clicom = null;

        try {
            clicom = clienteComprobanteService.getByIdclientecomprobante(cc.getIdclientecomprobante());
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(clicom == null){
            resp.put("mensaje", "No se encontraron conincidencias");
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.NOT_FOUND);
        }

        clicom.setEstado(cc.getEstado());
        
        try {
            clienteComprobanteService.saveCLICOM(clicom);
        } catch (Exception e) {
            resp.put("mensaje", "Error, no fue posible actualizar estado");
            resp.put("error", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        resp.put("mensaje", "Estado actualizado con éxito");
        return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.OK);

    }

}
