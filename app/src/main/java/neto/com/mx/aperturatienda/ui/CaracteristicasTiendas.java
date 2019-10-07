package neto.com.mx.aperturatienda.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import neto.com.mx.aperturatienda.MainActivity;
import neto.com.mx.aperturatienda.R;
import neto.com.mx.aperturatienda.adapter.TopArticulosAdapter;
import neto.com.mx.aperturatienda.fragment.FragmentCalendario;
import neto.com.mx.aperturatienda.model.Indicadores.CursorIndicadoresVo;
import neto.com.mx.aperturatienda.model.Indicadores.CursorTopArticulosVo;
import neto.com.mx.aperturatienda.model.Indicadores.IndicadorResponse;
import neto.com.mx.aperturatienda.model.caracteristicasGenerales.Caracteristica;
import neto.com.mx.aperturatienda.model.caracteristicasGenerales.ComponentesPantalla;
import neto.com.mx.aperturatienda.model.guardacaracteristica.DatosEvaluacion;
import neto.com.mx.aperturatienda.model.guardacaracteristica.GuardaCaracteristicaRequest;
import neto.com.mx.aperturatienda.model.guardacaracteristica.GuardaCaracteristicaResponse;
import neto.com.mx.aperturatienda.model.tiendaEspecifica.CaracteristicasTResponse;
import neto.com.mx.aperturatienda.provider.ProviderCaracteristicasTienda;
import neto.com.mx.aperturatienda.provider.ProviderGuardaCaracteristica;
import neto.com.mx.aperturatienda.provider.ProviderIndicadores;
import neto.com.mx.aperturatienda.utils.ConversorDpsToPixels;
import neto.com.mx.aperturatienda.utils.Util;


public class CaracteristicasTiendas extends AppCompatActivity {
    final String TAG = "CaracteristicasTiendas";
    ArrayList<ComponentesPantalla> listaComponentes = new ArrayList<>();
    List<Caracteristica> listCaracteristicas = null;
    TabLayout tableLayout;
    Button ll_btn_guardar_c_g, btn_abrirWorkChat;
    LinearLayout ll_caracteristicas_ii,
            ll_childLayout,
            ll_botones,
            indicadores;
    ProgressDialog progressDialog;
    SharedPreferences preferences, userDetails;
    boolean permisoEdicion = false;

    // variables de indicadores -------------------------------------------------
    private RecyclerView mRecyclerView;
    private TopArticulosAdapter mAdapter;
    RelativeLayout contenidoLista;
    TextView textoArtMayor, textoArtMenor,fechaApertura, diasInvConfigurados, existencia, frecuencia, montoVenta, nombre, stockout, tamanioTienda, tiendaIdView, ventaAlcohol, ventaPerdida, ventaPiezas;
    RecyclerView articulosMayorVta, articulosMenorVta;
    final String TAG_ind = "Indicadores.java";
    // ---------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caracteristicas_tiendas);
        userDetails = getApplicationContext().getSharedPreferences("usuario", MODE_PRIVATE);
        preferences = getSharedPreferences("apertura", MODE_PRIVATE);

        final int paisId = preferences.getInt("paisId", MODE_PRIVATE);
        final int tiendaId = preferences.getInt("tiendaId", MODE_PRIVATE);
        final int usuarioId = userDetails.getInt("usuario", MODE_PRIVATE);
        final int edicionInt = userDetails.getInt("permisoEdicion", MODE_PRIVATE);


        if(edicionInt == 1){
            permisoEdicion = true;
        }else{
            permisoEdicion = false;
        }

        ll_childLayout = findViewById(R.id.ll_childLayout);
        ll_caracteristicas_ii = findViewById(R.id.ll_caracteristicas_ii);
        ll_botones = findViewById(R.id.ll_botones);
        ll_btn_guardar_c_g = findViewById(R.id.ll_btn_guardar_c_g);
        ll_btn_guardar_c_g.setEnabled(permisoEdicion);
        btn_abrirWorkChat = findViewById(R.id.btn_abrirWorkChat);
        indicadores = findViewById(R.id.indicadores);
        progressDialog = new ProgressDialog(CaracteristicasTiendas.this);
        textoArtMayor = findViewById(R.id.tabla_articulos_mayor_viewText);
        textoArtMenor = findViewById(R.id.tabla_articulos_menor_viewText);

        // FUNCIONAMIENTO DEL CABECERO -------------------------------------------------------------
        // -----------------------------------------------------------------------------------------



        tableLayout = findViewById(R.id.tab_layout);
        tableLayout.addTab(tableLayout.newTab().setText("CARACTERISTICAS"));
        tableLayout.addTab(tableLayout.newTab().setText("INDICADORES"));
        List<String> list = new ArrayList<>();
        list.add("CARACTERISTICAS");
        list.add("INDICADORES");

        for (int i = 0; i < tableLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tableLayout.getTabAt(i);
            if (tab != null) {
                TextView ttv = new TextView(this);
                tab.setCustomView(ttv);
                Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/Roboto-Bold.ttf");
                ttv.setTypeface(tf);
                ttv.setText(list.get(i));
                ttv.setTextColor(ContextCompat.getColor(this, R.color.blanco));
            }
        }
        obtenerCaracteristicaTienda(paisId, tiendaId);

        tableLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    ll_caracteristicas_ii.setVisibility(View.VISIBLE);
                    indicadores.setVisibility(View.GONE);
                } else if (tab.getPosition() == 1) {
                    cargaPantallaIndicadores();
                    ll_caracteristicas_ii.setVisibility(View.GONE);
                    indicadores.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        btn_abrirWorkChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.facebook.workchat");
                if (launchIntent != null) {
                    Log.d(TAG, "abrirWorkPlace: " + launchIntent.toString());
                    startActivity(launchIntent);
                } else {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.facebook.workchat")));
                }
            }
        });
        ll_btn_guardar_c_g.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.loadingProgress(progressDialog, 0);
                final List<DatosEvaluacion> datosEvaluacions = new ArrayList<DatosEvaluacion>();
                int caracteristicaId;
                int nivelCaracteristicaId;
                String comentarios = "0";
                String valor = "-1.0";
                String valorReal = "0";

                for (ComponentesPantalla comp : listaComponentes) {
                    int seleccion = 0;

                    caracteristicaId = comp.getCaracteristicaId();
                    nivelCaracteristicaId = comp.getNivelId();
                    if (comp.getElementoId() == 1) { // EDIT TEXT
                        EditText elemento = comp.getEditText();
                        String texto = elemento.getText().toString();
                        if (!texto.equals("") && texto != null) {
                            if (caracteristicaId == 9) {
                                valorReal = texto;
                                valor = "1";
                            } else {
                                valorReal = "0";
                                valor = texto;
                            }
                            DatosEvaluacion dato = new DatosEvaluacion(caracteristicaId, nivelCaracteristicaId, valor, valorReal, comentarios);
                            datosEvaluacions.add(dato);
//                                Log.d(TAG," dato -> "+dato.getCaracteristicaId()+ " "+ dato.getNivelCaracteristicaId()+" "+dato.getValor()+" "+dato.getValorReal());

                        }
                    }

                    if (comp.getElementoId() == 2) { // SPINNER
                        Spinner elemento = comp.getSpiner();
                        seleccion = elemento.getSelectedItemPosition();
                        nivelCaracteristicaId = (Integer) comp.getSpinerid().get(seleccion);
                        valor = "1";
                        valorReal = "0";

                        DatosEvaluacion dato = new DatosEvaluacion(caracteristicaId, nivelCaracteristicaId, valor, valorReal, comentarios);
                        datosEvaluacions.add(dato);
//                        Log.d(TAG," dato -> "+dato.getCaracteristicaId()+ " "+ dato.getNivelCaracteristicaId()+" "+dato.getValor()+" "+dato.getValorReal());

                    }
                    if (comp.getElementoId() == 3) { // SWITCH
                        Switch elemento = comp.getElemswitch();
                        valorReal = "0";
                        if (elemento.isChecked()) {
                            valor = "1.0";
                        } else {
                            valor = "0.0";
                        }
                        DatosEvaluacion dato = new DatosEvaluacion(caracteristicaId, nivelCaracteristicaId, valor, valorReal, comentarios);
                        datosEvaluacions.add(dato);
                    }
                    if (comp.getElementoId() == 4) { // CHECKBOX
                        CheckBox elemento = comp.getCheckbox();
                        if (elemento.isChecked()) {
                            valor = "1.0";
                        } else {
                            valor = "0.0";
                        }

                        DatosEvaluacion dato = new DatosEvaluacion(caracteristicaId, nivelCaracteristicaId, valor, valorReal, comentarios);
                        datosEvaluacions.add(dato);

                    }
                    if (comp.getElementoId() == 5) {  // CALENDARIOS - TEXT VIEW
                        TextView elemento = comp.getDate();
                        String texto = elemento.getText().toString();
                        if (!texto.equals("") && texto != null) {
                            valorReal = texto;
                            valor = "1";

                            DatosEvaluacion dato = new DatosEvaluacion(caracteristicaId, nivelCaracteristicaId, valor, valorReal, comentarios);
                            datosEvaluacions.add(dato);
                        }
                    }
                }
