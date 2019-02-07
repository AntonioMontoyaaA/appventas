package neto.com.mx.reporte.provider;

import android.content.Context;
import android.os.AsyncTask;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

import neto.com.mx.reporte.model.login.Login;
import neto.com.mx.reporte.utils.HttpsTrustManager;
import neto.com.mx.reporte.utils.Util;

public class ProviderLogin {

    private static ProviderLogin instance;

    String NAMESPACE = "http://servicio.rutas.movil.abasto.neto";
    String METHOD_NAME = "validaUsuario";
    String URL_PRUEBAS = "https://www.servicios.tiendasneto.com/WSSIANMoviles/services/WSRutasMovil/";
    //String URL_PRUEBAS = "http://10.81.12.46:7777/appWSSIANMovilesPAR/services/WSRutasMovil/"; //QA

    private Context context;

    private ProviderLogin() {}

    // configurables los ambientes de app

    public static ProviderLogin getInstance(Context context) {
        if(instance == null) {
            instance = new ProviderLogin();
        }
        instance.context = context;
        return instance;
    }

    Util serviciosObject = new Util();


    public void getLogin(final String usuario, final String contrasena, final ConsultaLogin promise){

        (new AsyncTask<Void, Void, Login>() {
            @Override
            protected Login doInBackground(Void... voids) {
                Login consultaLogin = null;
                HttpsTrustManager.allowAllSSL();

                try {

                    SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
                    request.addProperty("usuario", usuario);
                    request.addProperty("password", serviciosObject.md5(contrasena));
                    request.addProperty("idApp", context.getString(neto.com.mx.reporte.R.string.idapp));

                    SoapSerializationEnvelope soapEnvolve = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                    soapEnvolve.dotNet = true;
                    soapEnvolve.setOutputSoapObject(request);

                    HttpTransportSE transport = new HttpTransportSE(URL_PRUEBAS);
                    transport.call(NAMESPACE+METHOD_NAME, soapEnvolve);

                    SoapObject response = (SoapObject) soapEnvolve.getResponse();
                    consultaLogin = serviciosObject.modelLoginParse(response);

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                }

                return consultaLogin;
            }

            @Override
            protected void onPostExecute(Login consultaLogin){
                promise.resolve(consultaLogin);
            }
        }).execute();
    }

    public interface ConsultaLogin {
        void resolve(Login login);
        void reject(Exception e);
    }

}
