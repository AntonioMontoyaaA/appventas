package neto.com.mx.reporte.model.dashboard;

import java.text.DecimalFormat;

import neto.com.mx.reporte.utils.SortedListAdapter;

public class Ventas implements SortedListAdapter.ViewModel {

    private int tiendaId;
    private String nombreTienda;
    private int numeroTiendas;
    private int ticketPromedio;
    private double ventaReal;
    private double ventaObjetivo;
    private double ventaPerdida;
    private double porcentaje;

    public double getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(double porcentaje) {
        this.porcentaje = porcentaje;
    }

    public int getNumeroTiendas() {
        return numeroTiendas;
    }

    public void setNumeroTiendas(int numeroTiendas) {
        this.numeroTiendas = numeroTiendas;
    }

    public int getTicketPromedio() {
        return ticketPromedio;
    }

    public void setTicketPromedio(int ticketPromedio) {
        this.ticketPromedio = ticketPromedio;
    }

    public double getVentaPerdida() {
        return ventaPerdida;
    }

    public void setVentaPerdida(double ventaPerdida) {
        this.ventaPerdida = ventaPerdida;
    }

    public int getTiendaId() {
        return tiendaId;
    }

    public void setTiendaId(int tiendaId) {
        this.tiendaId = tiendaId;
    }

    public String getNombreTienda() {
        return nombreTienda;
    }

    public void setNombreTienda(String nombreTienda) {
        this.nombreTienda = nombreTienda;
    }

    public double getVentaReal() {
        return ventaReal;
    }

    public void setVentaReal(double ventaReal) {
        this.ventaReal = ventaReal;
    }

    public double getVentaObjetivo() {
        return ventaObjetivo;
    }

    public void setVentaObjetivo(double ventaObjetivo) {
        this.ventaObjetivo = ventaObjetivo;
    }


    public String converter(double conver){
        DecimalFormat formatter = new DecimalFormat("#,###");
        return "$"+formatter.format(conver);
    }

    public String conversion(double porcentaje){
        DecimalFormat formatter = new DecimalFormat("#,###");
        if(Double.isNaN(porcentaje)){
            return "0";
        }else{
            return "%"+formatter.format(porcentaje);
        }
    }

    public int porcentaje(double porcentaje){
        if(Double.isNaN(porcentaje)){
            return 0;
        }else{
            return (int) porcentaje;
        }
    }

}
