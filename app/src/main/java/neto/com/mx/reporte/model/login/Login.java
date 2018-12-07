package neto.com.mx.reporte.model.login;

public class Login {

    private int codigo;
    private boolean esUsuarioValido;
    private String mensaje;
    private String nombreUsuario;
    private String password;

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public boolean getEsUsuarioValido() {
        return esUsuarioValido;
    }

    public void setEsUsuarioValido(boolean esUsuarioValido) {
        this.esUsuarioValido = esUsuarioValido;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
