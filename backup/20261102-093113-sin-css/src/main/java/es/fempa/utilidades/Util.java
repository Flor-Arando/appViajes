package es.fempa.utilidades;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;

import java.util.Calendar;
import java.util.Date;

public class Util{

    public static void mensajeInfo(String cabecera, String mensaje) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, cabecera, mensaje));
    }

    public static void mensajeError(String cabecera, String mensaje) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, cabecera, mensaje));
    }
    public static void mensajeWarn(String cabecera, String mensaje) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, cabecera, mensaje));
    }

    public static Date crearFecha(int dia, int mes, int anio) {
        Calendar cal = Calendar.getInstance();
        cal.setLenient(false);
        cal.set(anio, mes, dia);
        return cal.getTime();
    }
}