//                Log.d(TAG, ""+usuarioId);
                ProviderGuardaCaracteristica.getInstance(CaracteristicasTiendas.this).GuardaCaracteristicas(new GuardaCaracteristicaRequest(paisId, usuarioId, tiendaId, 1, datosEvaluacions), new ProviderGuardaCaracteristica.GuardaCaracteristicaI() {
                    @Override
                    public void resolve(GuardaCaracteristicaResponse o) {
                        try {
                            if (o.getMensaje() != null && o.getMensaje() != "") {
                                Toast.makeText(CaracteristicasTiendas.this, o.getMensaje(), Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(CaracteristicasTiendas.this, MainActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(CaracteristicasTiendas.this, "Ocurrió un error al guardar la información", Toast.LENGTH_SHORT).show();
                            }
                        }
                        catch(Exception e){
                            Toast.makeText(CaracteristicasTiendas.this, "Ocurrió un error al guardar la información", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void reject(Exception e) {
                    }
                });
                Util.loadingProgress(progressDialog, 1);
            }
        });
    }

    public void obtenerCaracteristicaTienda(int paisId, int tiendaId) {
        Util.loadingProgress(progressDialog, 0);
        ProviderCaracteristicasTienda.getInstance(this).getCaracteristicaTienda(paisId, tiendaId, new ProviderCaracteristicasTienda.CaracteristicaCTienda() {
            @Override
            public void resolver(CaracteristicasTResponse caracteristicasTResponse) {

                if (caracteristicasTResponse != null) {
                    if (caracteristicasTResponse.getListaCaracteristicas() != null) {
//                        Log.d(TAG,caracteristicasTResponse.getListaCaracteristicas()+" < ------ segundo if");
                        listCaracteristicas = caracteristicasTResponse.getListaCaracteristicas();
                        metodoGenerador(listCaracteristicas);
                        ll_caracteristicas_ii.setVisibility(View.VISIBLE);
                        ll_botones.setVisibility(View.VISIBLE);
                        ll_childLayout.setVisibility(View.VISIBLE);
                    }
                } else {
                    Toast.makeText(CaracteristicasTiendas.this, "No se encontraron características", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void reject(Exception e) {
            }
        });
        Util.loadingProgress(progressDialog, 1);
    }

    public void metodoGenerador(List<Caracteristica> lista) {
        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/Roboto-Bold.ttf");
        Typeface tfr = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/Roboto-Light.ttf");
        ConversorDpsToPixels pixels = new ConversorDpsToPixels();
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        int widthScreen = metrics.widthPixels - pixels.Conversor(getApplicationContext(), 20); // ancho absoluto en pixels
        int widthEditText1 = (widthScreen / 3) * 2;
        int widthExitText2 = widthScreen / 3;
        int widthSpinner1 = widthScreen / 2;
        int widthSpinner2 = widthScreen / 2;
        int widhtSwitch1 = (widthScreen / 3) * 2;
        int widhtSwitch2 = widthScreen / 3;
        int widthCheckBox1 = LinearLayout.LayoutParams.WRAP_CONTENT;
        int widthCheckBox2 = widthScreen - widthCheckBox1;
        int calendario1 = widthScreen / 2;
        int calendario2 = calendario1 / 2;



        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        List<Caracteristica> listaTemporal = new ArrayList<Caracteristica>();

        for (Caracteristica row : lista) {
//            Log.d(TAG, row.getCaracteristicaId() + "-" + row.getCaracteristica() + " " +
//                    row.getNivelCaracteristicaId() + "-" + row.getNivelCaracteristica() +
//                    " " + row.getComponenteHtmlId() + "-" + row.getComponenteHtml() + " " + row.getNumComponenteHtml());
            boolean bandera = false;
            for (Caracteristica elem : listaTemporal) {
                if (elem.getCaracteristicaId() == row.getCaracteristicaId() &&
                        elem.getComponenteHtmlId() == row.getComponenteHtmlId() &&
                        elem.getNumComponenteHtml() == row.getNumComponenteHtml()
                ) {
                    bandera = true;
                    break;
                }
            }
            if (!bandera) {
                listaTemporal.add(row);
            }
        }
        int caracteristica = 0;
        for (Caracteristica row : listaTemporal) {
            if (row.getCaracteristicaId() != caracteristica) {
                caracteristica = row.getCaracteristicaId();

                LinearLayout contenedorPrinc = new LinearLayout(getApplicationContext()); //renglon principal
                ll_childLayout.addView(contenedorPrinc, layoutParams);

                TextView miTextView = new TextView(getApplicationContext()); //creacion de descripcion
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(0, 20, 0, 20);
                miTextView.setLayoutParams(params);
                miTextView.setText(row.getCaracteristica());
                miTextView.setTypeface(tf);
                miTextView.setTextColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
                miTextView.setTextSize(18);
                contenedorPrinc.addView(miTextView);


            }


            if (row.getComponenteHtmlId() == 1) {
                for (Caracteristica elem : lista) {
                    if (elem.getCaracteristicaId() == row.getCaracteristicaId() &&
                            elem.getComponenteHtmlId() == row.getComponenteHtmlId() &&
                            elem.getNumComponenteHtml() == row.getNumComponenteHtml()
                    ) {

                        LinearLayout contenedor = new LinearLayout(getApplicationContext()); //renglon principal
                        ll_childLayout.addView(contenedor, layoutParams);
                        // ------------------------------- // PARA TEXT DESCRIPCION
                        TextView miTextViewSub = new TextView(getApplicationContext()); //creacion de descripcion
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(widthEditText1,
                                LinearLayout.LayoutParams.WRAP_CONTENT);
                        params.setMargins(0, 10, 0, 10);
                        miTextViewSub.setLayoutParams(params);
                        miTextViewSub.setTypeface(tfr);
                        miTextViewSub.setText(elem.getNivelCaracteristica());
                        miTextViewSub.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.negro));
                        miTextViewSub.setTextSize(14);
                        contenedor.addView(miTextViewSub);

                        // --------------------- PARA EDITTEXT
                        EditText editTexto = new EditText(getApplicationContext());
                        LinearLayout.LayoutParams paramsTxt = new LinearLayout.LayoutParams(widthExitText2,
                                LinearLayout.LayoutParams.WRAP_CONTENT);
                        paramsTxt.setMargins(0, 10, 0, 10);

                        if (elem.getCaracteristicaId() == 9) {
                            editTexto.setInputType(1);
                        } else {
                            editTexto.setInputType(2);
                        }
                        editTexto.setLayoutParams(paramsTxt);
                        editTexto.setTypeface(tfr);

                        contenedor.addView(editTexto);
                        ComponentesPantalla componente = new ComponentesPantalla();
                        componente.setCaracteristicaId(elem.getCaracteristicaId());
                        componente.setNivelId(elem.getNivelCaracteristicaId());
                        componente.setElementoId(1);
                        componente.setEditText(editTexto);

                        listaComponentes.add(componente);

                        if (elem.getCaracteristicaId() == 9) {
                            if (elem.getValorGuardado() == 1) {
                                editTexto.setText(elem.getValorReal());
                            }
                        } else if (elem.getCaracteristicaId() == 10) {
                            if (elem.getValorGuardado() == 1) {
                                editTexto.setText((int) elem.getValor() + "");
                            }
                        } else {
                            if (elem.getValorGuardado() == 1) {
                                editTexto.setText(elem.getValor() + "");
                            }
                        }
                           editTexto.setEnabled(permisoEdicion);

                    }
                }
            } else if (row.getComponenteHtmlId() == 2) {
                int seleccionado = 0;
                LinearLayout contenedor = new LinearLayout(getApplicationContext()); //renglon principal
                ll_childLayout.addView(contenedor, layoutParams);
                // ------------------------------- // PARA TEXT DESCRIPCION
                TextView miTextViewSub = new TextView(getApplicationContext()); //creacion de descripcion
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(widthSpinner1,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(0, 10, 0, 10);
                miTextViewSub.setLayoutParams(params);
                miTextViewSub.setText(row.getCaracteristica());
                miTextViewSub.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.negro));
                miTextViewSub.setTextSize(14);
                miTextViewSub.setTypeface(tfr);
                contenedor.addView(miTextViewSub);

                List<String> listacadenas = new ArrayList<>();
                List<Integer> listaId = new ArrayList<>();

                int i = 0;
                for (Caracteristica elem : lista) {
                    if (elem.getCaracteristicaId() == row.getCaracteristicaId() &&
                            elem.getComponenteHtmlId() == row.getComponenteHtmlId() &&
                            elem.getNumComponenteHtml() == row.getNumComponenteHtml()
                    ) {
                        listacadenas.add(elem.getNivelCaracteristica());
                        listaId.add(elem.getNivelCaracteristicaId());
                        if (elem.getValorGuardado() == 1) {
                            seleccionado = listacadenas.size();

                        }
                    }
                    i++;
                }
                // ------------------------------- // PARA SPINNER
                Spinner spi = new Spinner(getApplicationContext());

                spi.setId(row.getCaracteristicaId());
                LinearLayout.LayoutParams paramsSpi = new LinearLayout.LayoutParams(widthSpinner2,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                paramsSpi.setMargins(0, 10, 0, 10);
                spi.setLayoutParams(paramsSpi);
                spi.setEnabled(permisoEdicion);

                spi.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, listacadenas));
                contenedor.addView(spi);
                spi.setSelection(--seleccionado);
//                Log.d(TAG, "seleccionado "+seleccionado);
                ComponentesPantalla componente = new ComponentesPantalla();
                componente.setCaracteristicaId(row.getCaracteristicaId());
                componente.setNivelId(row.getNivelCaracteristicaId());
                componente.setSpiner(spi);
                componente.setElementoId(2);
                componente.setSpinerid(listaId);

                listaComponentes.add(componente);


            } else if (row.getComponenteHtmlId() == 3) {
                for (Caracteristica elem : lista) {
                    int id = Integer.parseInt(elem.getCaracteristicaId() + "" + elem.getNivelCaracteristicaId());
                    if (elem.getCaracteristicaId() == row.getCaracteristicaId() &&
                            elem.getComponenteHtmlId() == row.getComponenteHtmlId() &&
                            elem.getNumComponenteHtml() == row.getNumComponenteHtml()
                    ) {
                        LinearLayout contenedor = new LinearLayout(getApplicationContext()); //renglon principal
                        contenedor.setWeightSum(10);
                        ll_childLayout.addView(contenedor, layoutParams);
                        // ------------------------------- // PARA TEXT DESCRIPCION
                        TextView miTextViewSub = new TextView(getApplicationContext()); //creacion de descripcion
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(widhtSwitch1,
                                LinearLayout.LayoutParams.WRAP_CONTENT);
                        params.setMargins(0, 10, 0, 10);
                        miTextViewSub.setLayoutParams(params);
                        miTextViewSub.setTypeface(tfr);
                        miTextViewSub.setText(elem.getNivelCaracteristica());
                        miTextViewSub.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.negro));
                        miTextViewSub.setTextSize(14);

                        contenedor.addView(miTextViewSub);

                        // --------------------- PARA SWITCH
                        Switch switchD = new Switch(getApplicationContext());
                        switchD.setId(id);
                        LinearLayout.LayoutParams paramsSw = new LinearLayout.LayoutParams(widhtSwitch2,
                                LinearLayout.LayoutParams.WRAP_CONTENT);
                        paramsSw.setMargins(0, 10, 0, 10);
                        switchD.setLayoutParams(paramsSw);
                        switchD.setEnabled(permisoEdicion);
                        contenedor.addView(switchD);

                        ComponentesPantalla componente = new ComponentesPantalla();
                        componente.setCaracteristicaId(row.getCaracteristicaId());
                        componente.setNivelId(row.getNivelCaracteristicaId());
                        componente.setElementoId(3);
                        componente.setElemswitch(switchD);
                        listaComponentes.add(componente);

                        if (elem.getValorGuardado() == 1) {
                            if ((int) elem.getValor() == 1) {
                                switchD.setChecked(true);
                            } else if ((int) elem.getValor() == 0) {
                                switchD.setChecked(false);
                            }
                        }

                    }
                }
            } else if (row.getComponenteHtmlId() == 4) {
                for (Caracteristica elem : lista) {
                    int id = Integer.parseInt(elem.getCaracteristicaId() + "" + elem.getNivelCaracteristicaId());
                    if (elem.getCaracteristicaId() == row.getCaracteristicaId() &&
                            elem.getComponenteHtmlId() == row.getComponenteHtmlId() &&
                            elem.getNumComponenteHtml() == row.getNumComponenteHtml()
                    ) {
                        LinearLayout contenedor = new LinearLayout(getApplicationContext()); //renglon principal
                        contenedor.setWeightSum(10);
                        ll_childLayout.addView(contenedor, layoutParams);
                        // ------------------------------- // PARA TEXT DESCRIPCION
                        TextView miTextViewSub = new TextView(getApplicationContext()); //creacion de descripcion
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(widthCheckBox2,
                                LinearLayout.LayoutParams.WRAP_CONTENT);
                        params.setMargins(0, 10, 0, 10);
                        miTextViewSub.setLayoutParams(params);
                        miTextViewSub.setText(elem.getNivelCaracteristica());
                        miTextViewSub.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.negro));
                        miTextViewSub.setTypeface(tfr);
                        miTextViewSub.setTextSize(14);

                        // --------------------- PARA CHECKBOX
                        CheckBox checkBox = new CheckBox(getApplicationContext());
                        checkBox.setId(id);
                        LinearLayout.LayoutParams paramsChk = new LinearLayout.LayoutParams(widthCheckBox1,
                                LinearLayout.LayoutParams.WRAP_CONTENT);
                        paramsChk.setMargins(0, 10, 0, 10);
                        checkBox.setLayoutParams(paramsChk);
                        checkBox.setEnabled(permisoEdicion);
                        contenedor.addView(checkBox);
                        contenedor.addView(miTextViewSub);

                        ComponentesPantalla componente = new ComponentesPantalla();
                        componente.setCaracteristicaId(row.getCaracteristicaId());
                        componente.setNivelId(row.getNivelCaracteristicaId());
                        componente.setElementoId(4);
                        componente.setCheckbox(checkBox);
                        listaComponentes.add(componente);

                        if (elem.getValorGuardado() == 1) {
                            if ((int) elem.getValor() == 1) {
                                checkBox.setChecked(true);
                            } else if ((int) elem.getValor() == 0) {
                                checkBox.setChecked(false);
                            }
                        }
                    }
                }
            } else if (row.getComponenteHtmlId() == 5) { // CALENDARIO
                for (Caracteristica elem : lista) {
                    int id = Integer.parseInt(elem.getCaracteristicaId() + "" + elem.getNivelCaracteristicaId());
                    if (elem.getCaracteristicaId() == row.getCaracteristicaId() &&
                            elem.getComponenteHtmlId() == row.getComponenteHtmlId() &&
                            elem.getNumComponenteHtml() == row.getNumComponenteHtml()
                    ) {
                        LinearLayout contenedor = new LinearLayout(getApplicationContext()); //renglon principal
                        contenedor.setWeightSum(10);
                        ll_childLayout.addView(contenedor, layoutParams);

                        // ------------------------------- // PARA TEXT DESCRIPCION
                        TextView miTextViewSub = new TextView(getApplicationContext()); //creacion de descripcion
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(calendario1,
                                LinearLayout.LayoutParams.WRAP_CONTENT);
                        params.setMargins(0, 40, 0, 40);
                        miTextViewSub.setLayoutParams(params);
                        miTextViewSub.setText(elem.getNivelCaracteristica());
                        miTextViewSub.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.negro));
                        miTextViewSub.setTextSize(14);
                        miTextViewSub.setTypeface(tfr);
                        contenedor.addView(miTextViewSub);

                        // ------------------------------- // PARA TEXT CONTENEDOR DE FECHA
                        final TextView fechaCambiante = new TextView(getApplicationContext()); //creacion de descripcion
                        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(calendario2,
                                LinearLayout.LayoutParams.WRAP_CONTENT);
                        params2.setMargins(0, 40, 0, 40);
                        fechaCambiante.setLayoutParams(params2);
                        fechaCambiante.setId(id);
                        fechaCambiante.setText("");
                        fechaCambiante.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.negro));
                        fechaCambiante.setTextSize(14);
                        fechaCambiante.setTypeface(tfr);
                        contenedor.addView(fechaCambiante);
                        if (elem.getValorGuardado() == 1) {
                            fechaCambiante.setText(elem.getValorReal());
                        }

                        ComponentesPantalla componente = new ComponentesPantalla();
                        componente.setCaracteristicaId(row.getCaracteristicaId());
                        componente.setNivelId(row.getNivelCaracteristicaId());
                        componente.setElementoId(5);
                        componente.setDate(fechaCambiante);
                        listaComponentes.add(componente);

                        // --------------------- PARA DATE

                        final ImageView iconoCalendario = new ImageView(getApplicationContext()); //creacion de descripcion
                        int heightIcono = pixels.Conversor(getApplicationContext(), 25);
                        int widthIcono = heightIcono;
                        LinearLayout.LayoutParams params3 = new LinearLayout.LayoutParams(
                                widthIcono,
                                heightIcono
                        );
                        params3.setMargins(calendario2 / 2, 40, 0, 40);
                        iconoCalendario.setImageResource(R.drawable.calendario);
                        iconoCalendario.setLayoutParams(params3);
                        iconoCalendario.setEnabled(permisoEdicion);
                        contenedor.addView(iconoCalendario);

                        iconoCalendario.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                FragmentCalendario a = new FragmentCalendario();
                                Bundle arg = new Bundle();
                                arg.putInt("type", 0);
                                a.setArguments(arg);
                                a.show(getSupportFragmentManager(), "child");
                                a.setListener(new FragmentCalendario.InterfaceFragmentCalendario() {
                                    @Override
                                    public void fechaSeleccionada(String fechaSeleccionada) {
                                        fechaCambiante.setText(fechaSeleccionada);
                                    }
                                });
                            }
                        });
                    }
                }
            }
        }
    }

    public void abrirWorkPlace(View view) {
        /*
        final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("com.facebook.workchat"));
        intent.putExtra("EXTRA_LOCUS_ID", "http://m.me/100034117639534");
        startActivity(intent);
        */
        Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.facebook.workchat");
        Log.d(TAG, "abrirWorkPlace: " + launchIntent.toString());
        if (launchIntent != null) {
            //launchIntent.putExtra("user","100026442226745");
            Log.d(TAG, "abrirWorkPlace: " + launchIntent.toString());
            startActivity(launchIntent);
        } else {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.facebook.workchat")));
        }
    }

    // CODIGO PARA INDICADORES
