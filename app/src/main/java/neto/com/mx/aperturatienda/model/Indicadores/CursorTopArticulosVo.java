package neto.com.mx.aperturatienda.model.Indicadores;

public class CursorTopArticulosVo {
    private Long articuloId;
    private Float cantidad;
    private Float diasInvReales;
    private Float existencia;
    private Float monto;
    private String nombreArt;

    public Long getArticuloId() {
        return articuloId;
    }

    public void setArticuloId(Long articuloId) {
        this.articuloId = articuloId;
    }

    public Float getCantidad() {
        return cantidad;
    }

    public void setCantidad(Float cantidad) {
        this.cantidad = cantidad;
    }

    public Float getDiasInvReales() {
        return diasInvReales;
    }

    public void setDiasInvReales(Float diasInvReales) {
        this.diasInvReales = diasInvReales;
    }

    public Float getExistencia() {
        return existencia;
    }

    public void setExistencia(Float existencia) {
        this.existencia = existencia;
    }

    public Float getMonto() {
        return monto;
    }

    public void setMonto(Float monto) {
        this.monto = monto;
    }

    public String getNombreArt() {
        return nombreArt;
    }

    public void setNombreArt(String nombreArt) {
        this.nombreArt = nombreArt;
    }
}
