package es.fempa;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.Locale;

@Named
@ViewScoped
public class LoginBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private UsuarioBean usuarioBean;
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Valida las credenciales y redirige al inicio si son correctas.
    public String login() {
        String usuario = username == null ? "" : username.trim().toLowerCase(Locale.ROOT);
        String clave = password == null ? "" : password;
        FacesContext context = FacesContext.getCurrentInstance();

        if (usuario.isEmpty() || clave.isEmpty()) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "El usuario y la contraseña son obligatorios", null));
            return null;
        }

        if (usuarioBean.autenticar(usuario, clave)) {
            return "index?faces-redirect=true";
        } else {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Usuario o contraseña incorrectos", null));
            return null;
        }
    }
}
