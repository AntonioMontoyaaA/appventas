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
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Toast;

import com.eralp.circleprogressview.ProgressAnimationListener;

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

public class ActivityRegion extends AppCompatActivity implements VentasHolder.Listener {

    ActivityMainBinding binding;
    String fechaInicial = "";
    String fechaFinal = "";
    SimpleDateFormat sdf;
    Date date;
    Calendar c;
    SharedPreferences preferences;
    String region;
    int banderaBoton = 0;
    SharedPreferences.Editor editor;
    int day, month, year;
    private int tipoTienda = 0, tipoVenta = 0;

    //entero para saber que peticion fue hecha si truena regresar a la
    //1= normal, 2=SinVenta, 3=ConVenta, 4=, 5=, 6=, 7=, 8=,
    int peticion = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int width = displaymetrics.widthPixels;

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        int height = displaymetrics.heightPixels;

        try {
            binding.header.txtappversion.setText("v" + getPackageManager().getPackageInfo(getPackageName(), 0).versionName);
        } catch (PackageManager.NameNotFoundException ne) {
            Log.e("CARGA_FOLIO_TAG", "Error al obtener la versión: " + ne.getMessage());
        }
        if (height <= 1775) {
            resizeRecycler(binding, 140, this);
        } else if (height <= 1920) {
            resizeRecycler(binding, 190, this);
        } else if (height <= 2049){
            resizeRecycler(binding, 240, this);
        }else {
            resizeRecycler(binding, 300, this);
        }
        if (width < 500) {
            binding.tacometro.getLayoutParams().height = 190;
            binding.tacometro.getLayoutParams().width = 190;
            binding.lugar.setMaxWidth(130);
            binding.lugar.setTextSize(12);
            binding.total.setTextSize(18);
            binding.tiendasPromedio.setTextSize(16);
            binding.tiendasVenta.setTextSize(16);
            binding.ventaPerdida.setTextSize(16);
            binding.robotoTextView.setTextSize(12);
            binding.robotoTextViewTotal.setTextSize(12);
            binding.ventareal.setTextSize(12);
            resizeRecycler(binding, 200, this);
        }

