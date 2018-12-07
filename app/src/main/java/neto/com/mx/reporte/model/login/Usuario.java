package neto.com.mx.reporte.model.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.text.Html;
import android.view.View;

import neto.com.mx.reporte.R;
import neto.com.mx.reporte.databinding.ActivityLoginBinding;
import neto.com.mx.reporte.provider.ProviderLogin;
import neto.com.mx.reporte.ui.ActivityMain;
import neto.com.mx.reporte.utils.Util;


/**
 * Created by marcosmarroquin on 21/03/18.
 */
public class Usuario {

    String usuario;
    String contra;

    Context context;
    ActivityLoginBinding binding;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    public Usuario(String usuario, String contra, Context context,
                   ActivityLoginBinding binding) {
        this.usuario = usuario;
        this.contra = contra;
        this.context = context;
        this.binding = binding;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContra() {
        return contra;
    }

    public void setContra(String contra) {
        this.contra = contra;
    }


    /**
     * Método que contiene el evento para el boton entrar, verifica si existe en la BD de tiendas neto y crea una variable de sesión
     * @param usuario
     * @param contra
     */
    public void onClickEntrar(String usuario, String contra) {
        blockUI();

        if(usuario.length() > 0 && contra.length() > 0){
            binding.entrar.setAlpha(0.45f);
            binding.entrar.setEnabled(false);
            compruebaUsuario(usuario, contra);

        }else{
            binding.entrar.setEnabled(true);
            binding.entrar.setAlpha(1f);
            Snackbar snackbar = Snackbar.make(binding.container,
                    Html.fromHtml("<b><font color=\"#254581\">" +
                            context.getString(R.string.sizeContra) +
                            "</font></b>"), Snackbar.LENGTH_SHORT);
            View snackBarView = snackbar.getView();
            snackBarView.setBackgroundColor(context.getResources().getColor(R.color.blanco)); // snackbar background color
            snackbar.show();
            unblockUI();
        }

    }

    public void compruebaUsuario(final String usuario, String contrasena) {
        ProviderLogin.getInstance(context).getLogin(usuario, contrasena,
                new ProviderLogin.ConsultaLogin() {
                    @Override
                    public void resolve(Login login) {
                        if(login.getEsUsuarioValido()==true){

                            Intent main = new Intent(context, ActivityMain.class);
                            context.startActivity(main);
                            binding.entrar.setAlpha(1f);

                            sharedSave(context, usuario,
                                    login.getPassword());
                            unblockUI();

                        }else{
                            Snackbar snackbar = Snackbar.make(binding.container,
                                    Html.fromHtml("<b><font color=\"#254581\">" +
                                            "Este perfil no tiene permisos"+
                                            "</font></b>"), Snackbar.LENGTH_SHORT);

                            View snackBarView = snackbar.getView();
                            snackBarView.setBackgroundColor(context.getResources().getColor(R.color.blanco));
                            snackbar.show();
                            binding.entrar.setAlpha(1f);
                            binding.entrar.setEnabled(true);
                            unblockUI();

                        }
                    }
                    @Override
                    public void reject(Exception e) {}
                });
    }

    private void blockUI(){
        Util.addProgressBar(context, binding.container,binding.container.getChildCount()-1 );
    }

    private void unblockUI(){
        binding.container.removeViewAt(binding.container.getChildCount()-2);
    }

    public static void sharedSave(Context context, String usuario, String contra){

        SharedPreferences preferences = context.getSharedPreferences("datosReporte", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("usuario", usuario);
        editor.putString("contra", contra);
        editor.apply();

    }



}
