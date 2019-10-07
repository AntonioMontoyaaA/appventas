package neto.com.mx.aperturatienda.model.caracteristicasGenerales;

import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;

public class ComponentesPantalla {
    private Spinner spiner;
    private List spinerid;
    private EditText editText;
    private Switch elemswitch;
    private CheckBox checkbox;
    private TextView date;
    private int caracteristicaId;
    private int nivelId;
    private int elementoId;

    public int getElementoId() {
        return elementoId;
    }

    public void setElementoId(int elementoId) {
        this.elementoId = elementoId;
    }

    public Spinner getSpiner() {
        return spiner;
    }

    public void setSpiner(Spinner spiner) {
        this.spiner = spiner;
    }

    public List getSpinerid() {
        return spinerid;
    }

    public void setSpinerid(List spinerid) {
        this.spinerid = spinerid;
    }

    public EditText getEditText() {
        return editText;
    }

    public void setEditText(EditText editText) {
        this.editText = editText;
    }

    public Switch getElemswitch() {
        return elemswitch;
    }

    public void setElemswitch(Switch elemswitch) {
        this.elemswitch = elemswitch;
    }

    public CheckBox getCheckbox() {
        return checkbox;
    }

    public void setCheckbox(CheckBox checkbox) {
        this.checkbox = checkbox;
    }

    public TextView getDate() {
        return date;
    }

    public void setDate(TextView date) {
        this.date = date;
    }

    public int getCaracteristicaId() {
        return caracteristicaId;
    }

    public void setCaracteristicaId(int caracteristicaId) {
        this.caracteristicaId = caracteristicaId;
    }

    public int getNivelId() {
        return nivelId;
    }

    public void setNivelId(int nivelId) {
        this.nivelId = nivelId;
    }
}
