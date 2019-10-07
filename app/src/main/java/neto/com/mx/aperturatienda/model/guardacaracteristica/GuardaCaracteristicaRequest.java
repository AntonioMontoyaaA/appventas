package neto.com.mx.aperturatienda.model.guardacaracteristica;


import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;


public class GuardaCaracteristicaRequest implements KvmSerializable, Serializable {
    private int paisId;
    private int usuarioId;
    private int tiendaId;
    private int medioRegistra;
   private List<DatosEvaluacion> datosEvaluacion;

    public int getMedioRegistra() {
        return medioRegistra;
    }

    public void setMedioRegistra(int medioRegistra) {
        this.medioRegistra = medioRegistra;
    }

    public GuardaCaracteristicaRequest() {
    }

    public GuardaCaracteristicaRequest(int paisId, int usuarioId, int tiendaId, int medioRegistra , List<DatosEvaluacion> datosEvaluacion ) {
        this.paisId = paisId;
        this.usuarioId = usuarioId;
        this.tiendaId = tiendaId;
        this.medioRegistra = medioRegistra;
        this.datosEvaluacion = datosEvaluacion;
    }

    public int getPaisId() {
        return paisId;
    }

    public void setPaisId(int paisId) {
        this.paisId = paisId;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public int getTiendaId() {
        return tiendaId;
    }

    public void setTiendaId(int tiendaId) {
        this.tiendaId = tiendaId;
    }

   public List<DatosEvaluacion> getDatosEvaluacion() {
        return datosEvaluacion;
    }

    public void setDatosEvaluacion(List<DatosEvaluacion> datosEvaluacion) {
        this.datosEvaluacion = datosEvaluacion;
    }


    @Override
    public Object getProperty(int index) {
        switch (index) {
            case 0:
                return paisId;
            case 1:
                return usuarioId;
            case 2:
                return tiendaId;
            case 3:
                return medioRegistra;
            case 4:
                return datosEvaluacion;
        }
        return null;
    }

    @Override
    public int getPropertyCount() {
        return 4;
    }

    @Override
    public void setProperty(int index, Object value) {
        switch (index) {
            case 0:
                paisId = Integer.parseInt(value.toString());
                break;
            case 1:
                usuarioId = Integer.parseInt(value.toString());
                break;
            case 2:
                tiendaId = Integer.parseInt(value.toString());
                break;
            case 3:
                medioRegistra = Integer.parseInt(value.toString());
                break;
            case 4:
                datosEvaluacion = (List<DatosEvaluacion>) value;
                break;
            default:
                break;
        }
    }

    @Override
    public void getPropertyInfo(int index, Hashtable properties, org.ksoap2.serialization.PropertyInfo info) {
        switch (index) {
            case 0:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "paisId";
                break;
            case 1:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "usuarioId";
                break;
            case 2:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "tiendaId";
                break;
            case 3:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "medioRegistra";
                break;
            case 4:
                //info.type = PropertyInfo.LISTCLASS;
                info.type = new ArrayList().getClass();
                info.name = "datosEvaluacion";
                break;
            default:
                break;
        }
    }


}

