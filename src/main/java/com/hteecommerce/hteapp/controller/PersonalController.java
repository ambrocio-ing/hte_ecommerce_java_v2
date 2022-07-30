package com.hteecommerce.hteapp.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.hteecommerce.hteapp.entity.Persona;
import com.hteecommerce.hteapp.entity.Personal;
import com.hteecommerce.hteapp.enumm.NombreRole;
import com.hteecommerce.hteapp.file_manager.IFileService;
import com.hteecommerce.hteapp.mapper.Mapper;
import com.hteecommerce.hteapp.model.MPersonal;
import com.hteecommerce.hteapp.security.entity.Role;
import com.hteecommerce.hteapp.security.entity.Usuario;
import com.hteecommerce.hteapp.security.service.IRoleService;
import com.hteecommerce.hteapp.security.service.IUsuarioService;
import com.hteecommerce.hteapp.service.IPersonaService;
import com.hteecommerce.hteapp.service.IPersonalService;
import com.hteecommerce.hteapp.util.RutaActual;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("/personal/per")
public class PersonalController {

    @Autowired
    private IPersonalService personalService;

    @Autowired
    private IPersonaService personaService;

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IFileService fileService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/lista")
    public ResponseEntity<?> perList(){

        Map<String, String> resp = new HashMap<>();
        List<Personal> personals = null;

        try {
            personals = personalService.getAll();
        } catch (DataAccessException e) {            
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity<Map<String,String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(personals != null && personals.size() != 0){
            List<MPersonal> mPersonals = personals.stream()
                .map(per -> new MPersonal(per))
                .collect(Collectors.toList());

            return new ResponseEntity<List<MPersonal>>(mPersonals, HttpStatus.OK);
        }
        else{
            resp.put("mensaje", "Sin datos que mostrar");
            return new ResponseEntity<Map<String,String>>(resp, HttpStatus.NOT_FOUND);
        }

    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/crear")
    public ResponseEntity<?> createPersonal(@Valid @RequestBody MPersonal personal, BindingResult result) {

        Map<String, Object> resp = new HashMap<>();

        if (result.hasErrors()) {

            List<String> errors = result.getFieldErrors().stream()
                    .map(err -> {
                        return "El campo " + err.getField() + " " + err.getDefaultMessage();
                    })
                    .collect(Collectors.toList());

            resp.put("mensaje", errors);
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.BAD_REQUEST);
        }

        if (personaService.isExistsDni(personal.getPersona().getDni())) {
            resp.put("mensaje", "El DNI ya existe en el sistema");
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.NOT_FOUND);
        }

        if (personaService.isExistsTelefono(personal.getPersona().getTelefono())) {
            resp.put("mensaje", "El telefono ya existe en el sistema");
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.NOT_FOUND);
        }

