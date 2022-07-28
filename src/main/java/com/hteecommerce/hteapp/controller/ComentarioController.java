package com.hteecommerce.hteapp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.hteecommerce.hteapp.entity.Cliente;
import com.hteecommerce.hteapp.entity.Comentario;
import com.hteecommerce.hteapp.entity.Comprobante;
import com.hteecommerce.hteapp.entity.DetalleComprobante;
import com.hteecommerce.hteapp.entity.DetalleIngreso;
import com.hteecommerce.hteapp.mapper.Mapper;
import com.hteecommerce.hteapp.model.MComentario;
import com.hteecommerce.hteapp.service.IClienteService;
import com.hteecommerce.hteapp.service.IComentarioSerivce;
import com.hteecommerce.hteapp.service.IComprobanteService;
import com.hteecommerce.hteapp.service.IIngresoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
@RequestMapping("/comentario/com")
public class ComentarioController {

    @Autowired
    private IComentarioSerivce comentarioSerivce;

    @Autowired
    private IIngresoService ingresoService;

    @Autowired
    private IClienteService clienteService;

    @Autowired
    private IComprobanteService comprobanteService;

    @PreAuthorize("hasRole('CLIENT')")
    @GetMapping("/lista/{iddi}")
    public ResponseEntity<?> comList(@PathVariable(value = "iddi") Integer iddi){

        Map<String,String> resp = new HashMap<>();
        List<Comentario> comentarios = null;

        try {
            comentarios = comentarioSerivce.getByIddetalleingreso(iddi);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity< Map<String,String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(comentarios != null && comentarios.size() != 0){
            List<MComentario> mlista = Mapper.mapComentarios(comentarios);
            return new ResponseEntity<List<MComentario>>(mlista, HttpStatus.OK);
        }
        else{
            comentarios = new ArrayList<>();
            return new ResponseEntity<List<Comentario>>(comentarios, HttpStatus.OK);
        }
    }

    @PreAuthorize("hasRole('CLIENT')")
    @GetMapping("/valid/comentario/{iddi}/{idcli}")
    public ResponseEntity<?> comValidComentario(@PathVariable(value = "iddi") Integer iddi,
        @PathVariable(value = "idcli") Integer idcliente){

        Map<String,String> resp = new HashMap<>();
        List<Comprobante> coms = null;
        String condicion = "false";

        try {
            coms = comprobanteService.getByClienteByIdcliente(idcliente);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity< Map<String,String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        for(Comprobante com : coms){
            for(DetalleComprobante dc : com.getDetalleComprobantes()){
                if(dc.getDetalleIngreso().getIddetalleingreso() == iddi){
                    condicion = "true";
                }
            }
        }

        resp.put("condicion", condicion);
        return new ResponseEntity<Map<String,String>>(resp, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('CLIENT')")
    @PostMapping("/crear")
    public ResponseEntity<?> comCreate(@Valid @RequestBody Comentario comentario, BindingResult result){

        Map<String, Object> resp = new HashMap<>();
        Cliente cliente = null;
        DetalleIngreso di = null;
        List<Comentario> comens = null;
        List<Integer> puntos_list = new ArrayList<>();
        int punto_mayor = 0;
        
        if(result.hasErrors()){

            List<String> errors = result.getFieldErrors().stream()
                .map(err -> "El campo: "+err.getField()+" "+err.getDefaultMessage())
                .collect(Collectors.toList());
            
            resp.put("mensaje", errors);
            return new ResponseEntity< Map<String,Object>>(resp, HttpStatus.BAD_REQUEST);

        }

        try {
            cliente = clienteService.getByIdcliente(comentario.getCliente().getIdcliente());
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity< Map<String,Object>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        try {
            di = ingresoService.getByIddetalleingreso(comentario.getDetalleIngreso().getIddetalleingreso());
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity< Map<String,Object>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(cliente == null || di == null){
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity< Map<String,Object>>(resp, HttpStatus.NOT_FOUND);
        }

        comens = comentarioSerivce.getByIdproducto(di.getProducto().getIdproducto());     

        if(comens != null && comens.size() != 0){
            comens.forEach(co -> {
                puntos_list.add(co.getEstrellas());
                puntos_list.add(comentario.getEstrellas());
            });
            int nums[] = puntos_list.stream().mapToInt(num -> num).toArray();
            punto_mayor = Mapper.masRepeticiones(nums);                      
        }
        else{
            punto_mayor = comentario.getEstrellas();
        }          

        comentario.setCliente(cliente);
        comentario.setDetalleIngreso(di);

        if(punto_mayor > 0){
            comentario.getDetalleIngreso().getProducto().setPuntos(punto_mayor);
        }

        try {
            comentarioSerivce.saveCOM(comentario);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error al enviar comentario");            
            return new ResponseEntity< Map<String,Object>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        resp.put("mensaje", "Comentario publicado con éxito");
        return new ResponseEntity< Map<String,Object>>(resp, HttpStatus.CREATED);

    }
    
}
