package com.hteecommerce.hteapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hteecommerce.hteapp.entity.Delivery;

@Repository
public interface IDeliveryRepository extends JpaRepository<Delivery, Integer> {
        
    public boolean existsByEmpresa(String empresa);

    @Query("from Delivery de where de.empresa = ?1 and de.iddelivery != ?2")
    public Optional<Delivery> getByEmpresaAndIddelivery(String empresa, Integer iddelivery);
}
