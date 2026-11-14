package es.fempa.validadores;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.FacesValidator;
import jakarta.faces.validator.Validator;
import jakarta.faces.validator.ValidatorException;

/**
 * Valida el número de tarjeta en el servidor: entre 13 y 19 dígitos y
 * dígito de control correcto (algoritmo de Luhn).
 *
 * Sustituye a la validación que hacía pago.js, pero al ejecutarse en el
 * servidor no puede saltarse desde el navegador.
 */
@FacesValidator("tarjetaValidator")
public class TarjetaValidator implements Validator<String> {

    @Override
    public void validate(FacesContext context, UIComponent component, String value)
            throws ValidatorException {

        if (value == null || value.isBlank()) {
            return; // la obligatoriedad la cubre required="true"
        }

        String numero = value.replaceAll("\\s", "");

        if (!numero.matches("\\d{13,19}")) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "El número debe tener entre 13 y 19 dígitos.", null));
        }

        if (!luhnValido(numero)) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "El número de tarjeta no es válido.", null));
        }
    }

    /** Algoritmo de Luhn: suma de comprobación del dígito de control. */
    private boolean luhnValido(String numero) {
        int suma = 0;
        boolean doblar = false;

        for (int i = numero.length() - 1; i >= 0; i--) {
            int digito = numero.charAt(i) - '0';
            if (doblar) {
                digito *= 2;
                if (digito > 9) {
                    digito -= 9;
                }
            }
            suma += digito;
            doblar = !doblar;
        }
        return suma % 10 == 0;
    }
}