        List<String> errors = Mapper.isValidPersona(personal.getPersona());
        if (errors != null && errors.size() != 0) {
            resp.put("mensaje", errors);
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.BAD_REQUEST);
        }

        if (usuarioService.isExistsUsername(personal.getUsuario().getUsername())) {
            resp.put("mensaje", "El nombre de usuario ya existe en el sistema");
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.NOT_FOUND);
        }

        if (usuarioService.isExistsEmail(personal.getUsuario().getEmail())) {
            resp.put("mensaje", "El correo ya existe en el sistema");
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.NOT_FOUND);
        }

        List<String> uerrors = Mapper.isValidUsuario(personal.getUsuario());
        if (uerrors != null && uerrors.size() != 0) {
            resp.put("mensaje", uerrors);
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.BAD_REQUEST);
        }

        Set<Role> roles = new HashSet<>();
        roles.add(roleService.getByNombreRole(NombreRole.ROLE_USER).get());
        if(personal.getUsuario().getRoles().contains("admin")){
            roles.add(roleService.getByNombreRole(NombreRole.ROLE_ADMIN).get());
        }        

        String password = passwordEncoder.encode(personal.getUsuario().getPassword());
        Usuario usuario = new Usuario(personal.getUsuario().getUsername(), personal.getUsuario().getEmail(),
                personal.getUsuario().getEstado(), password, roles);

        Personal per = new Personal(personal.getEstado(), personal.getFecha(), personal.getPersona(), usuario);

        try {
            personalService.savePe(per);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error al guardar datos del personal, por favor revice que no haya errores en los campos solicitados");
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        resp.put("mensaje", "Datos del personal guardado con éxito");
        resp.put("documento", per.getPersona().getDni());
        return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/foto")
    public ResponseEntity<?> guardarImagenPerfil(@RequestParam(name = "documento") String documento,
        @RequestParam(name = "imagen") MultipartFile imagen){

        Map<String,String> resp = new HashMap<>();
        String ruta = RutaActual.RUTA_PERSONA;
        Persona persona = null;

        if(imagen.isEmpty()){
            resp.put("mensaje", "La imagen no es válida");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }
        
        try {
            persona = personaService.getByDni(documento);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(persona == null){
            resp.put("mensaje", "No fue posible cotejar sus datos");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }

        String nombreImagen = null;

        try {
            nombreImagen = fileService.copiar(ruta, imagen);
        } catch (IOException e) {
            resp.put("mensaje", "No fue posible guardar su foto de perfil");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }

        if(nombreImagen == null){
            resp.put("mensaje", "No fue posible guardar su foto de perfil");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }

        fileService.eliminar(ruta, persona.getFotoPerfil());

        try {
            persona.setFotoPerfil(nombreImagen);
            personaService.saveP(persona);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error al guardar imagen");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        resp.put("mensaje", "Foto de perfil guardado con éxito");
        resp.put("nombre_foto", nombreImagen);
        return new ResponseEntity<Map<String, String>>(resp, HttpStatus.CREATED);
    }    

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/obtener/{id}")
    public ResponseEntity<?> getPersonal(@PathVariable(value = "id") Integer id){

        Map<String,String> resp = new HashMap<>();        
        Personal personal = null;

        try {
           personal = personalService.getByIdpersonal(id);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(personal == null){
            resp.put("mensaje", "No se encontraron coincidencias en el sistema");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }

        MPersonal mper = new MPersonal(personal);
        return new ResponseEntity<MPersonal>(mper, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/editar")
    public ResponseEntity<?> editPersonal(@RequestBody MPersonal personal) {

        Map<String, Object> resp = new HashMap<>();
        Personal per = null;        
        
        try {
            per = personalService.getByIdpersonal(personal.getIdpersonal());
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(per == null){
            resp.put("mensaje", "No se encontraron coincidencias en el sistema");
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.NOT_FOUND);
        }

        per.setEstado(personal.getEstado());
        per.setPersona(personal.getPersona());
        per.getUsuario().setUsername(personal.getUsuario().getUsername());
        per.getUsuario().setEmail(personal.getUsuario().getEmail());
        per.getUsuario().setEstado(personal.getEstado());
              
        if(!personal.getUsuario().getPassword().equals("noeditar")){
            per.getUsuario().setPassword(passwordEncoder.encode(personal.getUsuario().getPassword()));
        }            
              
        if(per.getUsuario().getRoles().size() == 1 && personal.getUsuario().getRoles().contains("admin")){
            per.getUsuario().getRoles().add(roleService.getByNombreRole(NombreRole.ROLE_ADMIN).get());            
        }

        if(per.getUsuario().getRoles().size() == 2 && !personal.getUsuario().getRoles().contains("admin")){
            Set<Role> roles = new HashSet<>();
            roles = per.getUsuario().getRoles().stream()
                .filter(role -> !role.getNombreRole().equals(NombreRole.ROLE_ADMIN))
                .collect(Collectors.toSet());
            per.getUsuario().setRoles(roles);
        }

        try {
            personalService.savePe(per);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error al guardar datos del personal, por favor revice que no haya errores en los campos solicitados");
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        resp.put("mensaje", "Datos del personal actualizados con éxito");        
        return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> personalEliminar(@PathVariable(value = "id") Integer id){

        Map<String,String> resp = new HashMap<>();
        String ruta = RutaActual.RUTA_PERSONA;
        Personal personal = null;

        try {
           personal = personalService.getByIdpersonal(id);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(personal == null){
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }

        fileService.eliminar(ruta, personal.getPersona().getFotoPerfil());

        try {
            personalService.deletePe(personal.getIdpersonal());
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error al eliminar registro del personal");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        resp.put("mensaje", "Datos del personal eliminados con éxito");
        return new ResponseEntity<Map<String, String>>(resp, HttpStatus.OK);
    }    

}
