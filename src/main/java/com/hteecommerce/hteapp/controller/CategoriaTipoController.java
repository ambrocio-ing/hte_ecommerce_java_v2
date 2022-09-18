package com.hteecommerce.hteapp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.hteecommerce.hteapp.entity.Categoria;
import com.hteecommerce.hteapp.entity.Tipo;
import com.hteecommerce.hteapp.service.ICategoriaTipoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cat-tipo")
public class CategoriaTipoController {
    
    @Autowired
    private ICategoriaTipoService categoriaTipoService;

    //categoria
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/cat-lista")
    public ResponseEntity<?> listCategorias(){

        Map<String,String> resp = new HashMap<>();
        List<Categoria> lista = null;

        try {
            lista = categoriaTipoService.getCategorias();
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity<Map<String,String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(lista != null && lista.size() != 0){
            return new ResponseEntity<List<Categoria>>(lista, HttpStatus.OK);
        }
        else{
            resp.put("mensaje", "Sin datos que mostrar");
            return new ResponseEntity<Map<String,String>>(resp, HttpStatus.NOT_FOUND);
        }
        
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/cat-crear")
    public ResponseEntity<?> createCategoria(@Valid @RequestBody Categoria categoria, BindingResult result){

        Map<String,Object> resp = new HashMap<>();   
        
        if(result.hasErrors()){
            List<String> errors = result.getFieldErrors().stream()
                .map(err -> "El campo: "+err.getField()+" "+err.getDefaultMessage())
                .collect(Collectors.toList());

            resp.put("mensaje", errors);
            return new ResponseEntity<Map<String,Object>>(resp, HttpStatus.BAD_REQUEST);
        }

        if(categoriaTipoService.isExistCatByNombre(categoria.getNombre())){
            resp.put("mensaje", "El nombre de la categoria ya existe");
            return new ResponseEntity<Map<String,Object>>(resp, HttpStatus.BAD_REQUEST);
        }

        try {
            categoriaTipoService.saveCAT(categoria);
        } catch (Exception e) {
            resp.put("mensaje", "Error al guardar datos");
            resp.put("error", e.getMessage());
            return new ResponseEntity<Map<String,Object>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        resp.put("mensaje", "Datos guardados con éxito");
        return new ResponseEntity<Map<String,Object>>(resp, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/cat-obtener/{id}")
    public ResponseEntity<?> getCategoria(@PathVariable(value = "id") Integer id){

        Map<String,String> resp = new HashMap<>();
        Categoria categoria = null;

        try {
            categoria = categoriaTipoService.getCategoriaByIdcategoria(id);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity<Map<String,String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(categoria != null){
            return new ResponseEntity<Categoria>(categoria, HttpStatus.OK);
        }
        else{
            resp.put("mensaje", "Sin datos que mostrar");
            return new ResponseEntity<Map<String,String>>(resp, HttpStatus.NOT_FOUND);
        }
        
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/cat-editar")
    public ResponseEntity<?> updateCategoria(@Valid @RequestBody Categoria cat, BindingResult result){

        Map<String,Object> resp = new HashMap<>();   
        Categoria categoria = null;
        
        if(result.hasErrors()){
            List<String> errors = result.getFieldErrors().stream()
                .map(err -> "El campo: "+err.getField()+" "+err.getDefaultMessage())
                .collect(Collectors.toList());

            resp.put("mensaje", errors);
            return new ResponseEntity<Map<String,Object>>(resp, HttpStatus.BAD_REQUEST);
        }

        try {
            categoria = categoriaTipoService.getCategoriaByIdcategoria(cat.getIdcategoria());
        } catch (DataAccessException e) {
            resp.put("mensaje", "No se encontraron coincidencias");
            return new ResponseEntity<Map<String,Object>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(categoria == null){
            resp.put("mensaje", "No se encontraron coincidencias");
            return new ResponseEntity<Map<String,Object>>(resp, HttpStatus.NOT_FOUND);
        }

        categoria.setNombre(cat.getNombre());
        categoria.setDescripcion(cat.getDescripcion());

        try {
            categoriaTipoService.saveCAT(categoria);
        } catch (Exception e) {
            resp.put("mensaje", "Error al actualiar registro");
            resp.put("error", e.getMessage());
            return new ResponseEntity<Map<String,Object>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        resp.put("mensaje", "Datos actualizados con éxito");
        return new ResponseEntity<Map<String,Object>>(resp, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/cat-eliminar/{id}")
    public ResponseEntity<?> deleteCategoria(@PathVariable(value = "id") Integer id){

        Map<String,String> resp = new HashMap<>();
        Categoria categoria = null;

        try {
            categoria = categoriaTipoService.getCategoriaByIdcategoria(id);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity<Map<String,String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(categoria == null){
            resp.put("mensaje", "No se encontraron coincidencias");
            return new ResponseEntity<Map<String,String>>(resp, HttpStatus.NOT_FOUND);
        }
        
        try {            
            categoriaTipoService.deleteCAT(categoria.getIdcategoria());
        } catch (Exception e) {
            resp.put("mensaje", "Error al eliminar registro");
            resp.put("error", e.getMessage());
            return new ResponseEntity<Map<String,String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        resp.put("mensaje", "Registro eliminado con éxito");
        return new ResponseEntity<Map<String,String>>(resp, HttpStatus.OK);
    }

    //tipo
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/tip-lista")
    public ResponseEntity<?> listTipos(){

        Map<String,String> resp = new HashMap<>();
        List<Tipo> lista = null;

        try {
            lista = categoriaTipoService.getTipos();
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity<Map<String,String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(lista != null && lista.size() != 0){
            return new ResponseEntity<List<Tipo>>(lista, HttpStatus.OK);
        }
        else{
            resp.put("mensaje", "Sin datos que mostrar");
            return new ResponseEntity<Map<String,String>>(resp, HttpStatus.NOT_FOUND);
        }
        
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/tip/by-cat/{id}")
    public ResponseEntity<?> listTiposByCategoria(@PathVariable(value = "id") Integer idcategoria){

        Map<String,String> resp = new HashMap<>();
        List<Tipo> lista = null;

        try {
            lista = categoriaTipoService.getTiposByIdcategoria(idcategoria);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity<Map<String,String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(lista != null && lista.size() != 0){
            return new ResponseEntity<List<Tipo>>(lista, HttpStatus.OK);
        }
        else{
            resp.put("mensaje", "Sin datos que mostrar");
            return new ResponseEntity<Map<String,String>>(resp, HttpStatus.NOT_FOUND);
        }
        
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/tip-crear")
    public ResponseEntity<?> createTipo(@Valid @RequestBody Tipo tipo, BindingResult result){

        Map<String,Object> resp = new HashMap<>();   
        
        if(result.hasErrors()){
            List<String> errors = result.getFieldErrors().stream()
                .map(err -> "El campo: "+err.getField()+" "+err.getDefaultMessage())
                .collect(Collectors.toList());

            resp.put("mensaje", errors);
            return new ResponseEntity<Map<String,Object>>(resp, HttpStatus.BAD_REQUEST);
        }

        if(categoriaTipoService.isExistTiByNombre(tipo.getNombre())){
            resp.put("mensaje", "El nombre del tipo ya existe");
            return new ResponseEntity<Map<String,Object>>(resp, HttpStatus.BAD_REQUEST);
        }

        try {
            categoriaTipoService.saveTI(tipo);
        } catch (Exception e) {
            resp.put("mensaje", "Error al guardar datos");
            resp.put("error", e.getMessage());
            return new ResponseEntity<Map<String,Object>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        resp.put("mensaje", "Datos guardados con éxito");
        return new ResponseEntity<Map<String,Object>>(resp, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/tip-obtener/{id}")
    public ResponseEntity<?> getTipo(@PathVariable(value = "id") Integer id){

        Map<String,String> resp = new HashMap<>();
        Tipo tipo = null;

        try {
            tipo = categoriaTipoService.getTipoByIdtipo(id);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity<Map<String,String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(tipo != null){
            return new ResponseEntity<Tipo>(tipo, HttpStatus.OK);
        }
        else{
            resp.put("mensaje", "Sin datos que mostrar");
            return new ResponseEntity<Map<String,String>>(resp, HttpStatus.NOT_FOUND);
        }
        
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/tip-editar")
    public ResponseEntity<?> updateTipo(@Valid @RequestBody Tipo tipo, BindingResult result){

        Map<String,Object> resp = new HashMap<>();   
        Tipo tip = null;
        
        if(result.hasErrors()){
            List<String> errors = result.getFieldErrors().stream()
                .map(err -> "El campo: "+err.getField()+" "+err.getDefaultMessage())
                .collect(Collectors.toList());

            resp.put("mensaje", errors);
            return new ResponseEntity<Map<String,Object>>(resp, HttpStatus.BAD_REQUEST);
        }

        try {
            tip = categoriaTipoService.getTipoByIdtipo(tipo.getIdtipo());
        } catch (DataAccessException e) {
            resp.put("mensaje", "No se encontraron coincidencias");
            return new ResponseEntity<Map<String,Object>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(tip == null){
            resp.put("mensaje", "No se encontraron coincidencias");
            return new ResponseEntity<Map<String,Object>>(resp, HttpStatus.NOT_FOUND);
        }

        tip.setNombre(tipo.getNombre());
        tip.setDescripcion(tipo.getDescripcion());

        try {
            categoriaTipoService.saveTI(tip);
        } catch (Exception e) {
            resp.put("mensaje", "Error al actualizar datos");
            resp.put("error", e.getMessage());
            return new ResponseEntity<Map<String,Object>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        resp.put("mensaje", "Datos actualizados con éxito");
        return new ResponseEntity<Map<String,Object>>(resp, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/tip-eliminar/{id}")
    public ResponseEntity<?> deleteTipo(@PathVariable(value = "id") Integer id){

        Map<String,String> resp = new HashMap<>();
        Tipo tipo = null;

        try {
            tipo = categoriaTipoService.getTipoByIdtipo(id);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity<Map<String,String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(tipo == null){
            resp.put("mensaje", "No se encontraron coincidencias");
            return new ResponseEntity<Map<String,String>>(resp, HttpStatus.NOT_FOUND);
        }
        
        try {            
            categoriaTipoService.deleteTI(tipo.getIdtipo());
        } catch (Exception e) {
            resp.put("mensaje", "Error al eliminar registro");
            resp.put("error", e.getMessage());
            return new ResponseEntity<Map<String,String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        resp.put("mensaje", "Registro eliminado con éxito");
        return new ResponseEntity<Map<String,String>>(resp, HttpStatus.OK);
    }

}
