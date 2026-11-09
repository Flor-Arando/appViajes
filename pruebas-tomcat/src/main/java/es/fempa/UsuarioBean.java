package es.fempa;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.io.Serializable;

@Named
@SessionScoped
public class UsuarioBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<Usuario> usuariosRegistrados = new ArrayList<>();
    // Usuario autenticado en la sesión actual; null mientras no se inicia sesión.
    private Usuario usuarioActual;
    private String nombre;
    private String mensaje;
    private int value = 50;

    public UsuarioBean() {
        usuariosRegistrados.add(new Usuario("Administrador", "admin", "1234"));
    }

    public String getSaludo() {
        return "HOla soy un saludo estático";
    }

    public List<Usuario> getUsuariosRegistrados() {
        return usuariosRegistrados;
    }

    public boolean existeUsuario(String username) {
        String normalizedUsername = normalizeUsername(username);
        if (normalizedUsername.isEmpty()) {
            return false;
        }

        for (Usuario usuario : usuariosRegistrados) {
            if (usuario.getUsername().equalsIgnoreCase(normalizedUsername)) {
                return true;
            }
        }
        return false;
    }

    public boolean agregarUsuario(String nombre, String username, String password) {
        String normalizedUsername = normalizeUsername(username);
        if (nombre == null || nombre.isBlank() || normalizedUsername.isEmpty()
                || password == null || password.isEmpty() || existeUsuario(normalizedUsername)) {
            return false;
        }

        usuariosRegistrados.add(new Usuario(nombre.trim(), normalizedUsername, password));
        return true;
    }

    public boolean autenticar(String username, String password) {
        String normalizedUsername = normalizeUsername(username);
        if (normalizedUsername.isEmpty() || password == null || password.isEmpty()) {
            return false;
        }

        for (Usuario usuario : usuariosRegistrados) {
            if (usuario.getUsername().equalsIgnoreCase(normalizedUsername)
                    && usuario.getPassword().equals(password)) {
                // Guarda el usuario en la sesión: es lo que hace estar "logueado".
                this.usuarioActual = usuario;
                return true;
            }
        }
        return false;
    }

    // True mientras haya un usuario autenticado en esta sesión.
    public boolean isLogged() {
        return usuarioActual != null;
    }

    // Nombre para mostrar en la cabecera; vacío si no hay sesión iniciada.
    public String getNombreUsuario() {
        return usuarioActual == null ? "" : usuarioActual.getNombre();
    }

    // Cierra la sesión: vacía el usuario actual y invalida la HttpSession.
    public String logout() {
        this.usuarioActual = null;
        FacesContext context = FacesContext.getCurrentInstance();
        Object rawSession = context.getExternalContext().getSession(false);
        if (rawSession instanceof jakarta.servlet.http.HttpSession session) {
            session.invalidate();
        } else {
            context.getExternalContext().invalidateSession();
        }
        return "dashboard?faces-redirect=true";
    }

    private String normalizeUsername(String username) {
        return username == null ? "" : username.trim().toLowerCase(Locale.ROOT);
    }

    public int getValue() {
        return value;
    }
    public void setValue(int value) {
        this.value = value;
    }

    public void saludar() {
        mensaje = "Hola " + nombre;
        System.out.println(mensaje);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMensaje() {
        return mensaje;
    }

    public static class Usuario implements Serializable {
        private static final long serialVersionUID = 1L;

        private String nombre;
        private String username;
        private String password;

        public Usuario(String nombre, String username, String password) {
            this.nombre = nombre;
            this.username = username;
            this.password = password;
        }

        public String getNombre() {
            return nombre;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }
    }

    public void onChange() {
        System.out.println("On change: " + value);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "You have selected: " + value, null));
    }
}