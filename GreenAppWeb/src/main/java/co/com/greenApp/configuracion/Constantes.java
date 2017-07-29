package co.com.greenApp.configuracion;

/**
 * Clase que contiene las constantes de la aplicación
 *
 * @author wsalazar
 */
public class Constantes {
    
    public String CONFIGURACION_NUMERO_GRUPO = "CONFIGURACION_NUMERO_GRUPO";
    public String CAPACIDAD_T_PARQUE_JORNADA = "CAPACIDAD_T_PARQUE_JORNADA";
    public String NUMERO_DIAS_PARA_CREAR_RESERVA = "NUMERO_DIAS_PARA_CREAR_RESERVA";
    public static final String SUCCESS = "0";
    public static final String PASSWORD_DIFERENT = "1";
    public static final String NOT_EXIXTING_USER = "2";
    public static final String PATH_FILE_XLS = "C:/archivo/";

    public static Constantes getInstance() {
        return ConstantesHolder.INSTANCE;

    }

    private static class ConstantesHolder {

        private static final Constantes INSTANCE = new Constantes();
    }

}
