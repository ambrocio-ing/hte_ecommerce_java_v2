package com.hteecommerce.hteapp.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hteecommerce.hteapp.security.entity.Usuario;
import com.hteecommerce.hteapp.security.model.UsuarioPrincipal;

@Service
public class UserDetailsServiceImplements implements UserDetailsService {

    @Autowired
    private IUsuarioService usuarioService;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        
        Usuario usuario = usuarioService.getByUsernameOrEmail(usernameOrEmail);

        return UsuarioPrincipal.build(usuario);
    }
    
}
