package neto.com.mx.aperturatienda.model.tiendaEspecifica;

import java.util.ArrayList;

import neto.com.mx.aperturatienda.model.caracteristicasGenerales.Caracteristica;

public class CaracteristicasTResponse {
    private int codigo;
    private String mensaje;
    private String nombreTienda;
    private ArrayList<Caracteristica> listaCaracteristicas;

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

    public String getNombreTienda() {
        return nombreTienda;
    }

    public void setNombreTienda(String nombreTienda) {
        this.nombreTienda = nombreTienda;
    }

    public ArrayList<Caracteristica> getListaCaracteristicas() {
        return listaCaracteristicas;
    }

    public void setListaCaracteristicas(ArrayList<Caracteristica> listaCaracteristicas) {
        this.listaCaracteristicas = listaCaracteristicas;
    }
}
