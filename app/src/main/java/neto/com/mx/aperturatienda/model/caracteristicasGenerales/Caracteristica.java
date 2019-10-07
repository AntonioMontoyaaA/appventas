package neto.com.mx.aperturatienda.model.caracteristicasGenerales;

public class Caracteristica {

    private int caracteristicaId;
    private String caracteristica;
    private int nivelCaracteristicaId;
    private String nivelCaracteristica;
    private int componenteHtmlId;
    private String componenteHtml;
    private float valor;
    private String unidadMedicion;
    private int numComponenteHtml;
    private String comentarios;
    private String valorReal;
    private int ordenamientoCaract;
    private int ordenamientoNivelCaract;
    private int valorGuardado;

    public int getOrdenamientoCaract() {
        return ordenamientoCaract;
    }

    public void setOrdenamientoCaract(int ordenamientoCaract) {
        this.ordenamientoCaract = ordenamientoCaract;
    }

    public int getOrdenamientoNivelCaract() {
        return ordenamientoNivelCaract;
    }

    public void setOrdenamientoNivelCaract(int ordenamientoNivelCaract) {
        this.ordenamientoNivelCaract = ordenamientoNivelCaract;
    }

    public int getValorGuardado() {
        return valorGuardado;
    }

    public void setValorGuardado(int valorGuardado) {
        this.valorGuardado = valorGuardado;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public String getUnidadMedicion() {
        return unidadMedicion;
    }

    public void setUnidadMedicion(String unidadMedicion) {
        this.unidadMedicion = unidadMedicion;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public String getValorReal() {
        return valorReal;
    }

    public void setValorReal(String valorReal) {
        this.valorReal = valorReal;
    }

    public int getNumComponenteHtml() {
        return numComponenteHtml;
    }

    public void setNumComponenteHtml(int numComponenteHtml) {
        this.numComponenteHtml = numComponenteHtml;
    }

    public String getComponenteHtml() {
        return componenteHtml;
    }

    public void setComponenteHtml(String componenteHtml) {
        this.componenteHtml = componenteHtml;
    }

    public int getComponenteHtmlId() {
        return componenteHtmlId;
    }

    public void setComponenteHtmlId(int componenteHtmlId) {
        this.componenteHtmlId = componenteHtmlId;
    }

    public String getCaracteristica() {
        return caracteristica;
    }

    public void setCaracteristica(String caracteristica) {
        this.caracteristica = caracteristica;
    }

    public int getCaracteristicaId() {
        return caracteristicaId;
    }

    public void setCaracteristicaId(int caracteristicaId) {
        this.caracteristicaId = caracteristicaId;
    }
    public String getNivelCaracteristica() {
        return nivelCaracteristica;
    }

    public void setNivelCaracteristica(String nivelCaracteristica) {
        this.nivelCaracteristica = nivelCaracteristica;
    }

    public int getNivelCaracteristicaId() {
        return nivelCaracteristicaId;
    }

    public void setNivelCaracteristicaId(int nivelCaracteristicaId) {
        this.nivelCaracteristicaId = nivelCaracteristicaId;
    }
}
