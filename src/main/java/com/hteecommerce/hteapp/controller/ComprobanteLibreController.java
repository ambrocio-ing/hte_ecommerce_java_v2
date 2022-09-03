package com.hteecommerce.hteapp.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import com.hteecommerce.hteapp.entity.Comprobante;
import com.hteecommerce.hteapp.entity.Destinatario;
import com.hteecommerce.hteapp.entity.DetalleComprobante;
import com.hteecommerce.hteapp.entity.DetalleIngreso;
import com.hteecommerce.hteapp.entity.DireccionEnvio;
import com.hteecommerce.hteapp.exception.InsufficientStockError;
import com.hteecommerce.hteapp.file_manager.IFileService;
import com.hteecommerce.hteapp.mapper.Mapper;
import com.hteecommerce.hteapp.model.MComprobante;
import com.hteecommerce.hteapp.service.IComprobanteService;
import com.hteecommerce.hteapp.service.IDireccionEnvioService;
import com.hteecommerce.hteapp.service.IIngresoService;
import com.hteecommerce.hteapp.util.RutaActual;
import com.hteecommerce.hteapp.util.UDetalleComprobante;

@RestController
@RequestMapping("/com-lib/cl")
public class ComprobanteLibreController {

    @Autowired
    private IIngresoService ingresoService;

    @Autowired
    private IComprobanteService comprobanteService;   

    @Autowired
    private IDireccionEnvioService direccionEnvioService;      
    
    @Autowired IFileService fileService;
    
    @PostMapping("/com/crear")
    public ResponseEntity<?> createComprobante(@Valid @RequestBody Comprobante comprobante, BindingResult result) {

        Map<String, String> resp = new HashMap<>();   
        DireccionEnvio direccionEnvio = null;     
        String numero = null;

        if(result.hasErrors()){
            String errors = result.getFieldErrors().stream()
                .map(err -> "El campo: " + err.getField() + " " + err.getDefaultMessage())
                .collect(Collectors.joining(", "));

            resp.put("mensaje", errors);
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.BAD_REQUEST);
        }     
                       
        String errorsDC = comprobante.getDetalleComprobantes().stream()
                .flatMap(dc -> UDetalleComprobante.isValidDC(dc.getCantidad(), dc.getSubTotal()))
                .collect(Collectors.joining(", "));

