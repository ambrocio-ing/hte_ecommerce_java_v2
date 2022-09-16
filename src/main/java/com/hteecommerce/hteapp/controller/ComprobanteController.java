package com.hteecommerce.hteapp.controller;

import java.time.LocalDate;
//import java.time.LocalDateTime;
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

    // OTRAS TAREAS
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

        if (com == null) {
            resp.put("mensaje", "No fue posible deshaser cambios");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }

        for (DetalleComprobante dc : com.getDetalleComprobantes()) {
            Double cantidad = dc.getDetalleIngreso().getStockActual() + dc.getCantidad();
            dc.getDetalleIngreso().setStockActual(cantidad);
            if (dc.getVariedades() != null) {
                dc.getDetalleIngreso().setVariedades(
                        Mapper.restablecerVariedades(dc.getVariedades(), dc.getDetalleIngreso().getVariedades()));
            }
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

        for (DetalleComprobante dc : com.getDetalleComprobantes()) {
            if (dc.getVariedades() != null) {
                dc.getDetalleIngreso().setVariedades(
                        Mapper.restablecerVariedades(dc.getVariedades(), dc.getDetalleIngreso().getVariedades()));
            }
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

    // BUSQUEDAS
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/fp/{fecha}/{sucursal}")
    public ResponseEntity<?> searchByFechaPedido(@PathVariable(value = "fecha") String fecha,
            @PathVariable(value = "sucursal") String sucursal) {

        Map<String, String> resp = new HashMap<>();
        List<Comprobante> coms = null;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate ffecha = LocalDate.parse(fecha, formatter);

        try {
            coms = comprobanteService.getByFechaByEstadoPedido(ffecha, sucursal);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            resp.put("error", e.getMessage().concat(" : ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (coms != null && coms.size() != 0) {
            List<MComprobante> mlista = coms.stream().map(com -> Mapper.mapComprobante(com))
                    .collect(Collectors.toList());
            return new ResponseEntity<List<MComprobante>>(mlista, HttpStatus.OK);
        } else {
            resp.put("mensaje", "Sin datos que mostrar");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }

    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/fe/{fecha}/{sucursal}")
    public ResponseEntity<?> searchByFechaEstadoEntregado(@PathVariable(value = "fecha") String fecha,
            @PathVariable(value = "sucursal") String sucursal) {

        Map<String, String> resp = new HashMap<>();
        List<Comprobante> coms = null;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate ffecha = LocalDate.parse(fecha, formatter);

        try {
            coms = comprobanteService.getByFechaByEstadoEntregado(ffecha, sucursal);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (coms != null && coms.size() != 0) {
            List<MComprobante> mlista = coms.stream().map(com -> Mapper.mapComprobante(com))
                    .collect(Collectors.toList());
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

            List<MComprobante> mlista = coms.stream().map(com -> Mapper.mapComprobante(com))
                    .collect(Collectors.toList());
            mlista = mlista.stream().limit(10).collect(Collectors.toList());
            return new ResponseEntity<List<MComprobante>>(mlista, HttpStatus.OK);

        } else {
            resp.put("mensaje", "No se encontraron coincidencias para el cliente seleccionado");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }

    }

    // paginadores para sucursal huacho
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/huacho/en/{page}")
    public ResponseEntity<?> comListEstadoEntregadoHuacho(@PathVariable(value = "page") int page) {

        Map<String, String> resp = new HashMap<>();
        Page<Comprobante> comPage = null;

        Pageable pageable = PageRequest.of(page, 10);

        try {
            comPage = comprobanteService.getByEstadoEntregadoHuacho(pageable);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (comPage != null && comPage.getContent().size() != 0) {
            Page<MComprobante> mpage = comPage.map(com -> Mapper.mapComprobante(com));
            return new ResponseEntity<Page<MComprobante>>(mpage, HttpStatus.OK);
        } else {
            resp.put("mensaje", "Sin datos que mostrar");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/huacho/pe/{page}")
    public ResponseEntity<?> comListEstadoPedidoHuacho(@PathVariable(value = "page") int page) {

        Map<String, String> resp = new HashMap<>();
        Page<Comprobante> comPage = null;

        Pageable pageable = PageRequest.of(page, 10);

        try {
            comPage = comprobanteService.getByEstadoPedidoHuacho(pageable);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (comPage != null && comPage.getContent().size() != 0) {
            Page<MComprobante> mpage = comPage.map(com -> Mapper.mapComprobante(com));
            return new ResponseEntity<Page<MComprobante>>(mpage, HttpStatus.OK);
        } else {
            resp.put("mensaje", "Sin datos que mostrar");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }

    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/huacho/an/{page}")
    public ResponseEntity<?> comListEstadoAnuladoHuacho(@PathVariable(value = "page") int page) {

        Map<String, String> resp = new HashMap<>();
        Page<Comprobante> comPage = null;

        Pageable pageable = PageRequest.of(page, 10);

        try {
            comPage = comprobanteService.getByEstadoAnuladoHuacho(pageable);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (comPage != null && comPage.getContent().size() != 0) {
            Page<MComprobante> mpage = comPage.map(com -> Mapper.mapComprobante(com));
            return new ResponseEntity<Page<MComprobante>>(mpage, HttpStatus.OK);
        } else {
            resp.put("mensaje", "Sin datos que mostrar");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }

    }

    // paginadores para sucursal Barranca
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/barranca/en/{page}")
    public ResponseEntity<?> comListEstadoEntregadoBarranca(@PathVariable(value = "page") int page) {

        Map<String, String> resp = new HashMap<>();
        Page<Comprobante> comPage = null;

        Pageable pageable = PageRequest.of(page, 10);

        try {
            comPage = comprobanteService.getByEstadoEntregadoBarranca(pageable);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (comPage != null && comPage.getContent().size() != 0) {
            Page<MComprobante> mpage = comPage.map(com -> Mapper.mapComprobante(com));
            return new ResponseEntity<Page<MComprobante>>(mpage, HttpStatus.OK);
        } else {
            resp.put("mensaje", "Sin datos que mostrar");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/barranca/pe/{page}")
    public ResponseEntity<?> comListEstadoPedidoBarranca(@PathVariable(value = "page") int page) {

        Map<String, String> resp = new HashMap<>();
        Page<Comprobante> comPage = null;

        Pageable pageable = PageRequest.of(page, 10);

        try {
            comPage = comprobanteService.getByEstadoPedidoBarranca(pageable);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (comPage != null && comPage.getContent().size() != 0) {
            Page<MComprobante> mpage = comPage.map(com -> Mapper.mapComprobante(com));
            return new ResponseEntity<Page<MComprobante>>(mpage, HttpStatus.OK);
        } else {
            resp.put("mensaje", "Sin datos que mostrar");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }

    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/barranca/an/{page}")
    public ResponseEntity<?> comListEstadoAnuladoBarranca(@PathVariable(value = "page") int page) {

        Map<String, String> resp = new HashMap<>();
        Page<Comprobante> comPage = null;

        Pageable pageable = PageRequest.of(page, 10);

        try {
            comPage = comprobanteService.getByEstadoAnuladoBarranca(pageable);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (comPage != null && comPage.getContent().size() != 0) {
            Page<MComprobante> mpage = comPage.map(com -> Mapper.mapComprobante(com));
            return new ResponseEntity<Page<MComprobante>>(mpage, HttpStatus.OK);
        } else {
            resp.put("mensaje", "Sin datos que mostrar");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }

    }

    // Resumen de vanta aqui
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/resumen/venta/{sucursal}")
    public ResponseEntity<?> listResumen(@PathVariable(value = "sucursal") String sucursal) {

        Map<String, String> resp = new HashMap<>();
        List<Comprobante> comprobantes = null;

        try {
            comprobantes = comprobanteService.getByEntregaPendienteSucursal(sucursal);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de servidor");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (comprobantes == null || comprobantes.size() == 0) {
            resp.put("mensaje", "No se encontró entregas pendientes");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }

        List<MResumenVenta> resumens = Mapper.agruparDetalleComprobantes(comprobantes);

        if (resumens != null && resumens.size() != 0) {
            return new ResponseEntity<List<MResumenVenta>>(resumens, HttpStatus.OK);
        }

        resp.put("mensaje", "No se encontró entregas pendientes");
        return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/resumen/venta/by/{fecha}/{sucursal}")
    public ResponseEntity<?> listResumenPorFecha(@PathVariable(value = "fecha") String fecha,
            @PathVariable(value = "sucursal") String sucursal) {

        Map<String, String> resp = new HashMap<>();
        List<Comprobante> comprobantes = null;

        LocalDate fechachita = LocalDate.parse(fecha);

        try {
            comprobantes = comprobanteService.getByFechaEntregaByEstadoPedido(fechachita, sucursal);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de servidor");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (comprobantes == null || comprobantes.size() == 0) {
            resp.put("mensaje", "No se encontró entregas pendientes");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }

        List<MResumenVenta> resumens = Mapper.agruparDetalleComprobantes(comprobantes);

        if (resumens != null && resumens.size() != 0) {
            return new ResponseEntity<List<MResumenVenta>>(resumens, HttpStatus.OK);
        }

        resp.put("mensaje", "No se encontró entregas pendientes");
        return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
    }

    // buscar ventas por validar por fecha
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/validar/by/{fecha}/{sucursal}")
    public ResponseEntity<?> searchByFechaPedidoValidar(@PathVariable(value = "fecha") String fecha,
            @PathVariable(value = "sucursal") String sucursal) {

        Map<String, String> resp = new HashMap<>();
        List<Comprobante> coms = null;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate ffecha = LocalDate.parse(fecha, formatter);

        try {
            coms = comprobanteService.getByFechaByEstadoPedidoValidar(ffecha, sucursal);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            resp.put("error", e.getMessage().concat(" : ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (coms != null && coms.size() != 0) {
            List<MComprobante> mlista = coms.stream().map(com -> Mapper.mapComprobante(com))
                    .collect(Collectors.toList());
            return new ResponseEntity<List<MComprobante>>(mlista, HttpStatus.OK);
        } else {
            resp.put("mensaje", "Sin datos que mostrar");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }

    }

    // lista paginada de ventas por validar por sucursal
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/por/validar/huacho/{page}")
    public ResponseEntity<?> comListEstadoPedidoValidarHuacho(@PathVariable(value = "page") int page) {

        Map<String, String> resp = new HashMap<>();
        Page<Comprobante> comPage = null;

        Pageable pageable = PageRequest.of(page, 10);

        try {
            comPage = comprobanteService.getByEstadoPedidoValidarHuacho(pageable);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (comPage != null && comPage.getContent().size() != 0) {
            Page<MComprobante> mpage = comPage.map(com -> Mapper.mapComprobante(com));
            return new ResponseEntity<Page<MComprobante>>(mpage, HttpStatus.OK);
        } else {
            resp.put("mensaje", "Sin datos que mostrar");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }

    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/por/validar/barranca/{page}")
    public ResponseEntity<?> comListEstadoPedidoValidarBarranca(@PathVariable(value = "page") int page) {

        Map<String, String> resp = new HashMap<>();
        Page<Comprobante> comPage = null;

        Pageable pageable = PageRequest.of(page, 10);

        try {
            comPage = comprobanteService.getByEstadoPedidoValidarBarranca(pageable);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (comPage != null && comPage.getContent().size() != 0) {
            Page<MComprobante> mpage = comPage.map(com -> Mapper.mapComprobante(com));
            return new ResponseEntity<Page<MComprobante>>(mpage, HttpStatus.OK);
        } else {
            resp.put("mensaje", "Sin datos que mostrar");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }
    }

}
