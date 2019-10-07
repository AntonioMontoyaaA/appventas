package neto.com.mx.aperturatienda.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import neto.com.mx.aperturatienda.model.Indicadores.CursorIndicadoresVo;
import neto.com.mx.aperturatienda.model.Indicadores.CursorTopArticulosVo;
import neto.com.mx.aperturatienda.model.Indicadores.IndicadorResponse;
import neto.com.mx.aperturatienda.model.Login.Permisos;
import neto.com.mx.aperturatienda.model.Login.ResultadoVo;
import neto.com.mx.aperturatienda.model.Login.UsuarioRowVo;
import neto.com.mx.aperturatienda.model.caracteristicasGenerales.Caracteristica;
import neto.com.mx.aperturatienda.model.guardacaracteristica.GuardaCaracteristicaResponse;
import neto.com.mx.aperturatienda.model.tiendaEspecifica.CaracteristicasTResponse;
import neto.com.mx.aperturatienda.model.Login.Login;
import neto.com.mx.aperturatienda.model.tiendasLista.Tienda;
import neto.com.mx.aperturatienda.model.tiendasLista.TiendasResponse;
import neto.com.mx.aperturatienda.model.caracteristicasGenerales.CaracteristicasGResponse;

public class Util {
    static final String TAG = "Util";
    public static GuardaCaracteristicaResponse modelGuardaCaracteristicasResponse(SoapObject servicio, Context context) {

       final String str = servicio.toString();
//        Log.d(TAG,str+" < ------ respuesta servicio");
        GuardaCaracteristicaResponse item = new GuardaCaracteristicaResponse();

        int count = servicio.getPropertyCount();
        if (!str.contains("Error")) {

            SoapPrimitive codigo = (SoapPrimitive) servicio.getProperty("codigo");
            SoapPrimitive mensaje = (SoapPrimitive) servicio.getProperty("mensaje");

            item.setCodigo(Integer.valueOf((String) codigo.getValue()));
            item.setMensaje((String) mensaje.getValue());

        }
        return item;
    }
    public static CaracteristicasGResponse modelParseCaracteristicasGenerales(SoapObject servicio, Context context) {
        final String str = servicio.toString();
        CaracteristicasGResponse item = new CaracteristicasGResponse();
        ArrayList<Caracteristica> caracteristicas = null;
        int count = servicio.getPropertyCount();
        if (str.contains("Error")) {
            SoapPrimitive coidgo = (SoapPrimitive) servicio.getProperty("codigo");
            SoapPrimitive mensaje = (SoapPrimitive) servicio.getProperty("mensaje");
            item.setCodigo(Integer.valueOf((String) coidgo.getValue()));
            item.setMensake((String) mensaje.getValue());
            item.setListaCaracteristicas(caracteristicas);
        } else {
            if (count > 0) {
                caracteristicas = new ArrayList<>();
                for (int i = 0; i < count; i++) {
                    Caracteristica items = new Caracteristica();
                    if (servicio.getPropertyAsString(i).contains("CaracteristicaVo")) {
                        SoapObject pojoSoap = (SoapObject) servicio.getProperty(i);
                        SoapPrimitive caracteristica = (SoapPrimitive) pojoSoap.getProperty("caracteristica");
                        SoapPrimitive caracteristicaId = (SoapPrimitive) pojoSoap.getProperty("caracteristicaId");
                        SoapPrimitive nivelCaracteristica = (SoapPrimitive) pojoSoap.getProperty("nivelCaracteristica");
                        SoapPrimitive nivelCaracteristicaId = (SoapPrimitive) pojoSoap.getProperty("nivelCaracteristicaId");

                        items.setCaracteristica((String) caracteristica.getValue());
                        items.setCaracteristicaId(Integer.valueOf((String) caracteristicaId.getValue()));
                        items.setNivelCaracteristica((String) nivelCaracteristica.getValue());
                        items.setNivelCaracteristicaId(Integer.valueOf((String) nivelCaracteristicaId.getValue()));
                        caracteristicas.add(items);
                    }
                }
                if (!str.contains("null")) {
                    SoapPrimitive coidgo = (SoapPrimitive) servicio.getProperty("codigo");
                    SoapPrimitive mensaje = (SoapPrimitive) servicio.getProperty("mensaje");
                    item.setCodigo(Integer.valueOf((String) coidgo.getValue()));
                    item.setMensake((String) mensaje.getValue());
                    item.setListaCaracteristicas(caracteristicas);
                }
            }
        }
        return item;
    }

