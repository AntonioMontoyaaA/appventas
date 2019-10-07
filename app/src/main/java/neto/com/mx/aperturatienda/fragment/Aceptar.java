package neto.com.mx.aperturatienda.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import neto.com.mx.aperturatienda.R;
import neto.com.mx.aperturatienda.databinding.FragmentAceptarBinding;

public class Aceptar extends DialogFragment {

    FragmentAceptarBinding aceptarBinding;

    TextView tv_mensaje;
    Button btn_aceptar;
    public String mensaje;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
       aceptarBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_aceptar, container,false);
       View view = aceptarBinding.getRoot();
       return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        aceptarBinding.tvMensaje.setText(mensaje);
        aceptarBinding.btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
    }

    public Aceptar(){}

    public void setMensaje(String mensaje){
        this.mensaje = mensaje;
    }
}
