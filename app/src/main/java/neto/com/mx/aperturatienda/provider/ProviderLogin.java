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
import java.net.SocketTimeoutException;

import neto.com.mx.aperturatienda.model.Login.Login;
import neto.com.mx.aperturatienda.model.Login.Permisos;
import neto.com.mx.aperturatienda.utils.HttpsTrustManager;
import neto.com.mx.aperturatienda.utils.Util;

import static neto.com.mx.aperturatienda.constantes.Constantes.METHOD_NAME_AUTENTICAR;
import static neto.com.mx.aperturatienda.constantes.Constantes.METHOD_NAME_OBTIENE_PERMISOS_USUARIO;
import static neto.com.mx.aperturatienda.constantes.Constantes.MODULO;
import static neto.com.mx.aperturatienda.constantes.Constantes.NAMESPACE;
import static neto.com.mx.aperturatienda.constantes.Constantes.PAISID;
import static neto.com.mx.aperturatienda.constantes.Constantes.SISTEMAID;
import static neto.com.mx.aperturatienda.constantes.Constantes.SUBMODULO;
import static neto.com.mx.aperturatienda.constantes.Constantes.URL;

public class ProviderLogin {
    String TAG = "ProviderLogin";
    private static ProviderLogin instance;

    private Context context;

    private ProviderLogin() {
    }

    public static ProviderLogin getInstance(Context context) {
        if (instance == null) {
            instance = new ProviderLogin();
        }
        instance.context = context;
        return instance;
    }

    private Util serviciosObject = new Util();

    public void getLogin(final String usuario, final String contrasena, final ConsultaLogin promise) {
        (new AsyncTask<Void, Void, Login>() {
            @Override
            protected Login doInBackground(Void... voids) {
                Login consultaLogin = null;
                HttpsTrustManager.allowAllSSL();
                ProviderLogin.this.toString();

                try {
                    SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME_AUTENTICAR);
                    request.addProperty("usuario", usuario);
                    request.addProperty("password", serviciosObject.md5(contrasena));

                    SoapSerializationEnvelope soapEnvolve = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                    soapEnvolve.dotNet = true;
                    soapEnvolve.setOutputSoapObject(request);

                    HttpTransportSE transport = new HttpTransportSE(URL,10000);
                    Log.d(TAG, "doInBackground: estoyaqui?");
                        transport.call(NAMESPACE + METHOD_NAME_AUTENTICAR, soapEnvolve);
                        SoapObject response = (SoapObject) soapEnvolve.getResponse();
                        consultaLogin = serviciosObject.modelLoginParse(response);

                } catch (SocketTimeoutException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return consultaLogin;
            }

            @Override
            protected void onPostExecute(Login login) {
                promise.resolve(login);
            }
        }
        ).execute();
    }

    public void getPermisos(final String usuario, final ConsultaPermisos promise) {
        (new AsyncTask<Void, Void, Permisos>() {
            @Override
            protected Permisos doInBackground(Void... voids) {
                Permisos consultaPermisos = null;
                HttpsTrustManager.allowAllSSL();
                ProviderLogin.this.toString();
//                Log.d(TAG, PAISID+" "+ SISTEMAID+" "+usuario+" "+MODULO+" "+SUBMODULO );
                try {
                    SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME_OBTIENE_PERMISOS_USUARIO);
                    request.addProperty("paisId", PAISID);
                    request.addProperty("sistemaId", SISTEMAID);
                    request.addProperty("usuarioId", usuario);
                    request.addProperty("moduloId", MODULO);
                    request.addProperty("submoduloId", SUBMODULO);

                    SoapSerializationEnvelope soapEnvolve = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                    soapEnvolve.dotNet = true;
                    soapEnvolve.setOutputSoapObject(request);

                    HttpTransportSE transport = new HttpTransportSE(URL);
                    transport.call(NAMESPACE + METHOD_NAME_OBTIENE_PERMISOS_USUARIO, soapEnvolve);

                    SoapObject response = (SoapObject) soapEnvolve.getResponse();
                    consultaPermisos = serviciosObject.modelPermisosParse(response);

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                }

                return consultaPermisos;
            }

            @Override
            protected void onPostExecute(Permisos permiso) {
                promise.resolve(permiso);
            }


        }
        ).execute();
    }
    public interface ConsultaPermisos {
        void resolve(Permisos permiso);

        void reject(Exception e);
    }

    public interface ConsultaLogin {
        void resolve(Login login);

        void reject(Exception e);
    }
}
