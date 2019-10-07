package neto.com.mx.aperturatienda.provider;

import android.content.Context;
import android.os.AsyncTask;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

import neto.com.mx.aperturatienda.model.Indicadores.IndicadorResponse;
import neto.com.mx.aperturatienda.utils.HttpsTrustManager;
import neto.com.mx.aperturatienda.utils.Util;

import static neto.com.mx.aperturatienda.constantes.Constantes.METHOD_NAME_OBTIENE_INDICADORES;
import static neto.com.mx.aperturatienda.constantes.Constantes.NAMESPACE;
import static neto.com.mx.aperturatienda.constantes.Constantes.URL;


public class ProviderIndicadores {
    String TAG = "ProviderIndicadores";
    private static ProviderIndicadores instance;
    private Context context;

    private ProviderIndicadores() {
    }
    public static ProviderIndicadores getInstance(Context context) {
        if (instance == null) {
            instance = new ProviderIndicadores();
        }
        instance.context = context;
        return instance;
    }
    Util serviciosObject = new Util();
    public void getIndicadores( final int paisId, final int tiendaId,  final ConsultaIndicadores promise){
        (new AsyncTask<Void, Void, IndicadorResponse>() {
            IndicadorResponse indicadorResponse = null;
            @Override
            protected IndicadorResponse doInBackground(Void... voids) {
                try {
                    SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME_OBTIENE_INDICADORES);
                    request.addProperty("paisId",paisId);
                    request.addProperty("tiendaId",tiendaId);

                    SoapSerializationEnvelope soapEnvolve = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                    soapEnvolve.dotNet = true;
                    soapEnvolve.setOutputSoapObject(request);

                    HttpTransportSE transport = new HttpTransportSE(URL);
                    transport.call(NAMESPACE + METHOD_NAME_OBTIENE_INDICADORES, soapEnvolve);
                    SoapObject response = (SoapObject) soapEnvolve.getResponse();
                    indicadorResponse = serviciosObject.modelIndicadoresParse(response, context);

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                }
                return indicadorResponse;
            }

            @Override
            protected void onPostExecute(IndicadorResponse indicadorResponse) {
                promise.resolver(indicadorResponse);
            }
        }).execute();
    }
    public interface ConsultaIndicadores {
        void resolver(IndicadorResponse indicadorResponse);

        void reject(Exception e);
    }
}
