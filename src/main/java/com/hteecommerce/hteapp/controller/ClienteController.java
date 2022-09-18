package com.hteecommerce.hteapp.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.hteecommerce.hteapp.entity.Cliente;
import com.hteecommerce.hteapp.entity.Persona;
import com.hteecommerce.hteapp.enumm.NombreRole;
import com.hteecommerce.hteapp.file_manager.IFileService;
import com.hteecommerce.hteapp.mapper.Mapper;
import com.hteecommerce.hteapp.model.MCliente;
import com.hteecommerce.hteapp.security.entity.Role;
import com.hteecommerce.hteapp.security.entity.Usuario;
import com.hteecommerce.hteapp.security.service.IRoleService;
import com.hteecommerce.hteapp.security.service.IUsuarioService;
import com.hteecommerce.hteapp.service.IClienteProveedorService;
import com.hteecommerce.hteapp.service.IClienteService;
import com.hteecommerce.hteapp.service.IPersonaService;
import com.hteecommerce.hteapp.util.RutaActual;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
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

@RestController
@RequestMapping("/cliente/cli")
public class ClienteController {

    @Autowired
    private IClienteService clienteService;

    @Autowired
    private IPersonaService personaService;

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IFileService fileService;

    @Autowired
    private IClienteProveedorService clienteProveedorService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/crear")
    public ResponseEntity<?> createCliente(@Valid @RequestBody MCliente cliente, BindingResult result) {

        Map<String, Object> resp = new HashMap<>();
        Cliente cli = new Cliente();

        if (result.hasErrors()) {

            List<String> errores = result.getFieldErrors().stream()
                    .map(err -> {
                        return "El campo: " + err.getField() + " " + err.getDefaultMessage();
                    })
                    .collect(Collectors.toList());

            resp.put("mensaje", errores);
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.BAD_REQUEST);

        }
        // validando persona
        if (personaService.isExistsDni(cliente.getPersona().getDni())) {
            resp.put("mensaje", "El DNI ya existe en el sistema");
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.NOT_FOUND);
        }

        if (personaService.isExistsTelefono(cliente.getPersona().getTelefono())) {
            resp.put("mensaje", "El telefono ya existe en el sistema");
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.NOT_FOUND);
        }

        List<String> errors = Mapper.isValidPersona(cliente.getPersona());
        if (errors != null && errors.size() != 0) {
            resp.put("mensaje", errors);
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.BAD_REQUEST);
        }

        if (usuarioService.isExistsUsername(cliente.getUsuario().getUsername())) {
            resp.put("mensaje", "El nombre de usuario ya existe en el sistema");
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.NOT_FOUND);
        }

        if (usuarioService.isExistsEmail(cliente.getUsuario().getEmail())) {
            resp.put("mensaje", "El correo ya existe en el sistema");
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.NOT_FOUND);
        }

        List<String> uerrors = Mapper.isValidUsuario(cliente.getUsuario());
        if (uerrors != null && uerrors.size() != 0) {
            resp.put("mensaje", uerrors);
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.BAD_REQUEST);
        }

        if (Mapper.isPresentClienteProveedor(cliente.getClienteProveedor())) {

            if(clienteProveedorService.isExistsRuc(cliente.getClienteProveedor().getRuc())){
                resp.put("mensaje", "El ruc que ingresó ya existe en el sistema");
                return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.NOT_FOUND);
            }

            List<String> errores = Mapper.isValidClienteProveedor(cliente.getClienteProveedor());
            if (errores != null && errores.size() != 0) {
                resp.put("mensaje", uerrors);
                return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.BAD_REQUEST);
            }
        }

        Set<Role> roles = new HashSet<>();
        roles.add(roleService.getByNombreRole(NombreRole.ROLE_CLIENT).orElse(null));        

        if (roles.size() != 1) {
            resp.put("mensaje", "Ocurrio un error al guardar sus datos, por favor intentelo denuevo");
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.NOT_FOUND);
        }
        
        String password = passwordEncoder.encode(cliente.getUsuario().getPassword());
        Usuario usuario = new Usuario(cliente.getUsuario().getUsername(), cliente.getUsuario().getEmail(),
                cliente.getUsuario().getEstado(), password, roles);

        cli.setEstado("Activo");        
        cli.setPuntos(0);
        cli.setSucursal(cliente.getSucursal());
        cli.setPersona(cliente.getPersona());
        cli.setUsuario(usuario);

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

        Cliente clien = null;
        
        try {
            clien = clienteService.saveCli(cli);
        } catch (Exception e) {
            resp.put("mensaje", "Ocurrio error al guardar sus datos, por favor intentelo nuevamente");
            resp.put("error", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        resp.put("mensaje", "Datos guardados con éxito");
        resp.put("dni", cli.getPersona().getDni());
        resp.put("id", clien.getIdcliente());
        return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.CREATED);
    }

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

        resp.put("mensaje", "Felicidades,sus datos fueron guardados con éxito, ya puede iniciar sesión");
        return new ResponseEntity<Map<String, String>>(resp, HttpStatus.OK);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> deleteCliente(@PathVariable(value = "id") Integer idcliente){

        Map<String,String> resp = new HashMap<>();
        Cliente cliente = null;
        String ruta = RutaActual.RUTA_PERSONA;

        try {
            cliente = clienteService.getByIdcliente(idcliente);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity<Map<String,String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(cliente == null){
            resp.put("mensaje", "No se encontraron coincidencias que eliminar");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }

        try {
            clienteService.deleteCli(cliente.getIdcliente());
        } catch (Exception e) {
            resp.put("mensaje", "Error al eliminar registro del sistema");
            resp.put("error", e.getMessage());
            return new ResponseEntity<Map<String,String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        fileService.eliminar(ruta, cliente.getPersona().getFotoPerfil());

        resp.put("mensaje", "Registro eliminado con éxito");
        return new ResponseEntity<Map<String, String>>(resp, HttpStatus.OK);
    }

    @GetMapping("/obtener/imagen/{imagen:.+}")
    public ResponseEntity<Resource> getImage(@PathVariable(value = "imagen") String nombreImagen){

        String ruta = RutaActual.RUTA_PERSONA;
        String archivoAhuxiliar = "fotologo.jpg";
        Resource resource = null;

        try {
            resource = fileService.cargar(nombreImagen, ruta, ruta, archivoAhuxiliar);
        } catch (MalformedURLException e) {            
            e.printStackTrace();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+resource.getFilename());

        return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);

    }

}
