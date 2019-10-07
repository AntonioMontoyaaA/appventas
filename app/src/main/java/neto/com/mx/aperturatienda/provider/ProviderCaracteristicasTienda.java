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

import neto.com.mx.aperturatienda.model.tiendaEspecifica.CaracteristicasTResponse;
import neto.com.mx.aperturatienda.utils.Util;

import static neto.com.mx.aperturatienda.constantes.Constantes.METHOD_NAME_CONSULTA_CARACTERISTICAS_TIENDA;
import static neto.com.mx.aperturatienda.constantes.Constantes.NAMESPACE;
import static neto.com.mx.aperturatienda.constantes.Constantes.URL;

public class ProviderCaracteristicasTienda {
    final String TAG = "ProviderCaractT";
    private static ProviderCaracteristicasTienda instance;
    private Context context;
    public static ProviderCaracteristicasTienda getInstance(Context context){
        if(instance == null) {
            instance = new ProviderCaracteristicasTienda();
        }
        instance.context = context;
        return instance;
    }
    Util serviciosObject = new Util();

    public void getCaracteristicaTienda(final int paisId, final int tiendaId, final CaracteristicaCTienda promise){
        (new AsyncTask<Void,Void, CaracteristicasTResponse>(){
            CaracteristicasTResponse caracteristicasTResponse = null;

            @Override
            protected CaracteristicasTResponse doInBackground(Void... voids) {
                try{
                    SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME_CONSULTA_CARACTERISTICAS_TIENDA);
                    request.addProperty("paisId",paisId);
                    request.addProperty("tiendaId",tiendaId);

                    SoapSerializationEnvelope sse = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                    sse.dotNet = true;
                    sse.setOutputSoapObject(request);

                    HttpTransportSE transportSE = new HttpTransportSE(URL);
                    transportSE.call(NAMESPACE+ METHOD_NAME_CONSULTA_CARACTERISTICAS_TIENDA, sse);

                    SoapObject response = (SoapObject) sse.getResponse();
                    caracteristicasTResponse = serviciosObject.modelCaracteristicasTResponse(response, context);

                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d(TAG, "error IOExepction");
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                    Log.d(TAG, "error XMLPPExepction");
                }
                return caracteristicasTResponse;
            }

            @Override
            protected void onPostExecute(CaracteristicasTResponse caracteristicasTResponse) {
                promise.resolver(caracteristicasTResponse);
            }
        }).execute();
    }

    public interface CaracteristicaCTienda{
        void resolver (CaracteristicasTResponse caracteristicasTResponse);
        void reject (Exception e);
    }
}
