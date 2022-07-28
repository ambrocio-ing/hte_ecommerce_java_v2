package com.hteecommerce.hteapp.util;

import java.time.LocalDate;

public class UPersona {    

    public static String setDni(String dni){
        String mensaje = null;

        if(dni == null || dni.equals("")){
            mensaje = "Campo dni es nulo";
        }
        
        if(mensaje == null && (dni.length() < 8 || dni.length() > 12)){
            mensaje = "Campo dni fuera de rango";
        }        

        return mensaje;
    }

    public static String setNombre(String nombre){
        String mensaje = null;

        if(nombre == null || nombre.equals("")){
            mensaje = "El campo nombre no puede ser nulo";
        }

        if(mensaje == null && nombre.length() > 50){
            mensaje = "El campo nombre no puede superar las 50 letras";
        }

        return mensaje;
    }

    public static String setApellidos(String apellidos){

        String mensaje = null;

        if(apellidos == null || apellidos.equals("")){
            mensaje = "El campo apellidos no puede ser nulo";
        }

        if(mensaje == null && apellidos.length() > 50){
            mensaje = "El campo apellidos no puede ser mas de 50 letras";
        }

        return mensaje;
    }

    public static String setGenero(String genero){
        String mensaje = null;
        if(genero == null || genero.equals("")){
            mensaje = "El campo género no puede ser nulo";
        }

        if(mensaje == null && genero.length() != 1){
            mensaje = "El campo género no puede ser mas de una letra";
        }

        return mensaje;
    }

    public static String setFechaNacimiento(LocalDate fecha){
        String mensaje = null;
        if(fecha == null){
            mensaje = "El campo fecha no puede ser nulo";
        }
        return mensaje;

    }

    public static String setTelefono(String telefono){
        String mensaje = null;
        if(telefono == null || telefono.equals("")){
            mensaje = "El campo fecha no puede ser nulo";
        }
        if(mensaje == null && (telefono.length() < 7 || telefono.length() > 9)){
            mensaje = "El campo telefono fuera de rango";
        }
        return mensaje;
    }

    public static String setDireccion(String direccion){
        String mensaje = null;        

        if(direccion != null){
            if(direccion.length() < 10 || direccion.length() > 100){
                mensaje = "El campo dirección fuera de rango";
            }
        }
        
        return mensaje;
    }

}
