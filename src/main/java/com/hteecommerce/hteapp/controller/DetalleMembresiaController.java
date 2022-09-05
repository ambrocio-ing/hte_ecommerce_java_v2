package com.hteecommerce.hteapp.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.hteecommerce.hteapp.entity.Cliente;
import com.hteecommerce.hteapp.entity.DetalleMembresia;
import com.hteecommerce.hteapp.entity.Membresia;
import com.hteecommerce.hteapp.file_manager.IFileService;
import com.hteecommerce.hteapp.model.MDetalleMembresia;
import com.hteecommerce.hteapp.service.IClienteService;
import com.hteecommerce.hteapp.service.IDetalleMembresiaService;
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
@RequestMapping("/detalle-mem/dm")
public class DetalleMembresiaController {

    @Autowired
    private IDetalleMembresiaService detalleMembresiaService;

    @Autowired
    private IClienteService clienteService;

    @Autowired
    private IMembresiaService membresiaService;

    @Autowired
    private IFileService fileService;

    @PreAuthorize("hasRole('CLIENT')")
    @GetMapping("/porcli/{idcliente}")
    public ResponseEntity<?> getByCliente(@PathVariable(value = "idcliente") Integer idcliente){

        Map<String,Object> resp = new HashMap<>();
        DetalleMembresia dm = null;
        LocalDate fecha = LocalDate.now();
        int puntos = 0;
        Cliente cliente = null;
        
        try {
            dm = detalleMembresiaService.getByIdcliente(idcliente, fecha);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity<Map<String,Object>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(dm != null){
            puntos = dm.getCliente().getPuntos();
            dm.setCliente(null);

            resp.put("dm", dm);
            resp.put("puntos", puntos);
            return new ResponseEntity<Map<String,Object>>(resp, HttpStatus.OK);
        }

        try {
            cliente = clienteService.getByIdcliente(idcliente);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity<Map<String,Object>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(cliente != null){
            puntos = cliente.getPuntos();         
            resp.put("dm", new DetalleMembresia());
            resp.put("puntos", puntos);
            return new ResponseEntity<Map<String,Object>>(resp, HttpStatus.OK);
        }

        resp.put("mensaje", "Sin datos que mostrar");
        return new ResponseEntity<Map<String,Object>>(resp, HttpStatus.NOT_FOUND);        
    }

    @PreAuthorize("hasRole('CLIENT')")
    @GetMapping("/valid/{idcliente}")
    public ResponseEntity<?> validarCompra(@PathVariable(value = "idcliente") Integer idcliente){

        Map<String,String> resp = new HashMap<>();
        DetalleMembresia dm = null;
        LocalDate fecha = LocalDate.now();       
        
        try {
            dm = detalleMembresiaService.getByIdcliente(idcliente, fecha);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity<Map<String,String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(dm != null){           

            resp.put("valid", "true");            
            return new ResponseEntity<Map<String,String>>(resp, HttpStatus.OK);
        }        

        resp.put("valid", "false");            
        return new ResponseEntity<Map<String,String>>(resp, HttpStatus.OK);       
    }

    @PreAuthorize("hasRole('CLIENT')")
    @PostMapping("/crear")
    public ResponseEntity<?> createDetalleMembresia(@Valid @RequestBody DetalleMembresia demem, 
        BindingResult result){

        Map<String,Object> resp = new HashMap<>();
        DetalleMembresia dm = null;
        Cliente cliente = null;
        Membresia membresia = null;
        
        if(result.hasErrors()){
            List<String> errors = result.getFieldErrors().stream()
                    .map(err -> "El campo: " + err.getField() + " " + err.getDefaultMessage())
                    .collect(Collectors.toList());

            resp.put("mensaje", errors);
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.BAD_REQUEST);
        }

        try {
            cliente = clienteService.getByIdcliente(demem.getCliente().getIdcliente());
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base detos, intentelo mas tarde");
            return new ResponseEntity<Map<String,Object>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        try {
            membresia = membresiaService.getByIdmembresia(demem.getMembresia().getIdmembresia());
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base detos, intentelo mas tarde");
            return new ResponseEntity<Map<String,Object>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(cliente == null || membresia == null){
            resp.put("mensaje", "Error de consulta a la base detos, intentelo mas tarde");
            return new ResponseEntity<Map<String,Object>>(resp, HttpStatus.NOT_FOUND);
        }

        LocalDate fecha_inicio = LocalDate.now();
        LocalDate fecha_fin = fecha_inicio.plusMonths(membresia.getDuracion());
        
        demem.setFechaInicio(fecha_inicio);
        demem.setFechaFin(fecha_fin);
        demem.setCliente(cliente);
        demem.setMembresia(membresia);

        try {
            dm = detalleMembresiaService.saveDM(demem);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error al comprar membresia, intentelo mas tarde");
            return new ResponseEntity<Map<String,Object>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        resp.put("mensaje", "Membresia adquirido con éxito");
        resp.put("iddm", dm.getIddetallemembresia());
        return new ResponseEntity<Map<String,Object>>(resp, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('CLIENT')")
    @PostMapping("/img")
    public ResponseEntity<?> saveImg(@RequestParam(name = "iddm") Integer iddm,
         @RequestParam(name = "imagen") MultipartFile file){

        Map<String, String> resp = new HashMap<>();  
        DetalleMembresia dm = null;
        String ruta = RutaActual.RUTA_DMEMBRESIA;

        if(file.isEmpty()){
            resp.put("mensaje", "La imagen no es valida");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.BAD_REQUEST);
        }

        try {
            dm = detalleMembresiaService.getByIddetallemembresia(iddm);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de servidor");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (dm == null) {
            resp.put("mensaje", "Error de servidor");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.BAD_REQUEST);
        }

        String nombreImagen = null;

        try {
            nombreImagen = fileService.copiar(ruta, file);
        } catch (IOException e) {
            resp.put("mensaje", "Error de servidor");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.BAD_REQUEST);
        }

        if(nombreImagen == null){
            resp.put("mensaje", "Error de servidor");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.BAD_REQUEST);
        }        

        try {
            dm.setImagen(nombreImagen);
            detalleMembresiaService.saveDM(dm);
        } catch (Exception e) {
            resp.put("mensaje", "Error al guardar datos");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        resp.put("mensaje", "Comprobante de pago guardado con éxito");  
        resp.put("iddm", dm.getIddetallemembresia().toString());       
        return new ResponseEntity<Map<String, String>>(resp, HttpStatus.OK);        
    }

    @PreAuthorize("hasRole('CLIENT')")
    @GetMapping("/obtener/{iddm}")
    public ResponseEntity<?> getDetalleMembresia(@PathVariable(value = "iddm") Integer iddm){

        Map<String,String> resp = new HashMap<>();
        DetalleMembresia demem = null;
        
        try {
            demem = detalleMembresiaService.getByIddetallemembresia(iddm);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity<Map<String,String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(demem == null){
            resp.put("mensaje", "Sin datos que mostrar, no se encontraron coincidencias");
            return new ResponseEntity<Map<String,String>>(resp, HttpStatus.NOT_FOUND);
        }
        
        MDetalleMembresia mdm = new MDetalleMembresia(demem);
        return new ResponseEntity<MDetalleMembresia>(mdm, HttpStatus.OK);

    }

    @PreAuthorize("hasRole('CLIENT')")
    @PostMapping("/editar")
    public ResponseEntity<?> updateDetalleMembresia(@RequestBody DetalleMembresia demem){

        Map<String,Object> resp = new HashMap<>();             
        DetalleMembresia dm = null;        

        try {
            dm = detalleMembresiaService.getByIddetallemembresia(demem.getIddetallemembresia());
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base detos, intentelo mas tarde");
            return new ResponseEntity<Map<String,Object>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(dm == null){
            resp.put("mensaje", "No fue posible guardar el código de transacción");
            return new ResponseEntity<Map<String,Object>>(resp, HttpStatus.NOT_FOUND);
        }
        
        dm.setIdtransaccion(demem.getIdtransaccion());
        dm.setEstado(demem.getEstado());
        dm.getDetallePago().setEstadoPago(demem.getDetallePago().getEstadoPago());

        try {
            detalleMembresiaService.saveDM(dm);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error al comprar membresia, intentelo mas tarde");
            return new ResponseEntity<Map<String,Object>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        resp.put("mensaje", "Membresia actualizado con éxito");
        return new ResponseEntity<Map<String,Object>>(resp, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('CLIENT')")
    @DeleteMapping("/eliminar/{iddm}")
    public ResponseEntity<?> deleteDetalleMembresia(@PathVariable(value = "iddm") Integer iddm){

        Map<String,String> resp = new HashMap<>();
        DetalleMembresia demem = null;
        
        try {
            demem = detalleMembresiaService.getByIddetallemembresia(iddm);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity<Map<String,String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(demem == null){
            resp.put("mensaje", "Error al cotejar valores");
            return new ResponseEntity<Map<String,String>>(resp, HttpStatus.NOT_FOUND);
        }

        try {
            detalleMembresiaService.deleteDM(demem.getIddetallemembresia());
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error al deshacer cambios");
            return new ResponseEntity<Map<String,String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        String ruta = RutaActual.RUTA_DMEMBRESIA;
        fileService.eliminar(ruta, demem.getImagen());
        
        resp.put("mensaje", "Compra eliminado con éxito");
        return new ResponseEntity<Map<String,String>>(resp, HttpStatus.OK);
    }
    
}
