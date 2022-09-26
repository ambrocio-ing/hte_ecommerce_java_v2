package com.hteecommerce.hteapp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hteecommerce.hteapp.entity.DetalleIngreso;
import com.hteecommerce.hteapp.entity.Producto;
import com.hteecommerce.hteapp.mapper.Mapper;
import com.hteecommerce.hteapp.service.IIngresoService;
import com.hteecommerce.hteapp.service.IProductoService;

@RestController
@RequestMapping("/tarea/admin")
public class TareaAdministraitvaController {

    @Autowired
    private IProductoService productoService;

    @Autowired
    private IIngresoService ingresoService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/corregir")
    public ResponseEntity<?> corregirEstados() {

        Map<String, String> resp = new HashMap<>();
        List<Producto> productos = null;
        List<DetalleIngreso> dis = null;

        try {
            productos = productoService.getAllTemporal();
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        int contador = 0;

        for (Producto pro : productos) {

            dis = ingresoService.getDetalleIngresoByIdproducto(pro.getIdproducto());

            if (dis != null && dis.size() != 0) {

                int num = Mapper.validandoIngreso(dis);

                switch (num) {
                    case 1:
                        pro.setIngresadoHuacho(true);
                        break;
                    case 2:
                        pro.setIngresadoBarranca(true);
                        break;
                    case 3:
                        pro.setIngresadoHuacho(true);
                        pro.setIngresadoBarranca(true);
                        break;
                }

                try {
                    productoService.updatePto(pro);
                } catch (Exception e) {
                    resp.put("mensaje", "Error al actualizar estados");
                    resp.put("error", e.getMessage());
                    return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
                }

                contador++;
            }

        }

        resp.put("mensaje", "Operación completado con éxito");
        resp.put("cantidad", contador + " productos actualizados con exito");
        return new ResponseEntity<Map<String, String>>(resp, HttpStatus.OK);
    }

}