    public TiendasResponse modelTiendasParse(SoapObject servicio, Context context) {
        final String str = servicio.toString();
//        Log.d(TAG,str+" < ------ respuesta servicio");
        TiendasResponse item = new TiendasResponse();
        ArrayList<Tienda> tiendas = null;
        int count = servicio.getPropertyCount();
        if (str.contains("Error")) {

            SoapPrimitive codigo = (SoapPrimitive) servicio.getProperty("codigo");
            SoapPrimitive mensaje = (SoapPrimitive) servicio.getProperty("mensaje");
            item.setCodigo(Integer.valueOf((String) codigo.getValue()));
            item.setMensaje((String) mensaje.getValue());
            item.setListaTiendas(tiendas);

        } else {
            if (count > 0) {
                tiendas = new ArrayList<>();
                for (int i = 0; i < count; i++) {
                    Tienda items = new Tienda();
                    //Log.d(TAG, servicio.getPropertyAsString(i));
                    if (servicio.getPropertyAsString(i).contains("TiendaVo")) {
                        SoapObject pojoSoap = (SoapObject) servicio.getProperty(i);

                        SoapPrimitive direccion = (SoapPrimitive) pojoSoap.getProperty("direccion");
                        SoapPrimitive fechaApertura = (SoapPrimitive) pojoSoap.getProperty("fechaApertura");
                        SoapPrimitive nombreTienda = (SoapPrimitive) pojoSoap.getProperty("nombreTienda");
                        SoapPrimitive paisId = (SoapPrimitive) pojoSoap.getProperty("paisId");
                        SoapPrimitive porcentajeAvance = (SoapPrimitive) pojoSoap.getProperty("porcentajeAvance");
                        SoapPrimitive region = (SoapPrimitive) pojoSoap.getProperty("region");
                        SoapPrimitive regionId = (SoapPrimitive) pojoSoap.getProperty("regionId");
                        SoapPrimitive tiendaId = (SoapPrimitive) pojoSoap.getProperty("tiendaId");
                        SoapPrimitive zona = (SoapPrimitive) pojoSoap.getProperty("zona");
                        SoapPrimitive zonaId = (SoapPrimitive) pojoSoap.getProperty("zonaId");

                        items.setDireccion((String) direccion.getValue());
                        items.setFechaApertura((String) fechaApertura.getValue());
                        items.setNombreTienda((String) nombreTienda.getValue());
                        items.setPaisId(Integer.valueOf((String) paisId.getValue()));
                        items.setPorcentajeAvance(Float.valueOf((String) porcentajeAvance.getValue()));
                        items.setRegion((String) region.getValue());
                        items.setRegionId(Integer.valueOf((String) regionId.getValue()));
                        items.setTiendaId((Integer.valueOf((String) tiendaId.getValue())));
                        items.setZona((String) zona.getValue());
                        items.setZonaId(Integer.valueOf((String) zonaId.getValue()));
                        tiendas.add(items);
                    }
                }
                if (!str.contains("null")) {
                    SoapPrimitive codigo = (SoapPrimitive) servicio.getProperty("codigo");
                    SoapPrimitive mensaje = (SoapPrimitive) servicio.getProperty("mensaje");
                    item.setCodigo(Integer.valueOf((String) codigo.getValue()));
                    item.setMensaje((String) mensaje.getValue());
                    item.setListaTiendas(tiendas);
                }
            }
        }
        return item;
    }
    public IndicadorResponse modelIndicadoresParse(SoapObject servicio, Context context) {
        IndicadorResponse item = new IndicadorResponse();
        final String str = servicio.toString();

        if (!str.contains("null")) {
            Log.d(TAG, str+"");
            SoapPrimitive codigo = (SoapPrimitive) servicio.getProperty("codigo");
            SoapPrimitive mensaje = (SoapPrimitive) servicio.getProperty("mensaje");

            item.setCodigo(Integer.valueOf((String) codigo.getValue()));
            item.setMensaje((String)mensaje.getValue());

            int count = servicio.getPropertyCount();
//            Log.d(TAG, count+"  hubo count");
            if (count > 0) {
                CursorIndicadoresVo cursorIndicadores= new CursorIndicadoresVo(); // se crea lista para el cursor
                List<CursorTopArticulosVo> cursorTopArtMayorVta = new ArrayList<>();
                List<CursorTopArticulosVo> cursorTopArtMenorVta = new ArrayList<>();

                for (int i = 0; i < count; i++) {
                    if (servicio.getPropertyInfo(i).toString().contains("cursorIndicadores")) {
                        SoapObject pojoSoap = (SoapObject) servicio.getProperty(i);

                        SoapPrimitive diasInvConfigurados = (SoapPrimitive) pojoSoap.getProperty("diasInvConfigurados");
                        SoapPrimitive existencia = (SoapPrimitive) pojoSoap.getProperty("existencia");
                        SoapPrimitive frecuencia = (SoapPrimitive) pojoSoap.getProperty("frecuencia");
                        SoapPrimitive montoVenta = (SoapPrimitive) pojoSoap.getProperty("montoVenta");
                        SoapPrimitive nombre = (SoapPrimitive) pojoSoap.getProperty("nombre");
                        SoapPrimitive stockout = (SoapPrimitive) pojoSoap.getProperty("stockout");
                        SoapPrimitive tamanioTienda = (SoapPrimitive) pojoSoap.getProperty("tamanioTienda");
                        SoapPrimitive tiendaId = (SoapPrimitive) pojoSoap.getProperty("tiendaId");
                        SoapPrimitive ventaAlcohol = (SoapPrimitive) pojoSoap.getProperty("ventaAlcohol");
                        SoapPrimitive ventaPerdida = (SoapPrimitive) pojoSoap.getProperty("ventaPerdida");
                        SoapPrimitive ventaPiezas = (SoapPrimitive) pojoSoap.getProperty("ventaPiezas");
                        SoapPrimitive fechaApertura = (SoapPrimitive) pojoSoap.getProperty("fechaApertura");

                        cursorIndicadores.setDiasInvConfigurados(Integer.valueOf((String)diasInvConfigurados.getValue()));
                        cursorIndicadores.setExistencia(Integer.valueOf((String)existencia.getValue()));
                        cursorIndicadores.setFrecuencia((String) frecuencia.getValue());
                        cursorIndicadores.setMontoVenta(Float.valueOf((String) montoVenta.getValue()));
                        cursorIndicadores.setNombre((String) nombre.getValue());
                        cursorIndicadores.setStockout(Integer.valueOf((String) stockout.getValue()));
                        cursorIndicadores.setTamanioTienda((String) tamanioTienda.getValue());
                        cursorIndicadores.setTiendaId(Integer.valueOf((String) tiendaId.getValue()));
                        cursorIndicadores.setVentaAlcohol((String) ventaAlcohol.getValue());
                        cursorIndicadores.setVentaPerdida(Integer.valueOf((String) ventaPerdida.getValue()));
                        cursorIndicadores.setVentaPiezas(Float.valueOf((String) ventaPiezas.getValue()));
                        cursorIndicadores.setFechaApertura((String) fechaApertura.getValue());

                        item.setCursorIndicadores(cursorIndicadores);
                    }
                    if (servicio.getPropertyInfo(i).toString().contains("cursorTopArtMayorVta")) {
                        CursorTopArticulosVo articulo = new CursorTopArticulosVo();
                        SoapObject pojoSoap = (SoapObject) servicio.getProperty(i);

                        SoapPrimitive articuloId = (SoapPrimitive) pojoSoap.getProperty("articuloId");
                        SoapPrimitive cantidad = (SoapPrimitive) pojoSoap.getProperty("cantidad");
                        SoapPrimitive diasInvReales = (SoapPrimitive) pojoSoap.getProperty("diasInvReales");
                        SoapPrimitive existencia = (SoapPrimitive) pojoSoap.getProperty("existencia");
                        SoapPrimitive monto = (SoapPrimitive) pojoSoap.getProperty("monto");
                        SoapPrimitive nombreArt = (SoapPrimitive) pojoSoap.getProperty("nombreArt");


                        articulo.setArticuloId(Long.valueOf((String)articuloId.getValue()));
                        articulo.setCantidad(Float.valueOf((String)cantidad.getValue()));
                        articulo.setDiasInvReales(Float.valueOf((String)diasInvReales.getValue()));
                        articulo.setExistencia(Float.valueOf((String) existencia.getValue()));
                        articulo.setMonto(Float.valueOf((String) monto.getValue()));
                        articulo.setNombreArt((String) nombreArt.getValue());

                        cursorTopArtMayorVta.add(articulo);

                    }
                    if (servicio.getPropertyInfo(i).toString().contains("cursorTopArtMenorVta")) {
                        CursorTopArticulosVo articulo = new CursorTopArticulosVo();
                        SoapObject pojoSoap = (SoapObject) servicio.getProperty(i);

                        SoapPrimitive articuloId = (SoapPrimitive) pojoSoap.getProperty("articuloId");
                        SoapPrimitive cantidad = (SoapPrimitive) pojoSoap.getProperty("cantidad");
                        SoapPrimitive diasInvReales = (SoapPrimitive) pojoSoap.getProperty("diasInvReales");
                        SoapPrimitive existencia = (SoapPrimitive) pojoSoap.getProperty("existencia");
                        SoapPrimitive monto = (SoapPrimitive) pojoSoap.getProperty("monto");
                        SoapPrimitive nombreArt = (SoapPrimitive) pojoSoap.getProperty("nombreArt");

                        articulo.setArticuloId(Long.valueOf((String)articuloId.getValue()));
                        articulo.setCantidad(Float.valueOf((String)cantidad.getValue()));
                        articulo.setDiasInvReales(Float.valueOf((String)diasInvReales.getValue()));
                        articulo.setExistencia(Float.valueOf((String) existencia.getValue()));
                        articulo.setMonto(Float.valueOf((String) monto.getValue()));
                        articulo.setNombreArt((String) nombreArt.getValue());

                        cursorTopArtMenorVta.add(articulo);
                    }
                }
                item.setCursorTopArtMayorVta(cursorTopArtMayorVta);
                item.setCursorTopArtMenorVta(cursorTopArtMenorVta);

            }
        }
        return item;
    }
    public CaracteristicasTResponse modelCaracteristicasTResponse(SoapObject servicio, Context context) {
        final String str = servicio.toString();
              Log.d(TAG,str+" < ------ respuesta servicio");

        CaracteristicasTResponse item = new CaracteristicasTResponse();
        ArrayList<Caracteristica> caracteristicas = null;

        if (str.contains("error")) {
            SoapPrimitive codigo = (SoapPrimitive) servicio.getProperty("codigo");
            SoapPrimitive mensaje = (SoapPrimitive) servicio.getProperty("mensaje");
            item.setCodigo(Integer.valueOf((String) codigo.getValue()));
            item.setMensaje((String) mensaje.getValue());
            item.setListaCaracteristicas(caracteristicas);

//            Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
        }
         else {
            if (!str.contains("null")) {
                SoapPrimitive codigo = (SoapPrimitive) servicio.getProperty("codigo");
                SoapPrimitive mensaje = (SoapPrimitive) servicio.getProperty("mensaje");
                SoapPrimitive nombreTienda = (SoapPrimitive) servicio.getProperty("nombreTienda");

                item.setCodigo(Integer.valueOf((String) codigo.getValue()));
                item.setMensaje((String) mensaje.getValue());
                item.setNombreTienda((String) nombreTienda.getValue());

            }
            int count = servicio.getPropertyCount();
            if (count > 0) {
                caracteristicas = new ArrayList<>();
                for (int i = 0; i < count; i++) {
                    Caracteristica items = new Caracteristica();

                    if (servicio.getPropertyAsString(i).contains("CaracteristicaTiendaVo")) {
                        SoapObject pojoSoap = (SoapObject) servicio.getProperty(i);

                        SoapPrimitive caracteristicaId = (SoapPrimitive) pojoSoap.getProperty("caracteristicaId");
                        SoapPrimitive caracteristica = (SoapPrimitive) pojoSoap.getProperty("caracteristica");
                        SoapPrimitive nivelCaracteristicaId = (SoapPrimitive) pojoSoap.getProperty("nivelCaracteristicaId");
                        SoapPrimitive nivelCaracteristica = (SoapPrimitive) pojoSoap.getProperty("nivelCaracteristica");
                        SoapPrimitive componenteHtmlId = (SoapPrimitive) pojoSoap.getProperty("componenteHtmlId");
                        SoapPrimitive componenteHtml = (SoapPrimitive) pojoSoap.getProperty("componenteHtml");
                        SoapPrimitive valor = (SoapPrimitive) pojoSoap.getProperty("valor");
                        SoapPrimitive unidadMedicion = (SoapPrimitive) pojoSoap.getProperty("unidadMedicion");
                        SoapPrimitive numComponenteHtml = (SoapPrimitive) pojoSoap.getProperty("numComponenteHtml");
                        SoapPrimitive comentarios = (SoapPrimitive) pojoSoap.getProperty("comentarios");
                        SoapPrimitive valorReal = (SoapPrimitive) pojoSoap.getProperty("valorReal");
                        SoapPrimitive ordenamientoCaract = (SoapPrimitive) pojoSoap.getProperty("ordenamientoCaract");
                        SoapPrimitive ordenamientoNivelCaract = (SoapPrimitive) pojoSoap.getProperty("ordenamientoNivelCaract");
                        SoapPrimitive valorGuardado = (SoapPrimitive) pojoSoap.getProperty("valorGuardado");

                        items.setCaracteristicaId(Integer.valueOf((String) caracteristicaId.getValue()));
                        items.setCaracteristica((String) caracteristica.getValue());
                        items.setNivelCaracteristicaId(Integer.valueOf((String) nivelCaracteristicaId.getValue()));
                        items.setNivelCaracteristica((String) nivelCaracteristica.getValue());
                        items.setComponenteHtmlId(Integer.valueOf((String) componenteHtmlId.getValue()));
                        items.setComponenteHtml((String) componenteHtml.getValue());
                        items.setValor(Float.valueOf((String)valor.getValue()));
                        items.setUnidadMedicion((String) unidadMedicion.getValue());
                        items.setNumComponenteHtml(Integer.valueOf((String) numComponenteHtml.getValue()));
                        items.setComentarios((String) comentarios.getValue());
                        items.setValorReal((String) valorReal.getValue());
                        items.setOrdenamientoCaract(Integer.valueOf((String) ordenamientoCaract.getValue()));
                        items.setOrdenamientoNivelCaract(Integer.valueOf((String) ordenamientoNivelCaract.getValue()));
                        items.setValorGuardado(Integer.valueOf((String) valorGuardado.getValue()));


                        caracteristicas.add(items);
                    }
                }
                item.setListaCaracteristicas(caracteristicas);
            }
        }
        return item;
    }

