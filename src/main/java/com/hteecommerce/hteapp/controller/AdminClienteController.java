package com.hteecommerce.hteapp.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hteecommerce.hteapp.entity.Cliente;
import com.hteecommerce.hteapp.entity.Persona;
import com.hteecommerce.hteapp.file_manager.IFileService;
import com.hteecommerce.hteapp.mapper.Mapper;
import com.hteecommerce.hteapp.model.MCliente;
import com.hteecommerce.hteapp.service.IClienteProveedorService;
import com.hteecommerce.hteapp.service.IClienteService;
import com.hteecommerce.hteapp.service.IPersonaService;
import com.hteecommerce.hteapp.util.RutaActual;

//@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/cli/admin")
public class AdminClienteController {

    @Autowired
    private IClienteService clienteService;

    @Autowired
    private IFileService fileService;

    @Autowired
    private IPersonaService personaService;

    @Autowired
    private IClienteProveedorService clienteProveedorService;

    @PreAuthorize("hasRole('CLIENT')")
    @GetMapping("/obtener/{id}")
    public ResponseEntity<?> getCliente(@PathVariable(value = "id") Integer idcliente){

        Map<String,String> resp = new HashMap<>();
        Cliente cliente = null;        

        try {
            cliente = clienteService.getByIdcliente(idcliente);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity<Map<String,String>>(resp, HttpStatus.OK);
        }

        if(cliente == null){
            resp.put("mensaje", "No se encontraron coincidencias que mostrar");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }               
        
        MCliente mcli = new MCliente(cliente);
        mcli.getUsuario().setRoles(new ArrayList<>());
        return new ResponseEntity<MCliente>(mcli, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('CLIENT')")
    @PostMapping("/editar")
    public ResponseEntity<?> updateCliente(@RequestBody MCliente cliente) {

        Map<String, Object> resp = new HashMap<>();
        Cliente cli = null;
        
        // validando persona      
        if (personaService.isExistsTelefonoAndDni(cliente.getPersona().getTelefono(), cliente.getPersona().getDni())) {
            resp.put("mensaje", "El telefono ya existe en el sistema");
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.NOT_FOUND);
        }   
      
        if (Mapper.isPresentClienteProveedor(cliente.getClienteProveedor())) {

            if(clienteProveedorService.isExistsRuc(cliente.getClienteProveedor().getRuc())){
                resp.put("mensaje", "El ruc que ingresó ya existe en el sistema");
                return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.NOT_FOUND);
            }

            if(clienteProveedorService.isExistsRazonSocial(cliente.getClienteProveedor().getRazonSocial())){
                resp.put("mensaje", "La razón social que ingresó ya existe en el sistema");
                return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.NOT_FOUND);
            }

        }      

        try {
            cli = clienteService.getByIdcliente(cliente.getIdcliente());
        } catch (DataAccessException e) {
            resp.put("mensaje", "Ocurrio error al guardar sus datos, por favor intentelo nuevamente");
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(cli == null){
            resp.put("mensaje", "Ocurrio error al cotejar sus datos en el sistema");
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        cli.getPersona().setTelefono(cliente.getPersona().getTelefono());
        cli.getPersona().setDireccion(cliente.getPersona().getDireccion());

        if (Mapper.isPresentClienteProveedor(cliente.getClienteProveedor())) {
            cli.setClienteProveedor(cliente.getClienteProveedor());
        } else {
            cli.setClienteProveedor(null);
        }

        if (Mapper.isPresentClienteCaracteristica(cliente.getClienteCaracteristica())) {
            cli.setClienteCaracteristica(cliente.getClienteCaracteristica());
        } else {
            cli.setClienteCaracteristica(null);
        }
        
        try {
            clienteService.saveCli(cli);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Ocurrio error al guardar sus datos, por favor intentelo nuevamente");
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        resp.put("mensaje", "Felicidades,sus datos fueron actualizados con éxito");
        resp.put("dni", cli.getPersona().getDni());
        resp.put("id", cli.getIdcliente());
        return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('CLIENT')")
    @PostMapping("/imagen")
    public ResponseEntity<?> saveImage(@RequestParam(name = "dni") String dni,
            @RequestParam(name = "imagen") MultipartFile archivo) {

        Map<String,String> resp = new HashMap<>();
        Persona persona = null;
        String rutaActual = RutaActual.RUTA_PERSONA;

        if(archivo.isEmpty()){
            resp.put("mensaje", "La imagen no es válido");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }

        try {
            persona = personaService.getByDni(dni);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity<Map<String,String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(persona == null){
            resp.put("mensaje", "La imagen no se pudo guardar");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }

        String nombreImagen = null;

        try {
            nombreImagen = fileService.copiar(rutaActual, archivo);
        } catch (IOException e) {
            resp.put("mensaje", "La imagen no se pudo guardar");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }

        if(nombreImagen == null){
            resp.put("mensaje", "La imagen no se pudo guardar");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }

        fileService.eliminar(rutaActual, persona.getFotoPerfil());

        try {
            persona.setFotoPerfil(nombreImagen);
            personaService.saveP(persona);
        } catch (DataAccessException e) {
            resp.put("mensaje", "No es posible guardar la imagen, por favor asegurece que la imagen es válido e intentelo mas denuevo");
            return new ResponseEntity<Map<String,String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        resp.put("mensaje", "Felicidades,sus datos fueron actualizados con éxito");
        return new ResponseEntity<Map<String, String>>(resp, HttpStatus.OK);
    }
    
}
