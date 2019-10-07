package neto.com.mx.aperturatienda.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import neto.com.mx.aperturatienda.MainActivity;
import neto.com.mx.aperturatienda.R;
import neto.com.mx.aperturatienda.fragment.Aceptar;
import neto.com.mx.aperturatienda.model.Login.Login;
import neto.com.mx.aperturatienda.model.Login.Permisos;
import neto.com.mx.aperturatienda.model.Login.ResultadoVo;
import neto.com.mx.aperturatienda.provider.ProviderLogin;
import neto.com.mx.aperturatienda.utils.Funciones;
import neto.com.mx.aperturatienda.utils.Util;

public class ActivityLogin extends AppCompatActivity {
    private String TAG = "ActivityLogin";
    EditText usuario, pass;
    Button btnentra;
    Funciones funciones;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        progressDialog = new ProgressDialog(ActivityLogin.this);
        funciones = new Funciones(this);

        usuario = findViewById(R.id.usuario);
        pass = findViewById(R.id.pass);
        progressDialog = new ProgressDialog(ActivityLogin.this);

        btnentra = findViewById(R.id.btn_entrar);

        btnentra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                usuario.setText("359351");
//                pass.setText("359351");

                if (funciones.isOnLine()) {
                    if (usuario.getText().toString().equals("")) {
                        Aceptar a = new Aceptar();
                        a.setMensaje("Escribe tu usuario por favor");
                        a.show(getSupportFragmentManager(), "child");
                    } else if (pass.getText().toString().equals("")) {
                        Aceptar a = new Aceptar();
                        a.setMensaje("Escribe tu contraseña por favor");
                        a.show(getSupportFragmentManager(), "child");
                    } else {
                        Util.loadingProgress(progressDialog, 0);
                        try {
                            ProviderLogin.getInstance(ActivityLogin.this).getLogin(usuario.getText().toString(), pass.getText().toString(), new ProviderLogin.ConsultaLogin() {
                                @Override
                                public void resolve(Login login) {
//                                    Log.d(TAG, "resssss");

                                    if (login != null) {
                                        if (login.getUsuarioRowVo() != null) {

                                            SharedPreferences userDetails = getApplicationContext().getSharedPreferences("usuario", MODE_PRIVATE);
                                            SharedPreferences.Editor editor = userDetails.edit();
                                            editor.putInt("usuario", Integer.parseInt(usuario.getText().toString()));
                                            editor.putString("contra", pass.getText().toString());
                                            editor.putInt("permisoEdicion", 0);
                                            editor.apply();

                                            ProviderLogin.getInstance(ActivityLogin.this).getPermisos(usuario.getText().toString(), new ProviderLogin.ConsultaPermisos() {
                                                @Override
                                                public void resolve(Permisos permiso) {
                                                    Util.loadingProgress(progressDialog, 1);
                                                    if (permiso.getListaResultado() != null) {
                                                        for (ResultadoVo res : permiso.getListaResultado()) {
                                                            if (Integer.parseInt(String.valueOf(res.getPermisoId())) == 2412) {
                                                                SharedPreferences userDetails = getApplicationContext().getSharedPreferences("usuario", MODE_PRIVATE);
                                                                SharedPreferences.Editor editor = userDetails.edit();
                                                                editor.putInt("permisoEdicion", 1);
                                                                editor.apply();
                                                            }
                                                        }
                                                        Intent intent = new Intent(ActivityLogin.this, MainActivity.class);
                                                        startActivity(intent);
                                                        finish();
                                                    } else {
                                                        Aceptar a = new Aceptar();
                                                        a.setMensaje(permiso.getStatusCode() != 0 ? permiso.getStatusMsj() : "Usuario sin permisos");
                                                        a.show(getSupportFragmentManager(), "child");
                                                    }
                                                }

                                                @Override
                                                public void reject(Exception e) {
                                                    Log.d(TAG, "resssss no");
                                                    Util.loadingProgress(progressDialog, 1);
                                                }
                                            });


                                        } else {
                                            Util.loadingProgress(progressDialog, 1);
                                            Aceptar a = new Aceptar();
                                            a.setMensaje(login.getCodigo() != 0 ? login.getStatusMsj() : "Usuario no valido");
                                            a.show(getSupportFragmentManager(), "child");

                                        }
                                    }else{
                                        Util.loadingProgress(progressDialog, 1);
                                        Toast.makeText(ActivityLogin.this, "Existe un problema con la conexión", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void reject(Exception e) {
                                }
                            });
                        }catch (Exception e){
                            Util.loadingProgress(progressDialog, 1);
                        }
                    }
                } else {
                    Aceptar a = new Aceptar();
                    a.setMensaje("Necesitas estar conectado a internet");
                    a.show(getSupportFragmentManager(), "child");//a.show(getFragmentManager(), "child");
                }

            }
        });
    }
}
