package neto.com.mx.aperturatienda.provider;

import android.content.Context;
import android.os.AsyncTask;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

import neto.com.mx.aperturatienda.model.tiendasLista.TiendasResponse;
import neto.com.mx.aperturatienda.utils.HttpsTrustManager;
import neto.com.mx.aperturatienda.utils.Util;

import static neto.com.mx.aperturatienda.constantes.Constantes.METHOD_NAME_CONSULTA_TIENDAS;
import static neto.com.mx.aperturatienda.constantes.Constantes.NAMESPACE;
import static neto.com.mx.aperturatienda.constantes.Constantes.URL;

public class ProviderTiendas {

    private static ProviderTiendas instance;

    private Context context;

    public static ProviderTiendas getInstance(Context context) {
        if (instance == null) {
            instance = new ProviderTiendas();
        }
        instance.context = context;
        return instance;
    }

    Util serviciosObject = new Util();

    public void getTiendas(final ConsultaTiendas promise) {

        (new AsyncTask<Void, Void, TiendasResponse>() {
            TiendasResponse tiendasResponse = null;

            @Override
            protected TiendasResponse doInBackground(Void... voids) {
                HttpsTrustManager.allowAllSSL();
                try {
                    SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME_CONSULTA_TIENDAS);

                    SoapSerializationEnvelope soapEnvolve = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                    soapEnvolve.dotNet = true;
                    soapEnvolve.setOutputSoapObject(request);

                    HttpTransportSE transportSE = new HttpTransportSE(URL);
                    transportSE.call(NAMESPACE + METHOD_NAME_CONSULTA_TIENDAS, soapEnvolve);

                    SoapObject response = (SoapObject) soapEnvolve.getResponse();

                    tiendasResponse = serviciosObject.modelTiendasParse(response, context);

                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.print("un error");
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                    System.out.print("un error2");
                }
                return tiendasResponse;
            }

            @Override
            protected void onPostExecute(TiendasResponse tiendasResponse) {
                promise.resolver(tiendasResponse);
            }
        }).execute();
    }

    public interface ConsultaTiendas {
        void resolver(TiendasResponse tiendasResponse);

        void reject(Exception e);
    }
}
