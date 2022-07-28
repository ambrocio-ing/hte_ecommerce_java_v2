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

import com.hteecommerce.hteapp.entity.ProveedorComprobante;
import com.hteecommerce.hteapp.entity.ProveedorOferta;
import com.hteecommerce.hteapp.file_manager.IFileService;
import com.hteecommerce.hteapp.mapper.Mapper;
import com.hteecommerce.hteapp.model.MProveedorComprobante;
import com.hteecommerce.hteapp.service.IProveedorComprobanteService;
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
@RequestMapping("/oferta-pro")
public class ProveedorComprobanteController {

    @Autowired
    private IProveedorComprobanteService proveedorComprobanteService;

    @Autowired
    private IFileService fileService;

    @PreAuthorize("hasRole('USER') or hasRole('SUPPLIER')")
    @GetMapping("/buscar/{finicio}/{ffin}")
    public ResponseEntity<?> searchByFechas(@PathVariable(value = "finicio") String finicio,
        @PathVariable(value = "ffin") String ffin) {

        Map<String, String> resp = new HashMap<>();
        List<ProveedorComprobante> pcs = null;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate f_inicio = LocalDate.parse(finicio, formatter);
        LocalDate f_fin = LocalDate.parse(ffin, formatter);
        
        try {
            pcs = proveedorComprobanteService.getByFechas(f_inicio, f_fin);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (pcs != null && pcs.size() != 0) {
            List<MProveedorComprobante> mlist = pcs.stream()
                .map(pc -> new MProveedorComprobante(pc))
                .collect(Collectors.toList()); 

            return new ResponseEntity<List<MProveedorComprobante>>(mlist, HttpStatus.OK);
        } else {
            resp.put("mensaje", "Sin datos que mostrar");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('USER') or hasRole('SUPPLIER')")
    @GetMapping("/all/{page}")
    public ResponseEntity<?> pcListAll(@PathVariable(value = "page") int page) {

        Map<String, String> resp = new HashMap<>();
        Page<ProveedorComprobante> pcs = null;

        Pageable pageable = PageRequest.of(page, 5);
        try {
            pcs = proveedorComprobanteService.getAll(pageable);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (pcs != null && pcs.getContent().size() != 0) {
            Page<MProveedorComprobante> mpage = pcs.map(pc -> new MProveedorComprobante(pc));
            return new ResponseEntity<Page<MProveedorComprobante>>(mpage, HttpStatus.OK);
        } else {
            resp.put("mensaje", "Sin datos que mostrar");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/pendiente/{page}")
    public ResponseEntity<?> pcListOfertado(@PathVariable(value = "page") int page) {

        Map<String, String> resp = new HashMap<>();
        Page<ProveedorComprobante> pcs = null;

        Pageable pageable = PageRequest.of(page, 10);
        try {
            pcs = proveedorComprobanteService.getByEstadoOfertado(pageable);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (pcs != null && pcs.getContent().size() != 0) {
            Page<MProveedorComprobante> mpage = pcs.map(pc -> new MProveedorComprobante(pc));
            return new ResponseEntity<Page<MProveedorComprobante>>(mpage, HttpStatus.OK);
        } else {
            resp.put("mensaje", "Sin datos que mostrar");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/rechazado/{page}")
    public ResponseEntity<?> pcListRechazado(@PathVariable(value = "page") int page) {

        Map<String, String> resp = new HashMap<>();
        Page<ProveedorComprobante> pcs = null;

        Pageable pageable = PageRequest.of(page, 10);
        try {
            pcs = proveedorComprobanteService.getByEstadoRechazado(pageable);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (pcs != null && pcs.getContent().size() != 0) {
            Page<MProveedorComprobante> mpage = pcs.map(pc -> new MProveedorComprobante(pc));
            return new ResponseEntity<Page<MProveedorComprobante>>(mpage, HttpStatus.OK);
        } else {
            resp.put("mensaje", "Sin datos que mostrar");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/aceptado/{page}")
    public ResponseEntity<?> pcListAceptado(@PathVariable(value = "page") int page) {

        Map<String, String> resp = new HashMap<>();
        Page<ProveedorComprobante> pcs = null;

        Pageable pageable = PageRequest.of(page, 10);
        try {
            pcs = proveedorComprobanteService.getByEstadoAceptado(pageable);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (pcs != null && pcs.getContent().size() != 0) {
            Page<MProveedorComprobante> mpage = pcs.map(pc -> new MProveedorComprobante(pc));
            return new ResponseEntity<Page<MProveedorComprobante>>(mpage, HttpStatus.OK);
        } else {
            resp.put("mensaje", "Sin datos que mostrar");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('SUPPLIER')")
    @PostMapping("/crear")
    public ResponseEntity<?> pcCreate(@Valid @RequestBody ProveedorComprobante pc, BindingResult result) {

        Map<String, Object> resp = new HashMap<>();
        String ruta = RutaActual.RUTA_PROVEEDOR_OFERTA;

        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors().stream()
                    .map(err -> "El campo: " + err.getField() + " " + err.getDefaultMessage())
                    .collect(Collectors.toList());

            resp.put("mensaje", errors);
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.BAD_REQUEST);
        }

        List<String> errors = pc.getProveedorOfertas().stream()
                .flatMap(po -> Mapper.isValidProveedorOferta(po))
                .collect(Collectors.toList());

        if (errors.size() != 0) {

            resp.put("mensaje", errors);
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.BAD_REQUEST);
        }       

        List<ProveedorOferta> pos = new ArrayList<>();
        for(ProveedorOferta po : pc.getProveedorOfertas()){
            String nombreImagen = null;
            try {
                nombreImagen = fileService.convertirBase64(po.getImagen(), ruta, po.getNombre());
            } catch (IOException e) {
                nombreImagen = "error";
            }

            if(!nombreImagen.equals("error") && nombreImagen != null){
                po.setImagen(nombreImagen);
                pos.add(po);
            }
        }

        if(pos.size() != pc.getProveedorOfertas().size()){
            resp.put("mensaje","No fue posible enviar su oferta, por favor intentelo mas tarde");
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.NOT_FOUND);
        }

        pc.setProveedorOfertas(pos);

        try {
            proveedorComprobanteService.savePC(pc);
        } catch (Exception e) {
            pos.forEach(po -> fileService.eliminar(ruta, po.getImagen()));
            resp.put("mensaje", "Error al enviar oferta, por favor intentelo mas tarde");
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        resp.put("mensaje","Sus productos en venta se enviaron con éxito");
        return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.CREATED);
    }    

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/editar")
    public ResponseEntity<?> pcUpdate(@RequestBody ProveedorComprobante pc) {

        Map<String, Object> resp = new HashMap<>();
        ProveedorComprobante procom = null;

        try {
            procom = proveedorComprobanteService.getByidproveedorcomprobante(pc.getIdproveedorcomprobante());
        } catch (Exception e) {            
            resp.put("mensaje", "Error, no se encontraron conincidencias");
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(procom == null){
            resp.put("mensaje", "No se encontraron conincidencias");
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.NOT_FOUND);
        }

        procom.setEstado(pc.getEstado());

        try {
            proveedorComprobanteService.savePC(pc);
        } catch (Exception e) {            
            resp.put("mensaje", "Error, no fue posible actualizar estado");
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    
        resp.put("mensaje", "Estado actualizado con éxito");
        return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.OK);
    }

}
