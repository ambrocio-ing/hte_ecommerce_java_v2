package com.hteecommerce.hteapp.security.model;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.hteecommerce.hteapp.security.entity.Usuario;

public class UsuarioPrincipal implements UserDetails {

    private String username;
    private String email;
    private String password;
    private Collection<? extends GrantedAuthority> autorities;

    public UsuarioPrincipal(){

    }   

    public UsuarioPrincipal(String username, String email, String password,
            Collection<? extends GrantedAuthority> autorities) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.autorities = autorities;
    }

    public static UsuarioPrincipal build(Usuario usuario) {

        List<GrantedAuthority> autorities = usuario.getRoles().stream()
            .map(role -> new SimpleGrantedAuthority(role.getNombreRole().name()))
            .collect(Collectors.toList());

        return new UsuarioPrincipal(usuario.getUsername(), usuario.getEmail(), 
            usuario.getPassword(), autorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        
        return autorities;
    }

    @Override
    public String getPassword() {
        
        return password;
    }

    @Override
    public String getUsername() {
        
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        
        return true;
    }

    @Override
    public boolean isEnabled() {
        
        return true;
    }

    public String getEmail() {
        return email;
    }    

}
