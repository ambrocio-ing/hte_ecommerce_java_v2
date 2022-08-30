package com.hteecommerce.hteapp.mapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.hteecommerce.hteapp.entity.Carrito;
import com.hteecommerce.hteapp.entity.ClienteCaracteristica;
import com.hteecommerce.hteapp.entity.ClienteOferta;
import com.hteecommerce.hteapp.entity.ClienteProveedor;
import com.hteecommerce.hteapp.entity.Color;
import com.hteecommerce.hteapp.entity.Comentario;
import com.hteecommerce.hteapp.entity.Comprobante;
import com.hteecommerce.hteapp.entity.Destinatario;
import com.hteecommerce.hteapp.entity.DetalleComprobante;
import com.hteecommerce.hteapp.entity.DetalleIngreso;
import com.hteecommerce.hteapp.entity.DetallePago;
import com.hteecommerce.hteapp.entity.DireccionEnvio;
import com.hteecommerce.hteapp.entity.Persona;
import com.hteecommerce.hteapp.entity.ProductoDatoNutricional;
import com.hteecommerce.hteapp.entity.ProductoOtros;
import com.hteecommerce.hteapp.entity.ProductoVestimenta;
import com.hteecommerce.hteapp.entity.ProveedorOferta;
import com.hteecommerce.hteapp.entity.Variedad;
import com.hteecommerce.hteapp.model.HistoricoPrecio;
import com.hteecommerce.hteapp.model.MCarrito;
import com.hteecommerce.hteapp.model.MComentario;
import com.hteecommerce.hteapp.model.MComprobante;
import com.hteecommerce.hteapp.model.MDetalleComprobante;
import com.hteecommerce.hteapp.model.MDetalleIngreso;
import com.hteecommerce.hteapp.model.MDetallePago;
import com.hteecommerce.hteapp.model.MDetalleResumenVenta;
import com.hteecommerce.hteapp.model.MDireccionEnvio;
import com.hteecommerce.hteapp.model.MResumenVenta;
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

        if (po.getColor() != null ||
                po.getMaterial() != null || po.getMedida() != null ||
                po.getModelo() != null || po.getPeso() != null) {

            return true;
        }

        return false;
    }

    public static boolean isPresentProductoVestimenta(ProductoVestimenta pv) {
        if (pv == null) {
            return false;
        }

        if (pv.getMaterial() != null || pv.getModelo() != null) {

            return true;
        }

        return false;
    }

    // validando detalle ingreso
    public static Stream<String> isValidDetalleIngreso(DetalleIngreso di) {
        List<String> mensajes = new ArrayList<>();

        if (di.getPrecioVenta() == null || di.getPrecioVenta() == 0) {
            mensajes.add("El precio de venta no puede ser nulo");
        }

        if (di.getPrecioVentaAnterior() == null || di.getPrecioVentaAnterior() == 0) {
            mensajes.add("EL precio de venta anterior no puede ser nulo ni cero");
        }

        if (di.getPorcentajeDescuento() == null || di.getPorcentajeDescuento() == 0) {
            mensajes.add("El porcentaje de descuento no debe ser nulo");
        }

        if (di.getStockInicial() == null || di.getStockInicial() == 0) {
            mensajes.add("Stock inicial no debe ser nulo ni cero");
        }

        if (di.getStockActual() == null || di.getStockActual() == 0) {
            mensajes.add("Stock Actual no debe ser nulo ni cero");
        }

        if (di.getEstado() == null) {
            mensajes.add("Estado no debe ser nulo");
        }

        if (di.getSucursal() == null) {
            mensajes.add("La sucursal no puede ser nulo");
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
                            di.getEstado(), di.getSucursal(), di.getProducto(),
                            di.getVariedades(), di.getIngresoId());
                })
                .collect(Collectors.toList());

        return mlista;

    }

    public static List<MDetalleIngreso> mapDetalleIngresosTienda(List<DetalleIngreso> dis) {

        List<MDetalleIngreso> mlista = dis.stream()
                .map(di -> mapDetalleIngresoTienda(di))
                .collect(Collectors.toList());

        return mlista;
    }

    public static MDetalleIngreso mapDetalleIngresoTienda(DetalleIngreso di) {
        return new MDetalleIngreso(di.getIddetalleingreso(), di.getPrecioVenta(),
                di.getPrecioVentaAnterior(), di.getPorcentajeDescuento(),
                di.getStockActual(),
                di.getFechaProduccion(), di.getFechaVencimiento(),
                di.getEstado(), di.getSucursal(), di.getProducto(), di.getVariedades());
    }

    public static List<MComprobante> mapComprobantes(List<Comprobante> lista) {
        List<MComprobante> mlista = lista.stream()
                .map(com -> Mapper.mapComprobante(com))
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
                die.getPrincipal(), die.getDestinatario());

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

        mlista = lista.stream()
                .map(co -> mapComentario(co))
                .limit(20)
                .collect(Collectors.toList());
        return mlista;
    }

    public static MComentario mapComentario(Comentario co) {
        MComentario mco = new MComentario(co);
        mco.getCliente().getPersona().setDni(null);
        mco.getCliente().getPersona().setDireccion(null);
        mco.getCliente().getPersona().setTelefono(null);
        return mco;
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
        int cont = 1;

        for (DetalleIngreso di : lista) {
            HistoricoPrecio hp = new HistoricoPrecio(di.getPrecioVenta(), di.getPrecioVentaAnterior(), cont,
                    di.getCreateAt().getYear() + "");
            hps.add(hp);
            cont++;
        }

        return hps;
    }

    // agregando variedades del ingreso anterior
    public static List<Variedad> mapVariedadesNuevos(List<Variedad> vasNuevos, List<Variedad> anteriores) {

        List<Variedad> vasAnteriores = anteriores.stream()
                        .filter(va -> va.getCantidadTalla() > 0)
                        .collect(Collectors.toList());                   

        List<Variedad> lista_nueva = vasNuevos;

        for(Variedad vaAnterior : vasAnteriores) {           
            lista_nueva = mapVariedadesExistentes(lista_nueva, vaAnterior);
        }

        return lista_nueva;

    }

    public static List<Variedad> mapVariedadesExistentes(List<Variedad> vasNuevos, Variedad variedad) {

        List<Variedad> existeVariedades = vasNuevos.stream()
                .filter(va -> va.getNombreTalla().toLowerCase().equals(variedad.getNombreTalla().toLowerCase()))
                .collect(Collectors.toList());
        
        if(existeVariedades.size() != 0){
           
            for(Variedad va : vasNuevos){
                if(va.getNombreTalla().toLowerCase().equals(variedad.getNombreTalla().toLowerCase())){
                    va = mapVariedad(va, variedad);
                }
            }

            return vasNuevos;

        }
        else{

            List<Color> colores = variedad.getColores().stream()
                                .filter(co -> co.getCantidadColor() > 0)
                                .collect(Collectors.toList());

            List<Color> colores_nuevos = colores.stream()
                                .map(co -> new Color(co.getNombreColor(), co.getCantidadColor(), co.getNombreImagen()))
                                .collect(Collectors.toList());

            Variedad vari = new Variedad(variedad.getNombreTalla(),
                                variedad.getCantidadTalla(), colores_nuevos);
            
            vasNuevos.add(vari);
            return vasNuevos;
        }        

    }

    public static Variedad mapVariedad(Variedad nuevo, Variedad anterior){       

        List<Color> coloresAnteriores = anterior.getColores().stream()
                            .filter(co -> co.getCantidadColor() > 0)
                            .collect(Collectors.toList());

        List<Color> nueva_lista = nuevo.getColores();

        for(Color color : coloresAnteriores){
            nueva_lista = mapColores(nueva_lista, color);
        }       

        return new Variedad(nuevo.getNombreTalla(),
                            nuevo.getCantidadTalla() + anterior.getCantidadTalla(), 
                            nueva_lista);
    }

    public static List<Color> mapColores(List<Color> cosNuevos, Color coAnterior){
        
        List<Color> color_lista = cosNuevos.stream()
                        .filter(co -> co.getNombreColor().toLowerCase().equals(coAnterior.getNombreColor().toLowerCase()))
                        .collect(Collectors.toList());

        if(color_lista.size() != 0){

            for(Color co : cosNuevos){
                if(co.getNombreColor().toLowerCase().equals(coAnterior.getNombreColor().toLowerCase())){

                    co.setCantidadColor(co.getCantidadColor() + coAnterior.getCantidadColor());                            
                }
            }

            return cosNuevos;
            
        }
        else{
            Color col = new Color(coAnterior.getNombreColor(),
                coAnterior.getCantidadColor(), coAnterior.getNombreImagen());
            cosNuevos.add(col);

            return cosNuevos;
        }
    }

    //map degalle pago
    public static MDetallePago mapDetallePago(DetallePago dp){
        MDetallePago mdp = new MDetallePago(dp.getIddetallepago(), dp.getEstadoPago(), 
                            dp.getFormaPago(), dp.getMarcaTarjeta(), dp.getTipoTarjeta(), 
                            dp.getFechaCreacion(), dp.getFechaExpiracion());
        return mdp;
    }

    public static MComprobante mapComprobante(Comprobante com){
        MComprobante mcom = new MComprobante(com.getIdcomprobante(), com.getNumero(), 
                com.getIdtransaccion(), com.getTipoComprobante(), com.getFechaPedido(), 
                com.getEstado(), com.getIgv(), com.getMontoEnvio(), com.getSubTotal(), 
                com.getTotal(), com.getDescuento(), com.getFechaEntrega(), com.getNbolsa(),
                com.getFormaEnvio(), com.getRuc(), com.getRazonSocial(), com.getDireccionEnvio(), com.getDetallePago(), 
                com.getDetalleComprobantes());
        return mcom;
    }

    public static List<MDetalleComprobante> mapDetalleComprobantes(List<DetalleComprobante> lista){
        List<MDetalleComprobante> mlista = new ArrayList<>();
        for(DetalleComprobante dc : lista){
            MDetalleComprobante mdc = new MDetalleComprobante(dc.getIddetallecomprobante(), dc.getCantidad(), dc.getDescuento(), 
                dc.getPrecioUnitario(), dc.getSubTotal(), dc.getVariedades(), dc.getComprobanteId());
            mlista.add(mdc);
        }

        return mlista;
    }

    public static List<DetalleComprobante> unirDetalleComprobantes(List<Comprobante> lista){
        List<DetalleComprobante> dcs = new ArrayList<>();
        for(Comprobante com : lista){
            dcs.addAll(com.getDetalleComprobantes());
        }

        return dcs;
    }

    public static List<MResumenVenta> agruparDetalleComprobantes(List<Comprobante> coms, List<DetalleComprobante> lista){

        List<MResumenVenta> mresumenes = new ArrayList<>(); 
        
        Map<String, List<DetalleComprobante>> result = lista.stream()
            .collect(Collectors.groupingBy(dc -> dc.getDetalleIngreso().getProducto().getNombre()));
        
        for(Map.Entry<String, List<DetalleComprobante>> pair : result.entrySet()){
            mresumenes.add(new MResumenVenta(pair.getKey(), asignarNombreImagen(pair.getValue()), asignarComprobante(coms, pair.getValue())));
        }        

        List<MResumenVenta> m_resumenes = new ArrayList<>(); 

        for(MResumenVenta rm : mresumenes){

            Map<String, List<MDetalleResumenVenta>> resultTwo = rm.getDetalleResumenVentas().stream().collect(Collectors.groupingBy(drv -> drv.getFechaEntrega().toString().split("T")[0]));
            for(Map.Entry<String, List<MDetalleResumenVenta>> pair : resultTwo.entrySet()){

                m_resumenes.add(new MResumenVenta(rm.getNombreProducto(), rm.getImagenProducto(), LocalDate.parse(pair.getKey()), pair.getValue()));
            }
        }

        return m_resumenes;        
    }        

    public static List<MDetalleResumenVenta> asignarComprobante(List<Comprobante> coms, List<DetalleComprobante> dcs){

        List<MDetalleResumenVenta> detalleResumenVentas = new ArrayList<>();

        for(DetalleComprobante dc : dcs){
            for(Comprobante com : coms){
                if(com.getIdcomprobante() == dc.getComprobanteId()){
                    MDetalleResumenVenta mdetalleResumen = new MDetalleResumenVenta(obtenerDocumento(com.getDireccionEnvio()),
                                        obtenerNombres(com.getDireccionEnvio()), com.getNumero(), 
                                        com.getFechaPedido(), com.getFechaEntrega(), dc);
                    
                    detalleResumenVentas.add(mdetalleResumen);
                    break;
                }
            }
        }        


        return detalleResumenVentas;
    }    

    public static String asignarNombreImagen(List<DetalleComprobante> lista){
        return lista.get(0).getDetalleIngreso().getProducto().getProductoImagenes().get(0).getImagen();
    }

    public static String obtenerDocumento(DireccionEnvio de){

        if(de.getDestinatario() != null){
            return de.getDestinatario().getDni();
        }
        else{
            return de.getCliente().getPersona().getDni();
        }

    }

    public static String obtenerNombres(DireccionEnvio de){

        if(de.getDestinatario() != null){
            return de.getDestinatario().getNombre() + " " + de.getDestinatario().getApellidos();
        }
        else{
            return de.getCliente().getPersona().getNombre() + " " + de.getCliente().getPersona().getApellidos();
        }

    }

}
