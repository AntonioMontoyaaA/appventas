package neto.com.mx.reporte.model.dashboard;

import java.io.Serializable;
import java.util.ArrayList;

public class VentasResponse implements Serializable {

    private int codigo;
    private String mensaje;
    private String nombreUbicacion;
    private String tickPromGeneral;
    private String tiendasConVentaGeneral;
    private String ubicacionId;
    private String vObjetivoGeneral;
    private String vObjetivoTotal;
    private String vPerdidaGeneral;
    private String vRealGeneral;
    private ArrayList<Ventas> listaVentas;

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

    public String getNombreUbicacion() {
        return nombreUbicacion;
    }

    public void setNombreUbicacion(String nombreUbicacion) {
        this.nombreUbicacion = nombreUbicacion;
    }

    public String getTickPromGeneral() {
        return tickPromGeneral;
    }

    public void setTickPromGeneral(String tickPromGeneral) {
        this.tickPromGeneral = tickPromGeneral;
    }

    public String getTiendasConVentaGeneral() {
        return tiendasConVentaGeneral;
    }

    public void setTiendasConVentaGeneral(String tiendasConVentaGeneral) {
        this.tiendasConVentaGeneral = tiendasConVentaGeneral;
    }

    public String getUbicacionId() {
        return ubicacionId;
    }

    public void setUbicacionId(String ubicacionId) {
        this.ubicacionId = ubicacionId;
    }

    public String getvObjetivoGeneral() {
        return vObjetivoGeneral;
    }

    public void setvObjetivoGeneral(String vObjetivoGeneral) {
        this.vObjetivoGeneral = vObjetivoGeneral;
    }
    public String getvObjetivoTotal() {
        return vObjetivoTotal;
    }

    public void setvObjetivoTotal(String vObjetivoTotal) {
        this.vObjetivoTotal = vObjetivoTotal;
    }


    public String getvPerdidaGeneral() {
        return vPerdidaGeneral;
    }

    public void setvPerdidaGeneral(String vPerdidaGeneral) {
        this.vPerdidaGeneral = vPerdidaGeneral;
    }

    public String getvRealGeneral() {
        return vRealGeneral;
    }

    public void setvRealGeneral(String vRealGeneral) {
        this.vRealGeneral = vRealGeneral;
    }

    public ArrayList<Ventas> getListaVentas() {
        return listaVentas;
    }

    public void setListaVentas(ArrayList<Ventas> listaVentas) {
        this.listaVentas = listaVentas;
    }
}
