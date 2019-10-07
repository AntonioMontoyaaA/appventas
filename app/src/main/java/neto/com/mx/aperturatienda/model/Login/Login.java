package neto.com.mx.aperturatienda.model.Login;

import java.util.List;

public class Login {

    private int codigo;
    private String statusMsj;
    private List<UsuarioRowVo> usuarioRowVo;

    public List<UsuarioRowVo> getUsuarioRowVo() {
        return usuarioRowVo;
    }

    public void setUsuarioRowVo(List<UsuarioRowVo> usuarioRowVo) {
        this.usuarioRowVo = usuarioRowVo;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getStatusMsj() {
        return statusMsj;
    }

    public void setStatusMsj(String statusMsj) {
        this.statusMsj = statusMsj;
    }



//    private int codigo;
//    private boolean esUsuarioValido;
//    private String mensaje;
//    private String nombreUsuario;
//    private String password;
//
//    public Integer getCodigo() {
//        return codigo;
//    }
//
//    public void setCodigo(Integer codigo) {
//        this.codigo = codigo;
//    }
//
//    public boolean getEsUsuarioValido() {
//        return esUsuarioValido;
//    }
//
//    public void setEsUsuarioValido(boolean esUsuarioValido) {
//        this.esUsuarioValido = esUsuarioValido;
//    }
//
//    public String getMensaje() {
//        return mensaje;
//    }
//
//    public void setMensaje(String mensaje) {
//        this.mensaje = mensaje;
//    }
//
//    public String getNombreUsuario() {
//        return nombreUsuario;
//    }
//
//    public void setNombreUsuario(String nombreUsuario) {
//        this.nombreUsuario = nombreUsuario;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
}
