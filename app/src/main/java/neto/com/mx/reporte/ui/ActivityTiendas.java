package neto.com.mx.reporte.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Toast;

import com.eralp.circleprogressview.ProgressAnimationListener;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;

import neto.com.mx.reporte.R;
import neto.com.mx.reporte.adapter.AdapterVentas;
import neto.com.mx.reporte.adapter.VentasHolder;
import neto.com.mx.reporte.databinding.ActivityMainBinding;
import neto.com.mx.reporte.fragment.Aceptar;
import neto.com.mx.reporte.fragment.FragmentCalendario;
import neto.com.mx.reporte.fragment.FragmentTiendas;
import neto.com.mx.reporte.model.dashboard.Consulta;
import neto.com.mx.reporte.model.dashboard.Ventas;
import neto.com.mx.reporte.model.dashboard.VentasResponse;
import neto.com.mx.reporte.provider.ProviderDashboard;
import neto.com.mx.reporte.utils.Util;

import static neto.com.mx.reporte.ui.ActivityMain.resizeRecycler;

public class ActivityTiendas extends AppCompatActivity implements VentasHolder.Listener {

    ActivityMainBinding binding;
    String fechaInicial = "";
    String fechaFinal = "";
    SimpleDateFormat sdf;
    Date date;
    Calendar c;
    String zona;
    String region;
    String tienda;
    int banderaBoton = 0;
    SharedPreferences.Editor editor;
    int day, month, year;
    SharedPreferences preferences;
    private int tipoTienda = 0, tipoVenta = 0, banderaFiltroTiendas = 0, tipoPresupuesto = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int width = displaymetrics.widthPixels;

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        if (width < 500) {
            binding.tacometro.getLayoutParams().height = 190;
            binding.tacometro.getLayoutParams().width = 190;
            binding.total.setTextSize(18);
            binding.tiendasPromedio.setTextSize(16);
            binding.tiendasVenta.setTextSize(16);
            binding.ventaPerdida.setTextSize(16);
            binding.robotoTextView.setTextSize(12);
            binding.ventareal.setTextSize(12);
            resizeRecycler(binding, 222, this);
        }


