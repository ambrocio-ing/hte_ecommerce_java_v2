package com.hteecommerce.hteapp.controller;

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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hteecommerce.hteapp.entity.Carrito;
import com.hteecommerce.hteapp.entity.Cliente;
import com.hteecommerce.hteapp.entity.DetalleIngreso;
import com.hteecommerce.hteapp.mapper.Mapper;
import com.hteecommerce.hteapp.model.MCarrito;
import com.hteecommerce.hteapp.model.MCompraRapida;
import com.hteecommerce.hteapp.service.ICarritoService;
import com.hteecommerce.hteapp.service.IClienteService;
import com.hteecommerce.hteapp.service.IIngresoService;

@RestController
@RequestMapping("/carrito/carr")
public class CarritoController {

    @Autowired
    private ICarritoService carritoService;

    @Autowired
    private IClienteService clienteService;

    @Autowired
    private IIngresoService ingresoService;

    @PreAuthorize("hasRole('CLIENT')")
    @GetMapping("/lista/{idcli}")
    public ResponseEntity<?> listCarritos(@PathVariable(value = "idcli") Integer idcliente) {

        Map<String, Object> resp = new HashMap<>();
        List<Carrito> carritos = null;

        try {
            carritos = carritoService.getByCliente(idcliente);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.NOT_FOUND);
        }

