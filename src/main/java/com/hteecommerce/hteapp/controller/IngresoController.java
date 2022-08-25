package com.hteecommerce.hteapp.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.hteecommerce.hteapp.entity.DetalleIngreso;
import com.hteecommerce.hteapp.entity.Ingreso;
import com.hteecommerce.hteapp.entity.Personal;
import com.hteecommerce.hteapp.entity.Producto;
import com.hteecommerce.hteapp.mapper.Mapper;
import com.hteecommerce.hteapp.model.MDetalleIngreso;
import com.hteecommerce.hteapp.model.MIngreso;
import com.hteecommerce.hteapp.service.IIngresoService;
import com.hteecommerce.hteapp.service.IPersonalService;
import com.hteecommerce.hteapp.service.IProductoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

@RestController
@RequestMapping("/ingreso/in")
public class IngresoController {

    @Autowired
    private IPersonalService personalService;

    @Autowired
    private IProductoService productoService;

    @Autowired
    private IIngresoService ingresoService;

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/di/lista/{page}")
    public ResponseEntity<?> diList(@PathVariable(value = "page") int page) {

        Map<String, String> resp = new HashMap<>();
        Page<DetalleIngreso> dis = null;

        Pageable pageable = PageRequest.of(page, 5);

        try {
            dis = ingresoService.pageAllDetalleIngreso(pageable);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (dis.getContent().size() != 0) {

            Page<MDetalleIngreso> mpage = dis.map(di -> new MDetalleIngreso(di));
            return new ResponseEntity<Page<MDetalleIngreso>>(mpage, HttpStatus.OK);
        } else {
            resp.put("mensaje", "Sin datos que mostrar");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }

    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/di/por/producto/{texto}")
    public ResponseEntity<?> searchByProducto(@PathVariable(value = "texto") String texto) {

        Map<String, String> resp = new HashMap<>();
        List<DetalleIngreso> dis = null;

        try {
            dis = ingresoService.getDetalleIngresoByCodigoOrNombre(texto);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (dis != null && dis.size() != 0) {
            List<MDetalleIngreso> mlista = Mapper.mapDetalleIngresos(dis);
            return new ResponseEntity<List<MDetalleIngreso>>(mlista, HttpStatus.OK);
        } else {
            resp.put("mensaje", "Sin datos que mostrar");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }

    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/di/por/idp/{idproducto}")
    public ResponseEntity<?> getDIByIdproducto(@PathVariable(value = "idproducto") Integer idproducto) {

        Map<String, String> resp = new HashMap<>();
        DetalleIngreso di = null;

        try {
            di = ingresoService.getDetalleIngresoByIdproducto(idproducto);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (di == null) {

            resp.put("mensaje", "Sin datos que mostrar");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);

        }

        MDetalleIngreso mdi = new MDetalleIngreso(di.getIddetalleingreso(), di.getPrecioVenta(),
                di.getPrecioVentaAnterior(),
                di.getPorcentajeDescuento(),
                di.getStockInicial(), di.getStockActual(),
                di.getEstado(), di.getSucursal(), di.getVariedades());
        return new ResponseEntity<MDetalleIngreso>(mdi, HttpStatus.OK);

    }    

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/lista/{page}")
    public ResponseEntity<?> inList(@PathVariable(value = "page") int page) {

        Map<String, String> resp = new HashMap<>();
        Page<Ingreso> inPage = null;

        Pageable pageable = PageRequest.of(page, 10);

        try {
            inPage = ingresoService.getAll(pageable);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (inPage.getContent().size() != 0) {

            Page<MIngreso> mpage = inPage.map(di -> new MIngreso(di));
            return new ResponseEntity<Page<MIngreso>>(mpage, HttpStatus.OK);
        } else {
            resp.put("mensaje", "Sin datos que mostrar");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }

    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/buscar/{fecha}")
    public ResponseEntity<?> ingresosByFecha(@PathVariable(value = "fecha") String fecha) {

        Map<String, String> resp = new HashMap<>();
        List<Ingreso> ingresos = null;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate ffecha = LocalDate.parse(fecha, formatter);

        try {
            ingresos = ingresoService.getByFecha(ffecha);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (ingresos.size() != 0) {

            List<MIngreso> mingresos = ingresos.stream()
                    .map(in -> new MIngreso(in))
                    .collect(Collectors.toList());
            return new ResponseEntity<List<MIngreso>>(mingresos, HttpStatus.OK);
        } else {
            resp.put("mensaje", "Sin datos que mostrar");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }

    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/crear")
    public ResponseEntity<?> ingresoCreate(@Valid @RequestBody Ingreso ingreso, BindingResult result) {

        Map<String, Object> resp = new HashMap<>();
        Personal per = null;

        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors().stream()
                    .map(err -> "El campo: " + err.getField() + " " + err.getDefaultMessage())
                    .collect(Collectors.toList());

            resp.put("mensaje", errors);
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.BAD_REQUEST);
        }

        List<String> errorsDI = ingreso.detalleIngresos.stream()
                .flatMap(di -> Mapper.isValidDetalleIngreso(di))
                .collect(Collectors.toList());

        if (errorsDI.size() != 0) {
            resp.put("mensaje", errorsDI);
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.BAD_REQUEST);
        }

        try {
            per = personalService.getByIdpersonal(ingreso.getPersonal().getIdpersonal());
        } catch (Exception e) {
            resp.put("mensaje", "Error al guardar ingresos");
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (per == null) {
            resp.put("mensaje", "Error al guardar ingresos");
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.NOT_FOUND);
        }

        List<DetalleIngreso> dis = new ArrayList<>();
        List<DetalleIngreso> dis_anterior = new ArrayList<>();
        for (DetalleIngreso di_nuevo : ingreso.getDetalleIngresos()) {

            Producto pro = productoService.getByIdproducto(di_nuevo.getProducto().getIdproducto());
            DetalleIngreso di_anterior = ingresoService.getDIByIdproducto(di_nuevo.getProducto().getIdproducto(), di_nuevo.getSucursal());
            if (pro != null) {

                if(pro.getProductoVestimenta() == null){
                    di_nuevo.setVariedades(null);
                } 

                di_nuevo.setProducto(pro);

                if (di_anterior != null) {

                    di_anterior.setEstado(false);
                    if(di_anterior.getProducto().getProductoVestimenta() != null && di_anterior.getStockActual() > 0){

                        di_nuevo.setStockActual(di_nuevo.getStockInicial() + di_anterior.getStockActual());
                        di_nuevo.setVariedades(Mapper.mapVariedadesNuevos(di_nuevo.getVariedades(), di_anterior.getVariedades()));
                    }

                    dis_anterior.add(di_anterior);

                } else {
                    di_nuevo.setStockActual(di_nuevo.getStockInicial());
                }

                dis.add(di_nuevo);
            }
        }

        if (ingreso.getDetalleIngresos().size() != dis.size()) {
            resp.put("mensaje", "Error al guardar ingresos");
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.NOT_FOUND);
        }

        ingreso.setPersonal(per);
        ingreso.setDetalleIngresos(dis);

        try {
            ingresoService.saveIN(ingreso, dis_anterior);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error al guardar ingresos");
            resp.put("error", e.getMessage().concat(" : ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        resp.put("mensaje", "Ingresos guardados con éxito");
        return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/obtener/{id}")
    public ResponseEntity<?> getDI(@PathVariable(value = "id") Integer idingreso) {

        Map<String, String> resp = new HashMap<>();
        Ingreso ingreso = null;

        try {
            ingreso = ingresoService.getByIdngreso(idingreso);
        } catch (Exception e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (ingreso == null) {

            resp.put("mensaje", "Error no se encontraron coincidencias");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }

        MIngreso mIngreso = new MIngreso(ingreso);
        return new ResponseEntity<MIngreso>(mIngreso, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/editar")
    public ResponseEntity<?> ingresoUpdate(@RequestBody Ingreso ingreso) {

        Map<String, Object> resp = new HashMap<>();
        Ingreso ingre = null;

        try {
            ingre = ingresoService.getByIdngreso(ingreso.getIdingreso());
        } catch (Exception e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (ingre == null) {
            resp.put("mensaje", "Error al actualizar ingreso");
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.NOT_FOUND);
        }
        
        for (DetalleIngreso di : ingre.getDetalleIngresos()) {          
            
            for(DetalleIngreso diAnterior : ingreso.getDetalleIngresos()){

                if(di.getIddetalleingreso() == diAnterior.getIddetalleingreso()){

                    di.setFechaProduccion(diAnterior.getFechaProduccion());
                    di.setFechaVencimiento(diAnterior.getFechaVencimiento());
                    di.setPorcentajeDescuento(diAnterior.getPorcentajeDescuento());
                    di.setPrecioCompra(diAnterior.getPrecioCompra());
                    di.setPrecioVenta(diAnterior.getPrecioVenta());
                    di.setPrecioVentaAnterior(diAnterior.getPrecioVentaAnterior());
                    di.setStockActual(diAnterior.getStockActual());
                    di.setStockInicial(diAnterior.getStockInicial());

                    if(di.getVariedades() != null){
                        di.setVariedades(diAnterior.getVariedades());
                    }                  

                }
            }
        }        

        ingre.setEstado(ingreso.getEstado());        

        try {
            ingresoService.updateIN(ingre);
        } catch (Exception e) {
            resp.put("mensaje", "Error al guardar ingresos");
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        resp.put("mensaje", "Ingresos actualiados con éxito");
        return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> deleteIn(@PathVariable(value = "id") Integer idingreso) {

        Map<String, String> resp = new HashMap<>();
        Ingreso ingreso = null;
        String sucursal;

        try {
            ingreso = ingresoService.getByIdngreso(idingreso);
        } catch (Exception e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        sucursal = ingreso.getDetalleIngresos().get(0).getSucursal();

        if (ingreso == null || sucursal == null) {
            resp.put("mensaje", "Error, no se encontraron coincidencias");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }

        List<Producto> productos = new ArrayList<>();
        for (DetalleIngreso di : ingreso.getDetalleIngresos()) {
            productos.add(di.getProducto());
        }

        try {
            ingresoService.deleteIN(ingreso.getIdingreso(), productos, sucursal);
        } catch (Exception e) {
            resp.put("mensaje", "Error, es posible que algún producto del ingreso ya fue vendido");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        resp.put("mensaje", "Ingreso eliminado con éxito");
        return new ResponseEntity<Map<String, String>>(resp, HttpStatus.OK);

    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/di/eliminar/{id}")
    public ResponseEntity<?> deleteDI(@PathVariable(value = "id") Integer iddi) {

        Map<String, String> resp = new HashMap<>();
        DetalleIngreso di = null;

        try {
            di = ingresoService.getByIddetalleingreso(iddi);
        } catch (Exception e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (di == null) {
            resp.put("mensaje", "Error, no se encontraron coincidencias");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }        

        try {
            ingresoService.deleteDI(di.getIddetalleingreso(), di.getProducto(), di.getSucursal());
        } catch (Exception e) {
            resp.put("mensaje", "No se a podido eliminar el detalle de ingreso");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        resp.put("mensaje", "Detalle de ingreso eliminado con éxito");
        return new ResponseEntity<Map<String, String>>(resp, HttpStatus.OK);
    }

}
