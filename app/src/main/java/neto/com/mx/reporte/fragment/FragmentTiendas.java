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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import neto.com.mx.reporte.R;
import neto.com.mx.reporte.adapter.AdapterTiendas;
import neto.com.mx.reporte.adapter.AdapterVentas;
import neto.com.mx.reporte.adapter.TiendasHolder;
import neto.com.mx.reporte.databinding.FragmentTiendasBinding;
import neto.com.mx.reporte.model.dashboard.Tienda;
import neto.com.mx.reporte.model.dashboard.Tiendas;
import neto.com.mx.reporte.model.dashboard.Ventas;
import neto.com.mx.reporte.model.dashboard.VentasResponse;
import neto.com.mx.reporte.provider.ProviderTiendas;
import neto.com.mx.reporte.ui.ActivityTiendas;
import neto.com.mx.reporte.utils.Util;


public class FragmentTiendas extends DialogFragment implements TiendasHolder.Listener {

    AdapterTiendas adapter;
    FragmentTiendasBinding binding;
    SharedPreferences preferences;
    List<Tienda> listaTiendas = null;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tiendas, container, false);
        View view = binding.getRoot();
        return view;
    }

    ProgressDialog progressDialog;

    public void obtenerTiendas(String usuario, int tipoTienda) {
        progressDialog = new ProgressDialog(getContext());
        Util.loadingProgress(progressDialog, 0);
        ProviderTiendas.getInstance(getContext()).getVentas(usuario, tipoTienda, new ProviderTiendas.ConsultaTiendas() {
            @Override
            public void resolve(Tiendas tiendas) {
                if (tiendas != null) {
                    if (tiendas.getTiendas() != null) {
                        listaTiendas = tiendas.getTiendas();
                        adapter.edit().replaceAll(tiendas.getTiendas()).commit();
                        adapter.notifyItemRangeRemoved(0, adapter.getItemCount());
                        binding.recyclerview.setAdapter(adapter);
                        binding.view.setVisibility(View.VISIBLE);
                    }
                    Util.loadingProgress(progressDialog, 1);
                } else {
                    dismiss();
                    Util.loadingProgress(progressDialog, 1);
                }
            }

            @Override
            public void reject(Exception e) {

            }
        });
    }

    private static final Comparator<Tienda> ALPHABETICAL_COMPARATOR = new Comparator<Tienda>() {
        @Override
        public int compare(Tienda a, Tienda b) {
            return 0;
        }
    };

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        Window window = getDialog().getWindow();
//        window.setLayout(666, 160);
//        window.setGravity(Gravity.CENTER);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        preferences = getContext().getSharedPreferences("datosReporte", Context.MODE_PRIVATE);
        String usuario = preferences.getString("usuario", "");

        adapter = new AdapterTiendas(getContext(), ALPHABETICAL_COMPARATOR, this);
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        final int type = getArguments().getInt("typeTiendas");
        obtenerTiendas(usuario, type);


        binding.buscar.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @SuppressLint("DefaultLocale")
            @Override
            public void afterTextChanged(Editable editable) {
                if (listaTiendas != null) {
                    String texto = binding.buscar.getText().toString();
                    List<Tienda> listaTemporal = new ArrayList<>();

                    binding.recyclerview.removeAllViews();
                    adapter.edit().removeAll().commit();
                    if (texto.equals("")) {
                        adapter.edit().replaceAll(listaTiendas).commit();
                        adapter.notifyItemRangeRemoved(0, adapter.getItemCount());
                    } else {
                        for (Tienda memoria : listaTiendas) {
                            if (memoria.getNombreTienda().toLowerCase().contains(texto.toLowerCase())) {
                                listaTemporal.add(memoria);
                            }
                        }
                        adapter.edit().replaceAll(listaTemporal).commit();
                        adapter.notifyItemRangeRemoved(0, adapter.getItemCount());
                    }
                }
            }
        });


    }

    public void onResume() {
        super.onResume();
    }

    @Override
    public void onTiendasSelect(Tienda model) {

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("zona", String.valueOf(model.getIdZona()));
        editor.putString("zonaNombre", model.getNombreZona());
        editor.putString("region", String.valueOf(model.getIdRegion()));
        editor.putString("regionNombre", model.getNombreRegion());
        editor.putString("tienda", String.valueOf(model.getIdTienda()));
        editor.apply();

        Intent intent = new Intent(getContext(), ActivityTiendas.class);
        startActivity(intent);

        dismiss();
    }
}
