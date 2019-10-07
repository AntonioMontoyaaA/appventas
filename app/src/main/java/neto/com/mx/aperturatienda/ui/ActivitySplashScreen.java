package neto.com.mx.aperturatienda.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import neto.com.mx.aperturatienda.MainActivity;
import neto.com.mx.aperturatienda.R;

public class ActivitySplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final int DURACION_SPLASH = 1000;

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash_screen);

        SharedPreferences userDetails = getApplicationContext().getSharedPreferences("usuario", MODE_PRIVATE);
        final String id_usuario = userDetails.getString("id", "");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    if (id_usuario.equalsIgnoreCase("")) {
                        Intent intent = new Intent(ActivitySplashScreen.this, ActivityLogin.class);
                        startActivity(intent);
                        finish();
                    }else{
                        Intent intent = new Intent(ActivitySplashScreen.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }catch (NullPointerException ex){
                    ex.printStackTrace();
                }
            }
        }, DURACION_SPLASH);

    }
}
