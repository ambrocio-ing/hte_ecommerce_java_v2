package com.hteecommerce.hteapp.send_mail.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hteecommerce.hteapp.security.entity.Usuario;
import com.hteecommerce.hteapp.security.service.IUsuarioService;
import com.hteecommerce.hteapp.send_mail.dto.ChangePasswordDto;
import com.hteecommerce.hteapp.send_mail.dto.EmailValuesDto;
import com.hteecommerce.hteapp.send_mail.service.EmailService;

@RestController
@RequestMapping("/ch-pass")
public class EmailController {
    
    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private EmailService emailService;

    @Value("${spring.mail.username}")
    private String mailFrom;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/send-email")
    public ResponseEntity<?> enviarEmail(@RequestBody EmailValuesDto dto){

        Map<String,String> resp = new HashMap<>();
        Usuario usuario = null;

        try {
            usuario = usuarioService.getByUsernameOrEmail(dto.getMailTo());
        } catch (Exception e) {
            resp.put("mensaje", "Error de conexión, por favor intentelo mas tarde");
            return new ResponseEntity<Map<String,String>>(resp, HttpStatus.NOT_FOUND);
        }

        if(usuario == null){
            resp.put("mensaje", "Nombre de usuario o correo que ingresó no es válido");
            return new ResponseEntity<Map<String,String>>(resp, HttpStatus.NOT_FOUND);
        }

        dto.setMailFrom(mailFrom);
        dto.setMailTo(usuario.getEmail());
        dto.setSubject("Cambio de contraseña");
        dto.setUsername(usuario.getUsername());

        UUID uuid = UUID.randomUUID();
        String tokenPassword = uuid.toString();
        usuario.setTokenPassword(tokenPassword);

        try {
            usuarioService.saveU(usuario);
        } catch (Exception e) {
            resp.put("mensaje", "Error con el sistema por favor intentelo mas tarde");
            return new ResponseEntity<Map<String,String>>(resp, HttpStatus.NOT_FOUND);
        }

        emailService.mailSend(dto);
        resp.put("mensaje", "Correo de recuperación enviado con éxito, por favor revise su correo");
        return new ResponseEntity<Map<String,String>>(resp, HttpStatus.OK);
    }

    @PostMapping("/change-pass")
    public ResponseEntity<?> cambiarContra(@Valid @RequestBody ChangePasswordDto dto, BindingResult result){

        Map<String,String> resp = new HashMap<>();
        Usuario usuario = null;

        if(result.hasErrors()){
            resp.put("mensaje", "Los campos ingresados no son válidos");
            return new ResponseEntity<Map<String,String>>(resp, HttpStatus.NOT_FOUND);
        }

        if(!dto.getPassword().equals(dto.getConfirmPassword())){
            resp.put("mensaje", "La contraseña no coincide con su confirmación");
            return new ResponseEntity<Map<String,String>>(resp, HttpStatus.NOT_FOUND);
        }

        try {
            usuario = usuarioService.getByTokenPassword(dto.getTokenPassword());
        } catch (Exception e) {
            resp.put("mensaje", "Error de conexión, por favor intentelo mas tarde");
            return new ResponseEntity<Map<String,String>>(resp, HttpStatus.NOT_FOUND);
        }

        if(usuario == null){
            resp.put("mensaje", "No se encontro usuario");
            return new ResponseEntity<Map<String,String>>(resp, HttpStatus.NOT_FOUND);
        }

        String password = passwordEncoder.encode(dto.getPassword());
        usuario.setPassword(password);
        usuario.setTokenPassword(null);

        try {
            usuarioService.saveU(usuario);
        } catch (Exception e) {
            resp.put("mensaje", "Error con el sistema por favor intentelo mas tarde");
            return new ResponseEntity<Map<String,String>>(resp, HttpStatus.NOT_FOUND);
        }

        resp.put("mensaje", "Contraseña cambiado con éxito");
        return new ResponseEntity<Map<String,String>>(resp, HttpStatus.OK);
    }
}
