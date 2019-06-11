package neto.com.mx.reporte.model.dashboard;

public class Consulta {

    private String numeroEmpleado;
    private String region;
    private String zona;
    private String tienda;
    private String fechaInicial;
    private String fechaFinal;
    private int tipoTienda;
    private int tipoVenta;
    private int paTipoPpto;

    public Consulta(String numeroEmpleado, String region, String zona, String tienda, String fechaInicial, String fechaFinal, int tipoTienda, int tipoVenta, int paTipoPpto ) {
        this.numeroEmpleado = numeroEmpleado;
        this.region = region;
        this.zona = zona;
        this.tienda = tienda;
        this.fechaInicial = fechaInicial;
        this.fechaFinal = fechaFinal;
        this.tipoTienda = tipoTienda;
        this.tipoVenta = tipoVenta;
        this.paTipoPpto = paTipoPpto;
    }

    public String getNumeroEmpleado() {
        return numeroEmpleado;
    }

    public void setNumeroEmpleado(String numeroEmpleado) {
        this.numeroEmpleado = numeroEmpleado;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public String getTienda() {
        return tienda;
    }

    public void setTienda(String tienda) {
        this.tienda = tienda;
    }

    public String getFechaInicial() {
        return fechaInicial;
    }

    public void setFechaInicial(String fechaInicial) {
        this.fechaInicial = fechaInicial;
    }

    public String getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(String fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public int getTipoTienda() {
        return tipoTienda;
    }

    public void setTipoTienda(int tipoTienda) {
        this.tipoTienda = tipoTienda;
    }

    public int getTipoVenta() {
        return tipoVenta;
    }

    public void setTipoVenta(int tipoVenta) {
        this.tipoVenta = tipoVenta;
    }

    public int getPaTipoPpto() {
        return paTipoPpto;
    }

    public void setPaTipoPpto(int paTipoPpto) {
        this.paTipoPpto = paTipoPpto;
    }
}
