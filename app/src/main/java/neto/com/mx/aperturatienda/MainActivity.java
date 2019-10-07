package neto.com.mx.aperturatienda;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import neto.com.mx.aperturatienda.adapter.AdapterTiendas;
import neto.com.mx.aperturatienda.adapter.TiendasHolder;
import neto.com.mx.aperturatienda.fragment.Aceptar;
import neto.com.mx.aperturatienda.model.caracteristicasGenerales.CaracteristicasGResponse;
import neto.com.mx.aperturatienda.model.tiendasLista.Tienda;
import neto.com.mx.aperturatienda.model.tiendasLista.TiendasResponse;
import neto.com.mx.aperturatienda.provider.ProviderCaracteristicasGenerales;
import neto.com.mx.aperturatienda.provider.ProviderTiendas;
import neto.com.mx.aperturatienda.ui.CaracteristicasTiendas;
import neto.com.mx.aperturatienda.utils.Util;

import static neto.com.mx.aperturatienda.R.id.drawer_layout;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, TiendasHolder.Listener {
    ProgressDialog progressDialog;

    AdapterTiendas adapterTiendas;
    RecyclerView rvTiendas;
    EditText etBuscar;

    List<Tienda> tiendas = null;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    LinearLayout ll_caracteristicas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etBuscar = findViewById(R.id.etBuscar);
        rvTiendas = (RecyclerView) findViewById(R.id.rvTiendas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        progressDialog = new ProgressDialog(MainActivity.this);

        ll_caracteristicas = findViewById(R.id.llcaracteristicas);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Actualizando tiendas", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                obtenerTiendas();
            }
        });

//        DrawerLayout drawer = (DrawerLayout) findViewById(drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();
//
//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);

        obtenerTiendas();

        etBuscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String texto = etBuscar.getText().toString();
                List<Tienda> listaTemporal = new ArrayList<Tienda>();
                rvTiendas.removeAllViews();
                if (texto.equals("")) {
                    obtenerTiendas();
                } else {
                    if (tiendas != null) {

                        listaTemporal.clear();
                        for (Tienda tienda : tiendas) {
                            if (tienda.getNombreTienda().toLowerCase().contains(texto.toLowerCase())) {
                                listaTemporal.add(tienda);
                            }
                            adapterTiendas = new AdapterTiendas(getApplicationContext(), ALPHABETICAL_COMPARATOR, MainActivity.this);
                            rvTiendas.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                            adapterTiendas.edit().replaceAll(listaTemporal).commit();
                            adapterTiendas.notifyItemRangeRemoved(0, adapterTiendas.getItemCount());
                            rvTiendas.setAdapter(adapterTiendas);

                        }
                    }
                }
            }
        });


    }

    @Override
    public void onBackPressed() {
//        DrawerLayout drawer = (DrawerLayout) findViewById(drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    } public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
//        // Handle navigation view item clicks here.
//        int id = item.getItemId();
//        if (id == R.id.nav_camera) {
//            // Handle the camera action
//        } else if (id == R.id.nav_gallery) {
//        } else if (id == R.id.nav_slideshow) {
//        } else if (id == R.id.nav_manage) {
//        } else if (id == R.id.nav_share) {
//        } else if (id == R.id.nav_send) {
//        }
//
//        DrawerLayout drawer = (DrawerLayout) findViewById(drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void obtenerTiendas() {
        Util.loadingProgress(progressDialog, 0);
        ProviderTiendas.getInstance(this).getTiendas(new ProviderTiendas.ConsultaTiendas() {
            @Override
            public void resolver(TiendasResponse tiendasResponse) {
                Util.loadingProgress(progressDialog, 1);
                if (tiendasResponse != null) {
                    if (tiendasResponse.getListaTiendas() != null) {
                        tiendas = tiendasResponse.getListaTiendas();
                        adapterTiendas = new AdapterTiendas(getApplicationContext(), ALPHABETICAL_COMPARATOR, MainActivity.this);
                        rvTiendas.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                        adapterTiendas.edit().replaceAll(tiendasResponse.getListaTiendas()).commit();
                        adapterTiendas.notifyItemRangeRemoved(0, adapterTiendas.getItemCount());
                        rvTiendas.setAdapter(adapterTiendas);
                    } else {
                        Aceptar a = new Aceptar();
                        a.setMensaje(tiendasResponse.getMensaje());
                        a.show(getSupportFragmentManager(), "child");
                    }
                } else {
                    Aceptar a = new Aceptar();
                    a.setMensaje("No hay datos de tiendas");
                    a.show(getSupportFragmentManager(), "child");
                }
            }

            @Override
            public void reject(Exception e) {

            }
        });
    }

    private static final Comparator<Tienda> ALPHABETICAL_COMPARATOR = new Comparator<Tienda>() {
        @Override
        public int compare(Tienda o1, Tienda o2) {
            return 0;
        }
    };

    @Override
    public void OnProcesoSelect(Tienda tienda) {
        preferences = getApplicationContext().getSharedPreferences("apertura", MODE_PRIVATE);
        editor = preferences.edit();
        editor.putInt("tiendaId", tienda.getTiendaId());
        editor.putInt("paisId", tienda.getPaisId());
        editor.apply();
        Intent intent = new Intent(MainActivity.this, CaracteristicasTiendas.class);
        startActivity(intent);
    }
}