    /**
     * Método que obtiene un SoapObject y regresa el modelo completo de la respuesta del Actualizar Servicio
     *
     * @param servicio
     * @return
     */
    public Login modelLoginParse(SoapObject servicio) {
        Login item = new Login();
        final String str = servicio.toString();
        Log.d(TAG,str+" < ------ respuesta servicio");
        if (!str.contains("null")) {
            SoapPrimitive codigo = (SoapPrimitive) servicio.getProperty("codigo"); //se obtienen los campos de la respuesta
            SoapPrimitive statusMsj = (SoapPrimitive) servicio.getProperty("statusMsj"); // se hace set al obj correpondiente

            item.setCodigo(Integer.valueOf((String) codigo.getValue()));
            item.setStatusMsj((String) statusMsj.getValue());

            int count = servicio.getPropertyCount();
            if (count > 0) {
                List<UsuarioRowVo> listaUsuarioRowVo= new ArrayList<>(); // se crea lista para el cursor
                for (int i = 0; i < count; i++) {
                    if (servicio.getPropertyAsString(i).contains("UsuarioRowVo")) {
                        UsuarioRowVo usuario = new UsuarioRowVo();
                        SoapObject pojoSoap = (SoapObject) servicio.getProperty(i);

                        SoapPrimitive apellidoMaterno = ((SoapPrimitive) pojoSoap.getProperty("apellidoMaterno")) == null ? (SoapPrimitive) pojoSoap.getProperty("guion") : ((SoapPrimitive) pojoSoap.getProperty("apellidoMaterno"));
                        SoapPrimitive apellidoPaterno = (SoapPrimitive) pojoSoap.getProperty("apellidoPaterno");
                        SoapPrimitive contrasena = (SoapPrimitive) pojoSoap.getProperty("contrasena");
                        SoapPrimitive estatus = (SoapPrimitive) pojoSoap.getProperty("estatus");
                        SoapPrimitive nombre = (SoapPrimitive) pojoSoap.getProperty("nombre");
                        SoapPrimitive puestoId = (SoapPrimitive) pojoSoap.getProperty("puestoId");
                        SoapPrimitive tiendaId = (SoapPrimitive) pojoSoap.getProperty("tiendaId");
                        SoapPrimitive usuarioId = (SoapPrimitive) pojoSoap.getProperty("usuarioId");

                        usuario.setApellidoMaterno((String) apellidoMaterno.getValue());
                        usuario.setApellidoPaterno((String) apellidoPaterno.getValue());
                        usuario.setContrasena((String) contrasena.getValue());
                        usuario.setEstatus(Integer.valueOf((String) estatus.getValue()));
                        usuario.setNombre((String) nombre.getValue());
                        usuario.setPuestoId(Integer.valueOf((String) puestoId.getValue()));
                        usuario.setTiendaId((String) tiendaId.getValue());
                        usuario.setUsuarioId(Integer.valueOf((String) usuarioId.getValue()));

                        listaUsuarioRowVo.add(usuario);
                        item.setUsuarioRowVo(listaUsuarioRowVo);
                    }
                }
            }
        }
        return item;
    }
    public Permisos modelPermisosParse(SoapObject servicio) {
        Permisos item = new Permisos();
        String str = servicio.toString();
//      Log.d(TAG,str+" < ------ respuesta servicio permisos");
        if (!str.contains("null")) {
            SoapPrimitive statusCode = (SoapPrimitive) servicio.getProperty("statusCode"); //se obtienen los campos de la respuesta
            SoapPrimitive statusMsj = (SoapPrimitive) servicio.getProperty("statusMsj"); // se hace set al obj correpondiente

            item.setStatusCode(Integer.valueOf((String) statusCode.getValue()));
            item.setStatusMsj((String) statusMsj.getValue());

            int count = servicio.getPropertyCount();
            if (count > 0) {
                List<ResultadoVo> listaResultadoVo= new ArrayList<>(); // se crea lista para el cursor
                for (int i = 0; i < count; i++) {
                    if (servicio.getPropertyAsString(i).contains("ResultadoVo")) {
                        ResultadoVo resultado = new ResultadoVo();
                        SoapObject pojoSoap = (SoapObject) servicio.getProperty(i);

                        SoapPrimitive moduloId = (SoapPrimitive) pojoSoap.getProperty("moduloId");
                        SoapPrimitive opcionId = (SoapPrimitive) pojoSoap.getProperty("opcionId");
                        SoapPrimitive opcionSistemaId = (SoapPrimitive) pojoSoap.getProperty("opcionSistemaId");
                        SoapPrimitive perfilId = (SoapPrimitive) pojoSoap.getProperty("perfilId");
                        SoapPrimitive permisoId = (SoapPrimitive) pojoSoap.getProperty("permisoId");
                        SoapPrimitive sistemaId = (SoapPrimitive) pojoSoap.getProperty("sistemaId");
                        SoapPrimitive submoduloId = (SoapPrimitive) pojoSoap.getProperty("submoduloId");

                        resultado.setModuloId(Integer.valueOf((String) moduloId.getValue()));
                        resultado.setOpcionId(Integer.valueOf((String) opcionId.getValue()));
                        resultado.setOpcionSistemaId((String) opcionSistemaId.getValue());
                        resultado.setPerfilId(Integer.valueOf((String) perfilId.getValue()));
                        resultado.setPermisoId(Integer.valueOf((String) permisoId.getValue()));
                        resultado.setSistemaId(Integer.valueOf((String) sistemaId.getValue()));
                        resultado.setSubmoduloId(Integer.valueOf((String) submoduloId.getValue()));

                       listaResultadoVo.add(resultado);
                    }
                }
                item.setListaResultado(listaResultadoVo);
            }
        }
        return item;
    }

    public static final String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++) {
                String h = Integer.toHexString(0xFF & messageDigest[i]);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void loadingProgress(final ProgressDialog progressDialog, int i) {
        if (i == 0) {
            progressDialog.dismiss();
            progressDialog.setTitle("Consultando...");
            progressDialog.setMessage("Espera mientras se carga la información...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        } else {
            progressDialog.dismiss();
        }
    }

}