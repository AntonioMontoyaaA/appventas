package neto.com.mx.aperturatienda.provider;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

import neto.com.mx.aperturatienda.model.caracteristicasGenerales.CaracteristicasGResponse;
import neto.com.mx.aperturatienda.utils.Util;

import static neto.com.mx.aperturatienda.constantes.Constantes.METHOD_NAME_CONSULTA_CARACTERISTICAS;
import static neto.com.mx.aperturatienda.constantes.Constantes.NAMESPACE;
import static neto.com.mx.aperturatienda.constantes.Constantes.URL;

public class ProviderCaracteristicasGenerales {
    final String TAG = "ProviderCaractG";
    private static ProviderCaracteristicasGenerales instance;
    private Context context;

    public static ProviderCaracteristicasGenerales getInstance(Context context) {
        if (instance == null) {
            instance = new ProviderCaracteristicasGenerales();
        }
        instance.context = context;
        return instance;
    }

    Util serviciosUtileria = new Util();

    public void getCaracteristicasG(final ConstulaCaracteristicasG promise) {
        (new AsyncTask<Void, Void, CaracteristicasGResponse>() {
            CaracteristicasGResponse caracteristicasGResponse = null;

            @Override
            protected CaracteristicasGResponse doInBackground(Void... voids) {
                try {
                    SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME_CONSULTA_CARACTERISTICAS);

                    SoapSerializationEnvelope sse = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                    sse.dotNet = true;
                    sse.setOutputSoapObject(request);
                    HttpTransportSE transportSE = new HttpTransportSE(URL);
                    transportSE.call(NAMESPACE + METHOD_NAME_CONSULTA_CARACTERISTICAS, sse);

                    SoapObject response = (SoapObject) sse.getResponse();
                    Log.d(TAG, response.toString());

                    caracteristicasGResponse = serviciosUtileria.modelParseCaracteristicasGenerales(response, context);
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d(TAG, "error IOExepction");
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                    Log.d(TAG, "error XMLPPExepction");
                }


                return caracteristicasGResponse;
            }

            @Override
            protected void onPostExecute(CaracteristicasGResponse caracteristicasGResponse) {
                promise.resolver(caracteristicasGResponse);
            }
        }).execute();
    }

    public interface ConstulaCaracteristicasG {
        void resolver(CaracteristicasGResponse caracteristicasGResponse);

        void reject(Exception e);

    }

}
