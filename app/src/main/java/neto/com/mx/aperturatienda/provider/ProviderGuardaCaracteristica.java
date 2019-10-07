package neto.com.mx.aperturatienda.provider;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.ksoap2.SoapEnvelope;
//import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import neto.com.mx.aperturatienda.R;
import neto.com.mx.aperturatienda.model.caracteristicasGenerales.ComponentesPantalla;
import neto.com.mx.aperturatienda.model.guardacaracteristica.DatosEvaluacion;
import neto.com.mx.aperturatienda.model.guardacaracteristica.GuardaCaracteristicaRequest;
import neto.com.mx.aperturatienda.model.guardacaracteristica.GuardaCaracteristicaResponse;
import neto.com.mx.aperturatienda.utils.HttpsTrustManager;
import neto.com.mx.aperturatienda.utils.Util;

import static neto.com.mx.aperturatienda.constantes.Constantes.METHOD_NAME_GUARDA_CARACTERISTICAS;
import static neto.com.mx.aperturatienda.constantes.Constantes.NAMESPACE;
import static neto.com.mx.aperturatienda.constantes.Constantes.SOAP_ACTION;
import static neto.com.mx.aperturatienda.constantes.Constantes.URL;

public class ProviderGuardaCaracteristica {
    private final String TAG = "ProviderGuardaC";
    private static ProviderGuardaCaracteristica insance;
    private Context context;

    public static ProviderGuardaCaracteristica getInstance(Context context) {
        if (insance == null) {
            insance = new ProviderGuardaCaracteristica();
        }
        insance.context = context;
        return insance;
    }

    Util serviciosObject = new Util();

    public void GuardaCaracteristicas(final GuardaCaracteristicaRequest gcr, final GuardaCaracteristicaI promise) {
//        for(DatosEvaluacion dato : gcr.getDatosEvaluacion()){
//            Log.d(TAG," dato -> "+dato.getCaracteristicaId()+ " "+ dato.getNivelCaracteristicaId()+" "+dato.getValor()+" "+dato.getValorReal());
//        }
//        final ArrayList<DatosEvaluacion> listaP = new ArrayList<DatosEvaluacion>();
//        DatosEvaluacion dato = new DatosEvaluacion(9,1,"1.0","123","ninguno");
//        listaP.add(dato);


        (new AsyncTask<Void, Void, GuardaCaracteristicaResponse>() {
            GuardaCaracteristicaResponse gCresponse = null;
            @Override
            protected GuardaCaracteristicaResponse doInBackground(Void... voids) {
                HttpsTrustManager.allowAllSSL();
                try {
                    SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME_GUARDA_CARACTERISTICAS);
                    request.addProperty("paisId", gcr.getPaisId());
                    request.addProperty("tiendaId", gcr.getTiendaId());
                    request.addProperty("usuarioId", gcr.getUsuarioId());
                    request.addProperty("medioRegistra", "1");
                    for(DatosEvaluacion dato : gcr.getDatosEvaluacion()){
                        request.addProperty("arrayDatos", dato);
                    }

                    SoapSerializationEnvelope soapEnvolve = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                    soapEnvolve.dotNet = true;
                    soapEnvolve.setOutputSoapObject(request);

                    HttpTransportSE transportSE = new HttpTransportSE(URL);
                    transportSE.call(SOAP_ACTION, soapEnvolve);
                    SoapObject response = (SoapObject) soapEnvolve.getResponse();
                    gCresponse = serviciosObject.modelGuardaCaracteristicasResponse(response, context);

//                    Log.d(TAG, "doInBackground: "+ response.toString());

                } catch (IOException e) {

                    e.printStackTrace();
                    System.out.print("un error");
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                    System.out.print("un error2");
                }
                return gCresponse;
            }
            @Override
            protected void onPostExecute(GuardaCaracteristicaResponse guardaCaracteristicaResponse) {
                promise.resolve(guardaCaracteristicaResponse);
            }
        }).execute();


    }
    public interface GuardaCaracteristicaI {
        void resolve(GuardaCaracteristicaResponse guardaCaracteristicaResponse);

        void reject(Exception e);
    }
}
/*SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME_GUARDA_CARACTERISTICAS);

                HttpsTrustManager.allowAllSSL();

                PropertyInfo pi = new PropertyInfo();
                pi.setName("GuardaCaracteristicaRequest");
                pi.setValue(gcr);
                pi.setType(gcr.getClass());
                request.addProperty(pi);

                SoapSerializationEnvelope sse = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                sse.dotNet = true;
                sse.setOutputSoapObject(request);

                sse.addMapping(NAMESPACE , "guardaCaracteristicas", new GuardaCaracteristicaRequest().getClass());

                try {
                    HttpTransportSE transportSE = new HttpTransportSE(URL);
                    transportSE.call(NAMESPACE+ METHOD_NAME_GUARDA_CARACTERISTICAS, sse);
                    SoapObject response = (SoapObject) sse.getResponse();
                    Log.d(TAG, "doInBackground: " + response.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d(TAG, "error IOExepction");
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                    Log.d(TAG, "error XMLPPExepction");
                }
                return null;*/