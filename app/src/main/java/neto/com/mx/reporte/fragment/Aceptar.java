package neto.com.mx.reporte.fragment;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import neto.com.mx.reporte.R;
import neto.com.mx.reporte.databinding.FragmentAceptarBinding;

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
        aceptarBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_aceptar,container,false);
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
                        getDialog().dismiss();
                    }
                }
        );
    }

    public void onResume() {
        super.onResume();
        Window window = getDialog().getWindow();
        window.setLayout(1064, 420);
        window.setGravity(Gravity.CENTER);
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

}