//    private RecyclerView mRecyclerView;
//    private TopArticulosAdapter mAdapter;
//    LinearLayout indicadores ;
//    RelativeLayout contenidoLista;
//    TextView fechaApertura, diasInvConfigurados, existencia, frecuencia, montoVenta, nombre, stockout, tamanioTienda, tiendaIdView, ventaAlcohol, ventaPerdida, ventaPiezas;
//    RecyclerView articulosMayorVta, articulosMenorVta;
//    SharedPreferences preferences;
//    final String TAG = "Indicadores.java";

    public void cargaPantallaIndicadores(){
        Util.loadingProgress(progressDialog, 0);
        preferences = getSharedPreferences("apertura", MODE_PRIVATE);
        final int paisId = preferences.getInt("paisId", 1);
        final int tiendaId = preferences.getInt("tiendaId", 1);
        obtieneIndicadores(paisId,tiendaId);
    }

    public void obtieneIndicadores(int paisId, int tiendaId){


        //articulosMayorVta = (RecyclerView)findViewById(R.id.rvMayorVenta);
        //articulosMenorVta = (RecyclerView)findViewById(R.id.rvMenorVenta);
        nombre = findViewById(R.id.nombreIndicador);
        existencia = findViewById(R.id.indicadores_existencia);
        frecuencia = findViewById(R.id.indicadores_frecuencia);
        montoVenta = findViewById(R.id.indicadores_montoVenta);
        stockout = findViewById(R.id.indicadores_stockout);
        tamanioTienda = findViewById(R.id.indicadores_tamanioTienda);
        tiendaIdView = findViewById(R.id.indicadores_tiendaId);
        ventaAlcohol = findViewById(R.id.indicadores_ventaAlcohol);
        ventaPerdida = findViewById(R.id.indicadores_ventaPerdida);
        ventaPiezas = findViewById(R.id.indicadores_ventaPiezas);
        fechaApertura = findViewById(R.id.indicadores_fechaApertura);
        diasInvConfigurados = findViewById(R.id.indicadores_diasInvConfigurados);
        //mRecyclerView = (RecyclerView) findViewById(R.id.rvMayorVenta);
        String[] myDataSet= {"ej1", "ej2", "ej3"};

        // Usar esta línea para mejorar el rendimiento
        // si sabemos que el contenido no va a afectar
        // el tamaño del RecyclerView
        //mRecyclerView.setHasFixedSize(true);

        // Nuestro RecyclerView usará un linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        //mRecyclerView.setLayoutManager(layoutManager);

        // Asociamos un adapter (ver más adelante cómo definirlo)
        mAdapter = new TopArticulosAdapter(myDataSet);
        //mRecyclerView.setAdapter(mAdapter);

        ProviderIndicadores.getInstance(this).getIndicadores(paisId, tiendaId, new ProviderIndicadores.ConsultaIndicadores() {
            @Override
            public void resolver(IndicadorResponse indicadorResponse) {
                DecimalFormat formateador = new DecimalFormat("###,###.##");
                Util.loadingProgress(progressDialog, 1);
                if (indicadorResponse.getCursorIndicadores() != null) {
                    CursorIndicadoresVo dato = indicadorResponse.getCursorIndicadores();
                    nombre.setText(dato.getNombre());
                    diasInvConfigurados.setText(String.valueOf(dato.getDiasInvConfigurados()));
                    existencia.setText(String.valueOf(formateador.format(dato.getExistencia())));
                    frecuencia.setText(String.valueOf(dato.getFrecuencia()));
                    montoVenta.setText(String.valueOf(formateador.format(dato.getMontoVenta()).toString()));
                    stockout.setText(String.valueOf(formateador.format(dato.getStockout())));
                    tamanioTienda.setText(String.valueOf(dato.getTamanioTienda()));
                    tiendaIdView.setText(String.valueOf(dato.getTiendaId()));
                    ventaAlcohol.setText(String.valueOf(dato.getVentaAlcohol()));
                    ventaPerdida.setText(String.valueOf(formateador.format(dato.getVentaPerdida())));
                    ventaPiezas.setText(String.valueOf(formateador.format(dato.getVentaPiezas()).toString()));
                    fechaApertura.setText(String.valueOf(dato.getFechaApertura()));

                } else {
                    Toast.makeText(CaracteristicasTiendas.this, "No se encontraron indicadores", Toast.LENGTH_SHORT).show();
                }

                if (indicadorResponse.getCursorTopArtMayorVta() != null && indicadorResponse.getCursorTopArtMayorVta().size() > 0) {
                    dibujaArticulosVenta(indicadorResponse.getCursorTopArtMayorVta(), 0);
                    textoArtMayor.setVisibility(View.VISIBLE);
                }
                else {
                    Toast.makeText(CaracteristicasTiendas.this, "No se encontraron artículos", Toast.LENGTH_SHORT).show();
                }
                if (indicadorResponse.getCursorTopArtMenorVta() != null && indicadorResponse.getCursorTopArtMenorVta().size()>0) {
                    dibujaArticulosVenta(indicadorResponse.getCursorTopArtMenorVta(), 1);
                    textoArtMenor.setVisibility(View.VISIBLE);
                }
                else {
                    Toast.makeText(CaracteristicasTiendas.this, "No se encontraron artículos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void reject(Exception e) {
            }
        });
    }

    public void dibujaArticulosVenta(List<CursorTopArticulosVo> cursorTopArtMayorVta, int tipo) {
        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/Roboto-Bold.ttf");
        Typeface tfr = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/Roboto-Light.ttf");
        DecimalFormat formateador = new DecimalFormat("###,###.##");
        ConversorDpsToPixels pixels = new ConversorDpsToPixels();
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        int widthScreen = metrics.widthPixels - pixels.Conversor(getApplicationContext(), 20); // ancho absoluto en pixels
        int col1 =(widthScreen / 10)*4;
        int col2 =(widthScreen / 10)*1;
        int col3 =(widthScreen / 10)*2;
        int col4 =(widthScreen / 10)*2;
        int col5 =(widthScreen / 10)*1;

        TableLayout ll = null;
        if(tipo == 0) {
            ll = (TableLayout) findViewById(R.id.tabla_articulos_mayor_view);

        } else {
            ll = (TableLayout) findViewById(R.id.tabla_articulos_menor_view);
        }
        ll.removeAllViews();
        TableRow row = new TableRow(this);

        row.setGravity(Gravity.CENTER_HORIZONTAL);
        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT);
        lp.height=90;
        row.setLayoutParams(lp);
        TextView tArticuloHeader = new TextView(this);
        tArticuloHeader.setWidth(col1);
        tArticuloHeader.setTextSize(11);
        tArticuloHeader.setText("Artículo");
        tArticuloHeader.setPadding(0, 0, 0, 0);
        tArticuloHeader.setTextColor(getResources().getColor(R.color.negro));
        tArticuloHeader.setTypeface(tf);
        row.addView(tArticuloHeader);
        TextView tCantidadHeader = new TextView(this);
        tCantidadHeader.setWidth(col2);
        tCantidadHeader.setTextSize(11);
        tCantidadHeader.setText("Vta");
        tCantidadHeader.setTypeface(tf);
        tCantidadHeader.setPadding(0, 0, 0, 0);
        tCantidadHeader.setTextColor(getResources().getColor(R.color.negro));
        tCantidadHeader.setGravity(Gravity.CENTER);
        row.addView(tCantidadHeader);
        TextView tMontoHeader = new TextView(this);
        tMontoHeader.setWidth(col3);
        tMontoHeader.setTextSize(11);
        tMontoHeader.setText("Vta $");
        tMontoHeader.setTypeface(tf);
        tMontoHeader.setPadding(0, 0, 0, 0);
        tMontoHeader.setTextColor(getResources().getColor(R.color.negro));
        tMontoHeader.setGravity(Gravity.CENTER);
        row.addView(tMontoHeader);
        TextView tExistenciaHeader = new TextView(this);
        tExistenciaHeader.setWidth(col4);
        tExistenciaHeader.setTextSize(11);
        tExistenciaHeader.setText("Exist.");
        tExistenciaHeader.setTypeface(tf);
        tExistenciaHeader.setPadding(0, 0, 0, 0);
        tExistenciaHeader.setTextColor(getResources().getColor(R.color.negro));
        tExistenciaHeader.setGravity(Gravity.CENTER);
        row.addView(tExistenciaHeader);
        TextView tDDRHeader = new TextView(this);
        tDDRHeader.setWidth(col5);
        tDDRHeader.setTextSize(11);
        tDDRHeader.setText("DDR");
        tDDRHeader.setTypeface(tf);
        tDDRHeader.setPadding(0, 0, 0, 0);
        tDDRHeader.setTextColor(getResources().getColor(R.color.negro));
        tDDRHeader.setGravity(Gravity.CENTER);
        row.addView(tDDRHeader);
        ll.addView(row,0);
        int i = 1;
        for (i = 1; i <= cursorTopArtMayorVta.size(); i++) {
            if((i)%2 == 0){
                row.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.naranja10));
            }else{
                row.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.naranja20));
            }
            row = new TableRow(this);
            row.setGravity(Gravity.CENTER_HORIZONTAL);
            lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
            lp.height = 180;
            row.setLayoutParams(lp);
            TextView tArticulo = new TextView(this);
            tArticulo.setWidth(col1);
            tArticulo.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
            tArticulo.setText(cursorTopArtMayorVta.get(i-1).getNombreArt());