        setSupportActionBar(binding.header.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        preferences = getSharedPreferences("datosReporte", MODE_PRIVATE);
        final String usuario = preferences.getString("usuario", "");
        tipoTienda = preferences.getInt("tipoTienda", 0);
        tipoVenta = preferences.getInt("tipoVenta", 1);
        region = preferences.getString("region", "");

        final String fechaSeleccionada = preferences.getString("fechaSeleccionada", "");
        day = preferences.getInt("day", 0);
        month = preferences.getInt("month", 0);
        year = preferences.getInt("year", 0);

        editor = preferences.edit();
        peticion = 1;
        if(tipoTienda == 1){
            editor.putInt("tipoTienda", 1);
            binding.header.imgNuevaTiendaApertura.setImageResource(R.drawable.carritopuerta_azul);
        }else{
            binding.header.imgNuevaTiendaApertura.setImageResource(R.drawable.carritopuerta_naranja);
        }

        if (tipoVenta == 0) {
            binding.header.imgTiendasSinVentas.setImageResource(R.drawable.carritonaranja);
        } else {
            binding.header.imgTiendasSinVentas.setImageResource(R.drawable.carritoturquesa);
        }
        date = new Date();
        sdf = new SimpleDateFormat("dd/MM/yyyy");
        fechaInicial = sdf.format(date);
        fechaFinal = sdf.format(date);
        final Consulta[] consulta = {new Consulta(usuario, region, getString(R.string.zero), getString(R.string.zero), fechaInicial, fechaFinal, tipoTienda, tipoVenta)};

        binding.header.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityRegion.super.onBackPressed();
            }
        });

        binding.dia.setBackground(getDrawable(R.drawable.fill_left));
        binding.dia.setTextColor(ContextCompat.getColor(ActivityRegion.this, R.color.colorPrimary));

        binding.dia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putInt("button", 0);
                editor.apply();
                date = new Date();
                fechaInicial = sdf.format(date);
                fechaFinal = sdf.format(date);
                peticion = 1;
                if (fechaSeleccionada.length() > 0) {
                    fechaInicial = fechaSeleccionada;
                    fechaFinal = fechaSeleccionada;
                    consulta[0] = new Consulta(usuario, region, getString(R.string.zero), getString(R.string.zero), fechaInicial, fechaFinal, tipoTienda, tipoVenta);
                    obtenerVentas(binding, consulta[0]);
                } else {
                    Consulta consulta = new Consulta(usuario, region, getString(R.string.zero), getString(R.string.zero), fechaInicial, fechaFinal, tipoTienda, tipoVenta);
                    obtenerVentas(binding, consulta);
                }
                binding.rangoFechas.setText("Consulta al día: " + fechaInicial);
            }
        });

        binding.semana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putInt("button", 1);
                editor.apply();
                c = Calendar.getInstance();
                c.setFirstDayOfWeek(Calendar.MONDAY);
                c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                date = new Date();
                fechaInicial = sdf.format(c.getTime());
                fechaFinal = sdf.format(date);
                peticion = 1;
                if (fechaSeleccionada.length() > 0) {
                    fechaFinal = fechaSeleccionada;
                } else {
                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                    String fecha = format.format(calendar.getTime());
                    editor.putString("fechaSeleccionada", fecha);
                    fechaFinal = fecha;
                    editor.putInt("day", calendar.get(Calendar.DAY_OF_MONTH));
                    editor.putInt("month", calendar.get(Calendar.MONTH));
                    editor.putInt("year", calendar.get(Calendar.YEAR));
                    year = calendar.get(Calendar.YEAR);
                    month = calendar.get(Calendar.MONTH);
                    day = calendar.get(Calendar.DAY_OF_MONTH);
                }
                c = Calendar.getInstance();
                c.setFirstDayOfWeek(Calendar.MONDAY);
                c.set(year, month, day);
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
                Consulta consulta = new Consulta(usuario, getString(R.string.zero), getString(R.string.zero), getString(R.string.zero), fechaInicial, fechaFinal, tipoTienda, tipoVenta);
                obtenerVentas(binding, consulta);
                binding.rangoFechas.setText("Consulta del " + fechaInicial + " al " + fechaFinal);
            }
        });

        binding.mes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putInt("button", 2);
                editor.apply();
                Calendar c = Calendar.getInstance();   // this takes current date
                c.set(Calendar.DAY_OF_MONTH, 1);
                date = new Date();
                sdf = new SimpleDateFormat("dd/MM/yyyy");
                fechaInicial = sdf.format(c.getTime());
                fechaFinal = sdf.format(date);
                peticion = 1;
                if (fechaSeleccionada.length() > 0) {
                    c = Calendar.getInstance();
                    c.set(year, month, day);
                    c.set(Calendar.DAY_OF_MONTH, 1);
                    date = new Date();
                    sdf = new SimpleDateFormat("dd/MM/yyyy");
                    fechaInicial = sdf.format(c.getTime());
                    fechaFinal = fechaSeleccionada;
                    consulta[0] = new Consulta(usuario, region, getString(R.string.zero), getString(R.string.zero), fechaInicial, fechaFinal, tipoTienda, tipoVenta);
                    obtenerVentas(binding, consulta[0]);
                } else {
                    Consulta consulta = new Consulta(usuario, region, getString(R.string.zero), getString(R.string.zero), fechaInicial, fechaFinal, tipoTienda, tipoVenta);
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
                arg.putInt("type", 1);
                a.setArguments(arg);
                a.show(getSupportFragmentManager(), "child");
            }
        });

        binding.header.nuevasTiendasAperturas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tipoTienda == 1) {
                    editor.putInt("tipoTienda", 2);
                    editor.apply();
                    tipoTienda = 2;
                    Consulta consulta = new Consulta(usuario, region, getString(R.string.zero), getString(R.string.zero), fechaInicial, fechaFinal, tipoTienda, tipoVenta);
                    peticion =4;
                    obtenerVentas(binding, consulta);
                    binding.header.imgNuevaTiendaApertura.setImageResource(R.drawable.carritopuerta_naranja);

                } else {
                    editor.putInt("tipoTienda", 1);
                    editor.apply();
                    tipoTienda = 1;
                    Consulta consulta = new Consulta(usuario, region, getString(R.string.zero), getString(R.string.zero), fechaInicial, fechaFinal, tipoTienda, tipoVenta);
                    peticion =5;
                    obtenerVentas(binding, consulta);
                    binding.header.imgNuevaTiendaApertura.setImageResource(R.drawable.carritopuerta_azul);
                }
            }
        });

        binding.header.tiendasSinVentas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tipoVenta == 1) {
                    editor.putInt("tipoVenta", 0);
                    editor.apply();
                    tipoVenta = 0;
                    Consulta consulta = new Consulta(usuario, region, getString(R.string.zero), getString(R.string.zero), fechaInicial, fechaFinal, tipoTienda, tipoVenta);
                    peticion = 2;
                    obtenerVentas(binding, consulta);
                    binding.header.imgTiendasSinVentas.setImageResource(R.drawable.carritonaranja);
                } else {
                    editor.putInt("tipoVenta", 1);
                    editor.apply();
                    tipoVenta = 1;
                    Consulta consulta = new Consulta(usuario, region, getString(R.string.zero), getString(R.string.zero), fechaInicial, fechaFinal, tipoTienda, tipoVenta);
                    peticion = 3;
                    obtenerVentas(binding, consulta);
                    binding.header.imgTiendasSinVentas.setImageResource(R.drawable.carritoturquesa);
                }
            }
        });

        if (fechaSeleccionada.length() > 0) {
            logicaPintadatos();
            if (banderaBoton == 0) {
                binding.dia.setBackground(getDrawable(R.drawable.fill_left));
                binding.dia.setTextColor(ContextCompat.getColor(ActivityRegion.this, R.color.colorPrimary));
                binding.semana.setBackground(getDrawable(R.drawable.square_center));
                binding.semana.setTextColor(ContextCompat.getColor(ActivityRegion.this, R.color.turquesa));
                binding.mes.setBackground(getDrawable(R.drawable.square_right));
                binding.mes.setTextColor(ContextCompat.getColor(ActivityRegion.this, R.color.turquesa));
                fechaInicial = fechaSeleccionada;
                fechaFinal = fechaSeleccionada;
                consulta[0] = new Consulta(usuario, region, getString(R.string.zero), getString(R.string.zero), fechaInicial, fechaFinal, tipoTienda, tipoVenta);
                obtenerVentas(binding, consulta[0]);
                binding.rangoFechas.setText("Consulta al día: " + fechaInicial);
            } else if (banderaBoton == 1) {
                binding.dia.setBackground(getDrawable(R.drawable.square_left));
                binding.dia.setTextColor(ContextCompat.getColor(ActivityRegion.this, R.color.turquesa));
                binding.semana.setBackground(getDrawable(R.drawable.fill_center));
                binding.semana.setTextColor(ContextCompat.getColor(ActivityRegion.this, R.color.colorPrimary));
                binding.mes.setBackground(getDrawable(R.drawable.square_right));
                binding.mes.setTextColor(ContextCompat.getColor(ActivityRegion.this, R.color.turquesa));
                c = Calendar.getInstance();
                Calendar a = c;
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
                fechaInicial = sdf.format(c.getTime());
                fechaFinal = fechaSeleccionada;
                consulta[0] = new Consulta(usuario, region, getString(R.string.zero), getString(R.string.zero), fechaInicial, fechaFinal, tipoTienda, tipoVenta);
                obtenerVentas(binding, consulta[0]);
                binding.rangoFechas.setText("Consulta del " + fechaInicial + " al " + fechaFinal);
            } else if (banderaBoton == 2) {
                binding.dia.setBackground(getDrawable(R.drawable.square_left));
                binding.dia.setTextColor(ContextCompat.getColor(ActivityRegion.this, R.color.turquesa));
                binding.semana.setBackground(getDrawable(R.drawable.square_center));
                binding.semana.setTextColor(ContextCompat.getColor(ActivityRegion.this, R.color.turquesa));
                binding.mes.setBackground(getDrawable(R.drawable.fill_right));
                binding.mes.setTextColor(ContextCompat.getColor(ActivityRegion.this, R.color.colorPrimary));
                Calendar c = Calendar.getInstance();
                c.set(year, month, day);
                c.set(Calendar.DAY_OF_MONTH, 1);
                sdf = new SimpleDateFormat("dd/MM/yyyy");
                fechaInicial = sdf.format(c.getTime());
                fechaFinal = fechaSeleccionada;
                consulta[0] = new Consulta(usuario, region, getString(R.string.zero), getString(R.string.zero), fechaInicial, fechaFinal, tipoTienda, tipoVenta);
                obtenerVentas(binding, consulta[0]);
                binding.rangoFechas.setText("Consulta del " + fechaInicial + " al " + fechaFinal);
            }
        } else {
            obtenerVentas(binding, consulta[0]);
            binding.rangoFechas.setText("Consulta al día: " + fechaInicial);
        }
    }

    public String converter(double conver) {
        DecimalFormat formatter = new DecimalFormat("#,###");
        return "$" + formatter.format(conver);
    }

    ProgressDialog progressDialog;
    AdapterVentas adapter;

    public void obtenerVentas(final ActivityMainBinding binding, Consulta consulta) {
        progressDialog = new ProgressDialog(ActivityRegion.this);
        Util.loadingProgress(progressDialog, 0);
        ProviderDashboard.getInstance(this).getVentas(consulta, new ProviderDashboard.ConsultaVentas() {
            @Override
            public void resolve(VentasResponse ventasResponse) {
                if (ventasResponse != null) {
                    logicaPintadatos();
                    Util.loadingProgress(progressDialog, 1);
                    binding.tiendasVenta.setText(ventasResponse.getTiendasConVentaGeneral() + "");
                    binding.tiendasPromedio.setText(converter(Double.parseDouble(ventasResponse.getTickPromGeneral())));
                    binding.ventaPerdida.setText(converter(Double.parseDouble(ventasResponse.getvPerdidaGeneral())));
                    binding.ventaObjetivo.setText(String.valueOf("$" + ventasResponse.getvObjetivoGeneral()));
                    binding.total.setText(converter(Double.parseDouble(ventasResponse.getvRealGeneral())));

                    String regionNombre = preferences.getString("regionNombre", "");
                    binding.lugar.setText(regionNombre);
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
                    adapter = new AdapterVentas(getApplicationContext(), ALPHABETICAL_COMPARATOR, ActivityRegion.this);
                    binding.recyclerview.setLayoutManager(new LinearLayoutManager(ActivityRegion.this));
                    adapter.edit().replaceAll(ventasResponse.getListaVentas()).commit();
                    adapter.notifyItemRangeRemoved(0, adapter.getItemCount());
                    binding.recyclerview.setAdapter(adapter);
                } else {
                    Util.loadingProgress(progressDialog, 1);
                    Aceptar a = new Aceptar();
                    a.setMensaje("Necesitas estar conectado a internet");
                    a.show(getSupportFragmentManager(), "child");
                    actualizaSiUnaPetiiconFalla();
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
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("zona", String.valueOf(model.getTiendaId()));
        editor.putString("zonaNombre", model.getNombreTienda());
        editor.putInt("button", 0);
        editor.apply();
        Intent intent = new Intent(this, ActivityZona.class);
        startActivity(intent);
    }
    public void logicaPintadatos() {
        banderaBoton = preferences.getInt("button", 0);
        if (banderaBoton == 0) {
            binding.dia.setBackground(getDrawable(R.drawable.fill_left));
            binding.dia.setTextColor(ContextCompat.getColor(ActivityRegion.this, R.color.colorPrimary));
            binding.semana.setBackground(getDrawable(R.drawable.square_center));
            binding.semana.setTextColor(ContextCompat.getColor(ActivityRegion.this, R.color.turquesa));
            binding.mes.setBackground(getDrawable(R.drawable.square_right));
            binding.mes.setTextColor(ContextCompat.getColor(ActivityRegion.this, R.color.turquesa));
        } else if (banderaBoton == 1) {
            binding.dia.setBackground(getDrawable(R.drawable.square_left));
            binding.dia.setTextColor(ContextCompat.getColor(ActivityRegion.this, R.color.turquesa));
            binding.semana.setBackground(getDrawable(R.drawable.fill_center));
            binding.semana.setTextColor(ContextCompat.getColor(ActivityRegion.this, R.color.colorPrimary));
            binding.mes.setBackground(getDrawable(R.drawable.square_right));
            binding.mes.setTextColor(ContextCompat.getColor(ActivityRegion.this, R.color.turquesa));
        } else if (banderaBoton == 2) {
            binding.dia.setBackground(getDrawable(R.drawable.square_left));
            binding.dia.setTextColor(ContextCompat.getColor(ActivityRegion.this, R.color.turquesa));
            binding.semana.setBackground(getDrawable(R.drawable.square_center));
            binding.semana.setTextColor(ContextCompat.getColor(ActivityRegion.this, R.color.turquesa));
            binding.mes.setBackground(getDrawable(R.drawable.fill_right));
            binding.mes.setTextColor(ContextCompat.getColor(ActivityRegion.this, R.color.colorPrimary));
        }
    }

    public void actualizaSiUnaPetiiconFalla(){
        if(peticion == 2){
            binding.header.imgTiendasSinVentas.setImageResource(R.drawable.carritoturquesa);
        }
        if(peticion == 3){
            binding.header.imgTiendasSinVentas.setImageResource(R.drawable.carritonaranja);
        }
        if(peticion == 4){
            binding.header.imgNuevaTiendaApertura.setImageResource(R.drawable.carritopuerta_azul);
        }
        if(peticion == 5){
            binding.header.imgNuevaTiendaApertura.setImageResource(R.drawable.carritopuerta_naranja);
        }
    }
}
