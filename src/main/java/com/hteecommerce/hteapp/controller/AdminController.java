package com.hteecommerce.hteapp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.hteecommerce.hteapp.entity.Cliente;
import com.hteecommerce.hteapp.entity.Destinatario;
import com.hteecommerce.hteapp.entity.Proveedor;
import com.hteecommerce.hteapp.model.MCliente;
import com.hteecommerce.hteapp.model.MProveedor;
import com.hteecommerce.hteapp.service.IClienteService;
import com.hteecommerce.hteapp.service.IDireccionEnvioService;
import com.hteecommerce.hteapp.service.IProveedorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private IClienteService clienteService;

    @Autowired
    private IProveedorService proveedorService;

    @Autowired
    private IDireccionEnvioService direccionEnvioService;

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/cli/lista/{page}")
    public ResponseEntity<?> listCliente(@PathVariable(value = "page") int page){

        Map<String, String> resp = new HashMap<>();
        Page<Cliente> clientes = null;

        Pageable pageable = PageRequest.of(page, 5);

        try {
            clientes = clienteService.getAll(pageable);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (clientes != null && clientes.getContent().size() != 0) {
            Page<MCliente> mpage = clientes.map(cli -> new MCliente(cli));
            return new ResponseEntity<Page<MCliente>>(mpage, HttpStatus.OK);

        } else {
            resp.put("mensaje", "Sin datos que mostrar");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }

    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/cli/buscar/{texto}")
    public ResponseEntity<?> searchClientes(@PathVariable(value = "texto") String texto){

        Map<String, String> resp = new HashMap<>();
        List<Cliente> clientes = null;

        try {
            clientes = clienteService.getByNombreOrDni(texto);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(clientes != null && clientes.size() != 0){
            List<MCliente> mclientes = clientes.stream()
                .map(cli -> new MCliente(cli))
                .collect(Collectors.toList());
            return new ResponseEntity<List<MCliente>>(mclientes, HttpStatus.OK);
        }
        else{
            resp.put("mensaje", "Sin datos que mostrar");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/cli/sus")
    public ResponseEntity<?> clienteSuspender(@RequestBody MCliente cliente){

        Map<String, String> resp = new HashMap<>();
        Cliente cli = null;
        Destinatario destinatario = null;
        boolean isEditDes = true;

        try {
            cli = clienteService.getByIdcliente(cliente.getIdcliente());
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(cli == null){
            resp.put("mensaje", "No se encontro registro asociado al cliente");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }

        try {
            destinatario = direccionEnvioService.getByDni(cli.getPersona().getDni());
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }      
        
        cli.setEstado(cliente.getEstado());
        cli.getUsuario().setEstado(cliente.getEstado());

        try {
            clienteService.updateCli(cli);           

        } catch (Exception e) {
            resp.put("mensaje", "Error al actualizar estado del cliente");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        try {           

            if(destinatario != null){
                destinatario.setEstado(cliente.getEstado());
                direccionEnvioService.updateDES(destinatario);
            }

        } catch (Exception e) {
            isEditDes = false;
        }

        if(isEditDes == false){
            resp.put("mensaje", "Estado del cliente actualizado con éxito, se detectó un destinatario que no fue posible actualizar su estado, verifique la lista de destinatarios");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.OK);
        }

        resp.put("mensaje", "Estado del cliente actualizado con éxito");
        return new ResponseEntity<Map<String, String>>(resp, HttpStatus.OK);

    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/pro/lista/{page}")
    public ResponseEntity<?> proveedorLista(@PathVariable(value = "page") int page){

        Map<String, String> resp = new HashMap<>();
        Page<Proveedor> proveedores = null;

        Pageable pageable = PageRequest.of(page, 5);

        try {
            proveedores = proveedorService.getAll(pageable);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (proveedores != null && proveedores.getContent().size() != 0) {

            Page<MProveedor> mpage = proveedores.map(pro -> new MProveedor(pro));
            return new ResponseEntity<Page<MProveedor>>(mpage, HttpStatus.OK);

        } else {
            resp.put("mensaje", "Sin datos que mostrar");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/pro/buscar/{texto}")
    public ResponseEntity<?> searchProveedores(@PathVariable(value = "texto") String texto){

        Map<String, String> resp = new HashMap<>();
        List<Proveedor> lista = null;

        try {
            lista = proveedorService.getByRazonOrRuc(texto);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(lista != null && lista.size() != 0){
            List<MProveedor> mpro = lista.stream()
                .map(pro -> new MProveedor(pro))
                .collect(Collectors.toList());
            return new ResponseEntity<List<MProveedor>>(mpro, HttpStatus.OK);
        }
        else{
            resp.put("mensaje", "Sin datos que mostrar");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/pro/sus")
    public ResponseEntity<?> proveedorSuspender(@RequestBody MProveedor proveedor){

        Map<String, String> resp = new HashMap<>();
        Proveedor pro = null;

        try {
            pro = proveedorService.getByIdproveedor(proveedor.getIdproveedor());
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(pro == null){
            resp.put("mensaje", "No se encontro registro asociado al cliente");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }

        pro.setEstado(proveedor.getEstado());
        pro.getUsuario().setEstado(proveedor.getEstado());

        try {
            proveedorService.savePro(pro);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error al actualizar estado del proveedor");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        resp.put("mensaje", "Estado del proveedor se actualizó con éxito");
        return new ResponseEntity<Map<String, String>>(resp, HttpStatus.OK);
    }

}