//            tArticulo.setTextColor(getResources().getColor(R.color.turquesa));
            tArticulo.setPadding(0, 0, 0, 0);
            tArticulo.setTextSize(10);
            tArticulo.setTypeface(tfr);
            tArticulo.setLayoutParams(lp);
            row.addView(tArticulo);
            TextView tCantidad = new TextView(this);
            tCantidad.setWidth(col2);
            tCantidad.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
            tCantidad.setText(String.valueOf(formateador.format(cursorTopArtMayorVta.get(i-1).getCantidad())));
//            tCantidad.setTextColor(getResources().getColor(R.color.turquesa));
            tCantidad.setPadding(0, 3, 3, 3);
            tCantidad.setTextSize(10);
            tCantidad.setLayoutParams(lp);
            tCantidad.setGravity(Gravity.CENTER);
            tCantidad.setTypeface(tfr);
            row.addView(tCantidad);
            TextView tMonto = new TextView(this);
            tMonto.setWidth(col3);
            tMonto.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
            tMonto.setText(String.valueOf(formateador.format(cursorTopArtMayorVta.get(i-1).getMonto())));
//            tMonto.setTextColor(getResources().getColor(R.color.turquesa));
            tMonto.setPadding(0, 3, 3, 3);
            tMonto.setTextSize(10);
            tMonto.setLayoutParams(lp);
            tMonto.setGravity(Gravity.CENTER);
            tMonto.setTypeface(tfr);
            row.addView(tMonto);
            TextView tExistencias = new TextView(this);
            tExistencias.setWidth(col4);
            tExistencias.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
            tExistencias.setText(String.valueOf(formateador.format(cursorTopArtMayorVta.get(i-1).getExistencia())));
//            tExistencias.setTextColor(getResources().getColor(R.color.turquesa));
            tExistencias.setPadding(0, 3, 3, 3);
            tExistencias.setTextSize(10);
            tExistencias.setLayoutParams(lp);
            tExistencias.setTypeface(tfr);
            tExistencias.setGravity(Gravity.CENTER);

            row.addView(tExistencias);
            TextView tDDR = new TextView(this);
            tDDR.setWidth(col5);
            tDDR.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
            tDDR.setText(String.valueOf(cursorTopArtMayorVta.get(i-1).getDiasInvReales()));
//            tDDR.setTextColor(getResources().getColor(R.color.turquesa));
            tDDR.setPadding(0, 3, 3, 3);
            tDDR.setTextSize(10);
            tDDR.setLayoutParams(lp);
            tDDR.setTypeface(tfr);

            tDDR.setGravity(Gravity.CENTER);
            row.addView(tDDR);
            ll.addView(row,i);
        }
        if((i)%2 == 0){
            row.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.naranja10));
        }else{
            row.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.naranja20));
        }
    }


}
