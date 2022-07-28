package com.hteecommerce.hteapp.file_manager;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import javax.xml.bind.DatatypeConverter;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileServiceImplements implements IFileService {

    @Override
    public Resource cargar(String nombreArchivo, String ruta, String rutaAhuxiliar, String archivoAhuxiliar)
            throws MalformedURLException {

        Resource resource = null;
        Path path = obtenerRuta(nombreArchivo, ruta);
        resource = new UrlResource(path.toUri());

        if (!resource.exists() && !resource.isReadable()) {
            Path path2 = obtenerRuta(archivoAhuxiliar, rutaAhuxiliar);
            resource = new UrlResource(path2.toUri());
        }

        return resource;
    }

    @Override
    public String copiar(String ruta, MultipartFile archivo) throws IOException {

        String nombreArchivo = UUID.randomUUID().toString() + "_" + archivo.getOriginalFilename().replace(" ", "");
        Path path = obtenerRuta(nombreArchivo, ruta);
        Files.copy(archivo.getInputStream(), path);
        return nombreArchivo;
    }

    @Override
    public boolean eliminar(String ruta, String nombreArchivo) {

        if (nombreArchivo != null && nombreArchivo.length() > 0) {
            Path path = obtenerRuta(nombreArchivo, ruta);
            File archivo = path.toFile();

            if (archivo.exists() && archivo.canRead()) {
                archivo.delete();
                return true;
            }
        }

        return false;
    }

    @Override
    public Path obtenerRuta(String nombreArchivo, String ruta) {

        Path path = Paths.get(ruta).resolve(nombreArchivo).toAbsolutePath();
        return path;
    }

    @Override
    public String convertirBase64(String base64, String ruta, String nombre) throws IOException {
        String new_nombre = null;
        String[] strings = base64.split(",");
        String extension = null;

        switch (strings[0]) {
            case "data:image/jpeg;base64":
                extension = "jpeg";
                break;
            case "data:image/png;base64":
                extension = "png";
                break;
            default:
                extension = "jpg";
                break;
        }        

        byte[] data = DatatypeConverter.parseBase64Binary(strings[1]);
        new_nombre = UUID.randomUUID().toString() + nombre.replaceAll(" ", "") + "." + extension;
        String new_ruta = ruta + "/" + new_nombre;

        File file = new File(new_ruta);
        OutputStream os = new BufferedOutputStream(new FileOutputStream(file));
        os.write(data);
        os.close();

        return new_nombre;

    }

}
