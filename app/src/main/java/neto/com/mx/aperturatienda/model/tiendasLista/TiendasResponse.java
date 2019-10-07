package neto.com.mx.aperturatienda.model.tiendasLista;

import java.io.Serializable;
import java.util.ArrayList;

public class TiendasResponse implements Serializable {

    private int codigo;
    private String mensaje;
    private ArrayList<Tienda> listaTiendas;

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

    public ArrayList<Tienda> getListaTiendas() {
        return listaTiendas;
    }

    public void setListaTiendas(ArrayList<Tienda> listaTiendas) {
        this.listaTiendas = listaTiendas;
    }
}
