package es.fempa.validadores;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.FacesValidator;
import jakarta.faces.validator.Validator;
import jakarta.faces.validator.ValidatorException;

/**
 * Exige que una casilla de aceptación esté marcada.
 *
 * El atributo required="true" de un checkbox no basta: el navegador envía un
 * valor "false" cuando está desmarcado, así que no se considera vacío. Este
 * validador sí comprueba el valor real.
 *
 * El texto del error se toma del atributo "mensajeAceptacion" del componente.
 */
@FacesValidator("aceptacionValidator")
public class AceptacionValidator implements Validator<Boolean> {

    @Override
    public void validate(FacesContext context, UIComponent component, Boolean value)
            throws ValidatorException {

        if (Boolean.TRUE.equals(value)) {
            return;
        }

        Object mensaje = component.getAttributes().get("mensajeAceptacion");
        String texto = (mensaje != null) ? mensaje.toString() : "Debes marcar esta casilla para continuar.";

        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, texto, null));
    }
}
