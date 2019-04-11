package neto.com.mx.reporte.provider;

import android.content.Context;
import android.os.AsyncTask;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

import neto.com.mx.reporte.model.dashboard.Tiendas;
import neto.com.mx.reporte.utils.HttpsTrustManager;
import neto.com.mx.reporte.utils.Util;

public class ProviderTiendas {

    private static ProviderTiendas instance;

    String NAMESPACE = "http://servicio.rutas.movil.abasto.neto";
    String METHOD_NAME = "obtieneTiendasPorEmpleado";
    // String URL = "https://www.servicios.tiendasneto.com/WSSIANMoviles/services/WSRutasMovil/"; //prod
    //String URL = "http://10.81.12.46:7777/appWSSIANMovilesPAR/services/WSRutasMovil/"; //QA
    String URL = "http://10.81.12.45:7777/WSSIAN/services/WSRutasMovil/"; //desa
    private Context context;
    private ProviderTiendas() {}

    public static ProviderTiendas getInstance(Context context) {
        if(instance == null) {
            instance = new ProviderTiendas();
        }
        instance.context = context;
        return instance;
    }

    Util serviciosObject = new Util();


    public void getVentas(final String usuario,final int tipoTienda, final ConsultaTiendas promise){

        (new AsyncTask<Void, Void, Tiendas>() {
            @Override
            protected Tiendas doInBackground(Void... voids) {
                Tiendas consultaTiendas = null;
                HttpsTrustManager.allowAllSSL();

                try {

                    SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
                    request.addProperty("numeroEmpleado", usuario);
                    request.addProperty("tipoTienda", tipoTienda);

                    SoapSerializationEnvelope soapEnvolve = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                    soapEnvolve.dotNet = true;
                    soapEnvolve.setOutputSoapObject(request);

                    HttpTransportSE transport = new HttpTransportSE(URL,30000);
                    transport.call(NAMESPACE+METHOD_NAME, soapEnvolve);

                    SoapObject response = (SoapObject) soapEnvolve.getResponse();
                    consultaTiendas = serviciosObject.modelTiendasParse(response, context);

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                }

                return consultaTiendas;
            }

            @Override
            protected void onPostExecute(Tiendas consultaVentas){
                promise.resolve(consultaVentas);
            }
        }).execute();
    }

    public interface ConsultaTiendas {
        void resolve(Tiendas ventasResponse);
        void reject(Exception e);
    }

}
