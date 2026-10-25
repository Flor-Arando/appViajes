package es.fempa;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class RegisterBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private AuthService authService;

    private String name;
    private String email;
    private String password;
    private String passwordConfirmation;

    // Valida los datos y registra la cuenta si cumple todas las reglas.
    public void register() {
        FacesContext context = FacesContext.getCurrentInstance();
        String userName = name == null ? "" : name.trim();
        String userEmail = email == null ? "" : email.trim().toLowerCase();
        String userPassword = password == null ? "" : password;

        // Repite en servidor las reglas mostradas por register.js.
        if (userName.length() < 3 || !userEmail.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]{2,}$")
                || userPassword.length() < 8 || !userPassword.matches(".*[A-Z].*")
                || !userPassword.matches(".*\\d.*")
                || !userPassword.equals(passwordConfirmation)) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "Revisa los datos del formulario", null));
            return;
        }

        if (authService.register(userName, userEmail, userPassword)) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Cuenta creada. Ya puedes iniciar sesión.", null));
            clear();
        } else {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Ese correo ya está registrado", null));
        }
    }

    // Vacía el formulario después de crear una cuenta correctamente.
    private void clear() {
        name = null;
        email = null;
        password = null;
        passwordConfirmation = null;
    }


    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public String getPasswordConfirmation() { return passwordConfirmation; }

    public void setPasswordConfirmation(String passwordConfirmation) { this.passwordConfirmation = passwordConfirmation; }
}
