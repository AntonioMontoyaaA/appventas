package neto.com.mx.reporte.ui;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import neto.com.mx.reporte.databinding.ActivityLoginBinding;

import neto.com.mx.reporte.R;
import neto.com.mx.reporte.model.login.Usuario;
import neto.com.mx.reporte.utils.Util;

public class ActivityLogin extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private static final int TIME_DELAY = 2000;
    private static long back_pressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        initDataBinding();
    }

    /**
     * Método que setea la vista con el binding y setea los tipos de fuente a los textinputlayout
     */
    private void initDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        binding.scrollBar.setVerticalScrollBarEnabled(false);
        binding.pass.setTypeface(Util.changeFont(this,1));
        binding.usuario.setTypeface(Util.changeFont(this,1));
        binding.entrar.setTypeface(Util.changeFont(this,1));
        Usuario usuario = new Usuario("", "", this, binding);
        binding.setLoginViewModel(usuario);
        binding.entrar.setEnabled(true);
        try{
        binding.txtAppVersion.setText("v" + getPackageManager().getPackageInfo(getPackageName(), 0).versionName);
        } catch(PackageManager.NameNotFoundException ne) {
            Log.e("CARGA_FOLIO_TAG", "Error al obtener la versión: " + ne.getMessage());
        }
    }

    @Override
    public void onBackPressed() {
        if (back_pressed + TIME_DELAY > System.currentTimeMillis()) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else {
            Snackbar snackbar = Snackbar.make(binding.container,
                    Html.fromHtml("<b><font color=\"#254581\">" +
                            getString(R.string.intentaSalir) +
                            "</font></b>"), Snackbar.LENGTH_SHORT);

            View snackBarView = snackbar.getView();
            snackBarView.setBackgroundColor(getResources().getColor(R.color.blanco));
            snackbar.show();
        }
        back_pressed = System.currentTimeMillis();
    }
}