        if (carritos != null && carritos.size() != 0) {
            List<Integer> ids = new ArrayList<>();
            for(Carrito car : carritos){
                if(car.getDetalleIngreso().getEstado() == false || car.getDetalleIngreso().getStockActual() == 0 
                    || car.getCantidad() > car.getDetalleIngreso().getStockActual()){

                    carritoService.deleteC(car.getIdcarrito());    
                    ids.add(car.getIdcarrito());                
                }
            }

            if(ids.size() > 0){
                for(Integer id : ids){
                    carritos = carritos.stream()
                            .filter(ca -> ca.getIdcarrito() != id)
                            .collect(Collectors.toList());
                }   
            }                    

            List<MCarrito> mlista = carritos.stream()
                    .map(carr -> new MCarrito(carr))
                    .collect(Collectors.toList());

            return new ResponseEntity<List<MCarrito>>(mlista, HttpStatus.OK);
        }
        // resp.put("mensaje", "Sin datos que mostrar");
        carritos = new ArrayList<>();
        return new ResponseEntity<List<Carrito>>(carritos, HttpStatus.OK);
    }    

    @PreAuthorize("hasRole('CLIENT')")
    @PostMapping("/crear")
    public ResponseEntity<?> createCarrito(@Valid @RequestBody Carrito carrito, BindingResult result) {

        Map<String, Object> resp = new HashMap<>();
        Cliente cliente = null;
        DetalleIngreso di = null;
        Carrito carr = null;

        if (result.hasErrors()) {
            String errors = result.getFieldErrors().stream()
                    .map(err -> "El campo: " + err.getField() + " " + err.getDefaultMessage())
                    .collect(Collectors.joining(", "));

            resp.put("mensaje", errors);
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.BAD_REQUEST);
        }

        try {
            carr = carritoService.getByIdproductoAndIdcliente(carrito.getDetalleIngreso().getProducto().getIdproducto(),
                    carrito.getIdcliente());
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error del sistema: Inténtelo mas tarde");
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.NOT_FOUND);
        }

        if (carr == null) {
            try {
                cliente = clienteService.getByIdcliente(carrito.getIdcliente());
            } catch (DataAccessException e) {
                resp.put("mensaje", "Error del sistema: Inténtelo mas tarde");
                return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.NOT_FOUND);
            }

            try {
                di = ingresoService.getByIddetalleingreso(carrito.getDetalleIngreso().getIddetalleingreso());
            } catch (DataAccessException e) {
                resp.put("mensaje", "Error del sistema: Inténtelo mas tarde");
                return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.NOT_FOUND);
            }

            if (cliente == null || di == null || di.getEstado() == false || di.getStockActual() == 0) {
                resp.put("mensaje", "Error de validación: Inténtelo mas tarde");
                return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.NOT_FOUND);
            }

            carrito.setDetalleIngreso(di);
            if(carrito.getDetalleIngreso().getProducto().getProductoVestimenta() == null){
                carrito.setVariedades(null);
            }

            try {
                carrito = carritoService.saveC(carrito);
            } catch (Exception e) {
                resp.put("mensaje", "Error del sistema: Inténtelo mas tarde");
                return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.NOT_FOUND);
            }

            resp.put("mensaje", "Producto agregado con éxito");            
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.CREATED);
        }

        resp.put("mensaje", "El producto ya existe en el carrito");        
        return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('CLIENT')")
    @PostMapping("/all-crear")
    public ResponseEntity<?> createAllCarrito(@RequestBody List<Carrito> carritos) {

        Map<String, Object> resp = new HashMap<>();
        List<MCompraRapida> compras = new ArrayList<>();
        List<Carrito> carrs = new ArrayList<>();
        
        for (Carrito car : carritos) {

            Carrito ca = null;
            DetalleIngreso di = null;

            try {
                di = ingresoService.getByIddetalleingreso(car.getDetalleIngreso().getIddetalleingreso());
            } catch (DataAccessException e) {
                resp.put("mensaje", "Error de consulta");
                return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.NOT_FOUND);
            }

            try {
                ca = carritoService.getByIdproductoAndIdcliente(car.getDetalleIngreso().getProducto().getIdproducto(), car.getIdcliente());
            } catch (DataAccessException e) {
                resp.put("mensaje", "Error de consulta");
                return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.NOT_FOUND);
            }

            if (ca == null && di != null) {
                if(di.getProducto().getProductoVestimenta() == null){
                    car.setVariedades(null);
                }         

                car.setDetalleIngreso(di);
                car.setSubTotal(di.getPrecioVenta());
                carrs.add(car);
            }
            else{
                MCompraRapida mcompra = new MCompraRapida();
                mcompra.setCondicion("Producto no disponible");
                mcompra.setDetalleIngreso(Mapper.mapDetalleIngresoTienda(car.getDetalleIngreso()));                
                compras.add(mcompra);
            }            
        }       

        try {
            carritoService.saveAllC(carrs);
        } catch (Exception e) {
            resp.put("mensaje", "Error, no fue posible agregar productos al carrito");
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.NOT_FOUND);
        }        

        resp.put("cantidad_ok", carrs.size());
        resp.put("compras_error", compras);
        return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('CLIENT')")
    @PostMapping("/editar")
    public ResponseEntity<?> updateCarrito(@RequestBody Carrito carrito) {

        Map<String, Object> resp = new HashMap<>();
        Carrito carr = null;

        try {
            carr = carritoService.getByIdcarrito(carrito.getIdcarrito());
        } catch (Exception e) {
            resp.put("mensaje", "Error de cunsulta a la base de datos");
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.NOT_FOUND);
        }

        if (carr == null) {
            resp.put("mensaje", "Error de cunsulta a la base de datos");
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.NOT_FOUND);
        }

        carr.setCantidad(carrito.getCantidad());
        carr.setDescuento(carrito.getDescuento());
        carr.setSubTotal(carrito.getSubTotal());

        if(carrito.getDetalleIngreso().getProducto().getProductoVestimenta() != null){
            carr.setVariedades(carrito.getVariedades());
        }  

        try {
            carr = carritoService.saveC(carr);
        } catch (Exception e) {
            resp.put("mensaje", "Errores al actualizar carrito");
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.NOT_FOUND);
        }

        resp.put("mensaje", "Orden actualizado con éxito");        
        return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('CLIENT')")
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> deleteCarrito(@PathVariable(value = "id") Integer idcarrito) {

        Map<String, String> resp = new HashMap<>();
        Carrito carrito = null;

        try {
            carrito = carritoService.getByIdcarrito(idcarrito);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (carrito == null) {
            resp.put("mensaje", "Error, no se encontro coincidencias");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }

        try {
            carritoService.deleteC(carrito.getIdcarrito());
        } catch (Exception e) {
            resp.put("mensaje", "Error, el sistema no responde, por favor intentelo de nuevo");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        resp.put("mensaje", "Producto quitado del carrito con éxito");
        return new ResponseEntity<Map<String, String>>(resp, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('CLIENT')")
    @DeleteMapping("/all/eliminar/{id}")
    public ResponseEntity<?> deleteAllCarrito(@PathVariable(value = "id") Integer idcliente) {

        Map<String, String> resp = new HashMap<>();
        List<Carrito> carritos = null;

        try {
            carritos = carritoService.getByCliente(idcliente);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (carritos == null) {
            resp.put("mensaje",
                    "Error, no fue posible limpiar productos del carrito, por favor procure eliminarlos de forma manual");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }

        try {
            for (Carrito carr : carritos) {
                carritoService.deleteC(carr.getIdcarrito());
            }
        } catch (Exception e) {
            resp.put("mensaje",
                    "Error, no fue posible limpiar productos del carrito, por favor procure eliminarlos de forma manual");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        resp.put("mensaje", "Productos eliminados del carrito con éxito");
        return new ResponseEntity<Map<String, String>>(resp, HttpStatus.OK);
    }

}
