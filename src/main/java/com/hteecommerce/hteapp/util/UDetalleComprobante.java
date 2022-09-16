package com.hteecommerce.hteapp.util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class UDetalleComprobante {
    
    private UDetalleComprobante(){

    }

    public static Stream<String> isValidDC(Double cantidad, Double precioUnitario, Double subtotal){
        List<String> mensajes = new ArrayList<>();
        if(cantidad == null || cantidad == 0){
            mensajes.add("La cantidad no debe ser nulo");
        }

        if(precioUnitario == null || precioUnitario == 0){
            mensajes.add("Precio unitario no debe ser nulo ni cero");
        }

        if(subtotal == null){
            mensajes.add("Subtotal no debe ser nulo");
        }

        return mensajes.stream();
    }

}