        if (errorsDC != null && errorsDC.length() != 0) {
            resp.put("mensaje", errorsDC);
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.BAD_REQUEST);
        }

        try {
            direccionEnvio = direccionEnvioService.getByIddireccionenvio(comprobante.getDireccionEnvio().getIddireccion());
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error del sistema: Inténtelo mas tarde");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        List<DetalleComprobante> dcs = new ArrayList<>();
        for (DetalleComprobante dc : comprobante.getDetalleComprobantes()) {

            DetalleIngreso di = null;
            try {
                di = ingresoService.getByIddetalleingreso(dc.getDetalleIngreso().getIddetalleingreso());
            } catch (DataAccessException e) {
                resp.put("mensaje", "Error del sistema: Intentelo mas tarde");
                return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
            }           
 
            if (di != null) { 
                //di.setStockActual(dc.getDetalleIngreso().getStockActual());   
                if(di.getVariedades() != null){
                    di.setVariedades(Mapper.actualizarVariedades(dc.getVariedades(), di.getVariedades()));
                }

                dc.setDetalleIngreso(di);                

                try {
                    dc.decreaseStockActual();
                } catch (InsufficientStockError e) {
                    resp.put("mensaje", "Stock insuficiente por compra simultanea, por favor quite el producto: " + di.getProducto().getNombre() + " de su carrito y vuelva e intentar");
                    return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
                }                

                dcs.add(dc);
            }
        }       

        if (comprobante.getDetalleComprobantes().size() != dcs.size()) {
            resp.put("mensaje", "Error al crear orden, no fue posible validar existencias");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }

        try {
            numero = comprobanteService.getMaxId();
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(numero == null){
            String serie = "001";
            String correlativo = "000001";
            numero = serie+"-"+correlativo;
        }
        else{
            String arrayNumero[] = numero.split("-");
            String serie = arrayNumero[0];
            String correlativo = arrayNumero[1];
            if(Integer.parseInt(correlativo) < 999999){
                numero = serie+"-"+Mapper.generateNumero(correlativo, 6);
            }
            else if(Integer.parseInt(correlativo) == 999999){
                serie = Mapper.generateNumero(serie, 3);
                numero = serie+"-000001";
            }            
        }       
        
        comprobante.setNumero(numero);
        comprobante.setDireccionEnvio(direccionEnvio);        
        comprobante.setDetalleComprobantes(dcs);

        Comprobante com = null;
        try {
            com = comprobanteService.saveCOM(comprobante);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error al guardar datos");
            
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        resp.put("mensaje", "Orden creado con éxito");
        resp.put("id", com.getIdcomprobante().toString());        
        return new ResponseEntity<Map<String, String>>(resp, HttpStatus.CREATED);
    }

    @PostMapping("/img/com")
    public ResponseEntity<?> saveImg(@RequestParam(name = "idcom") Integer idcomprobante,
         @RequestParam(name = "imagen") MultipartFile file){

        Map<String, String> resp = new HashMap<>();  
        Comprobante com = null; 
        String ruta = RutaActual.RUTA_COMPROBANTE;

        if(file.isEmpty()){
            resp.put("mensaje", "La imagen no es valida");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.BAD_REQUEST);
        }

        try {
            com = comprobanteService.getByIdcomprobante(idcomprobante);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de servidor");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (com == null) {
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
            com.setImagen(nombreImagen);
            comprobanteService.saveCOM(com);
        } catch (Exception e) {
            resp.put("mensaje", "Error al guardar datos");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        resp.put("mensaje", "Comprobante creado con éxito");  
        resp.put("id", com.getIdcomprobante().toString());       
        return new ResponseEntity<Map<String, String>>(resp, HttpStatus.OK);        
    }

    @GetMapping("/com/obtener/{id}")
    public ResponseEntity<?> getDetalleComprobante(@PathVariable(value = "id") Integer idcom) {

        Map<String, String> resp = new HashMap<>();
        Comprobante com = null;

        try {
            com = comprobanteService.getByIdcomprobante(idcom);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (com != null) {
            MComprobante mcom = new MComprobante(com);
            return new ResponseEntity<MComprobante>(mcom, HttpStatus.OK);
        } else {
            resp.put("mensaje", "Sin datos que mostrar");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping("/com/editar")
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

        if(com.getDireccionEnvio().getCliente() != null){
            Integer puntos = com.getDireccionEnvio().getCliente().getPuntos() + 1;
            com.getDireccionEnvio().getCliente().setPuntos(1 + puntos);
        }                 

        com.setIdtransaccion(comprobante.getIdtransaccion());
        com.setTipoComprobante(comprobante.getTipoComprobante());
        com.setEstado(comprobante.getEstado());
        com.getDetallePago().setEstadoPago(comprobante.getDetallePago().getEstadoPago());       
          
        try {
            comprobanteService.saveCOM(com);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error al actualizar datos");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        resp.put("mensaje", "Orden actualizado con éxito");        
        return new ResponseEntity<Map<String, String>>(resp, HttpStatus.OK);
    }

    @DeleteMapping("/com/eliminar/{id}")
    public ResponseEntity<?> deleteComprobante(@PathVariable(value = "id") Integer idcom) {

        Map<String, String> resp = new HashMap<>();    
        Comprobante com = null;    
        List<DetalleIngreso> dis = new ArrayList<>();

        try {
            com = comprobanteService.getByIdcomprobante(idcom);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }    

        if(com == null){
            resp.put("mensaje", "No fue posible deshacer cambios");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }

        for(DetalleComprobante dc : com.getDetalleComprobantes()){
            Integer cantidad = dc.getDetalleIngreso().getStockActual() + dc.getCantidad();            
            dc.getDetalleIngreso().setStockActual(cantidad);
            if(dc.getVariedades() != null){
                dc.getDetalleIngreso().setVariedades(Mapper.restablecerVariedades(dc.getVariedades(), dc.getDetalleIngreso().getVariedades()));
            }
            
            dis.add(dc.getDetalleIngreso());
        }

        try {
            comprobanteService.deleteCOM(idcom, dis);

            if(com.getDireccionEnvio().getCliente() == null){
                direccionEnvioService.deleteDE(com.getDireccionEnvio().getIddireccion());
            }

        } catch (DataAccessException e) {
            resp.put("mensaje", "Error al eliminar comporobante");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        resp.put("mensaje", "Comprobante eliminado");
        return new ResponseEntity<Map<String, String>>(resp, HttpStatus.OK);   

    }

    //DIRECCION DE ENVIO CREAR
    @PostMapping("/de/crear")
    public ResponseEntity<?> createDE(@Valid @RequestBody DireccionEnvio die, BindingResult result) {

        Map<String, Object> resp = new HashMap<>();
        DireccionEnvio de = null;

        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors().stream()
                    .map(err -> "El campo: " + err.getField() + " " + err.getDefaultMessage())
                    .collect(Collectors.toList());

            resp.put("mensaje", errors);
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.BAD_REQUEST);
        }

        if (die.getDestinatario().getIddestinatario() == null &&
                direccionEnvioService.isExistsByDni(die.getDestinatario().getDni())) {

            resp.put("mensaje", "El documento ingresado para destinatario ya existe en el sistema");
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.NOT_FOUND);
        }        

        die.setCliente(null);        

        try {
            de = direccionEnvioService.saveDE(die);
        } catch (Exception e) {
            resp.put("mensaje", "Error.. no fue posible guardar nueva dirección");
            // resp.put("error", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        resp.put("mensaje", "Dirección creada con éxito");
        resp.put("direccionEnvio", de);
        return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.CREATED);
    }

    @DeleteMapping("/de/eliminar/{idde}")
    public ResponseEntity<?> deleteDireccionEnvio(@PathVariable(value = "idde") Integer idde) {

        Map<String, String> resp = new HashMap<>();
        DireccionEnvio de = null;

        try {
            de = direccionEnvioService.getByIddireccionenvio(idde);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (de == null) {
            resp.put("mensaje", "Error de consulta al sistema, intentelo mas tarde");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }

        try {
            direccionEnvioService.deleteDE(de.getIddireccion());
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error al deshacer cambios");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        resp.put("mensaje", "Cambios eliminados con éxito");
        return new ResponseEntity<Map<String, String>>(resp, HttpStatus.OK);
    }    

    @GetMapping("/de/by/dni/{dni}")
    public ResponseEntity<?> getDestinatarioByDni(@PathVariable(value = "dni") String dni) {

        Map<String, Object> resp = new HashMap<>();
        Destinatario des = null;

        try {
            des = direccionEnvioService.getByDni(dni);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta, inténtelo mas tarde");
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (des == null) {
            resp.put("mensaje", "Sin datos que mostrar");
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.NOT_FOUND);
        }        
        
        resp.put("destinatario", des);
        return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.OK);
    }    
    
}
