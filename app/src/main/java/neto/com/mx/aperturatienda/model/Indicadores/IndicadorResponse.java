package neto.com.mx.aperturatienda.model.Indicadores;

import java.util.List;

public class IndicadorResponse {

    private int codigo;
    private CursorIndicadoresVo cursorIndicadores;
    private List<CursorTopArticulosVo> cursorTopArtMayorVta;
    private List<CursorTopArticulosVo> cursorTopArtMenorVta;
    private String mensaje;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public CursorIndicadoresVo getCursorIndicadores() {
        return cursorIndicadores;
    }
    public void setCursorIndicadores(CursorIndicadoresVo cursorIndicadores) {
        this.cursorIndicadores = cursorIndicadores;
    }
    public List<CursorTopArticulosVo> getCursorTopArtMayorVta() {
        return cursorTopArtMayorVta;
    }

    public void setCursorTopArtMayorVta(List<CursorTopArticulosVo> cursorTopArtMayorVta) {
        this.cursorTopArtMayorVta = cursorTopArtMayorVta;
    }

    public List<CursorTopArticulosVo> getCursorTopArtMenorVta() {
        return cursorTopArtMenorVta;
    }

    public void setCursorTopArtMenorVta(List<CursorTopArticulosVo> cursorTopArtMenorVta) {
        this.cursorTopArtMenorVta = cursorTopArtMenorVta;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
