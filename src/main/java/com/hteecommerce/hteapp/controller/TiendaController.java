package com.hteecommerce.hteapp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hteecommerce.hteapp.entity.Comentario;
import com.hteecommerce.hteapp.entity.Delivery;
import com.hteecommerce.hteapp.entity.DetalleIngreso;
import com.hteecommerce.hteapp.entity.Membresia;
import com.hteecommerce.hteapp.entity.Producto;
import com.hteecommerce.hteapp.entity.Publicacion;
import com.hteecommerce.hteapp.entity.Tipo;
import com.hteecommerce.hteapp.mapper.Mapper;
import com.hteecommerce.hteapp.model.HistoricoPrecio;
import com.hteecommerce.hteapp.model.MComentario;
import com.hteecommerce.hteapp.model.MDetalleIngreso;
import com.hteecommerce.hteapp.service.ICategoriaTipoService;
import com.hteecommerce.hteapp.service.IComentarioSerivce;
import com.hteecommerce.hteapp.service.IDeliveryService;
import com.hteecommerce.hteapp.service.IIngresoService;
import com.hteecommerce.hteapp.service.IMembresiaService;
import com.hteecommerce.hteapp.service.IPublicacionService;

@RestController
@RequestMapping("/tienda/ti")
public class TiendaController {

    @Autowired
    private IIngresoService ingresoService;

    @Autowired
    private IMembresiaService membresiaService;   

    @Autowired
    private ICategoriaTipoService categoriaTipoService;

    @Autowired
    private IDeliveryService deliveryService;

    @Autowired
    private IPublicacionService publicacionService;

    @Autowired
    private IComentarioSerivce comentarioSerivce;

