package neto.com.mx.aperturatienda.model.Login;

public class ResultadoVo {
   private int moduloId;
   private int opcionId;
   private String opcionSistemaId;
   private int perfilId;
   private int permisoId;
   private int sistemaId;
   private int submoduloId;

    public int getPermisoId() {
        return permisoId;
    }

    public void setPermisoId(int permisoId) {
        this.permisoId = permisoId;
    }

    public int getModuloId() {
        return moduloId;
    }

    public void setModuloId(int moduloId) {
        this.moduloId = moduloId;
    }

    public int getOpcionId() {
        return opcionId;
    }

    public void setOpcionId(int opcionId) {
        this.opcionId = opcionId;
    }

    public String getOpcionSistemaId() {
        return opcionSistemaId;
    }

    public void setOpcionSistemaId(String opcionSistemaId) {
        this.opcionSistemaId = opcionSistemaId;
    }

    public int getPerfilId() {
        return perfilId;
    }

    public void setPerfilId(int perfilId) {
        this.perfilId = perfilId;
    }

    public int getSistemaId() {
        return sistemaId;
    }

    public void setSistemaId(int sistemaId) {
        this.sistemaId = sistemaId;
    }

    public int getSubmoduloId() {
        return submoduloId;
    }

    public void setSubmoduloId(int submoduloId) {
        this.submoduloId = submoduloId;
    }
}
