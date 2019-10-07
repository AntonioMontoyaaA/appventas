package neto.com.mx.aperturatienda.model.caracteristicasGenerales;

import java.util.ArrayList;

public class CaracteristicasGResponse {
    private int codigo;
    private String mensake;
    private ArrayList<Caracteristica> listaCaracteristicas;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getMensake() {
        return mensake;
    }

    public void setMensake(String mensake) {
        this.mensake = mensake;
    }

    public ArrayList<Caracteristica> getListaCaracteristicas() {
        return listaCaracteristicas;
    }

    public void setListaCaracteristicas(ArrayList<Caracteristica> listaCaracteristicas) {
        this.listaCaracteristicas = listaCaracteristicas;
    }
}

