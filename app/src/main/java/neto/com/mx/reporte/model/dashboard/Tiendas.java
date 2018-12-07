package neto.com.mx.reporte.model.dashboard;

import java.util.ArrayList;

import neto.com.mx.reporte.utils.SortedListAdapter;

public class Tiendas {

    private int codigo;
    private String mensaje;
    ArrayList<Tienda> tiendas;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public ArrayList<Tienda> getTiendas() {
        return tiendas;
    }

    public void setTiendas(ArrayList<Tienda> tiendas) {
        this.tiendas = tiendas;
    }



}
