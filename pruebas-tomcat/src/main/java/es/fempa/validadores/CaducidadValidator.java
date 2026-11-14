package es.fempa.validadores;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.FacesValidator;
import jakarta.faces.validator.Validator;
import jakarta.faces.validator.ValidatorException;

import java.time.LocalDate;
import java.time.YearMonth;

/**
 * Valida la fecha de caducidad de la tarjeta (formato MM/AA) en el servidor:
 * formato correcto, mes entre 1 y 12 y tarjeta no vencida.
 *
 * Sustituye a la validación que hacía pago.js y usa la fecha del servidor,
 * que es la referencia fiable.
 */
@FacesValidator("caducidadValidator")
public class CaducidadValidator implements Validator<String> {

    @Override
    public void validate(FacesContext context, UIComponent component, String value)
            throws ValidatorException {

        if (value == null || value.isBlank()) {
            return; // la obligatoriedad la cubre required="true"
        }

        if (!value.matches("\\d{2}/\\d{2}")) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Usa el formato MM/AA.", null));
        }

        int mes = Integer.parseInt(value.substring(0, 2));
        int anio = 2000 + Integer.parseInt(value.substring(3));

        if (mes < 1 || mes > 12) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "El mes no es válido.", null));
        }

        // La tarjeta es válida hasta el último día de su mes de caducidad.
        YearMonth caducidad = YearMonth.of(anio, mes);
        if (caducidad.isBefore(YearMonth.from(LocalDate.now()))) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "La tarjeta está vencida.", null));
        }
    }
}
