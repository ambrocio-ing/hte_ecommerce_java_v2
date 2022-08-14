package com.hteecommerce.hteapp.util;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.hteecommerce.hteapp.entity.Persona;
import com.hteecommerce.hteapp.entity.Personal;
//import com.hteecommerce.hteapp.entity.Variedad;
import com.hteecommerce.hteapp.enumm.NombreRole;
import com.hteecommerce.hteapp.security.entity.Role;
import com.hteecommerce.hteapp.security.entity.Usuario;
import com.hteecommerce.hteapp.security.service.IRoleService;
import com.hteecommerce.hteapp.service.IPersonalService;
//import com.hteecommerce.hteapp.service.IVariedadService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class Insercicion implements CommandLineRunner {

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IPersonalService personalService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /* @Autowired
    private IVariedadService variedadService; */

    @Override
    public void run(String... args) throws Exception {
        
        if(!roleService.isExistsNombreRole(NombreRole.ROLE_ADMIN) && 
            !roleService.isExistsNombreRole(NombreRole.ROLE_USER)){

            Set<Role> roles = new HashSet<>();
            roles.add(new Role(NombreRole.ROLE_ADMIN));
            roles.add(new Role(NombreRole.ROLE_USER));

            Usuario usuario = new Usuario("admin", "ameg100594@gmail.com", "Activo", passwordEncoder.encode("adminadmin"), roles);
            Persona persona = new Persona("12345678", "Admin", "Admin", "M", LocalDate.now(), "965874582");
            Personal personal = new Personal("Activo", LocalDate.now(), persona, usuario);
            personalService.savePe(personal);
        }

        if(!roleService.isExistsNombreRole(NombreRole.ROLE_CLIENT)){
            Role role = new Role(NombreRole.ROLE_CLIENT);
            roleService.saveR(role);
        }

        if(!roleService.isExistsNombreRole(NombreRole.ROLE_SUPPLIER)){
            Role role = new Role(NombreRole.ROLE_SUPPLIER);
            roleService.saveR(role);
        }  

        /* if(!variedadService.isExistsByNombre("Default")){
            Variedad variedad = new Variedad("Default", 0);
            variedadService.saveVA(variedad);
        }   */   
        
    } 
    
}