        setSupportActionBar(binding.header.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        preferences = getSharedPreferences("datosReporte", MODE_PRIVATE);
        final String usuario = preferences.getString("usuario", "");
        Bundle bundle = getIntent().getExtras();
        try{
            String tipoTiendaBundel = bundle.getString("tipoTienda");
            String tipoVentaBundel = bundle.getString("tipoVenta");
            tipoTienda = Integer.valueOf(tipoTiendaBundel);
            tipoVenta = Integer.valueOf(tipoVentaBundel);
            banderaFiltroTiendas = 1;

            preferences = getSharedPreferences("datosReporte", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("tipoTienda", 1);
            editor.putInt("tipoVenta", 1);
            editor.apply();
        }catch (NullPointerException npe){
            tipoTienda = preferences.getInt("tipoTienda", 0);
            tipoVenta = preferences.getInt("tipoVenta", 1);
        }



        region = preferences.getString("region", "");
        zona = preferences.getString("zona", "");
        tienda = preferences.getString("tienda", "");
        editor = preferences.edit();

        final String fechaSeleccionada = preferences.getString("fechaSeleccionada", "");
        day = preferences.getInt("day", 0);
        month = preferences.getInt("month", 0);
        year = preferences.getInt("year", 0);
        tipoPresupuesto = preferences.getInt("tipoPresupuesto",2);

        date = new Date();
        sdf = new SimpleDateFormat("dd/MM/yyyy");
        fechaInicial = sdf.format(date);
        fechaFinal = sdf.format(date);

        if (tipoTienda == 1) {
            editor.putInt("tipoTienda", 1);
            binding.header.imgNuevaTiendaApertura.setImageResource(R.drawable.carritopuerta_azul);
        } else {
            binding.header.imgNuevaTiendaApertura.setImageResource(R.drawable.carritopuerta_naranja);
        }

        if (tipoVenta == 0) {
            binding.header.imgTiendasSinVentas.setImageResource(R.drawable.carritonaranja);
        } else {
            binding.header.imgTiendasSinVentas.setImageResource(R.drawable.carritoturquesa);
        }

        if(tipoPresupuesto == 2){
            binding.header.imgPresupuesto.setImageResource(R.drawable.carrito_pesos_n);
        }else{
            binding.header.imgPresupuesto.setImageResource(R.drawable.carrito_pesos);
        }

        final Consulta[] consulta = {new Consulta(usuario, region, zona, tienda, fechaInicial, fechaFinal, tipoTienda, tipoVenta, tipoPresupuesto)};

        binding.dia.setBackground(getDrawable(R.drawable.fill_left));
        binding.dia.setTextColor(ContextCompat.getColor(ActivityTiendas.this, R.color.colorPrimary));

        binding.dia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putInt("button", 0);
                editor.apply();
                binding.dia.setBackground(getDrawable(R.drawable.fill_left));
                binding.dia.setTextColor(ContextCompat.getColor(ActivityTiendas.this, R.color.colorPrimary));
                binding.semana.setBackground(getDrawable(R.drawable.square_center));
                binding.semana.setTextColor(ContextCompat.getColor(ActivityTiendas.this, R.color.turquesa));
                binding.mes.setBackground(getDrawable(R.drawable.square_right));
                binding.mes.setTextColor(ContextCompat.getColor(ActivityTiendas.this, R.color.turquesa));
                date = new Date();
                fechaInicial = sdf.format(date);
                fechaFinal = sdf.format(date);
                if (fechaSeleccionada.length() > 0) {
                    fechaInicial = fechaSeleccionada;
                    fechaFinal = fechaSeleccionada;
                    consulta[0] = new Consulta(usuario, region, zona, tienda, fechaInicial, fechaFinal, tipoTienda, tipoVenta, tipoPresupuesto);
                    obtenerVentas(binding, consulta[0]);
                } else {
                    Consulta consulta = new Consulta(usuario, region, zona, tienda, fechaInicial, fechaFinal, tipoTienda, tipoVenta, tipoPresupuesto);
                    obtenerVentas(binding, consulta);
                }
                binding.rangoFechas.setText("Consulta al d??a: " + fechaInicial);
            }
        });

        binding.semana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putInt("button", 1);
                editor.apply();
                binding.dia.setBackground(getDrawable(R.drawable.square_left));
                binding.dia.setTextColor(ContextCompat.getColor(ActivityTiendas.this, R.color.turquesa));
                binding.semana.setBackground(getDrawable(R.drawable.fill_center));
                binding.semana.setTextColor(ContextCompat.getColor(ActivityTiendas.this, R.color.colorPrimary));
                binding.mes.setBackground(getDrawable(R.drawable.square_right));
                binding.mes.setTextColor(ContextCompat.getColor(ActivityTiendas.this, R.color.turquesa));
                c = Calendar.getInstance();
                c.setFirstDayOfWeek(Calendar.MONDAY);
                c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                date = new Date();
                fechaInicial = sdf.format(c.getTime());
                fechaFinal = sdf.format(date);
                if (fechaSeleccionada.length() > 0) {
                    c = Calendar.getInstance();
                    c.setFirstDayOfWeek(Calendar.MONDAY);
                    c.set(year, month, day);
                    //c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                    int diaSemana = c.get(Calendar.DAY_OF_WEEK);
                    for (int i = 0; i < 7; i++) {
                        if (diaSemana != Calendar.MONDAY) {
                            day--;
                            c.set(year, month, day);
                            diaSemana = c.get(Calendar.DAY_OF_WEEK);
                            if (diaSemana == Calendar.MONDAY) break;
                        } else {
                            break;
                        }
                    }
                    date = new Date();
                    fechaInicial = sdf.format(c.getTime());
                    fechaFinal = fechaSeleccionada;
                    Consulta consulta = new Consulta(usuario, region, zona, tienda, fechaInicial, fechaFinal, tipoTienda, tipoVenta, tipoPresupuesto);
                    obtenerVentas(binding, consulta);
                } else {
                    Consulta consulta = new Consulta(usuario, region, zona, tienda, fechaInicial, fechaFinal, tipoTienda, tipoVenta, tipoPresupuesto);
                    obtenerVentas(binding, consulta);
                }
                binding.rangoFechas.setText("Consulta del " + fechaInicial + " al " + fechaFinal);
            }
        });

        binding.header.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ActivityTiendas.super.onBackPressed();
                if(banderaFiltroTiendas == 0 && zona != "0"){
                    Intent activityMain = new Intent(ActivityTiendas.this, ActivityZona.class);
                    startActivity(activityMain);
                }else {
                    Intent activityMain = new Intent(ActivityTiendas.this, ActivityMain.class);
                    startActivity(activityMain);
                }
                finish();

            }
        });

        binding.mes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putInt("button", 2);
                editor.apply();
                binding.dia.setBackground(getDrawable(R.drawable.square_left));
                binding.dia.setTextColor(ContextCompat.getColor(ActivityTiendas.this, R.color.turquesa));
                binding.semana.setBackground(getDrawable(R.drawable.square_center));
                binding.semana.setTextColor(ContextCompat.getColor(ActivityTiendas.this, R.color.turquesa));
                binding.mes.setBackground(getDrawable(R.drawable.fill_right));
                binding.mes.setTextColor(ContextCompat.getColor(ActivityTiendas.this, R.color.colorPrimary));
                Calendar c = Calendar.getInstance();   // this takes current date
                c.set(Calendar.DAY_OF_MONTH, 1);
                date = new Date();
                sdf = new SimpleDateFormat("dd/MM/yyyy");
                fechaInicial = sdf.format(c.getTime());
                fechaFinal = sdf.format(date);
                if (fechaSeleccionada.length() > 0) {
                    c = Calendar.getInstance();
                    c.set(year, month, day);
                    c.set(Calendar.DAY_OF_MONTH, 1);
                    date = new Date();
                    sdf = new SimpleDateFormat("dd/MM/yyyy");
                    fechaInicial = sdf.format(c.getTime());
                    fechaFinal = fechaSeleccionada;
                    consulta[0] = new Consulta(usuario, region, zona, tienda, fechaInicial, fechaFinal, tipoTienda, tipoVenta, tipoPresupuesto);
                    obtenerVentas(binding, consulta[0]);
                } else {
                    Consulta consulta = new Consulta(usuario, region, zona, tienda, fechaInicial, fechaFinal, tipoTienda, tipoVenta, tipoPresupuesto);
                    obtenerVentas(binding, consulta);
                }
                binding.rangoFechas.setText("Consulta del " + fechaInicial + " al " + fechaFinal);
            }
        });

        binding.header.nuevasTiendas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTiendas a = new FragmentTiendas();
                Bundle bundle = new Bundle();
                bundle.putInt("typeTiendas", 2);
                a.setArguments(bundle);
                a.show(getSupportFragmentManager(), "child");
            }
        });

        binding.header.tiendas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTiendas a = new FragmentTiendas();
                Bundle bundle = new Bundle();
                bundle.putInt("typeTiendas", 1);
                a.setArguments(bundle);
                a.show(getSupportFragmentManager(), "child");
            }
        });

        binding.header.calendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentCalendario a = new FragmentCalendario();
                Bundle arg = new Bundle();
                arg.putInt("type", 3);
                a.setArguments(arg);
                a.show(getSupportFragmentManager(), "child");
            }
        });

        binding.header.nuevasPresupuesto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tipoPresupuesto == 1){
                    editor.putInt("tipoPresupuesto", 2);
                    editor.apply();
                    tipoPresupuesto = 2;
                    Consulta consulta = new Consulta(usuario, region, zona, tienda, fechaInicial, fechaFinal, tipoTienda, tipoVenta, tipoPresupuesto);
                    obtenerVentas(binding,consulta);
                    binding.header.imgPresupuesto.setImageResource(R.drawable.carrito_pesos_n);
                }else{
                    editor.putInt("tipoPresupuesto", 1);
                    editor.apply();
                    tipoPresupuesto = 1;
                    Consulta consulta = new Consulta(usuario, region, zona, tienda, fechaInicial, fechaFinal, tipoTienda, tipoVenta, tipoPresupuesto);
                    obtenerVentas(binding,consulta);
                    binding.header.imgPresupuesto.setImageResource(R.drawable.carrito_pesos);
                }

            }
        });
        binding.header.information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityTiendas.this, ActivityInformacion.class);
                startActivity(intent);
            }
        });

        if (fechaSeleccionada.length() > 0) {
            banderaBoton = preferences.getInt("button", 0);
            if (banderaBoton == 0) {
                binding.dia.setBackground(getDrawable(R.drawable.fill_left));
                binding.dia.setTextColor(ContextCompat.getColor(ActivityTiendas.this, R.color.colorPrimary));
                binding.semana.setBackground(getDrawable(R.drawable.square_center));
                binding.semana.setTextColor(ContextCompat.getColor(ActivityTiendas.this, R.color.turquesa));
                binding.mes.setBackground(getDrawable(R.drawable.square_right));
                binding.mes.setTextColor(ContextCompat.getColor(ActivityTiendas.this, R.color.turquesa));
                fechaInicial = fechaSeleccionada;
                fechaFinal = fechaSeleccionada;
                consulta[0] = new Consulta(usuario, region, zona, tienda, fechaInicial, fechaFinal, tipoTienda, tipoVenta, tipoPresupuesto);
                obtenerVentas(binding, consulta[0]);
                binding.rangoFechas.setText("Consulta al d??a: " + fechaInicial);
            } else if (banderaBoton == 1) {
                binding.dia.setBackground(getDrawable(R.drawable.square_left));
                binding.dia.setTextColor(ContextCompat.getColor(ActivityTiendas.this, R.color.turquesa));
                binding.semana.setBackground(getDrawable(R.drawable.fill_center));
                binding.semana.setTextColor(ContextCompat.getColor(ActivityTiendas.this, R.color.colorPrimary));
                binding.mes.setBackground(getDrawable(R.drawable.square_right));
                binding.mes.setTextColor(ContextCompat.getColor(ActivityTiendas.this, R.color.turquesa));
                c = Calendar.getInstance();
                c.setFirstDayOfWeek(Calendar.MONDAY);
                c.set(year, month, day);
                //c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                int diaSemana = c.get(Calendar.DAY_OF_WEEK);
                for (int i = 0; i < 7; i++) {
                    if (diaSemana != Calendar.MONDAY) {
                        day--;
                        c.set(year, month, day);
                        diaSemana = c.get(Calendar.DAY_OF_WEEK);
                        if (diaSemana == Calendar.MONDAY) break;
                    } else {
                        break;
                    }
                }
                date = new Date();
                fechaInicial = sdf.format(c.getTime());
                fechaFinal = fechaSeleccionada;
                consulta[0] = new Consulta(usuario, region, zona, tienda, fechaInicial, fechaFinal, tipoTienda, tipoVenta, tipoPresupuesto);
                obtenerVentas(binding, consulta[0]);
                binding.rangoFechas.setText("Consulta del " + fechaInicial + " al " + fechaFinal);
            } else if (banderaBoton == 2) {
                binding.dia.setBackground(getDrawable(R.drawable.square_left));
                binding.dia.setTextColor(ContextCompat.getColor(ActivityTiendas.this, R.color.turquesa));
                binding.semana.setBackground(getDrawable(R.drawable.square_center));
                binding.semana.setTextColor(ContextCompat.getColor(ActivityTiendas.this, R.color.turquesa));
                binding.mes.setBackground(getDrawable(R.drawable.fill_right));
                binding.mes.setTextColor(ContextCompat.getColor(ActivityTiendas.this, R.color.colorPrimary));
                c = Calendar.getInstance();
                c.set(year, month, day);
                c.set(Calendar.DAY_OF_MONTH, 1);
                sdf = new SimpleDateFormat("dd/MM/yyyy");
                fechaInicial = sdf.format(c.getTime());
                fechaFinal = fechaSeleccionada;
                consulta[0] = new Consulta(usuario, region, zona, tienda, fechaInicial, fechaFinal, tipoTienda, tipoVenta, tipoPresupuesto);
                obtenerVentas(binding, consulta[0]);
                binding.rangoFechas.setText("Consulta del " + fechaInicial + " al " + fechaFinal);
            }
        } else {
            obtenerVentas(binding, consulta[0]);
            binding.rangoFechas.setText("Consulta al d??a: " + fechaInicial);
        }
    }

    public String converter(double conver) {
        DecimalFormat formatter = new DecimalFormat("#,###");
        return "$" + formatter.format(conver);
    }

    ProgressDialog progressDialog;
    AdapterVentas adapter;

    public void obtenerVentas(final ActivityMainBinding binding, Consulta consulta) {
        progressDialog = new ProgressDialog(ActivityTiendas.this);
        Util.loadingProgress(progressDialog, 0);
        ProviderDashboard.getInstance(this).getVentas(consulta, new ProviderDashboard.ConsultaVentas() {
            @Override
            public void resolve(VentasResponse ventasResponse) {
                try {
                    if (ventasResponse != null) {
                        Util.loadingProgress(progressDialog, 1);
                        binding.tiendasVenta.setText(ventasResponse.getTiendasConVentaGeneral() + "");
                        binding.tiendasPromedio.setText(converter(Double.parseDouble(ventasResponse.getTickPromGeneral())));
                        binding.ventaPerdida.setText(converter(Double.parseDouble(ventasResponse.getvPerdidaGeneral())));
                        binding.ventaObjetivo.setText(String.valueOf("$" + ventasResponse.getvObjetivoGeneral()));
                        binding.total.setText(converter(Double.parseDouble(ventasResponse.getvRealGeneral())));
                        if(banderaFiltroTiendas == 1){
                            String zonaNombre = preferences.getString("zonaNombre", "");
                            String regionNombre = preferences.getString("regionNombre", "");
                            String tiendaNombre = preferences.getString("tiendaNombre", "");
                            binding.lugar.setText(regionNombre + " / " + zonaNombre + " / " + tiendaNombre);
                        }else if (tipoTienda == 1 && tipoVenta == 1) {
                            String zonaNombre = preferences.getString("zonaNombre", "");
                            String regionNombre = preferences.getString("regionNombre", "");
                            binding.lugar.setText(regionNombre + " / " + zonaNombre + " / " + ventasResponse.getListaVentas().get(0).getNombreTienda());
                        } else {
                            binding.lugar.setText(ventasResponse.getListaVentas().get(0).getNombreTienda());
                        }
                        double real = Integer.valueOf(ventasResponse.getvRealGeneral());
                        double objetivo = Integer.valueOf(ventasResponse.getvObjetivoGeneral());
                        double operacion = real / objetivo * 100;
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append(converter(Double.parseDouble(ventasResponse.getvObjetivoGeneral())));
                        stringBuilder.append(getString(R.string.mdp));
                        banderaBoton = preferences.getInt("button", 0);
                        if (banderaBoton == 1 || banderaBoton == 2) {
                            binding.robotoTextViewTotal.setVisibility(View.VISIBLE);
                            StringBuilder stringBuilderTotal = new StringBuilder();
                            stringBuilderTotal.append(converter(Double.parseDouble(ventasResponse.getvObjetivoTotal())));
                            stringBuilderTotal.append(getString(R.string.mdpTotal));
                            binding.robotoTextViewTotal.setText(stringBuilderTotal);
                        } else {
                            binding.robotoTextViewTotal.setVisibility(View.GONE);
                        }
                        binding.robotoTextView.setText(stringBuilder);
                        binding.tacometro.setTextEnabled(false);
                        binding.tacometro.setInterpolator(new AccelerateDecelerateInterpolator());
                        binding.tacometro.setStartAngle(270);
                        binding.tacometro.setProgressWithAnimation((float) operacion, 2000);
                        binding.tacometro.addAnimationListener(new ProgressAnimationListener() {
                            @Override
                            public void onValueChanged(float value) {
                            }

                            @Override
                            public void onAnimationEnd() {
                            }
                        });
                        adapter = new AdapterVentas(getApplicationContext(), ALPHABETICAL_COMPARATOR, ActivityTiendas.this,true);
                        binding.recyclerview.setLayoutManager(new LinearLayoutManager(ActivityTiendas.this));
                        adapter.edit().replaceAll(ventasResponse.getListaVentas()).commit();
                        adapter.notifyItemRangeRemoved(0, adapter.getItemCount());
                        binding.recyclerview.setAdapter(adapter);
                    } else {
                        Util.loadingProgress(progressDialog, 1);
                        Aceptar a = new Aceptar();
                        a.setMensaje("Necesitas estar conectado a internet");
                        a.show(getSupportFragmentManager(), "child");
                    }
                } catch (Exception e) {
                    Aceptar a = new Aceptar();
                    a.setMensaje("No hay datos");
                    a.show(getSupportFragmentManager(), "child");
                }
            }

            @Override
            public void reject(Exception e) {
            }
        });
    }

    private static final Comparator<Ventas> ALPHABETICAL_COMPARATOR = new Comparator<Ventas>() {
        @Override
        public int compare(Ventas a, Ventas b) {
            return 0;
        }
    };

    @Override
    public void onProcesoSelect(Ventas model) {
    }

}
