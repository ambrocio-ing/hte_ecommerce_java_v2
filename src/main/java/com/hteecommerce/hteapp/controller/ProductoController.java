package com.hteecommerce.hteapp.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.hteecommerce.hteapp.entity.Producto;
import com.hteecommerce.hteapp.entity.ProductoDatoNutricional;
import com.hteecommerce.hteapp.entity.ProductoImagen;
import com.hteecommerce.hteapp.entity.ProductoOtros;
import com.hteecommerce.hteapp.entity.ProductoVestimenta;
import com.hteecommerce.hteapp.file_manager.IFileService;
import com.hteecommerce.hteapp.mapper.Mapper;
import com.hteecommerce.hteapp.service.IProductoService;
import com.hteecommerce.hteapp.util.RutaActual;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/producto/pto")
public class ProductoController {

    @Autowired
    private IProductoService productoService;

    @Autowired
    private IFileService fileService;

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/lista/{page}")
    public ResponseEntity<?> productoList(@PathVariable(value = "page") int page) {

        Map<String, String> resp = new HashMap<>();
        Page<Producto> lista = null;

        Pageable pageable = PageRequest.of(page, 3);

        try {
            lista = productoService.getAll(pageable);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (lista.getContent().size() != 0) {
            return new ResponseEntity<Page<Producto>>(lista, HttpStatus.OK);
        } else {
            resp.put("mensaje", "Sin datos que mostrar");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }

    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/modal/lista/{page}")
    public ResponseEntity<?> productoListModal(@PathVariable(value = "page") int page) {

        Map<String, String> resp = new HashMap<>();
        Page<Producto> lista = null;

        Pageable pageable = PageRequest.of(page, 5);

        try {
            lista = productoService.getAll(pageable);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (lista.getContent().size() != 0) {
            return new ResponseEntity<Page<Producto>>(lista, HttpStatus.OK);
        } else {
            resp.put("mensaje", "Sin datos que mostrar");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }

    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/buscar/{texto}")
    public ResponseEntity<?> productoSearch(@PathVariable(value = "texto") String texto) {

        Map<String, String> resp = new HashMap<>();
        List<Producto> lista = null;

        try {
            lista = productoService.getByCodigoOrNombre(texto);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (lista != null && lista.size() != 0) {
            return new ResponseEntity<List<Producto>>(lista, HttpStatus.OK);
        } else {
            resp.put("mensaje", "Sin datos que mostrar");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }

    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/crear")
    public ResponseEntity<?> productoCreate(@Valid @RequestBody Producto producto, BindingResult result) {

        Map<String, Object> resp = new HashMap<>();

        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors().stream()
                    .map(err -> {
                        return "El campo: " + err.getField() + " " + err.getDefaultMessage();
                    })
                    .collect(Collectors.toList());

            resp.put("mensaje", errors);
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.BAD_REQUEST);
        }

        if (productoService.isExistsByCodigo(producto.getCodigo())) {
            resp.put("mensaje", "El código ingresado ya fue asignado a otro producto");
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.NOT_FOUND);
        }

        if (productoService.isExistsByNombre(producto.getNombre())) {
            resp.put("mensaje", "El nombre de producto ingresado ya fue asignado a otro producto");
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.NOT_FOUND);
        }

        if (Mapper.isPresentPDN(producto.getProductoDatoNutricional())) {
            producto.setProductoVestimenta(null);
            producto.setProductoOtros(null);
        } else if (Mapper.isPresentProductoVestimenta(producto.getProductoVestimenta())){
            producto.setProductoDatoNutricional(null);
            producto.setProductoOtros(null);
        } else if(Mapper.isPresentProductoOtros(producto.getProductoOtros())) {
            producto.setProductoDatoNutricional(null);
            producto.setProductoVestimenta(null);
        }
        else{
            producto.setProductoDatoNutricional(null);
            producto.setProductoVestimenta(null);
            producto.setProductoOtros(null);
        }

        Producto pro = null;

        try {
            pro = productoService.savePto(producto);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error al guardar datos en la base de datos");
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        resp.put("mensaje", "Producto creado con éxito");
        resp.put("id", pro.getIdproducto());        
        return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.CREATED);

    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/imagen/uno")
    public ResponseEntity<?> saveImageOne(@RequestParam(name = "id") Integer id,
            @RequestParam(name = "imagen") MultipartFile imagen) {

        Map<String, String> resp = new HashMap<>();
        String ruta = RutaActual.RUTA_PRODUCTO;
        Producto producto = null;
        ProductoImagen pi = new ProductoImagen();

        if (imagen.isEmpty()) {
            resp.put("mensaje", "La imagen no es válida");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }

        try {
            producto = productoService.getByIdproducto(id);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (producto == null) {
            resp.put("mensaje", "No fue posible cotejar sus datos");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }

        pi.setIdpimagen(producto.getProductoImagen().getIdpimagen());
        pi.setImagenUno(producto.getProductoImagen().getImagenUno());
        pi.setImagenDos(producto.getProductoImagen().getImagenDos());
        pi.setImagenTres(producto.getProductoImagen().getImagenTres());

        String nombreImagen = null;

        try {
            nombreImagen = fileService.copiar(ruta, imagen);
        } catch (IOException e) {
            resp.put("mensaje", "No fue posible guardar imagenes del producto");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }

        if (nombreImagen == null) {
            resp.put("mensaje", "No fue posible guardar imagenes del producto");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }

        fileService.eliminar(ruta, producto.getProductoImagen().getImagenUno());

        try {            
            pi.setImagenUno(nombreImagen);
            productoService.savePI(pi);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error al guardar imagen");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        resp.put("mensaje", "Producto creado con éxito");
        return new ResponseEntity<Map<String, String>>(resp, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/img/tres")
    public ResponseEntity<?> saveImageOneInverse(@RequestParam(name = "id") Integer id,
            @RequestParam(name = "imagen") MultipartFile imagen) {

        Map<String, String> resp = new HashMap<>();
        String ruta = RutaActual.RUTA_PRODUCTO;
        Producto producto = null;
        ProductoImagen pi = new ProductoImagen();

        if (imagen.isEmpty()) {
            resp.put("mensaje", "La imagen no es válida");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }

        try {
            producto = productoService.getByIdproducto(id);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (producto == null) {
            resp.put("mensaje", "No fue posible cotejar sus datos");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }

        pi.setIdpimagen(producto.getProductoImagen().getIdpimagen());
        pi.setImagenUno(producto.getProductoImagen().getImagenUno());
        pi.setImagenDos(producto.getProductoImagen().getImagenDos());
        pi.setImagenTres(producto.getProductoImagen().getImagenTres());

        String nombreImagen = null;

        try {
            nombreImagen = fileService.copiar(ruta, imagen);
        } catch (IOException e) {
            resp.put("mensaje", "No fue posible guardar imagenes del producto");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }

        if (nombreImagen == null) {
            resp.put("mensaje", "No fue posible guardar imagenes del producto");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }

        fileService.eliminar(ruta, pi.getImagenTres());

        try {            
            pi.setImagenTres(nombreImagen);
            productoService.savePI(pi);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error al guardar imagen");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        resp.put("mensaje", "Datos del producto actualizados con éxito");
        return new ResponseEntity<Map<String, String>>(resp, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/imagen/dos")
    public ResponseEntity<?> saveImageTwo(@RequestParam(name = "id") Integer id,
            @RequestParam(name = "imagen1") MultipartFile imagen1,
            @RequestParam(name = "imagen2") MultipartFile imagen2) {

        Map<String, String> resp = new HashMap<>();
        String ruta = RutaActual.RUTA_PRODUCTO;
        Producto producto = null;
        ProductoImagen pi = null;

        if (imagen1.isEmpty() || imagen2.isEmpty()) {
            resp.put("mensaje", "Una de las imagenes no es válida");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }

        try {
            producto = productoService.getByIdproducto(id);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (producto == null) {
            resp.put("mensaje", "No fue posible cotejar sus datos");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }

        pi = new ProductoImagen();
        pi.setIdpimagen(producto.getProductoImagen().getIdpimagen());
        pi.setImagenUno(producto.getProductoImagen().getImagenUno());
        pi.setImagenDos(producto.getProductoImagen().getImagenDos());
        pi.setImagenTres(producto.getProductoImagen().getImagenTres());

        String nombreImagen1 = null;
        String nombreImagen2 = null;

        try {
            nombreImagen1 = fileService.copiar(ruta, imagen1);
            nombreImagen2 = fileService.copiar(ruta, imagen2);
        } catch (IOException e) {
            resp.put("mensaje", "No fue posible guardar imagenes del producto");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }

        if (nombreImagen1 == null || nombreImagen2 == null) {
            resp.put("mensaje", "No fue posible guardar imagenes del producto");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }

        fileService.eliminar(ruta, producto.getProductoImagen().getImagenUno());
        fileService.eliminar(ruta, producto.getProductoImagen().getImagenDos());

        try {
            pi.setImagenUno(nombreImagen1);
            pi.setImagenDos(nombreImagen2);
            productoService.savePI(pi);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error al guardar imagenes");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        resp.put("mensaje", "Producto creado con éxito");
        return new ResponseEntity<Map<String, String>>(resp, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/img/dos")
    public ResponseEntity<?> saveImageTwoInverse(@RequestParam(name = "id") Integer id,
            @RequestParam(name = "imagen2") MultipartFile imagen2,
            @RequestParam(name = "imagen3") MultipartFile imagen3) {

        Map<String, String> resp = new HashMap<>();
        String ruta = RutaActual.RUTA_PRODUCTO;
        Producto producto = null;
        ProductoImagen pi = null;

        if (imagen2.isEmpty() || imagen3.isEmpty()) {
            resp.put("mensaje", "Una de las imagenes no es válida");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }

        try {
            producto = productoService.getByIdproducto(id);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (producto == null) {
            resp.put("mensaje", "No fue posible cotejar sus datos");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }

        pi = new ProductoImagen();
        pi.setIdpimagen(producto.getProductoImagen().getIdpimagen());
        pi.setImagenUno(producto.getProductoImagen().getImagenUno());
        pi.setImagenDos(producto.getProductoImagen().getImagenDos());
        pi.setImagenTres(producto.getProductoImagen().getImagenTres());

        String nombreImagen2 = null;
        String nombreImagen3 = null;

        try {
            nombreImagen2 = fileService.copiar(ruta, imagen2);
            nombreImagen3 = fileService.copiar(ruta, imagen3);
        } catch (IOException e) {
            resp.put("mensaje", "No fue posible guardar imagenes del producto");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }

        if (nombreImagen2 == null || nombreImagen3 == null) {
            resp.put("mensaje", "No fue posible guardar imagenes del producto");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }

        fileService.eliminar(ruta, pi.getImagenDos());
        fileService.eliminar(ruta, pi.getImagenTres());

        try {
            pi.setImagenDos(nombreImagen2);
            pi.setImagenTres(nombreImagen3);
            productoService.savePI(pi);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error al guardar imagenes");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        resp.put("mensaje", "Datos del producto actualizados con éxito");
        return new ResponseEntity<Map<String, String>>(resp, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/imagen/tres")
    public ResponseEntity<?> saveImageThree(@RequestParam(name = "id") Integer id,
            @RequestParam(name = "imagen1") MultipartFile imagen1,
            @RequestParam(name = "imagen2") MultipartFile imagen2,
            @RequestParam(name = "imagen3") MultipartFile imagen3) {

        Map<String, String> resp = new HashMap<>();
        String ruta = RutaActual.RUTA_PRODUCTO;
        Producto producto = null;
        ProductoImagen pi = null;

        if (imagen1.isEmpty() || imagen2.isEmpty() || imagen3.isEmpty()) {
            resp.put("mensaje", "Una de las imagenes no es válida");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }

        try {
            producto = productoService.getByIdproducto(id);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (producto == null) {
            resp.put("mensaje", "No fue posible cotejar sus datos");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }

        pi = new ProductoImagen();
        pi.setIdpimagen(producto.getProductoImagen().getIdpimagen());
        pi.setImagenUno(producto.getProductoImagen().getImagenUno());
        pi.setImagenDos(producto.getProductoImagen().getImagenDos());
        pi.setImagenTres(producto.getProductoImagen().getImagenTres());

        String nombreImagen1 = null;
        String nombreImagen2 = null;
        String nombreImagen3 = null;

        try {
            nombreImagen1 = fileService.copiar(ruta, imagen1);
            nombreImagen2 = fileService.copiar(ruta, imagen2);
            nombreImagen3 = fileService.copiar(ruta, imagen3);
        } catch (IOException e) {
            resp.put("mensaje", "No fue posible guardar imagenes del producto");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }

        if (nombreImagen1 == null || nombreImagen2 == null || nombreImagen3 == null) {
            resp.put("mensaje", "No fue posible guardar imagenes del producto");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }

        fileService.eliminar(ruta, producto.getProductoImagen().getImagenUno());
        fileService.eliminar(ruta, producto.getProductoImagen().getImagenDos());
        fileService.eliminar(ruta, producto.getProductoImagen().getImagenTres());

        try {
            pi.setImagenUno(nombreImagen1);
            pi.setImagenDos(nombreImagen2);
            pi.setImagenTres(nombreImagen3);
            productoService.savePI(pi);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error al guardar imagenes");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        resp.put("mensaje", "Datos del producto guardado con éxito");
        return new ResponseEntity<Map<String, String>>(resp, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/obtener/{id}")
    public ResponseEntity<?> getProducto(@PathVariable(value = "id") Integer Idproducto) {

        Map<String, String> resp = new HashMap<>();
        Producto producto = null;

        try {
            producto = productoService.getByIdproducto(Idproducto);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (producto == null) {
            resp.put("mensaje", "No fue posible cotejar datos del producto");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Producto>(producto, HttpStatus.OK);

    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/editar")
    public ResponseEntity<?> productoUpdate(@Valid @RequestBody Producto producto, BindingResult result) {

        Map<String, Object> resp = new HashMap<>();
        Producto pro = null;

        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors().stream()
                    .map(err -> {
                        return "El campo: " + err.getField() + " " + err.getDefaultMessage();
                    })
                    .collect(Collectors.toList());

            resp.put("mensaje", errors);
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.BAD_REQUEST);
        }

        try {
            pro = productoService.getByIdproducto(producto.getIdproducto());
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (pro == null) {
            resp.put("mensaje", "No se encontro producto a editar");
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.NOT_FOUND);
        }

        pro.setNombre(producto.getNombre());
        pro.setTipo(producto.getTipo());
        pro.setDescripcion(producto.getDescripcion());

        if (pro.getProductoDatoNutricional() != null) {
            pro.setProductoDatoNutricional(new ProductoDatoNutricional(
                    producto.getProductoDatoNutricional().getIddnutricional(),
                    producto.getProductoDatoNutricional().getCalorias(),
                    producto.getProductoDatoNutricional().getGrasa(),
                    producto.getProductoDatoNutricional().getColesterol(),
                    producto.getProductoDatoNutricional().getSodio(),
                    producto.getProductoDatoNutricional().getCarbohidrato(),
                    producto.getProductoDatoNutricional().getProteina(),
                    producto.getProductoDatoNutricional().getVitamina(),
                    producto.getProductoDatoNutricional().getCalcio(),
                    producto.getProductoDatoNutricional().getHierro(), 
                    producto.getProductoDatoNutricional().getMinerales()));
                    
        } else if (producto.getProductoVestimenta() != null) {
            pro.setProductoVestimenta(new ProductoVestimenta(
                    producto.getProductoVestimenta().getIdpvestimenta(),                                        
                    producto.getProductoVestimenta().getModelo(),
                    producto.getProductoVestimenta().getMaterial(),
                    producto.getProductoVestimenta().getColor(),
                    producto.getProductoVestimenta().getDescripcion(),
                    producto.getProductoVestimenta().getVariedades()));
        } else {
            pro.setProductoOtros(new ProductoOtros(
                    producto.getProductoOtros().getIdpotros(),                    
                    producto.getProductoOtros().getModelo(),
                    producto.getProductoOtros().getColor(),
                    producto.getProductoOtros().getMaterial(),
                    producto.getProductoOtros().getMedida(),
                    producto.getProductoOtros().getPeso(),
                    producto.getProductoOtros().getDetalle()));
        }

        try {
            productoService.updatePto(pro);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error al guardar datos en la base de datos");
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        resp.put("mensaje", "Datos del producto actualizados con éxito");
        resp.put("id", pro.getIdproducto());
        return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> deleteProducto(@PathVariable(value = "id") Integer Idproducto) {

        Map<String, String> resp = new HashMap<>();
        Producto producto = null;
        String ruta = RutaActual.RUTA_PRODUCTO;

        try {
            producto = productoService.getByIdproducto(Idproducto);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (producto == null) {
            resp.put("mensaje", "No fue posible cotejar datos del producto");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }

        try {
            productoService.deletePto(producto.getIdproducto());
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error al eliminar registro");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        fileService.eliminar(ruta, producto.getProductoImagen().getImagenUno());
        fileService.eliminar(ruta, producto.getProductoImagen().getImagenDos());
        fileService.eliminar(ruta, producto.getProductoImagen().getImagenTres());

        resp.put("mensaje", "Registro eliminado con éxito");
        return new ResponseEntity<Map<String, String>>(resp, HttpStatus.OK);
    }

}
