package neto.com.mx.reporte.provider;

import android.content.Context;
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

public class ProviderDashboard {

    private static ProviderDashboard instance;

    String NAMESPACE = "http://servicio.rutas.movil.abasto.neto";
    String METHOD_NAME = "obtieneVentasPorEmpleado";
    String URL = "https://www.servicios.tiendasneto.com/WSSIANMoviles/services/WSRutasMovil/";

    private Context context;
    private ProviderDashboard() {}

    public static ProviderDashboard getInstance(Context context) {
        if(instance == null) {
            instance = new ProviderDashboard();
        }
        instance.context = context;
        return instance;
    }

    Util serviciosObject = new Util();


    public void getVentas(final Consulta consulta, final ConsultaVentas promise){

        (new AsyncTask<Void, Void, VentasResponse>() {
            @Override
            protected VentasResponse doInBackground(Void... voids) {
                VentasResponse consultaVentas = null;

                HttpsTrustManager.allowAllSSL();

                try {

                    SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
                    request.addProperty("numeroEmpleado", consulta.getNumeroEmpleado());
                    request.addProperty("region", consulta.getRegion());
                    request.addProperty("zona", consulta.getZona());
                    request.addProperty("tienda", consulta.getTienda());
                    request.addProperty("fechaInicial", consulta.getFechaInicial());
                    request.addProperty("fechaFinal", consulta.getFechaFinal());


                    SoapSerializationEnvelope soapEnvolve = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                    soapEnvolve.dotNet = true;
                    soapEnvolve.setOutputSoapObject(request);

                    HttpTransportSE transport = new HttpTransportSE(URL);
                    transport.call(NAMESPACE+METHOD_NAME, soapEnvolve);

                    SoapObject response = (SoapObject) soapEnvolve.getResponse();
                    consultaVentas = serviciosObject.modelVentasParse(response, context);

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                }

                return consultaVentas;
            }

            @Override
            protected void onPostExecute(VentasResponse consultaVentas){
                promise.resolve(consultaVentas);
            }
        }).execute();
    }

    public interface ConsultaVentas {
        void resolve(VentasResponse ventasResponse);
        void reject(Exception e);
    }

}
