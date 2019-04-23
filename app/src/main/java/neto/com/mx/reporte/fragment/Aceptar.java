package neto.com.mx.reporte.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import neto.com.mx.reporte.R;
import neto.com.mx.reporte.databinding.FragmentAceptarBinding;
import neto.com.mx.reporte.ui.ActivityLogin;

/**
 * A simple {@link Fragment} subclass.
 */
public class Aceptar extends DialogFragment {

    FragmentAceptarBinding aceptarBinding;
    public String mensaje = "";


    public Aceptar() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_aceptar, container, false);
        aceptarBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_aceptar, container, false);
        View view = aceptarBinding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        aceptarBinding.textoMensaje.setText(mensaje);
        aceptarBinding.aceptar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (mensaje.contains("no tiene tiendas")) {
                            Intent intent = new Intent(getContext(), ActivityLogin.class);
                            getContext().startActivity(intent);
                            SharedPreferences preferences = getContext().getSharedPreferences("datosReporte", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("usuario", "");
                            editor.putString("contra", "");
                            editor.apply();
                        }
                        getDialog().dismiss();
                    }
                }
        );
    }

    public void onResume() {
        super.onResume();
        Window window = getDialog().getWindow();


        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int widthCEL = displaymetrics.widthPixels;
        if (widthCEL <= 500) {
            window.setLayout(532, 220);
        } else {
            window.setLayout(1064, 420);

        }

        window.setGravity(Gravity.CENTER);
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

}
