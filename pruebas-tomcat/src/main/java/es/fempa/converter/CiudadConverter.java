package es.fempa.converter;

import es.fempa.modelo.Ciudad;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;

/**
 * Converter de Jakarta Faces para poder usar el POJO Ciudad
 * dentro de un p:selectOneMenu.
 *
 * La ciudad se serializa por su nombre y se reconstruye al recibirla.
 */
@FacesConverter(forClass = Ciudad.class)
public class CiudadConverter implements Converter<Ciudad> {

    @Override
    public Ciudad getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        return new Ciudad(value);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Ciudad value) {
        if (value == null) {
            return "";
        }
        return value.getNombre();
    }
}
