package com.hteecommerce.hteapp.mapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.hteecommerce.hteapp.entity.Carrito;
import com.hteecommerce.hteapp.entity.ClienteCaracteristica;
import com.hteecommerce.hteapp.entity.ClienteOferta;
import com.hteecommerce.hteapp.entity.ClienteProveedor;
import com.hteecommerce.hteapp.entity.Comentario;
import com.hteecommerce.hteapp.entity.Comprobante;
import com.hteecommerce.hteapp.entity.Destinatario;
import com.hteecommerce.hteapp.entity.DetalleIngreso;
import com.hteecommerce.hteapp.entity.DireccionEnvio;
import com.hteecommerce.hteapp.entity.Persona;
import com.hteecommerce.hteapp.entity.ProductoDatoNutricional;
import com.hteecommerce.hteapp.entity.ProductoOtros;
import com.hteecommerce.hteapp.entity.ProductoVestimenta;
import com.hteecommerce.hteapp.entity.ProveedorOferta;
import com.hteecommerce.hteapp.model.HistoricoPrecio;
import com.hteecommerce.hteapp.model.MCarrito;
import com.hteecommerce.hteapp.model.MComentario;
import com.hteecommerce.hteapp.model.MComprobante;
import com.hteecommerce.hteapp.model.MDetalleIngreso;
import com.hteecommerce.hteapp.model.MDireccionEnvio;
import com.hteecommerce.hteapp.security.model.MUsuario;
import com.hteecommerce.hteapp.util.UClienteOferta;
import com.hteecommerce.hteapp.util.UClienteProveedor;
import com.hteecommerce.hteapp.util.UPersona;

import org.springframework.stereotype.Component;

@Component("mapper")
public class Mapper {

    private Mapper() {

    }

    public static List<String> isValidPersona(Persona persona) {
        List<String> lista = new ArrayList<>();

        String dni = UPersona.setDni(persona.getDni());

        if (dni != null) {
            lista.add(dni);
        }

        String nombre = UPersona.setNombre(persona.getNombre());
        if (nombre != null) {
            lista.add(nombre);
        }

        String apellidos = UPersona.setApellidos(persona.getApellidos());
        if (apellidos != null) {
            lista.add(apellidos);
        }

        String genero = UPersona.setGenero(persona.getGenero());
        if (genero != null) {
            lista.add(genero);
        }

        String fecha = UPersona.setFechaNacimiento(persona.getFechaNacimiento());
        if (fecha != null) {
            lista.add(fecha);
        }

        String telefono = UPersona.setTelefono(persona.getTelefono());
        if (telefono != null) {
            lista.add(telefono);
        }

        String direccion = UPersona.setDireccion(persona.getDireccion());
        if (direccion != null) {
            lista.add(direccion);
        }

        return lista;
    }

    public static List<String> isValidUsuario(MUsuario usuario) {
        List<String> lista = new ArrayList<>();

        if (usuario.getUsername() == null || usuario.getUsername().equals("")) {
            lista.add("El nombre de usuario no pude ser nulo");
        } else if (usuario.getUsername().length() < 4 || usuario.getUsername().length() > 15) {
            lista.add("El nombre usuario debe estar entre 4 y 15 dígitos");
        }

        if (usuario.getEmail() == null || usuario.getEmail().equals("")) {
            lista.add("El correo no puede ser nulo");
        }

        if (usuario.getEstado() == null || usuario.getEstado().equals("")) {
            lista.add("El estado no puede ser nulo");
        } else if (usuario.getEstado().length() > 12) {
            lista.add("El estado no puede ser mayor a 12 letras");
        }

        if (usuario.getPassword() == null || usuario.getPassword().equals("")) {
            lista.add("La contraseña no puede ser nulo");
        } else if (usuario.getPassword().length() < 8 || usuario.getPassword().length() > 15) {
            lista.add("La contraseña debe estar entre 8 y 15 caracteres");
        }

        return lista;

    }

