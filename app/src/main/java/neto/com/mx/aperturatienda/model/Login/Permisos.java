package neto.com.mx.aperturatienda.model.Login;

import java.util.List;

public class Permisos {
    private int statusCode;
    private  String statusMsj;
    private List<ResultadoVo> listaResultado;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusMsj() {
        return statusMsj;
    }

    public void setStatusMsj(String statusMsj) {
        this.statusMsj = statusMsj;
    }

    public List<ResultadoVo> getListaResultado() {
        return listaResultado;
    }

    public void setListaResultado(List<ResultadoVo> listaResultado) {
        this.listaResultado = listaResultado;
    }
}
