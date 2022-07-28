package com.hteecommerce.hteapp.util;

public class UClienteProveedor {

    private UClienteProveedor(){

    }
    
    //@NotNull
    //@Size(min = 10, max = 11)
    //private String ruc;
    public static String isValidRuc(String ruc){
        String mensaje = null;
        if(ruc == null || ruc.equals("")){
            mensaje = "El campo ruc no debe ser nulo";
        }
        else if(ruc.length() < 10 || ruc.length() > 11){
            mensaje = "El campo ruc debe ser de 10 o 11 dígitos";
        }
        return mensaje;

    }

    //@NotNull
    //@Size(max = 100)
    //private String razonSocial;
    public static String isValidRazonSocial(String razon){
        String mensaje = null;
        if(razon == null || razon.equals("")){
            mensaje = "El campo razón social no debe ser nulo";
        }
        else if(razon.length() > 100){
            mensaje = "El campo razón social es demaciado extenso";
        }
        return mensaje;
    }

    //@Size(max = 9, min = 7)
    //private String telefono;
    public static String isValidTelefono(String telefono){
        String mensaje = null;
        if(telefono == null || telefono.equals("")){
            mensaje = "El campo teléfono no debe ser nulo";
        }
        else if(telefono.length() < 7 || telefono.length() > 9){
            mensaje = "El campo teléfono debe estar entre 7 y 9 dígitos";
        }
        return mensaje;
    }

    //@Size(min = 5, max = 100)
    //private String direccion;
    public static String isValidDirecion(String direccion){
        String mensaje = null;
        if(direccion != null){
            if(direccion.length() < 5 || direccion.length() > 100){
                mensaje = "El campo dirección está fuera de rango";
            }
        }
        return mensaje;
    }

    //@Email
    //@Size(min = 5, max = 50)
    //private String email;
    public static String isValidEmail(String email){
        String mensaje = null;
        if(email != null){
            if(!email.contains("@") || !email.contains(".") || email.length() < 5 || email.length() > 50){
                mensaje = "El correo no cumple con las características de un correo";
            }
        }
        return mensaje;
    }
    
}
