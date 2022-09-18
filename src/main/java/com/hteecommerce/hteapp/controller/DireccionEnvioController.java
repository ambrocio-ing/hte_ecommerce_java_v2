package com.hteecommerce.hteapp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.hteecommerce.hteapp.entity.Cliente;
import com.hteecommerce.hteapp.entity.Comprobante;
import com.hteecommerce.hteapp.entity.Destinatario;
import com.hteecommerce.hteapp.entity.DireccionEnvio;
import com.hteecommerce.hteapp.mapper.Mapper;
import com.hteecommerce.hteapp.model.MComprobante;
import com.hteecommerce.hteapp.model.MDireccionEnvio;
import com.hteecommerce.hteapp.service.IClienteService;
import com.hteecommerce.hteapp.service.IComprobanteService;
import com.hteecommerce.hteapp.service.IDireccionEnvioService;

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
@RequestMapping("/di-envio/die")
public class DireccionEnvioController {

    @Autowired
    private IDireccionEnvioService direccionEnvioService;

    @Autowired
    private IClienteService clienteService;

    @Autowired
    private IComprobanteService comprobanteService;

    @PreAuthorize("hasRole('CLIENT')")
    @GetMapping("/historial/{idcliente}/{tipo}")
    public ResponseEntity<?> listComprobantesByCliente(@PathVariable(value = "idcliente") Integer idcliente,
            @PathVariable(value = "tipo") Integer tipo) {

        Map<String, String> resp = new HashMap<>();
        List<Comprobante> lista = null;

        try {
            lista = comprobanteService.getByClienteByIdcliente(idcliente);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (lista != null && lista.size() != 0 && tipo == 1) {
            List<MComprobante> mlista = lista.stream()
                    .limit(3)
                    .map(com -> new MComprobante(com))
                    .collect(Collectors.toList());

            return new ResponseEntity<List<MComprobante>>(mlista, HttpStatus.OK);
        }

        if (lista != null && lista.size() != 0 && tipo == 2) {
            List<MComprobante> mlista = lista.stream()
                    .map(com -> new MComprobante(com))
                    .collect(Collectors.toList());

            return new ResponseEntity<List<MComprobante>>(mlista, HttpStatus.OK);
        }

        resp.put("mensaje", "Sin datos que mostrar");
        return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
    }

    @PreAuthorize("hasRole('CLIENT')")
    @GetMapping("/lista/{idcliente}")
    public ResponseEntity<?> listDirecciones(@PathVariable(value = "idcliente") Integer idcliente) {

        Map<String, String> resp = new HashMap<>();
        List<DireccionEnvio> lista = null;

        try {
            lista = direccionEnvioService.getByCliente(idcliente);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (lista != null && lista.size() != 0) {

            List<MDireccionEnvio> mlista = lista.stream()
                    .map(de -> Mapper.mapDireccionEnvio(de))
                    .collect(Collectors.toList());

            return new ResponseEntity<List<MDireccionEnvio>>(mlista, HttpStatus.OK);
        }

        resp.put("mensaje", "Sin datos que mostrar");
        return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
    }

    @PreAuthorize("hasRole('CLIENT')")
    @PostMapping("/crear")
    public ResponseEntity<?> createDE(@Valid @RequestBody DireccionEnvio die, BindingResult result) {

        Map<String, Object> resp = new HashMap<>();
        Cliente cliente = null;
        Destinatario destinatario = null;
        // DireccionEnvio de = null;

        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors().stream()
                    .map(err -> "El campo: " + err.getField() + " " + err.getDefaultMessage())
                    .collect(Collectors.toList());

            resp.put("mensaje", errors);
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.BAD_REQUEST);
        }

        if (Mapper.isPresentDestinatario(die.getDestinatario())) {
            destinatario = new Destinatario();
            destinatario = die.getDestinatario();
            if (die.getDestinatario().getIddestinatario() == null &&
                    direccionEnvioService.isExistsByDni(die.getDestinatario().getDni())) {

                resp.put("mensaje", "El documento ingresado para destinatario ya existe en el sistema");
                return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.NOT_FOUND);
            }
        }

        try {
            cliente = clienteService.getByIdcliente(die.getCliente().getIdcliente());
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta al sistema, intentelo mas tarde");
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (cliente == null) {
            resp.put("mensaje", "Error de consulta al sistema, intentelo mas tarde");
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.NOT_FOUND);
        }

        die.setCliente(cliente);
        die.setDestinatario(destinatario);

        try {
            direccionEnvioService.saveDE(die);
        } catch (Exception e) {
            resp.put("mensaje", "Error.. no fue posible guardar nueva dirección");
            resp.put("error", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        resp.put("mensaje", "Dirección creada con éxito");
        // resp.put("direccionEnvio", Mapper.mapDireccionEnvio(de));
        return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('CLIENT')")
    @GetMapping("/obtener/{idde}")
    public ResponseEntity<?> getDireccionEnvio(@PathVariable(value = "idde") Integer idde) {

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

        MDireccionEnvio mdie = Mapper.mapDireccionEnvio(de);
        return new ResponseEntity<MDireccionEnvio>(mdie, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('CLIENT')")
    @PostMapping("/editar")
    public ResponseEntity<?> updateDE(@RequestBody DireccionEnvio die) {

        Map<String, Object> resp = new HashMap<>();
        Cliente cliente = null;
        DireccionEnvio de = null;

        try {
            cliente = clienteService.getByIdcliente(die.getCliente().getIdcliente());
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (cliente == null) {
            resp.put("mensaje", "Error de consulta al sistema, intentelo mas tarde");
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.NOT_FOUND);
        }

        die.setCliente(cliente);

        try {
            de = direccionEnvioService.saveDE(die);
        } catch (Exception e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            resp.put("error", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        resp.put("mensaje", "Dirección actualiada con éxito");
        resp.put("direccionEnvio", Mapper.mapDireccionEnvio(de));
        return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.CREATED);
    }

    // LISTA DE DESTINATARIOS
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/des-list/{page}")
    public ResponseEntity<?> listDestinatarios(@PathVariable(value = "page") int page) {

        Map<String, String> resp = new HashMap<>();
        Page<Destinatario> des = null;

        Pageable pageable = PageRequest.of(page, 50);
        try {
            des = direccionEnvioService.getAllDestinatario(pageable);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (des != null && des.getContent().size() != 0) {
            return new ResponseEntity<Page<Destinatario>>(des, HttpStatus.OK);
        }

        resp.put("mensaje", "Sin datos que mostrar");
        return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/des-obtener/{dni}")
    public ResponseEntity<?> getDestinatario(@PathVariable(value = "dni") String dni) {

        Map<String, String> resp = new HashMap<>();
        Destinatario des = null;

        try {
            des = direccionEnvioService.getByDni(dni);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (des == null) {
            resp.put("mensaje", "Sin datos que mostrar");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Destinatario>(des, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/des-editar")
    public ResponseEntity<?> updateDestinatario(@RequestBody Destinatario destinatario) {

        Map<String, String> resp = new HashMap<>();
        Destinatario des = null;

        try {
            des = direccionEnvioService.getByIddestinatario(destinatario.getIddestinatario());
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (des == null) {
            resp.put("mensaje", "No se encontraron conincidencias");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }

        des.setEstado(destinatario.getEstado());

        try {
            direccionEnvioService.updateDES(des);
        } catch (Exception e) {
            resp.put("mensaje", "Error: no fue posible actualizar estado");
            resp.put("error", e.getMessage());
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        resp.put("mensaje", "Destinatario suspendido con éxito");
        return new ResponseEntity<Map<String, String>>(resp, HttpStatus.OK);
    }

}
