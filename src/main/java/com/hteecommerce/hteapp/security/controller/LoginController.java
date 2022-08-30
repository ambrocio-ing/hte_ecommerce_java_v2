package com.hteecommerce.hteapp.security.controller;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hteecommerce.hteapp.entity.Cliente;
import com.hteecommerce.hteapp.entity.Personal;
import com.hteecommerce.hteapp.entity.Proveedor;
import com.hteecommerce.hteapp.security.jwt.JwtProvider;
import com.hteecommerce.hteapp.security.model.JwtDto;
import com.hteecommerce.hteapp.security.model.UsuarioLogin;
import com.hteecommerce.hteapp.service.IClienteService;
import com.hteecommerce.hteapp.service.IPersonalService;
import com.hteecommerce.hteapp.service.IProveedorService;

@RestController
@RequestMapping("/auth")
public class LoginController {
    
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtProvider jwtProvider;   

    @Autowired
    private IClienteService clienteService;

    @Autowired
    private IProveedorService proveedorService;

    @Autowired
    private IPersonalService personalService;

    @PostMapping("/cli-pro/login")
    public ResponseEntity<?> loginCliPro(@Valid @RequestBody UsuarioLogin usuarioLogin, BindingResult result) {

        Map<String, Object> resp = new HashMap<>();
        JwtDto jwtDto = null;      
        String token = null;
        boolean estado = false;
        UserDetails userDetails = null;

        if (result.hasErrors()) {
            resp.put("mensaje", "Credenciales incompletos");
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.NOT_FOUND);
        }               
        
        try {

            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(usuarioLogin.getUsernameOrEmail(), usuarioLogin.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            token = jwtProvider.generateToken(authentication);
            userDetails = (UserDetails) authentication.getPrincipal();           

        } catch (Exception e) {
            resp.put("mensaje", "Es posible que usted no se encuentre registrado o ingresó datos incorrectos");
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.NOT_FOUND);
        }         

        if(userDetails != null){

            Cliente cliente = clienteService.getByUsername(userDetails.getUsername());
            if(cliente != null){
                switch(cliente.getEstado()){
                    case "Activo":
                        estado = true;
                        break;
                    case "Suspendido":
                        estado = false;                        
                }

                boolean ispro = false;
                if(cliente.getClienteProveedor() != null){
                    ispro = true;
                }

                jwtDto = new JwtDto(cliente.getIdcliente(),
                    cliente.getPersona().getDni(), 
                    cliente.getPersona().getNombre()+" "+cliente.getPersona().getApellidos(), 
                    cliente.getPersona().getFotoPerfil(), cliente.getUsuario().getEmail(), 
                    cliente.getSucursal(), ispro);
            }
            else{
                Proveedor proveedor = proveedorService.getByUsername(userDetails.getUsername());
                if(proveedor != null){

                    switch(proveedor.getEstado()){
                        case "Activo":
                            estado = true;
                            break;
                        case "Suspendido":
                            estado = false;                        
                    }

                    jwtDto = new JwtDto(proveedor.getIdproveedor(), 
                        proveedor.getRuc(), proveedor.getRazonSocial(), 
                        proveedor.getFotoPerfil(), proveedor.getUsuario().getEmail(), 
                        proveedor.getSucursal(), false);
                }
            }
            
        }

        if(jwtDto != null && token != null && estado == true){
            resp.put("jwtDto", jwtDto);
            resp.put("token", token);
            resp.put("estado", estado);
            return new ResponseEntity<Map<String,Object>>(resp, HttpStatus.OK);
        }

        if(jwtDto != null && token != null && estado == false){
            
            resp.put("mensaje", "Este usuario se encuentra suspendido, para conocer los motivos comuniquese con la empresa");
            resp.put("estado", estado);
            return new ResponseEntity<Map<String,Object>>(resp, HttpStatus.OK);
        }

        resp.put("mensaje", "Credenciales incorrectos");
        return new ResponseEntity<Map<String,Object>>(resp, HttpStatus.NOT_FOUND);
    }

    @PostMapping("/per/login")
    public ResponseEntity<?> loginPersonal(@Valid @RequestBody UsuarioLogin usuarioLogin, BindingResult result) {

        Map<String, Object> resp = new HashMap<>();
        JwtDto jwtDto = null;      
        String token = null;
        boolean estado = false;
        UserDetails userDetails = null;

        if (result.hasErrors()) {
            resp.put("mensaje", "Credenciales incompletos");
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.NOT_FOUND);
        }               
        
        try {

            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(usuarioLogin.getUsernameOrEmail(), usuarioLogin.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            token = jwtProvider.generateToken(authentication);
            userDetails = (UserDetails) authentication.getPrincipal();           

        } catch (Exception e) {
            resp.put("mensaje", "Es posible que usted no se encuentre registrado o ingresó datos incorrectos");
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.NOT_FOUND);
        }

        if(userDetails != null){
            Personal personal = personalService.getByUsername(userDetails.getUsername());
            if(personal != null){
                switch(personal.getEstado()){
                    case "Activo":
                        estado = true;
                        break;
                    case "Suspendido":
                        estado = false;                        
                }                

                jwtDto = new JwtDto(personal.getIdpersonal(),
                    personal.getPersona().getDni(), 
                    personal.getPersona().getNombre()+" "+personal.getPersona().getApellidos(), 
                    personal.getPersona().getFotoPerfil(), personal.getUsuario().getEmail(), 
                    null, false);
            }          
            
        }

        if(jwtDto != null && token != null && estado == true){
            resp.put("jwtDto", jwtDto);
            resp.put("token", token);
            resp.put("estado", estado);
            return new ResponseEntity<Map<String,Object>>(resp, HttpStatus.OK);
        }

        if(jwtDto != null && token != null && estado == false){
            
            resp.put("mensaje", "Este usuario se encuentra suspendido, para conocer los motivos comuniquese con la empresa");
            resp.put("estado", estado);
            return new ResponseEntity<Map<String,Object>>(resp, HttpStatus.OK);
        }

        resp.put("mensaje", "Credenciales incorrectos");
        return new ResponseEntity<Map<String,Object>>(resp, HttpStatus.NOT_FOUND);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody String token) {

        Map<String, String> resp = new HashMap<>();
        String new_token = null;

        try {
            new_token = jwtProvider.refreshToken(token);
        } catch (ParseException e) {
            resp.put("mensaje", "Error, por favor vuelva a iniciar sesión");
            return new ResponseEntity<Map<String,String>>(resp, HttpStatus.FORBIDDEN);
        }

        if(new_token == null){
            resp.put("mensaje", "Error, por favor vuelva a iniciar sesión");
            return new ResponseEntity<Map<String,String>>(resp, HttpStatus.FORBIDDEN);
        }

        resp.put("token", new_token);
        return new ResponseEntity<Map<String,String>>(resp, HttpStatus.OK);
    }
}
