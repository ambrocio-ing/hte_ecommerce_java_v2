package com.hteecommerce.hteapp.file_manager;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface IFileService {
    
    public Resource cargar(String nombreArchivo, String ruta, String rutaAhuxiliar,String archivoAhuxiliar) throws MalformedURLException ;
    public String copiar(String ruta, MultipartFile archivo) throws IOException;
    public boolean eliminar(String ruta, String nombreArchivo);
    public Path obtenerRuta(String nombreArchivo, String ruta);
    public String convertirBase64(String base64, String ruta, String nombre) throws IOException;

}
