package com.hteecommerce.hteapp.security.service;

import java.util.List;

import com.hteecommerce.hteapp.security.entity.Usuario;

public interface IUsuarioService {
    
    public List<Usuario> getAll();
    public void saveU(Usuario usuario);
    public void deleteU(int idusuario);
    public Usuario getByIdusuario(Integer idusuario);

    public boolean isExistsUsername(String username);
    public boolean isExistsEmail(String email);

    public Usuario getByUsernameOrEmail(String usernameOrEmail);
    public Usuario getByUsername(String username);   
    public Usuario getByTokenPassword(String tokenPassword);   

}