    public static List<String> isValidClienteProveedor(ClienteProveedor clienteProveedor) {
        List<String> lista = new ArrayList<>();
        String err_ruc = UClienteProveedor.isValidRuc(clienteProveedor.getRuc());
        if (err_ruc != null) {
            lista.add(err_ruc);
        }

        String err_razon = UClienteProveedor.isValidRazonSocial(clienteProveedor.getRazonSocial());
        if (err_razon != null) {
            lista.add(err_razon);
        }

        String err_telefono = UClienteProveedor.isValidTelefono(clienteProveedor.getTelefono());
        if (err_telefono != null) {
            lista.add(err_telefono);
        }

        String err_direccion = UClienteProveedor.isValidDirecion(clienteProveedor.getDireccion());
        if (err_direccion != null) {
            lista.add(err_direccion);
        }

        String err_email = UClienteProveedor.isValidEmail(clienteProveedor.getEmail());
        if (err_email != null) {
            lista.add(err_email);
        }

        return lista;
    }

    public static boolean isPresentClienteProveedor(ClienteProveedor cp) {
        if (cp == null) {
            return false;
        } else {
            if (cp.getRazonSocial() != null && cp.getRuc() != null) {
                return true;
            } else {
                return false;
            }
        }
    }

    public static boolean isPresentClienteCaracteristica(ClienteCaracteristica cc) {
        if (cc == null) {
            return false;
        } else {
            if (cc.getAlergia() != null || cc.getDeseo() != null ||
                    cc.getDieta() != null || cc.getEnfermedad() != null ||
                    cc.getOcupacion() != null || cc.getPasatiempo() != null) {

                return true;

            } else {
                return false;
            }
        }
    }

    public static boolean isPresentPDN(ProductoDatoNutricional pdn) {
        if (pdn == null) {
            return false;
        } else {
            if (pdn.getCalcio() != null || pdn.getCalorias() != null || pdn.getGrasa() != null ||
                    pdn.getColesterol() != null || pdn.getCarbohidrato() != null || pdn.getHierro() != null ||
                    pdn.getProteina() != null || pdn.getSodio() != null || pdn.getVitamina() != null) {

                return true;
            } else {
                return false;
            }
        }
    }

    public static boolean isPresentProductoOtros(ProductoOtros po) {
        if (po == null) {
            return false;
        }

        if (po.getColor() != null || po.getDetalle() != null ||
                po.getMarca() != null || po.getMaterial() != null || po.getMedida() != null ||
                po.getModelo() != null || po.getPeso() != null) {

            return true;
        }

        return false;
    }

    public static boolean isPresentProductoVestimenta(ProductoVestimenta pv) {
        if (pv == null) {
            return false;
        }

        if (pv.getColor() != null || pv.getDescripcion() != null ||
                pv.getMarca() != null || pv.getMaterial() != null || pv.getModelo() != null
                || pv.getTalla() != null) {

            return true;
        }

        return false;
    }

    // validando detalle ingreso
    public static Stream<String> isValidDetalleIngreso(DetalleIngreso di) {
        List<String> mensajes = new ArrayList<>();
        if (di.getPrecioCompra() == null || di.getPrecioCompra() == 0) {
            mensajes.add("EL precio de compra no puede ser nulo ni cero");
        }

        if (di.getPrecioVenta() == null || di.getPrecioVenta() == 0) {
            mensajes.add("El precio de venta no puede ser nulo");
        }

        if (di.getStockInicial() == null || di.getStockInicial() == 0) {
            mensajes.add("Stock inicial no debe ser nulo ni cero");
        }

        if (di.getFechaProduccion() == null) {
            mensajes.add("Fecha no pruduccion no debe ser nulo");
        }

        if (di.getEstado() == null) {
            mensajes.add("Estado no debe ser nulo");
        }

        return mensajes.stream();

    }

    public static Stream<String> isValidClienteOferta(ClienteOferta co) {

        List<String> mensajes = new ArrayList<>();
        String nombre = UClienteOferta.validNombre(co.getNombre());
        if (nombre != null) {
            mensajes.add(nombre);
        }

        String calidad = UClienteOferta.validCalidad(co.getCalidad());
        if (calidad != null) {
            mensajes.add(calidad);
        }

        String cantidad = UClienteOferta.validCantidad(co.getCantidad());
        if (cantidad != null) {
            mensajes.add(cantidad);
        }

        String descripcion = UClienteOferta.validDescripcion(co.getDescripcion());
        if (descripcion != null) {
            mensajes.add(descripcion);
        }

        String precio = UClienteOferta.validPrecio(co.getPrecio());
        if (precio != null) {
            mensajes.add(precio);
        }

        String unidad = UClienteOferta.validUnidad(co.getUnidad());
        if (unidad != null) {
            mensajes.add(unidad);
        }

        String imagen = UClienteOferta.validImagen(co.getImagen());
        if (imagen != null) {
            mensajes.add(imagen);
        }

        return mensajes.stream();

    }

