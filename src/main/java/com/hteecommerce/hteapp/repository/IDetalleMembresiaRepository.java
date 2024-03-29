package com.hteecommerce.hteapp.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.hteecommerce.hteapp.entity.DetalleMembresia;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IDetalleMembresiaRepository extends JpaRepository<DetalleMembresia,Integer> {

    @Query("select dm from DetalleMembresia dm join dm.cliente cli where cli.idcliente = ?1 and ?2 between dm.fechaInicio and dm.fechaFin and dm.estado = 'Vigente'")
    public Optional<DetalleMembresia> findByIdcliente(Integer idcliente, LocalDate fecha);
    
    public List<DetalleMembresia> findByEstado(String estado);

    @Query("select dm from DetalleMembresia dm join dm.cliente cli join cli.persona per where per.dni like ?1% or upper(replace(per.nombre, ' ', '')) like concat(upper(?1), '%')")
    public List<DetalleMembresia> listByCliente(String dniOrNombre);
}
