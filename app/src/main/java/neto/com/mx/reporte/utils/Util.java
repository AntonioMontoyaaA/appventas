package neto.com.mx.reporte.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import neto.com.mx.reporte.R;
import neto.com.mx.reporte.model.dashboard.Tienda;
import neto.com.mx.reporte.model.dashboard.Tiendas;
import neto.com.mx.reporte.model.dashboard.Ventas;
import neto.com.mx.reporte.model.dashboard.VentasResponse;
import neto.com.mx.reporte.model.login.Login;
import neto.com.mx.reporte.ui.ActivityLogin;

public class Util {

    /**
     * Método que muestra el progress bar de manera horizontal programáticamente
     * @param context
     * @param view
     * @param index
     */
    public static void addProgressBar(Context context, ViewGroup view, int index) {

        ProgressBar progressBar = new ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal);

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );

        progressBar.setLayoutParams(layoutParams);
        progressBar.getIndeterminateDrawable().setColorFilter(0xffffffff, android.graphics.PorterDuff.Mode.MULTIPLY);
        progressBar.setIndeterminate(true);
        view.addView(progressBar, index);
    }

    public static boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    /**
     * Método que obtiene un SoapObject y regresa el modelo completo de la respuesta del Actualizar Servicio
     * @param servicio
     * @return
     */
    public Login modelLoginParse(SoapObject servicio){
        Login item = new Login();
        final String str = servicio.toString();
        if(!str.contains("null")){

            SoapPrimitive codigo = (SoapPrimitive) servicio.getProperty(0);
            SoapPrimitive esUsuarioValido = (SoapPrimitive) servicio.getProperty(1);
            SoapPrimitive mensaje = (SoapPrimitive) servicio.getProperty(2);
            SoapPrimitive nombreUsuario = (SoapPrimitive) servicio.getProperty(3);
            SoapPrimitive password = (SoapPrimitive) servicio.getProperty(4);

            item.setCodigo(Integer.valueOf((String) codigo.getValue()));
            item.setEsUsuarioValido(Boolean.parseBoolean((String) esUsuarioValido.getValue()));
            item.setMensaje((String) mensaje.getValue());
            item.setNombreUsuario((String) nombreUsuario.getValue());
            item.setPassword((String) password.getValue());
        }

        return item;
    }


    public Tiendas modelTiendasParse(SoapObject servicio, Context context){

        final String str = servicio.toString();
        Tiendas item = new Tiendas();
        ArrayList<Tienda> tiendas = null;
        int count = servicio.getPropertyCount();
        final String men = servicio.toString();


        if(count > 0){
            tiendas = new ArrayList<>();
            for (int i = 0; i < count; i++) {
                Tienda items = new Tienda();
                if(servicio.getPropertyAsString(i).contains("TiendaBean")){

                    SoapObject pojoSoap = (SoapObject) servicio.getProperty(i);

                    SoapPrimitive idPais = (SoapPrimitive) pojoSoap.getProperty("idPais");
                    SoapPrimitive idRegion = (SoapPrimitive) pojoSoap.getProperty("idRegion");
                    SoapPrimitive idTienda = (SoapPrimitive) pojoSoap.getProperty("idTienda");
                    SoapPrimitive idZona = (SoapPrimitive) pojoSoap.getProperty("idZona");
                    SoapPrimitive nombreTienda = (SoapPrimitive) pojoSoap.getProperty("nombreTienda");

                    items.setIdPais(Integer.valueOf( (String) idPais.getValue()));
                    items.setIdRegion(Integer.parseInt((String) idRegion.getValue()));
                    items.setIdTienda(Integer.valueOf( (String) idTienda.getValue()));
                    items.setIdZona(Integer.valueOf( (String) idZona.getValue()));
                    items.setNombreTienda((String) nombreTienda.getValue());




                    tiendas.add(items);
                }
            }
        }

        if(!str.contains("null")){
            SoapPrimitive codigo = (SoapPrimitive) servicio.getProperty("codigo");
            SoapPrimitive mensaje = (SoapPrimitive) servicio.getProperty("mensaje");
            item.setCodigo(Integer.valueOf((String) codigo.getValue()));
            item.setMensaje((String) mensaje.getValue());
            item.setTiendas(tiendas);
        }

        return item;
    }


    public VentasResponse modelVentasParse(SoapObject servicio, Context context){

        final String str = servicio.toString();
        VentasResponse item = new VentasResponse();
        ArrayList<Ventas> ventas = null;
        int count = servicio.getPropertyCount();

        final String men = servicio.toString();

        if(men.contains(context.getString(R.string.verifique))){
            Intent intent = new Intent(context, ActivityLogin.class);
            context.startActivity(intent);
            item.setMensaje(context.getString(R.string.no_tiendas));
        }else{
            if(count > 0){
                ventas = new ArrayList<>();
                for (int i = 0; i < count; i++) {
                    Ventas items = new Ventas();
                    if(servicio.getPropertyAsString(i).contains("VentaBean")){

                        SoapObject pojoSoap = (SoapObject) servicio.getProperty(i);

                        SoapPrimitive idElemento = (SoapPrimitive) pojoSoap.getProperty("idElemento");
                        SoapPrimitive nombreTienda = (SoapPrimitive) pojoSoap.getProperty("nombreElemento");
                        SoapPrimitive numeroTiendas = (SoapPrimitive) pojoSoap.getProperty("numeroTiendas");
                        SoapPrimitive ticketPromedio = (SoapPrimitive) pojoSoap.getProperty("ticketPromedio");
                        SoapPrimitive ventaReal = (SoapPrimitive) pojoSoap.getProperty("ventaReal");
                        SoapPrimitive ventaObjetivo = (SoapPrimitive) pojoSoap.getProperty("ventaObjetivo");
                        SoapPrimitive ventaPerdida = (SoapPrimitive) pojoSoap.getProperty("ventaPerdida");

                        items.setTiendaId(Integer.valueOf( (String) idElemento.getValue()));
                        items.setNombreTienda((String) nombreTienda.getValue());
                        items.setNumeroTiendas(Integer.valueOf( (String) numeroTiendas.getValue()));
                        items.setTicketPromedio(Integer.valueOf( (String) ticketPromedio.getValue()));
                        items.setVentaReal(Double.valueOf( (String) ventaReal.getValue()));
                        items.setVentaObjetivo(Double.valueOf( (String) ventaObjetivo.getValue()));
                        items.setVentaPerdida(Double.valueOf( (String) ventaPerdida.getValue()));

                        double real = Double.valueOf(items.getVentaReal());
                        double objetivo = Double.valueOf(items.getVentaObjetivo());

                        double operacion = (real/objetivo)*100;


                        items.setPorcentaje(operacion);

                        ventas.add(items);
                    }
                }
            }

            if(!str.contains("null")){

                SoapPrimitive codigo = (SoapPrimitive) servicio.getProperty("codigo");
                SoapPrimitive mensaje = (SoapPrimitive) servicio.getProperty("mensaje");
                SoapPrimitive nombreUbicacion = (SoapPrimitive) servicio.getProperty("nombreUbicacion");
                SoapPrimitive tickPromGeneral = (SoapPrimitive) servicio.getProperty("tickPromGeneral");
                SoapPrimitive tiendasConVentaGeneral = (SoapPrimitive) servicio.getProperty("tiendasConVentaGeneral");
                SoapPrimitive ubicacionId = (SoapPrimitive) servicio.getProperty("ubicacionId");
                SoapPrimitive vObjetivoGeneral = (SoapPrimitive) servicio.getProperty("vObjetivoGeneral");
                SoapPrimitive vObjetivoTotal = (SoapPrimitive) servicio.getProperty("vObjetivoTotal");
                SoapPrimitive vPerdidaGeneral = (SoapPrimitive) servicio.getProperty("vPerdidaGeneral");
                SoapPrimitive vRealGeneral = (SoapPrimitive) servicio.getProperty("vRealGeneral");

                item.setCodigo(Integer.valueOf((String) codigo.getValue()));
                item.setMensaje((String) mensaje.getValue());
                item.setNombreUbicacion((String) nombreUbicacion.getValue());
                item.setTickPromGeneral((String) tickPromGeneral.getValue());
                item.setTiendasConVentaGeneral((String) tiendasConVentaGeneral.getValue());
                item.setUbicacionId((String) ubicacionId.getValue());
                item.setvObjetivoGeneral((String) vObjetivoGeneral.getValue());
                item.setvObjetivoTotal((String) vObjetivoTotal.getValue());
                item.setvPerdidaGeneral((String) vPerdidaGeneral.getValue());
                item.setvRealGeneral((String) vRealGeneral.getValue());

                item.setListaVentas(ventas);

            }
        }
        return item;
    }

    public static void loadingProgress(ProgressDialog progressDialog, int i){
        if(i == 0){
            progressDialog.dismiss();
            progressDialog.setTitle("Consultando...");
            progressDialog.setMessage("Espera mientras se carga la informacion...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }else{
            progressDialog.dismiss();
        }
    }

    public static final String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
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

    /**
     * Método que regresa un Typeface con el tipo de letra según el caso
     * @param context
     * @param tipo
     * @return
     */
    public static Typeface changeFont(Context context, int tipo) {
        Typeface font = null;
        switch (tipo) {
            case 0:
                font = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Regular.ttf");
                break;
            case 1:
                font = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Bold.ttf");
                break;
            case 2:
                font = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Light.ttf");
                break;
        }
        return font;
    }

}
