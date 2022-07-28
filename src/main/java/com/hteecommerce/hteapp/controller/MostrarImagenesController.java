package com.hteecommerce.hteapp.controller;

import java.net.MalformedURLException;

import com.hteecommerce.hteapp.file_manager.IFileService;
import com.hteecommerce.hteapp.util.RutaActual;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mostrar")
public class MostrarImagenesController {

    @Autowired
    private IFileService fileService;

    @GetMapping("/per/imagen/{nombre:.+}")
    public ResponseEntity<Resource> getImagePersonal(@PathVariable(value = "nombre") String nombre){

        Resource resource = null;
        String ruta = RutaActual.RUTA_PERSONA;
        String archivoAhuxiliar = "fotologo.jpg";
        
        try {
            resource = fileService.cargar(nombre, ruta, ruta, archivoAhuxiliar);
        } catch (MalformedURLException e) {
            
            e.printStackTrace();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+resource.getFilename());

        return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);
    }

    @GetMapping("/cli/imagen/{imagen:.+}")
    public ResponseEntity<Resource> getImageCliente(@PathVariable(value = "imagen") String nombreImagen){

        String ruta = RutaActual.RUTA_PERSONA;
        String archivoAhuxiliar = "fotologo.jpg";
        Resource resource = null;

        try {
            resource = fileService.cargar(nombreImagen, ruta, ruta, archivoAhuxiliar);
        } catch (MalformedURLException e) {            
            e.printStackTrace();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+resource.getFilename());

        return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);

    }

    @GetMapping("/pro/imagen/{imagen:.+}")
    public ResponseEntity<Resource> getImageProveedor(@PathVariable(value = "imagen") String nombreImagen){

        String ruta = RutaActual.RUTA_PROVEEDOR;
        String archivoAhuxiliar = "fotologo.jpg";
        Resource resource = null;

        try {
            resource = fileService.cargar(nombreImagen, ruta, ruta, archivoAhuxiliar);
        } catch (MalformedURLException e) {            
            e.printStackTrace();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+resource.getFilename());

        return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);
    }

    @GetMapping("/pto/imagen/{imagen:.+}")
    public ResponseEntity<Resource> getImageProducto(@PathVariable(value = "imagen") String nombreImagen){

        String ruta = RutaActual.RUTA_PRODUCTO;
        String archivoAhuxiliar = "fotologo.jpg";
        Resource resource = null;

        try {
            resource = fileService.cargar(nombreImagen, ruta, ruta, archivoAhuxiliar);
        } catch (MalformedURLException e) {            
            e.printStackTrace();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+resource.getFilename());

        return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);
    }

    @GetMapping("/cli-oferta/imagen/{imagen:.+}")
    public ResponseEntity<Resource> getImageOfertaCliente(@PathVariable(value = "imagen") String nombreImagen){

        String ruta = RutaActual.RUTA_CLIENTE_OFERTA;
        String archivoAhuxiliar = "fotologo.jpg";
        Resource resource = null;

        try {
            resource = fileService.cargar(nombreImagen, ruta, ruta, archivoAhuxiliar);
        } catch (MalformedURLException e) {            
            e.printStackTrace();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+resource.getFilename());

        return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);
    }

    @GetMapping("/pro-oferta/imagen/{imagen:.+}")
    public ResponseEntity<Resource> getImageOfertaProveedor(@PathVariable(value = "imagen") String nombreImagen){

        String ruta = RutaActual.RUTA_PROVEEDOR_OFERTA;
        String archivoAhuxiliar = "fotologo.jpg";
        Resource resource = null;

        try {
            resource = fileService.cargar(nombreImagen, ruta, ruta, archivoAhuxiliar);
        } catch (MalformedURLException e) {            
            e.printStackTrace();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+resource.getFilename());

        return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);
    }

    @GetMapping("/mem/imagen/{imagen:.+}")
    public ResponseEntity<Resource> getImageMembresia(@PathVariable(value = "imagen") String nombreImagen){

        String ruta = RutaActual.RUTA_MEMBRESIA;
        String archivoAhuxiliar = "fotologo.jpg";
        Resource resource = null;

        try {
            resource = fileService.cargar(nombreImagen, ruta, ruta, archivoAhuxiliar);
        } catch (MalformedURLException e) {            
            e.printStackTrace();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+resource.getFilename());

        return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);
    }

    @GetMapping("/publi/imagen/{imagen:.+}")
    public ResponseEntity<Resource> getImagePublicacion(@PathVariable(value = "imagen") String nombreImagen){

        String ruta = RutaActual.RUTA_PUBLICACION;
        String archivoAhuxiliar = "fotologo.jpg";
        Resource resource = null;

        try {
            resource = fileService.cargar(nombreImagen, ruta, ruta, archivoAhuxiliar);
        } catch (MalformedURLException e) {            
            e.printStackTrace();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+resource.getFilename());

        return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);
    }
    
}
