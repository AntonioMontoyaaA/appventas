package neto.com.mx.reporte.fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import neto.com.mx.reporte.R;
import neto.com.mx.reporte.adapter.AdapterTiendas;
import neto.com.mx.reporte.adapter.TiendasHolder;
import neto.com.mx.reporte.databinding.FragmentCalendarioBinding;
import neto.com.mx.reporte.model.dashboard.Consulta;
import neto.com.mx.reporte.model.dashboard.Tienda;
import neto.com.mx.reporte.model.dashboard.Tiendas;
import neto.com.mx.reporte.provider.ProviderTiendas;
import neto.com.mx.reporte.ui.ActivityMain;
import neto.com.mx.reporte.ui.ActivityRegion;
import neto.com.mx.reporte.ui.ActivityTiendas;
import neto.com.mx.reporte.ui.ActivityZona;
import neto.com.mx.reporte.utils.Util;

/**
 * Created by Kevin on 26/6/2017.
 */

public class FragmentCalendario extends DialogFragment {

    FragmentCalendarioBinding binding;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    int day, month, year;

    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_calendario,container,false);
        View view = binding.getRoot();
        preferences = getContext().getSharedPreferences("datosReporte", Context.MODE_PRIVATE);
        editor = preferences.edit();

        final int type = getArguments().getInt("type");

        Date fechaActual = new Date();
        String diaActual    = (String) DateFormat.format("dd",   fechaActual);
        String mesActual    = (String) DateFormat.format("MM",   fechaActual);
        String anioActual   = (String) DateFormat.format("yyyy", fechaActual);

        day = preferences.getInt("day", Integer.parseInt(diaActual));
        month = preferences.getInt("month", Integer.parseInt(mesActual) - 1);
        year = preferences.getInt("year", Integer.parseInt(anioActual));

        binding.calendario.setMaxDate(new Date().getTime());
        binding.calendario.updateDate(year, month, day);

        binding.seleccionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int day = binding.calendario.getDayOfMonth();
                int month = binding.calendario.getMonth();
                int year = binding.calendario.getYear();

                editor.putInt("day", day);
                editor.putInt("month", month);
                editor.putInt("year", year);


                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, day);
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                String fecha = format.format(calendar.getTime());
                editor.putString("fechaSeleccionada", fecha);
                editor.apply();
                Intent intent;
                switch (type){
                    case 0:
                        intent = new Intent(getContext(), ActivityMain.class);
                        startActivity(intent);
                        break;

                    case 1:
                        intent = new Intent(getContext(), ActivityRegion.class);
                        startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent(getContext(), ActivityZona.class);
                        startActivity(intent);
                        break;
                    case 3:
                        intent = new Intent(getContext(), ActivityTiendas.class);
                        startActivity(intent);
                        break;
                    default:
                            break;
                }


            }
        });

        return view;
    }

//    public void obtenerDatosCalendario(int type, String idBoton, Calendar calendar, SimpleDateFormat format){
//        if(idBoton.equals("1")){
//
//            fechaInicial = format.format(calendar.getTime());
//            fechaFinal = format.format(calendar.getTime());
//
//
//
//            switch (type){
//                case 0:
//
//
//                    Consulta consulta = new Consulta(
//                            usuario,
//                            getString(R.string.zero),
//                            getString(R.string.zero),
//                            getString(R.string.zero),
//                            fechaInicial,
//                            fechaFinal
//                    );
//
//                    break;
//                case 1:
//
//                    break;
//                case 2:
//
//                    break;
//                default:
//
//                    break;
//            }
//
//        }else if(idBoton.equals("2")){
//
//        }else if(idBoton.equals("3")){
//
//        }
//    }
//
//    public void obtenerConsulta(Consulta consulta, int type){
//
//    }

}