    // Buscar detalle de ingreso por nombre del producto
    @GetMapping("/buscar/{nombre}")
    public ResponseEntity<?> diByFecha(@PathVariable(value = "nombre") String nombre) {

        Map<String, String> resp = new HashMap<>();
        List<DetalleIngreso> dis = null;

        try {
            dis = ingresoService.getByNombreProducto(nombre);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (dis != null && dis.size() != 0) {

            List<MDetalleIngreso> mlista = Mapper.mapDetalleIngresosTienda(dis);
            return new ResponseEntity<List<MDetalleIngreso>>(mlista, HttpStatus.OK);
        } else {
            resp.put("mensaje", "Sin datos que mostrar");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }

    }

    // Detalle de ingreso por tipo
    @GetMapping("/bytip/{id}")
    public ResponseEntity<?> diByTipo(@PathVariable(value = "id") Integer idtipo) {

        Map<String, String> resp = new HashMap<>();
        List<DetalleIngreso> dis = null;

        try {
            dis = ingresoService.getByTipo(idtipo);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (dis != null && dis.size() != 0) {

            List<MDetalleIngreso> mlista = Mapper.mapDetalleIngresosTienda(dis);
            return new ResponseEntity<List<MDetalleIngreso>>(mlista, HttpStatus.OK);
        } else {
            resp.put("mensaje", "Sin datos que mostrar");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }

    }

    // historico de precios
    @PostMapping("/history")
    public ResponseEntity<?> historicoPrecio(@RequestBody Producto producto) {

        Map<String, String> resp = new HashMap<>();       
        List<DetalleIngreso> dis = null;

        try {
            dis = ingresoService.getLast12ByProducto(producto);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (dis != null && dis.size() != 0) {

            List<HistoricoPrecio> hps = Mapper.mapHistoricoPrecios(dis);
            return new ResponseEntity<List<HistoricoPrecio>>(hps, HttpStatus.OK);            
        }
        
        resp.put("mensaje", "Sin datos que mostrar");
        return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);

    }

    // Mostrar todos los detalles de ingreso --> todos los productos
    @GetMapping("/all")
    public ResponseEntity<?> diAll() {

        Map<String, String> resp = new HashMap<>();
        List<DetalleIngreso> dis = null;

        try {
            dis = ingresoService.listDIAll();
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (dis != null && dis.size() != 0) {

            List<MDetalleIngreso> mlista = Mapper.mapDetalleIngresosTienda(dis);
            return new ResponseEntity<List<MDetalleIngreso>>(mlista, HttpStatus.OK);
        } else {
            resp.put("mensaje", "Sin datos que mostrar");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }
    }

    // Detalle de ingreso por id
    @GetMapping("/byid/{iddi}")
    public ResponseEntity<?> getDIById(@PathVariable(value = "iddi") Integer iddi) {

        Map<String, String> resp = new HashMap<>();
        DetalleIngreso di = null;

        try {
            di = ingresoService.getByIddetalleingreso(iddi);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (di == null) {

            resp.put("mensaje", "Sin datos que mostrar");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);

        }

        MDetalleIngreso mdi = Mapper.mapDetalleIngresoTienda(di);
        return new ResponseEntity<MDetalleIngreso>(mdi, HttpStatus.OK);
    }

    // membresia
    @GetMapping("/mem/lista")
    public ResponseEntity<?> listMembresia() {

        Map<String, String> resp = new HashMap<>();
        List<Membresia> lista = null;
        String estado = "Vigente";

        try {
            lista = membresiaService.getByEstado(estado);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (lista != null && lista.size() != 0) {
            return new ResponseEntity<List<Membresia>>(lista, HttpStatus.OK);
        } else {
            resp.put("mensaje", "Sin datos que mostrar");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }

    }

    // productos mas vendidos
    @GetMapping("/mas/ven/{idtipo}")
    public ResponseEntity<?> listMasVendidos(@PathVariable(value = "idtipo") Integer idtipo) {

        Map<String, String> resp = new HashMap<>();
        List<DetalleIngreso> dis = null;

        try {
            switch (idtipo) {
                case 0:
                    dis = ingresoService.getMasVendidosGeneral();
                    if(dis.size() < 15){
                        dis = ingresoService.getLastTwenty();
                    }
                    break;
                default:
                    dis = ingresoService.getMasVendidos(idtipo);
                    if(dis.size() < 15){
                        dis = ingresoService.getByTipo(idtipo);
                    }
            }

        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (dis != null && dis.size() != 0) {            
            
            List<MDetalleIngreso> mlista = dis.stream()
                .limit(50)
                .map(di -> Mapper.mapDetalleIngresoTienda(di))
                .collect(Collectors.toList());

            return new ResponseEntity<List<MDetalleIngreso>>(mlista, HttpStatus.OK);
        }       

        resp.put("mensaje", "Sin datos que mostrar");
        return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
    }

    // lista de tipos
    @GetMapping("/tip-lista")
    public ResponseEntity<?> listTipos() {

        Map<String, String> resp = new HashMap<>();
        List<Tipo> lista = null;

        try {
            lista = categoriaTipoService.getTipos();
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (lista != null && lista.size() != 0) {
            return new ResponseEntity<List<Tipo>>(lista, HttpStatus.OK);
        } else {
            resp.put("mensaje", "Sin datos que mostrar");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }
    }

    // LISTA DE DELIVERYS
    @GetMapping("/deli-lista")
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

    // LISTA DE PUBLICACIONES
    @GetMapping("/by/es/{estado}")
    public ResponseEntity<?> listByEstado(@PathVariable(value = "estado") String estado) {

        Map<String, String> resp = new HashMap<>();
        List<Publicacion> lista = null;

        try {
            lista = publicacionService.getByEstado(estado);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (lista != null && lista.size() != 0) {

            return new ResponseEntity<List<Publicacion>>(lista, HttpStatus.OK);
        } else {
            resp.put("mensaje", "Sin datos que mostrar");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }
    }

    //lista de comentarios para un producto
    @GetMapping("/comens/{idproducto}")
    public ResponseEntity<?> listComentario(@PathVariable(value = "idproducto") Integer idproducto){

        Map<String,String> resp = new HashMap<>();
        List<Comentario> comentarios = null;

        try {
            comentarios = comentarioSerivce.getByIdproducto(idproducto);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta");
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
}
