package neto.com.mx.aperturatienda.model.Indicadores;

public class CursorIndicadoresVo {
    private int diasInvConfigurados;
    private int existencia;
    private String frecuencia;
    private Float montoVenta;
    private String nombre;
    private int stockout;
    private String tamanioTienda;
    private int tiendaId;
    private String ventaAlcohol;
    private int ventaPerdida;
    private Float ventaPiezas;
    private String fechaApertura;

    public String getFechaApertura() {
        return fechaApertura;
    }

    public void setFechaApertura(String fechaApertura) {
        this.fechaApertura = fechaApertura;
    }

    public int getDiasInvConfigurados() {
        return diasInvConfigurados;
    }

    public void setDiasInvConfigurados(int diasInvConfigurados) {
        this.diasInvConfigurados = diasInvConfigurados;
    }

    public int getExistencia() {
        return existencia;
    }

    public void setExistencia(int existencia) {
        this.existencia = existencia;
    }

    public String getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(String frecuencia) {
        this.frecuencia = frecuencia;
    }

    public Float getMontoVenta() {
        return montoVenta;
    }

    public void setMontoVenta(Float montoVenta) {
        this.montoVenta = montoVenta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getStockout() {
        return stockout;
    }

    public void setStockout(int stockout) {
        this.stockout = stockout;
    }

    public String getTamanioTienda() {
        return tamanioTienda;
    }

    public void setTamanioTienda(String tamanioTienda) {
        this.tamanioTienda = tamanioTienda;
    }

    public int getTiendaId() {
        return tiendaId;
    }

    public void setTiendaId(int tiendaId) {
        this.tiendaId = tiendaId;
    }

    public String getVentaAlcohol() {
        return ventaAlcohol;
    }

    public void setVentaAlcohol(String ventaAlcohol) {
        this.ventaAlcohol = ventaAlcohol;
    }

    public int getVentaPerdida() {
        return ventaPerdida;
    }

    public void setVentaPerdida(int ventaPerdida) {
        this.ventaPerdida = ventaPerdida;
    }

    public Float getVentaPiezas() {
        return ventaPiezas;
    }

    public void setVentaPiezas(Float ventaPiezas) {
        this.ventaPiezas = ventaPiezas;
    }
}
