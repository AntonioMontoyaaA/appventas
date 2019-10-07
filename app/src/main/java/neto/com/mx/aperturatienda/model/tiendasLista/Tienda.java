package neto.com.mx.aperturatienda.model.tiendasLista;

import java.text.DecimalFormat;

import neto.com.mx.aperturatienda.utils.SortedListAdapter;

public class Tienda implements SortedListAdapter.ViewModel {
    private String direccion;
    private String fechaApertura;
    private String nombreTienda;
    private float porcentajeAvance;
    private String region;
    private int regionId;
    private int tiendaId;
    private String zona;
    private int zonaId;
    private int paisId;

    private int position = 2;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPaisId() {
        return paisId;
    }

    public void setPaisId(int paisId) {
        this.paisId = paisId;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getFechaApertura() {
        return fechaApertura;
    }

    public void setFechaApertura(String fechaApertura) {
        if( fechaApertura.equals("01/01/1901")){
            this.fechaApertura = "-";
        }else{
            this.fechaApertura = fechaApertura;
        }
    }

    public String getNombreTienda() {
        return nombreTienda;
    }

    public void setNombreTienda(String nombreTienda) {
        this.nombreTienda = nombreTienda;
    }

    public float getPorcentajeAvance() {
        return porcentajeAvance;
    }

    public void setPorcentajeAvance(float porcentajeAvance) {
        this.porcentajeAvance = porcentajeAvance;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public int getRegionId() {
        return regionId;
    }

    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }

    public int getTiendaId() {
        return tiendaId;
    }

    public void setTiendaId(int tiendaId) {
        this.tiendaId = tiendaId;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public int getZonaId() {
        return zonaId;
    }

    public void setZonaId(int zonaId) {
        this.zonaId = zonaId;
    }

    public String converter(double conver){
        DecimalFormat formatter = new DecimalFormat("#,###");
        return "$"+formatter.format(conver);
    }

    public String converter(float conver){
        DecimalFormat formatter = new DecimalFormat("#,###");
        return formatter.format(conver) + " % ";
    }

    public String converter(String conver){
        conver = conver.toUpperCase();
        return conver;
    }

   public String backGroud(int position){
        return position%2 == 0 ? "#FAEFE9":"#F7DED5";
    }
}
