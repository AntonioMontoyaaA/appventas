package neto.com.mx.aperturatienda.model.guardacaracteristica;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;


import java.io.Serializable;
import java.util.Hashtable;

public class DatosEvaluacion implements KvmSerializable {
    //los mismos que en el model Caracteristica
    private int caracteristicaId;
    private int nivelCaracteristicaId;
    private String valor;
    private String valorReal;
    private String comentarios;

    public DatosEvaluacion(int caracteristicaId, int nivelCaracteristicaId, String valor, String valorReal, String comentarios) {
        this.caracteristicaId = caracteristicaId;
        this.nivelCaracteristicaId = nivelCaracteristicaId;
        this.valor = valor;
        this.valorReal = valorReal;
        this.comentarios = comentarios;
    }

    public int getCaracteristicaId() {
        return caracteristicaId;
    }

    public void setCaracteristicaId(int caracteristicaId) {
        this.caracteristicaId = caracteristicaId;
    }

    public int getNivelCaracteristicaId() {
        return nivelCaracteristicaId;
    }

    public void setNivelCaracteristicaId(int nivelCaracteristicaId) {
        this.nivelCaracteristicaId = nivelCaracteristicaId;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getValorReal() {
        return valorReal;
    }

    public void setValorReal(String valorReal) {
        this.valorReal = valorReal;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    @Override
    public Object getProperty(int index) {
        switch (index) {
            case 0:
                return caracteristicaId;
            case 1:
                return nivelCaracteristicaId;
            case 2:
                return valor;
            case 3:
                return valorReal;
            case 4:
                return comentarios;
        }
        return null;
    }

    @Override
    public int getPropertyCount() {
        return 5;
    }

    @Override
    public void setProperty(int index, Object value) {
        switch (index) {
            case 0:
                caracteristicaId = Integer.parseInt(value.toString());
                break;
            case 1:
                nivelCaracteristicaId = Integer.parseInt(value.toString());
                break;
            case 2:
                valor = value.toString();
                break;
            case 3:
                valorReal = value.toString();
                break;
            case 4:
                comentarios = value.toString();
                break;

            default:
                break;
        }
    }

    @Override
    public void getPropertyInfo(int index, Hashtable properties, PropertyInfo info) {
        switch (index) {
            case 0:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "caracteristicaId";
                break;
            case 1:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "nivelCaracteristicaId";
                break;
            case 2:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "valor";
                break;
            case 3:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "valorReal";
                break;
            case 4:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "comentarios";
                break;
            default:
                break;
        }
    }

}