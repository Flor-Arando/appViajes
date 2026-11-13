package es.fempa;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;

import java.io.Serializable;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * Bean de la página de contacto.
 * Valida en el servidor (sin JS) y muestra el resultado con p:messages.
 */
@Named("contactoBean")
@RequestScoped
public class ContactoBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$");

    private String nombre;
    private String email;
    private String asunto;
    private String mensaje;

    public void enviar() {
        FacesContext context = FacesContext.getCurrentInstance();

        if (nombre == null || nombre.trim().isEmpty()) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "Indica tu nombre.", null));
            return;
        }

        if (email == null || !EMAIL_PATTERN.matcher(email.trim()).matches()) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "Escribe un correo electrónico válido.", null));
            return;
        }

        if (asunto == null || asunto.trim().isEmpty()) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "Elige un asunto.", null));
            return;
        }

        if (mensaje == null || mensaje.trim().length() < 10) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "Cuéntanos algo más (mínimo 10 caracteres).", null));
            return;
        }

        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Gracias, " + nombre.trim() + ". Hemos recibido tu mensaje.",
                "Te responderemos a " + email.trim().toLowerCase(Locale.ROOT) + "."));

        clear();
    }

    private void clear() {
        nombre = null;
        email = null;
        asunto = null;
        mensaje = null;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getAsunto() { return asunto; }
    public void setAsunto(String asunto) { this.asunto = asunto; }

    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
}
