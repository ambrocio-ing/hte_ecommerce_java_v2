package com.hteecommerce.hteapp.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.hteecommerce.hteapp.entity.Comprobante;
import com.hteecommerce.hteapp.entity.DetalleComprobante;
import com.hteecommerce.hteapp.entity.DetalleIngreso;
import com.hteecommerce.hteapp.mapper.Mapper;
import com.hteecommerce.hteapp.model.MComprobante;
import com.hteecommerce.hteapp.model.MResumenVenta;
import com.hteecommerce.hteapp.service.IComprobanteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comprobante/cbte")
public class ComprobanteController {    

    @Autowired
    private IComprobanteService comprobanteService;       
    
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/en/{page}")
    public ResponseEntity<?> comListEstadoEntregado(@PathVariable(value = "page") int page) {

        Map<String, String> resp = new HashMap<>();
        Page<Comprobante> comPage = null;

        Pageable pageable = PageRequest.of(page, 10);

        try {
            comPage = comprobanteService.getByEstadoEntregado(pageable);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (comPage != null && comPage.getContent().size() != 0) {
            Page<MComprobante> mpage = comPage.map(com -> Mapper.mapComprobante(com));
            return new ResponseEntity<Page<MComprobante>>(mpage, HttpStatus.OK);
        } 
        else {
            resp.put("mensaje", "Sin datos que mostrar");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }

    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/pe/{page}")
    public ResponseEntity<?> comListEstadoPedido(@PathVariable(value = "page") int page) {

        Map<String, String> resp = new HashMap<>();
        Page<Comprobante> comPage = null;

        Pageable pageable = PageRequest.of(page, 10);

        try {
            comPage = comprobanteService.getByEstadoPedido(pageable);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (comPage != null && comPage.getContent().size() != 0) {
            Page<MComprobante> mpage = comPage.map(com -> Mapper.mapComprobante(com));
            return new ResponseEntity<Page<MComprobante>>(mpage, HttpStatus.OK);
        } 
        else {
            resp.put("mensaje", "Sin datos que mostrar");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }

    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/an/{page}")
    public ResponseEntity<?> comListEstadoAnulado(@PathVariable(value = "page") int page) {

        Map<String, String> resp = new HashMap<>();
        Page<Comprobante> comPage = null;

        Pageable pageable = PageRequest.of(page, 10);

        try {
            comPage = comprobanteService.getByEstadoAnulado(pageable);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (comPage != null && comPage.getContent().size() != 0) {
            Page<MComprobante> mpage = comPage.map(com -> Mapper.mapComprobante(com));
            return new ResponseEntity<Page<MComprobante>>(mpage, HttpStatus.OK);
        } 
        else {
            resp.put("mensaje", "Sin datos que mostrar");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }

    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/fp/{fecha}")
    public ResponseEntity<?> searchByFechaPedido(@PathVariable(value = "fecha") String fecha) {

        Map<String, String> resp = new HashMap<>();
        List<Comprobante> coms = null;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate ffecha = LocalDate.parse(fecha, formatter);

        try {
            coms = comprobanteService.getByFechaPedido(ffecha);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            resp.put("error", e.getMessage().concat(" : ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (coms != null && coms.size() != 0) {
            List<MComprobante> mlista = Mapper.mapComprobantes(coms);
            return new ResponseEntity<List<MComprobante>>(mlista, HttpStatus.OK);
        } else {
            resp.put("mensaje", "Sin datos que mostrar");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }

    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/fe/{fecha}")
    public ResponseEntity<?> searchByFechaEstadoEntregado(@PathVariable(value = "fecha") String fecha) {

        Map<String, String> resp = new HashMap<>();
        List<Comprobante> coms = null;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate ffecha = LocalDate.parse(fecha, formatter);

        try {
            coms = comprobanteService.getByFechaByEstadoEntregado(ffecha);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (coms != null && coms.size() != 0) {
            List<MComprobante> mlista = Mapper.mapComprobantes(coms);
            return new ResponseEntity<List<MComprobante>>(mlista, HttpStatus.OK);
        } else {
            resp.put("mensaje", "Sin datos que mostrar");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }

    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/cli/dato/{texto}")
    public ResponseEntity<?> searchByClienteByDniOrNombre(@PathVariable(value = "texto") String texto) {

        Map<String, String> resp = new HashMap<>();       
        List<Comprobante> coms = null;

        try {
            coms = comprobanteService.getByClienteByDniOrNombre(texto);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (coms != null && coms.size() != 0) {

            List<MComprobante> mlista = Mapper.mapComprobantes(coms);
            mlista = mlista.stream().limit(10).collect(Collectors.toList());
            return new ResponseEntity<List<MComprobante>>(mlista, HttpStatus.OK);
            
        }
        else{
            resp.put("mensaje", "No se encontraron coincidencias para el cliente seleccionado");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }    
        
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/buscar/{numero}")
    public ResponseEntity<?> searchByNumero(@PathVariable(value = "numero") String numero) {

        Map<String, String> resp = new HashMap<>();
        Comprobante com = null;
        
        try {
            com = comprobanteService.getByNumero(numero);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (com != null) {
            MComprobante mcom = Mapper.mapComprobante(com);
            return new ResponseEntity<MComprobante>(mcom, HttpStatus.OK);
        } else {
            resp.put("mensaje", "Sin datos que mostrar");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }

    }    

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/obtener/{id}")
    public ResponseEntity<?> getComprobante(@PathVariable(value = "id") Integer idcom) {

        Map<String, String> resp = new HashMap<>();
        Comprobante com = null;

        try {
            com = comprobanteService.getByIdcomprobante(idcom);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (com != null) {
            MComprobante mcom = Mapper.mapComprobante(com);
            return new ResponseEntity<MComprobante>(mcom, HttpStatus.OK);
        } else {
            resp.put("mensaje", "Sin datos que mostrar");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }

    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/editar")
    public ResponseEntity<?> comUpdate(@RequestBody Comprobante comprobante) {

        Map<String, String> resp = new HashMap<>();
        Comprobante com = null;

        try {
            com = comprobanteService.getByIdcomprobante(comprobante.getIdcomprobante());
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (com == null) {
            resp.put("mensaje", "No fue posible actualizar comprobante");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }                 

        com.setIdtransaccion(comprobante.getIdtransaccion());
        com.setTipoComprobante(comprobante.getTipoComprobante());
        com.setEstado(comprobante.getEstado());       
          
        try {
            comprobanteService.saveCOM(com);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error al actualizar datos");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        resp.put("mensaje", "Orden actualizado con éxito");        
        return new ResponseEntity<Map<String, String>>(resp, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> deleteComprobante(@PathVariable(value = "id") Integer idcom) {

        Map<String, String> resp = new HashMap<>();    
        Comprobante com = null;    
        List<DetalleIngreso> dis = new ArrayList<>();

        try {
            com = comprobanteService.getByIdcomprobante(idcom);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }    

        if(com == null){
            resp.put("mensaje", "No fue posible deshaser cambios");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }

        for(DetalleComprobante dc : com.getDetalleComprobantes()){
            Integer cantidad = dc.getDetalleIngreso().getStockActual() + dc.getCantidad();
            dc.getDetalleIngreso().setStockActual(cantidad);
            dis.add(dc.getDetalleIngreso());
        }

        try {
            comprobanteService.deleteCOM(idcom, dis);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error al eliminar comporobante");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        resp.put("mensaje", "Comprobante eliminado");
        return new ResponseEntity<Map<String, String>>(resp, HttpStatus.OK);    

    }

    //metodo incompleto
    @PostMapping("/dc/editar")
    public ResponseEntity<?> comUpdateEstado(@RequestBody DetalleComprobante decom) {

        Map<String, String> resp = new HashMap<>();
        Comprobante com = null;
        DetalleComprobante dc = null;

        try {
            dc = comprobanteService.getDCByIddetallecomprobante(decom.getIddetallecomprobante());
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        try {
            com = comprobanteService.getByIdcomprobante(decom.getComprobanteId());
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (com == null || dc == null) {
            resp.put("mensaje", "No fue posible actualizar comprobante");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }     
      
        
        try {
            comprobanteService.saveCOM(com);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error al guardar datos");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        resp.put("mensaje", "Orden creado con éxito");
        resp.put("id", com.getIdcomprobante().toString());
        return new ResponseEntity<Map<String, String>>(resp, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/dc/eliminar/{iddc}/{idc}")
    public ResponseEntity<?> deleteDC(@PathVariable(value = "iddc") Integer iddc,
        @PathVariable(value = "idc") Integer idc) {

        Map<String, String> resp = new HashMap<>();        
        DetalleComprobante decom = null;   
        Comprobante com = null;          
        
        try {
            com = comprobanteService.getByIdcomprobante(idc);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        try {
            decom = comprobanteService.getDCByIddetallecomprobante(iddc);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (decom == null || com == null) {
            resp.put("mensaje", "No se encontraron coincidencias");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }

        LocalDateTime fechahoy = LocalDateTime.now();
        LocalDateTime fechaCompra = com.getFechaEntrega().plusHours(48);

        if(com.getEstado().equals("Vendido") && fechahoy.isBefore(fechaCompra) == false){
            
            resp.put("mensaje", "No fue posible eliminar detalle de comprobante, ya pasaron mas de 48 horas");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);            
        }

        decom.getDetalleIngreso().setStockActual(decom.replenishStockActual());     
     
        try {         
            comprobanteService.deleteDC(decom.getIddetallecomprobante(), decom.getDetalleIngreso());
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error al eliminar detalle de comprobante");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        resp.put("mensaje", "Comprobante anulado con éxito");
        return new ResponseEntity<Map<String, String>>(resp, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/anular")
    public ResponseEntity<?> comCancel(@RequestBody Comprobante comprobante) {

        Map<String, String> resp = new HashMap<>();        
        Comprobante com = null;          
        
        try {
            com = comprobanteService.getByIdcomprobante(comprobante.getIdcomprobante());
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (com == null) {
            resp.put("mensaje", "No fue posible actualizar comprobante");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }

        /* LocalDateTime fechahoy = LocalDateTime.now();
        LocalDateTime fechaCompra = LocalDateTime.parse(com.getFechaEntrega().toString()+"T"+com.getHora()).plusHours(48);

        if(fechahoy.isBefore(fechaCompra) == false || com.getEstado().equals("Anulado")){
            resp.put("mensaje", "No fue posible anular comprobante, ya pasaron mas de 48 horas o el comprobante ya fue anulado con anterioridad");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        } */

        for(DetalleComprobante dc : com.getDetalleComprobantes()){          
            
            dc.getDetalleIngreso().setStockActual(dc.replenishStockActual());
        }

        com.setEstado("Anulado");

        try {         
            comprobanteService.saveCOM(com);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        resp.put("mensaje", "Venta anulado con éxito");
        return new ResponseEntity<Map<String, String>>(resp, HttpStatus.OK);
    }

    //Resumen de vanta aqui
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/resumen/venta")
    public ResponseEntity<?> listResumen(){

        Map<String, String> resp = new HashMap<>();        
        List<Comprobante> comprobantes = null;   
        List<DetalleComprobante> dcs = null;
        List<MResumenVenta> resumens = null;
        
        try {
            comprobantes = comprobanteService.getByEstado("Entrega pendiente");
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de servidor");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(comprobantes == null){
            resp.put("mensaje", "No se encontró entregas pendientes");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }

        dcs = Mapper.unirDetalleComprobantes(comprobantes);
        resumens = Mapper.agruparDetalleComprobantes(comprobantes, dcs);

        if(resumens != null && resumens.size() != 0){
            return new ResponseEntity< List<MResumenVenta>>(resumens, HttpStatus.OK);
        }

        resp.put("mensaje", "No se encontró entregas pendientes");
        return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/resumen/venta/by/{fecha}")
    public ResponseEntity<?> listResumenPorFecha(@PathVariable(value = "fecha") LocalDate fecha){

        Map<String, String> resp = new HashMap<>();        
        List<Comprobante> comprobantes = null;   
        List<DetalleComprobante> dcs = null;
        List<MResumenVenta> resumens = null;
        
        try {
            comprobantes = comprobanteService.getByFechaPedido(fecha);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de servidor");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(comprobantes == null){
            resp.put("mensaje", "No se encontró entregas pendientes");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }

        dcs = Mapper.unirDetalleComprobantes(comprobantes);
        resumens = Mapper.agruparDetalleComprobantes(comprobantes, dcs);

        if(resumens != null && resumens.size() != 0){
            return new ResponseEntity< List<MResumenVenta>>(resumens, HttpStatus.OK);
        }

        resp.put("mensaje", "No se encontró entregas pendientes");
        return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
    }

}