    public static Stream<String> isValidProveedorOferta(ProveedorOferta co) {

        List<String> mensajes = new ArrayList<>();
        String nombre = UClienteOferta.validNombre(co.getNombre());
        if (nombre != null) {
            mensajes.add(nombre);
        }

        String calidad = UClienteOferta.validCalidad(co.getCalidad());
        if (calidad != null) {
            mensajes.add(calidad);
        }

        String cantidad = UClienteOferta.validCantidad(co.getCantidad());
        if (cantidad != null) {
            mensajes.add(cantidad);
        }

        String descripcion = UClienteOferta.validDescripcion(co.getDescripcion());
        if (descripcion != null) {
            mensajes.add(descripcion);
        }

        String precio = UClienteOferta.validPrecio(co.getPrecio());
        if (precio != null) {
            mensajes.add(precio);
        }

        String unidad = UClienteOferta.validUnidad(co.getUnidad());
        if (unidad != null) {
            mensajes.add(unidad);
        }

        String imagen = UClienteOferta.validImagen(co.getImagen());
        if (imagen != null) {
            mensajes.add(imagen);
        }

        return mensajes.stream();

    }

    // lista general detalle ingreso
    public static List<MDetalleIngreso> mapDetalleIngresos1(List<DetalleIngreso> dis) {
        List<MDetalleIngreso> mlista = dis.stream()
                .map(di -> new MDetalleIngreso(di))
                .collect(Collectors.toList());
        return mlista;
    }

    public static MDetalleIngreso mapDetalleIngreso(DetalleIngreso di) {
        return new MDetalleIngreso(di);
    }

    // lista para busqueda detalle ingreso
    public static List<MDetalleIngreso> mapDetalleIngresos(List<DetalleIngreso> dis) {

        List<MDetalleIngreso> mlista = dis.stream()
                .map(di -> {
                    return new MDetalleIngreso(di.getIddetalleingreso(), di.getPrecioVenta(),
                            di.getPrecioVentaAnterior(),
                            di.getPorcentajeDescuento(),
                            di.getStockInicial(), di.getStockActual(),
                            di.getFechaProduccion(), di.getFechaVencimiento(),
                            di.getEstado(), di.getProducto(), di.getIdingreso());
                })
                .collect(Collectors.toList());

        return mlista;

    }

    public static List<MDetalleIngreso> mapDetalleIngresosTienda(List<DetalleIngreso> dis) {

        List<MDetalleIngreso> mlista = dis.stream()
                .map(di -> {
                    return new MDetalleIngreso(di.getIddetalleingreso(), di.getPrecioVenta(),
                            di.getPrecioVentaAnterior(), di.getPorcentajeDescuento(),
                            di.getStockActual(),
                            di.getFechaProduccion(), di.getFechaVencimiento(),
                            di.getEstado(), di.getProducto());
                })
                .collect(Collectors.toList());

        return mlista;

    }

    public static List<MComprobante> mapComprobantes(List<Comprobante> lista) {
        List<MComprobante> mlista = lista.stream()
                .map(dc -> new MComprobante(dc))
                .collect(Collectors.toList());

        return mlista;
    }

    public static String generateNumero(String lastNumero, int size) {
        int num = Integer.parseInt(lastNumero) + 1;
        int tamano = (num + "").length();
        String texto = num + "";

        if (size == 6 && tamano < 6) {
            int repeticion = size - tamano;

            for (int i = 1; i <= repeticion; i++) {
                texto = 0 + texto;
            }
        }

        if (size == 3 && tamano < 3) {
            int repeticion = size - tamano;
            for (int i = 1; i <= repeticion; i++) {
                texto = 0 + texto;
            }
        }

        return texto;
    }

    public static MDireccionEnvio mapDireccionEnvio(DireccionEnvio die) {
        MDireccionEnvio mdie = new MDireccionEnvio(
                die.getIddireccion(), die.getDireccion(), die.getTelefono(),
                die.getReferencia(), die.getCodigoPostal(), die.getPais(),
                die.getRegion(), die.getProvincia(), die.getDistrito(),
                die.getPrincipal(), die.getFormaEnvio(), die.getDestinatario());

        return mdie;
    }

