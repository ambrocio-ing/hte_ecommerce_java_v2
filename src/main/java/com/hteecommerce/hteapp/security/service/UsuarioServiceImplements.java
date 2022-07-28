package com.hteecommerce.hteapp.security.service;

import java.util.List;

import com.hteecommerce.hteapp.security.entity.Usuario;
import com.hteecommerce.hteapp.security.repository.IUsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioServiceImplements implements IUsuarioService {

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> getAll() {
        
        return usuarioRepository.findAll();
    }

    @Override
    @Transactional
    public void saveU(Usuario usuario) {
        
        usuarioRepository.save(usuario);
    }

    @Override
    @Transactional
    public void deleteU(int idusuario) {
        
        usuarioRepository.deleteById(idusuario);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isExistsUsername(String username) {
        
        return usuarioRepository.existsByUsername(username);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isExistsEmail(String email) {
        
        return usuarioRepository.existsByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public Usuario getByUsernameOrEmail(String usernameOrEmail) {
        
        return usuarioRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail).orElse(null);
    }

    @Override
    public Usuario getByIdusuario(Integer idusuario) {
        
        return usuarioRepository.findById(idusuario).orElse(null);
    }

    @Override
    public Usuario getByUsername(String username) {
        
        return usuarioRepository.findByUsername(username).orElse(null);
    }

    @Override
    public Usuario getByTokenPassword(String tokenPassword) {
       
        return usuarioRepository.findByTokenPassword(tokenPassword).orElse(null);
    }    
    
}
