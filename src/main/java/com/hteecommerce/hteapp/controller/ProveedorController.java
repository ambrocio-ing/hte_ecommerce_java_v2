package com.hteecommerce.hteapp.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.hteecommerce.hteapp.entity.Proveedor;
import com.hteecommerce.hteapp.enumm.NombreRole;
import com.hteecommerce.hteapp.file_manager.IFileService;
import com.hteecommerce.hteapp.mapper.Mapper;
import com.hteecommerce.hteapp.model.MProveedor;
import com.hteecommerce.hteapp.security.entity.Role;
import com.hteecommerce.hteapp.security.entity.Usuario;
import com.hteecommerce.hteapp.security.service.IRoleService;
import com.hteecommerce.hteapp.security.service.IUsuarioService;
import com.hteecommerce.hteapp.service.IProveedorService;
import com.hteecommerce.hteapp.util.RutaActual;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/proveedor/pro")
public class ProveedorController {
    
    @Autowired
    private IProveedorService proveedorService;
    
    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IFileService fileService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/crear")
    public ResponseEntity<?> createProveedor(@Valid @RequestBody MProveedor proveedor, BindingResult result){

        Map<String,Object> resp = new HashMap<>();
        Proveedor pro = null;

        if(result.hasErrors()){
            List<String> errores = result.getFieldErrors().stream()
                .map(err -> {
                    return "El campo: "+err.getField()+" "+err.getDefaultMessage();
                })
                .collect(Collectors.toList());

            resp.put("mensaje", errores);
            return new ResponseEntity<Map<String,Object>>(resp, HttpStatus.BAD_REQUEST);

        }

        if(proveedorService.isExistsByRuc(proveedor.getRuc())){
            resp.put("mensaje", "El RUC ya existe en el sistema");
            return new ResponseEntity<Map<String,Object>>(resp, HttpStatus.BAD_REQUEST);
        }

        if(proveedorService.isExistsByRazonSocial(proveedor.getRazonSocial())){
            resp.put("mensaje", "Razón social ya existe en el sistema");
            return new ResponseEntity<Map<String,Object>>(resp, HttpStatus.BAD_REQUEST);
        }

        if(proveedorService.isExistsByTelefono(proveedor.getTelefono())){
            resp.put("mensaje", "El teléfono ya existe en el sistema");
            return new ResponseEntity<Map<String,Object>>(resp, HttpStatus.BAD_REQUEST);
        }

        if (usuarioService.isExistsUsername(proveedor.getUsuario().getUsername())) {
            resp.put("mensaje", "El nombre de usuario ya existe en el sistema");
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.NOT_FOUND);
        }

        if (usuarioService.isExistsEmail(proveedor.getUsuario().getEmail())) {
            resp.put("mensaje", "El correo ya existe en el sistema");
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.NOT_FOUND);
        }

        List<String> uerrors = Mapper.isValidUsuario(proveedor.getUsuario());
        if (uerrors != null && uerrors.size() != 0) {
            resp.put("mensaje", uerrors);
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.BAD_REQUEST);
        }

        Set<Role> roles = new HashSet<>();
        roles.add(roleService.getByNombreRole(NombreRole.ROLE_SUPPLIER).orElse(null));

        if(roles.size() != 1){
            resp.put("mensaje", "Error al guardar datos, por favor intentelo mas tarde");
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.BAD_REQUEST);
        }

        String password = passwordEncoder.encode(proveedor.getUsuario().getPassword());
        Usuario usuario = new Usuario(proveedor.getUsuario().getUsername(), 
            proveedor.getUsuario().getEmail(), proveedor.getUsuario().getEstado(),
            password, roles);

        pro = new Proveedor(proveedor.getRuc(), proveedor.getRazonSocial(), 
            proveedor.getTelefono(), proveedor.getDireccion(), proveedor.getEstado(), usuario);

        Proveedor prov = new Proveedor();
        
        try {
            prov = proveedorService.savePro(pro);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error al guardar datos, por favor revise sus datos e intentelo denuevo");
            return new ResponseEntity<Map<String,Object>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        resp.put("mensaje", "Datos guardados con éxito");
        resp.put("id", prov.getIdproveedor().toString());
        return new ResponseEntity<Map<String,Object>>(resp, HttpStatus.CREATED);

    }

    @PostMapping("/imagen")
    public ResponseEntity<?> saveImage(@RequestParam(name = "id") Integer idproveedor,
            @RequestParam(name = "imagen") MultipartFile archivo) {

        Map<String,String> resp = new HashMap<>();
        Proveedor proveedor = null;

        String ruta = RutaActual.RUTA_PROVEEDOR;

        if(archivo.isEmpty()){
            resp.put("mensaje", "La imagen no es válido");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }

        try {
            proveedor = proveedorService.getByIdproveedor(idproveedor);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity<Map<String,String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(proveedor == null){
            resp.put("mensaje", "La imagen no se pudo guardar");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }

        String nombreImagen = null;

        try {
            nombreImagen = fileService.copiar(ruta, archivo);
        } catch (IOException e) {
            resp.put("mensaje", "La imagen no se pudo guardar");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }

        if(nombreImagen == null){
            resp.put("mensaje", "La imagen no se pudo guardar");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }

        fileService.eliminar(ruta, proveedor.getFotoPerfil());

        try {
           proveedor.setFotoPerfil(nombreImagen);
           proveedorService.savePro(proveedor);
        } catch (DataAccessException e) {
            resp.put("mensaje", "No es posible guardar la imagen, por favor asegurece que la imagen es válido e intentelo mas denuevo");
            return new ResponseEntity<Map<String,String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        resp.put("mensaje", "Felicidades,sus datos fueron guardados con éxito, ya puede iniciar sesión");
        return new ResponseEntity<Map<String, String>>(resp, HttpStatus.OK);        
    }    

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> deleteCliente(@PathVariable(value = "id") Integer idproveedor){

        Map<String,String> resp = new HashMap<>();
        Proveedor proveedor = null;
        String ruta = RutaActual.RUTA_PROVEEDOR;

        try {
            proveedor = proveedorService.getByIdproveedor(idproveedor);
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error de consulta a la base de datos");
            return new ResponseEntity<Map<String,String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(proveedor == null){
            resp.put("mensaje", "No se encontraron coincidencias que eliminar");
            return new ResponseEntity<Map<String, String>>(resp, HttpStatus.NOT_FOUND);
        }

        try {
            proveedorService.deletePro(proveedor.getIdproveedor());
        } catch (DataAccessException e) {
            resp.put("mensaje", "Error al eliminar registro del sistema");
            return new ResponseEntity<Map<String,String>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        fileService.eliminar(ruta, proveedor.getFotoPerfil());

        resp.put("mensaje", "Registro eliminado con éxito");
        return new ResponseEntity<Map<String, String>>(resp, HttpStatus.OK);
    }

}
