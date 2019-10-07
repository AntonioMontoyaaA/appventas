package neto.com.mx.aperturatienda.constantes;

public class Constantes {
    public static  Integer PAISID = 1;
    public static  Integer SISTEMAID = 2;
    public static  Integer MODULO = 4;
    public static  Integer SUBMODULO = 1;

//    public static   String URL = "http://10.81.12.45:7777/WSSIAN/services/WSTiendasApertura"; // DESA
//    public static   String URL ="http://10.81.12.46:7777/appWSSIANMovilesPAR/services/WSTiendasApertura"; //QA
    public static   String URL ="https://www.servicios.tiendasneto.com/WSSIANMoviles/services/WSTiendasApertura"; // PROD

    public static String NAMESPACE = "http://servicio.caracteristicas.aperturas.neto";
    public static String METHOD_NAME_CONSULTA_TIENDAS = "consultaTiendas";
    public static String METHOD_NAME_CONSULTA_CARACTERISTICAS = "consultaCaracteristicas";
    public static String METHOD_NAME_CONSULTA_CARACTERISTICAS_TIENDA = "consultaCaracteristicasPorTienda";
    public static String METHOD_NAME_GUARDA_CARACTERISTICA = "guardaCaracteristica";
    public static String METHOD_NAME_GUARDA_CARACTERISTICAS = "guardaCaracteristicas";
    public static String METHOD_NAME_OBTIENE_INDICADORES = "obtieneIndicadores";
    public static String METHOD_NAME_AUTENTICAR = "autenticar";
    public static String METHOD_NAME_OBTIENE_PERMISOS_USUARIO = "obtienePermisosUsuario";

    public static String SOAP_ACTION = "http://servicio.caracteristicas.aperturas.neto/guardaCaracteristicas";
    public static String NAMESPACE_LOGIN = "http://servicio.rutas.movil.abasto.neto";
    public static String METHOD_NAME = "validaUsuario";
//    public static String URL_PRODUCCION_LOGIN = "https://www.servicios.tiendasneto.com/WSSIANMoviles/services/WSRutasMovil/"; //PROD
}
