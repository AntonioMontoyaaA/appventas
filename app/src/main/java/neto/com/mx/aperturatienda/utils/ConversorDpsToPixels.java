package neto.com.mx.aperturatienda.utils;

import android.content.Context;

public class ConversorDpsToPixels {
    public int  Conversor(Context context, int dps){
        final float scale = context.getResources().getDisplayMetrics().density;
        int pixels = (int) (dps * scale + 0.5f);

        return pixels;
    }
}
