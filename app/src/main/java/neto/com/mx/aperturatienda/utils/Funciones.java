package neto.com.mx.aperturatienda.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;

public class Funciones {
    Context fContex;

    public Funciones(Context context) {
        this.fContex = context;
    }

    public boolean isOnLine() {
        ConnectivityManager cm = (ConnectivityManager) fContex.getSystemService(Context.CONNECTIVITY_SERVICE);

        /*if (Build.VERSION.SDK_INT < 23) {*/
            final NetworkInfo netInfo = cm.getActiveNetworkInfo();
            if (netInfo != null) {
                return (netInfo.isConnected() && netInfo.getType() == ConnectivityManager.TYPE_WIFI || netInfo.getType() == ConnectivityManager.TYPE_MOBILE);
            }/*
        } else {
            final Network n = cm.getActiveNetwork();
            if (n != null) {
                final NetworkCapabilities nc = cm.getNetworkCapabilities(n);
                return (nc.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || nc.hasTransport(NetworkCapabilities.TRANSPORT_WIFI));
            }
        }*/
        return false;
        //return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
