package neto.com.mx.aperturatienda.utils;

import android.util.Log;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class HttpsTrustManager implements X509TrustManager {

    private static TrustManager[] trustManagers;

    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return new X509Certificate[0];
    }

    public static class _FakeX509TrustManager implements X509TrustManager{

        private static final X509Certificate[] _AcceptedIssuers = new X509Certificate[]{};
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return  (_AcceptedIssuers);
        }
    }

    public static void allowAllSSL(){
        SSLContext context;

        HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                if(!hostname.equalsIgnoreCase("http://servicio.ll_caracteristicas.aperturas.neto"))
                return true;
                else
                    return false;
            }
        });

        if(trustManagers == null){
            trustManagers = new TrustManager[]{new _FakeX509TrustManager()};
        }

        try{
            context = SSLContext.getInstance("TLS");
            context.init(null, trustManagers,new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
        }catch (NoSuchAlgorithmException e){
            Log.e("allowALLSSL", e.toString());
        }catch (KeyManagementException e){
            Log.e("allowALLSSL", e.toString());
        }
    }
}
