package neto.com.mx.reporte.ui;

import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import neto.com.mx.reporte.R;
import neto.com.mx.reporte.utils.font.RobotoTextView;

public class ActivityInformacion extends AppCompatActivity {

    LinearLayout back;
    RobotoTextView txtappversion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion);

        back = findViewById(R.id.back);
        txtappversion = findViewById(R.id.txtappversion);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        try {
            txtappversion.setText("v" + getPackageManager().getPackageInfo(getPackageName(), 0).versionName);
        } catch (PackageManager.NameNotFoundException ne) {
            Log.e("CARGA_FOLIO_TAG", "Error al obtener la versión: " + ne.getMessage());
        }
    }

}