    public static boolean isPresentDestinatario(Destinatario destinatario) {

        if (destinatario == null) {
            return false;
        }

        if (destinatario.getDni() != null && destinatario.getDni().length() >= 8 &&
                destinatario.getNombre() != null && destinatario.getApellidos() != null) {

            return true;
        }

        return false;
    }

    public static List<MCarrito> mapCarritos(List<Carrito> lista) {
        List<MCarrito> mlista = new ArrayList<>();
        mlista = lista.stream()
                .map(car -> new MCarrito(car))
                .collect(Collectors.toList());
        return mlista;
    }

    public static List<MComentario> mapComentarios(List<Comentario> lista) {
        List<MComentario> mlista = new ArrayList<>();
        Collections.reverse(lista);
        mlista = lista.stream()
                .map(co -> {
                    MComentario mco = new MComentario(co);
                    mco.getCliente().getPersona().setDni(null);
                    mco.getCliente().getPersona().setDireccion(null);
                    mco.getCliente().getPersona().setTelefono(null);
                    return mco;
                })
                .limit(20)
                .collect(Collectors.toList());

        return mlista;
    }

    public static int masRepeticiones(int[] numeros) {
        int mayor = numeros[0];
        for (int i = 1; i < numeros.length; i++) {
            if (repeticiones(mayor, numeros) < repeticiones(numeros[i], numeros)) {
                mayor = numeros[i];
            }
        }

        return mayor;
    }

    public static int repeticiones(int numero, int[] numeros) {
        int cuantos = 0;
        for (int i = 0; i < numeros.length; i++) {
            if (numeros[i] == numero) {
                cuantos++;
            }
        }

        return cuantos;
    }

    // historico
    public static List<HistoricoPrecio> mapHistoricoPrecios(List<DetalleIngreso> lista) {
        List<HistoricoPrecio> hps = new ArrayList<>();
        LocalDate fecha = LocalDate.now();

        List<DetalleIngreso> dis_annio_actual = lista.stream()
                .filter(di -> di.getCreateAt().getYear() == fecha.getYear())
                .collect(Collectors.toList());

        List<DetalleIngreso> dis_annio_anterior = lista.stream()
                .filter(di -> di.getCreateAt().getYear() < fecha.getYear())
                .collect(Collectors.toList());

        if (dis_annio_actual.size() > 0) {
            int nmes = fecha.getMonthValue();
            int annio = fecha.getYear();

            for (int i = nmes; i > 0; i--) {
                HistoricoPrecio hp = historico_Precio(dis_annio_actual, nmes, annio);
                hps.add(hp);
                nmes--;
            }
        }

        if(dis_annio_anterior.size() > 0){
            int tamanno = 12 - hps.size();
            int nmes = 12;
            int annio = fecha.getYear()-1;

            for (int i = tamanno; i > 0; i--) {
                HistoricoPrecio hp = historico_Precio(dis_annio_anterior, nmes, annio);
                hps.add(hp);
                nmes--;
                tamanno--;
            }
        }

        return hps;
    }

    public static HistoricoPrecio historico_Precio(List<DetalleIngreso> lista, int nmes, int annio) {
        HistoricoPrecio hp = new HistoricoPrecio();
        Double precio_acumulado = 0.0;
        int count = 0;

        for (DetalleIngreso di : lista) {
            if (di.getCreateAt().getMonthValue() == nmes) {
                precio_acumulado += di.getPrecioVenta();
                count++;
            }
        }

        Double promedio = precio_acumulado / count;
        promedio = (double) (Math.round(promedio * 100) / 100);

        hp.setPrecioPromedio(promedio);
        hp.setMes(asignarMes(nmes));
        hp.setAnnio(annio + "");

        return hp;
    }

    public static String asignarMes(int valor) {
        String mes = "";
        switch (valor) {
            case 1:
                mes = "Enero";
                break;
            case 2:
                mes = "Febrero";
                break;
            case 3:
                mes = "Marzo";
                break;
            case 4:
                mes = "Abril";
                break;
            case 5:
                mes = "Mayo";
                break;
            case 6:
                mes = "Junio";
                break;
            case 7:
                mes = "Julio";
                break;
            case 8:
                mes = "Agosto";
                break;
            case 9:
                mes = "Setiembre";
                break;
            case 10:
                mes = "Octubre";
                break;
            case 11:
                mes = "Noviembre";
                break;
            case 12:
                mes = "Diciembre";
                
        }

        return mes;
    }

}
