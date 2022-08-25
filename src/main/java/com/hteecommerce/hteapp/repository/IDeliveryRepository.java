package com.hteecommerce.hteapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hteecommerce.hteapp.entity.Delivery;

@Repository
public interface IDeliveryRepository extends JpaRepository<Delivery, Integer> {
        
    public boolean existsByEmpresaAndSucursal (String empresa, String sucursal);

    @Query("from Delivery de where de.empresa = ?1 and de.sucursal = ?2 and de.iddelivery != ?3")
    public Optional<Delivery> getByEmpresaAndSucursalAndIddelivery(String empresa, String sucursal, Integer iddelivery);

    public List<Delivery> findBySucursal(String sucursal);
}
