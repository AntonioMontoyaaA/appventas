package neto.com.mx.reporte.provider;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

import neto.com.mx.reporte.model.dashboard.Consulta;
import neto.com.mx.reporte.model.dashboard.VentasResponse;
import neto.com.mx.reporte.utils.HttpsTrustManager;
import neto.com.mx.reporte.utils.Util;

import static android.content.Context.MODE_PRIVATE;

public class ProviderDashboard {

    private static ProviderDashboard instance;

    String NAMESPACE = "http://servicio.rutas.movil.abasto.neto";
    String METHOD_NAME = "obtieneVentasPorEmpleado2";

    //String URL = "http://10.81.12.46:7777/appWSSIANMovilesPAR/services/WSRutasMovil/"; //QA
    //  String URL = "http://10.81.12.45:7777/WSSIAN/services/WSRutasMovil/"; //DESA1
    //String URL = "http://10.81.12.45:7777/WSSIANMoviles/services/WSRutasMovil/"; //desa2
    String URL = "https://www.servicios.tiendasneto.com/WSSIANMoviles/services/WSRutasMovil/";

    private Context context;

    private ProviderDashboard() {
    }

    SharedPreferences preferences;

    int banderaBoton = 0;

    public static ProviderDashboard getInstance(Context context) {
        if (instance == null) {
            instance = new ProviderDashboard();
        }
        instance.context = context;
        return instance;
    }

    Util serviciosObject = new Util();


    public void getVentas(final Consulta consulta, final ConsultaVentas promise) {
        try {
            preferences = context.getSharedPreferences("datosReporte", MODE_PRIVATE);
            banderaBoton = preferences.getInt("button", 0);
        } catch (Exception e) {
            banderaBoton = 2;
        }

        (new AsyncTask<Void, Void, VentasResponse>() {
            @Override
            protected VentasResponse doInBackground(Void... voids) {
                VentasResponse consultaVentas = null;

                HttpsTrustManager.allowAllSSL();

                try {

                    SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
                    request.addProperty("tipoConsulta", ++banderaBoton);
                    request.addProperty("numeroEmpleado", consulta.getNumeroEmpleado());
                    request.addProperty("region", consulta.getRegion());
                    request.addProperty("zona", consulta.getZona());
                    request.addProperty("tienda", consulta.getTienda());
                    request.addProperty("fechaInicial", consulta.getFechaInicial());
                    request.addProperty("fechaFinal", consulta.getFechaFinal());
                    request.addProperty("tipoTienda", consulta.getTipoTienda());
                    request.addProperty("tipoVenta", consulta.getTipoVenta());
                    request.addProperty("paTipoPpto", consulta.getPaTipoPpto());


                    SoapSerializationEnvelope soapEnvolve = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                    soapEnvolve.dotNet = true;
                    soapEnvolve.setOutputSoapObject(request);

                    HttpTransportSE transport = new HttpTransportSE(URL,60000);
                    //HttpTransportSE transport = new HttpTransportSE(URL);
                    transport.call(NAMESPACE + METHOD_NAME, soapEnvolve);

                    SoapObject response = (SoapObject) soapEnvolve.getResponse();
                    consultaVentas = serviciosObject.modelVentasParse(response, context);

                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Un error");
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                    System.out.println("Un error (2)");
                }

                return consultaVentas;
            }

            @Override
            protected void onPostExecute(VentasResponse consultaVentas) {
                promise.resolve(consultaVentas);
            }
        }).execute();
    }

    public interface ConsultaVentas {
        void resolve(VentasResponse ventasResponse);

        void reject(Exception e);
    }

}
