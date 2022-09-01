package com.hteecommerce.hteapp.controller;

import java.time.LocalDate;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hteecommerce.hteapp.entity.Comentario;
import com.hteecommerce.hteapp.entity.Delivery;
import com.hteecommerce.hteapp.entity.DetalleIngreso;
import com.hteecommerce.hteapp.entity.HoraEntrega;
import com.hteecommerce.hteapp.entity.Membresia;
//import com.hteecommerce.hteapp.entity.Producto;
import com.hteecommerce.hteapp.entity.Publicacion;
import com.hteecommerce.hteapp.entity.Sujerencia;
import com.hteecommerce.hteapp.entity.Tipo;
import com.hteecommerce.hteapp.mapper.Mapper;
//import com.hteecommerce.hteapp.model.HistoricoPrecio;
import com.hteecommerce.hteapp.model.MComentario;
import com.hteecommerce.hteapp.model.MDetalleIngreso;
import com.hteecommerce.hteapp.service.ICategoriaTipoService;
import com.hteecommerce.hteapp.service.IComentarioSerivce;
import com.hteecommerce.hteapp.service.IDeliveryService;
import com.hteecommerce.hteapp.service.IHoraEntregaService;
import com.hteecommerce.hteapp.service.IIngresoService;
import com.hteecommerce.hteapp.service.ILibroReclamoService;
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

    @Autowired
    private ILibroReclamoService libroReclamoService;

    @Autowired
    private IHoraEntregaService horaEntregaService;

    // Buscar detalle de ingreso por nombre del producto
    @GetMapping("/buscar/{nombre}/{sucursal}")
    public ResponseEntity<?> diByProductName(@PathVariable(value = "nombre") String nombre,
        @PathVariable(value = "sucursal") String sucursal) {

        Map<String, String> resp = new HashMap<>();
        List<DetalleIngreso> dis = null;

        try {
            dis = ingresoService.getByNombreProducto(nombre, sucursal);
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

    // Buscar detalle de ingreso por nombre del producto por marca
    @GetMapping("/buscar-ma/{nombre}/{sucursal}/{marca}")
    public ResponseEntity<?> diByProductNameToMarca(@PathVariable(value = "nombre") String nombre,
        @PathVariable(value = "sucursal") String sucursal, 
        @PathVariable(value = "marca") String marca) {

        Map<String, String> resp = new HashMap<>();
        List<DetalleIngreso> dis = null;

        try {
            dis = ingresoService.getByNombreProductoToMarca(nombre, sucursal, marca);
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
    @GetMapping("/bytip/{id}/{sucursal}")
    public ResponseEntity<?> diByTipoToMarca(@PathVariable(value = "id") Integer idtipo,
        @PathVariable(value = "sucursal") String sucursal) {

        Map<String, String> resp = new HashMap<>();
        List<DetalleIngreso> dis = null;

        try {
            dis = ingresoService.getByTipo(idtipo, sucursal);
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

    // Detalle de ingreso por tipo, sucursal y marca
    @GetMapping("/bytip-ma/{id}/{sucursal}/{marca}")
    public ResponseEntity<?> diByTipo(@PathVariable(value = "id") Integer idtipo,
        @PathVariable(value = "sucursal") String sucursal,
        @PathVariable(value = "marca") String marca) {

        Map<String, String> resp = new HashMap<>();
        List<DetalleIngreso> dis = null;

        try {
            dis = ingresoService.getByTipoToMarca(idtipo, sucursal, marca);
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
   /*  @PostMapping("/history")
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

    } */

    // Mostrar todos los detalles de ingreso --> todos los productos
    @GetMapping("/all/{sucursal}")
    public ResponseEntity<?> diAll(@PathVariable(value = "sucursal") String sucursal) {

        Map<String, String> resp = new HashMap<>();
        List<DetalleIngreso> dis = null;

        try {
            dis = ingresoService.listDIAll(sucursal);
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

    //mostrar todos los productos disponibles por marca
    @GetMapping("/all-ma/{sucursal}/{marca}")
    public ResponseEntity<?> diAllToMarca(@PathVariable(value = "sucursal") String sucursal, 
        @PathVariable(value = "marca") String marca) {

        Map<String, String> resp = new HashMap<>();
        List<DetalleIngreso> dis = null;

        try {
            dis = ingresoService.listDIAllToMarca(sucursal, marca);
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

        MDetalleIngreso mdi = Mapper.mapDetalleIngreso(di);
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
    @GetMapping("/mas/ven/{idtipo}/{sucursal}")
    public ResponseEntity<?> listMasVendidos(@PathVariable(value = "idtipo") Integer idtipo,
        @PathVariable(value = "sucursal") String sucursal) {

        Map<String, String> resp = new HashMap<>();
        List<DetalleIngreso> dis = null;

        try {
            switch (idtipo) {
                case 0:
                    dis = ingresoService.getMasVendidosGeneral(sucursal);
                    if(dis.size() < 15){
                        dis = ingresoService.getLastTwenty(sucursal);
                    }
                    break;
                default:
                    dis = ingresoService.getMasVendidos(idtipo, sucursal);
                    if(dis.size() < 15){
                        dis = ingresoService.getByTipo(idtipo, sucursal);
                    }
            }

        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (dis != null && dis.size() != 0) {            
            
            List<MDetalleIngreso> mlista = dis.stream()
                .limit(50)
                .map(di -> Mapper.mapDetalleIngreso(di))
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
            resp.put("mensaje", "Error de servidor");
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
    @GetMapping("/deli-lista/{sucursal}")
    public ResponseEntity<?> listDelivery(@PathVariable(value = "sucursal") String sucursal) {

        Map<String, String> resp = new HashMap<>();
        List<Delivery> ds = null;

        try {
            ds = deliveryService.getBySucursal(sucursal);
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
    @GetMapping("/by/es")
    public ResponseEntity<?> listByEstado() {

        Map<String, String> resp = new HashMap<>();
        List<Publicacion> lista = null;

        LocalDate fecha = LocalDate.now();

        try {
            lista = publicacionService.listByEstadoAndDates(fecha);
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

    //CREAR SUJERENCIA    
    @PostMapping("/suje-crear")
    public ResponseEntity<?> createSU(@Valid @RequestBody Sujerencia sujerencia, BindingResult result){

        Map<String,String> resp = new HashMap<>();
        Sujerencia suje = null;

        if(result.hasErrors()){
            List<String> errors = result.getFieldErrors().stream()
                .map(err -> "El campo: "+err.getField()+" "+err.getDefaultMessage())
                .collect(Collectors.toList());
            resp.put("mensaje", errors.toString());
            return new ResponseEntity< Map<String,String>>(resp, HttpStatus.BAD_REQUEST);
        }

        try {
            suje = libroReclamoService.getByDetalle(sujerencia.getDetalle());
        } catch (Exception e) {
            resp.put("mensaje", "Error: No fue posible enviar sujerencia");
            return new ResponseEntity<Map<String,String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }  

        if(suje != null){
            suje.setCantidad(suje.getCantidad() + 1);

            try {
                libroReclamoService.saveSU(sujerencia);
            } catch (Exception e) {
                resp.put("mensaje", "Error: No fue posible enviar sujerencia");
                return new ResponseEntity<Map<String,String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
            }  
        }
        else{
            try {
                libroReclamoService.saveSU(sujerencia);
            } catch (Exception e) {
                resp.put("mensaje", "Error: No fue posible enviar sujerencia");
                return new ResponseEntity<Map<String,String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
            }  
        }             

        resp.put("mensaje", "Sujerencia enviado con éxito");
        return new ResponseEntity<Map<String,String>>(resp, HttpStatus.CREATED);
    }

    //LISTA DE HORAS DE ENTREGA
    @GetMapping("/he/lista")
    public ResponseEntity<?> getAllHE(){

        Map<String, String> resp = new HashMap<>();
        List<HoraEntrega> hes = null;

        try {
            hes = horaEntregaService.getAll();
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de sistema: Inténtelo mas tarde");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(hes != null && hes.size() != 0){
            
            return new ResponseEntity<List<HoraEntrega>>(hes, HttpStatus.OK);
        }

        resp.put("mensaje", "Sin datos que mostrar");
        return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
    }

}
