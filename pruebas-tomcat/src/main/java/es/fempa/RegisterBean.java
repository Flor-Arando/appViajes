package es.fempa;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.Locale;
import java.util.regex.Pattern;

@Named
@ViewScoped
public class RegisterBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$");
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^(?=.*[A-Z])(?=.*\\d).{8,}$");

    @Inject
    private UsuarioBean usuarioBean;

    private String username;
    private String email;
    private String password;
    private String passwordConfirmation;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }

    // Valida los datos y registra la cuenta si cumple todas las reglas.
    public void register() {
        FacesContext context = FacesContext.getCurrentInstance();
        String userName = username == null ? "" : username.trim();
        String userEmail = email == null ? "" : email.trim().toLowerCase(Locale.ROOT);
        String userPassword = password == null ? "" : password;

        if (userName.isEmpty() || userEmail.isEmpty() || userPassword.isEmpty()) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "Completa todos los campos obligatorios", null));
            return;
        }

        if (!EMAIL_PATTERN.matcher(userEmail).matches()) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "Escribe un correo electrónico válido", null));
            return;
        }

        if (!PASSWORD_PATTERN.matcher(userPassword).matches()) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "La contraseña debe tener al menos 8 caracteres, una mayúscula y un número",
                    null));
            return;
        }

        if (!userPassword.equals(passwordConfirmation)) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "Las contraseñas no coinciden", null));
            return;
        }

        if (usuarioBean.existeUsuario(userName)) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Ese nombre de usuario ya está registrado", null));
            return;
        }

        if (!usuarioBean.agregarUsuario(userName, userName, userPassword, userEmail)) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Ese nombre de usuario ya está registrado", null));
            return;
        }

        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Cuenta creada. Ya puedes iniciar sesión.", null));
        clear();
    }

    // Vacía el formulario después de crear una cuenta correctamente.
    private void clear() {
        username = null;
        email = null;
        password = null;
        passwordConfirmation = null;
    }

}
