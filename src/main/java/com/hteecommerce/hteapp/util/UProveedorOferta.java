package com.hteecommerce.hteapp.util;

public class UProveedorOferta {

    private UProveedorOferta(){
        
    }

    //@NotNull
    //@Size(max = 50)
    //private String nombre;
    public static String validNombre(String nombre){
        String mensaje = null;
        if(nombre == null || nombre.equals("")){
            mensaje = "Nombre de producto no debe ser nulo";
        }
        else if(nombre.length() > 50){
            mensaje = "Nombre de producto no debe superar los 50 carácteres";
        }

        return mensaje;
        
    }

    //@NotNull
    //private Double precio;
    public static String validPrecio(Double precio){
        String mensaje = null;
        if(precio == null || precio == 0){
            mensaje = "Precio de producto no debe ser nulo";
        }
        return mensaje;
        
    }

    //@NotNull
    //private int cantidad;
    public static String validCantidad(Integer cantidad){
        String mensaje = null;
        if(cantidad == null || cantidad == 0){
            mensaje = "Cantidad de producto no debe ser nulo";
        }
        return mensaje;
        
    }

    //@NotNull
    //@Size(max = 10)
    //private String unidad;
    public static String validUnidad(String unidad){
        String mensaje = null;
        if(unidad == null || unidad.equals("")){
            mensaje = "Unidad de media no debe ser nulo";
        }
        else if(unidad.length() > 10){
            mensaje = "La unidad de medida debe estar en abrebiatura";
        }
        return mensaje;
        
    }

    //@NotNull
    //private String calidad;
    public static String validCalidad(String calidad){
        String mensaje = null;
        if(calidad == null || calidad.equals("")){
            mensaje = "Escriba calidad del producto";
        }
        return mensaje;
        
    }

    //@NotNull
    //private String descripcion;
    public static String validDescripcion(String descripcion){
        String mensaje = null;
        if(descripcion == null || descripcion.equals("")){
            mensaje = "La descripción del producto no debe ser nulo";
        }
        return mensaje;
        
    }
}
