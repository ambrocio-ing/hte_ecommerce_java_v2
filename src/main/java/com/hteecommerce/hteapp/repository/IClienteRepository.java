package com.hteecommerce.hteapp.repository;

import java.util.List;
import java.util.Optional;

import com.hteecommerce.hteapp.entity.Cliente;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IClienteRepository extends JpaRepository<Cliente,Integer> {
    
    @Query("from Cliente cli join cli.usuario usu where usu.username = ?1")
    public Optional<Cliente> findByUsername(String username);

    @Query("from Cliente cli order by cli.idcliente desc")
    public Page<Cliente> listClientes(Pageable pageable);

    @Query("from Cliente cli join cli.persona per where upper(concat(per.nombre,trim(per.apellidos))) like upper(concat('%',?1,'%')) or per.dni like concat('%',?2,'%')")
    public List<Cliente> listByNombreOrDni(String nombre, String dni);

}
