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
    private String nombre;
    private String mensaje;
    private String usuarioAutenticado;
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

    public boolean agregarUsuario(String nombre, String username, String password, String email) {
        String normalizedUsername = normalizeUsername(username);
        if (nombre == null || nombre.isBlank() || normalizedUsername.isEmpty()
                || password == null || password.isEmpty() || email == null || email.isBlank()
                || existeUsuario(normalizedUsername)) {
            return false;
        }

        usuariosRegistrados.add(new Usuario(nombre.trim(), normalizedUsername, password, email.trim()));
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
                    usuarioAutenticado = usuario.getUsername();
                    return true;
            }
        }
        return false;
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

    public Usuario getUsuarioAutenticado() {
        if (usuarioAutenticado == null) {
            return null;
        }
        for (Usuario usuario : usuariosRegistrados) {
            if (usuario.getUsername().equalsIgnoreCase(usuarioAutenticado)) {
                return usuario;
            }
        }
        return null;
    }

    // Elimina la cuenta actual y cierra la sesión.
    public String eliminarCuenta() {
        Usuario usuario = getUsuarioAutenticado();
        if (usuario != null) {
            usuariosRegistrados.remove(usuario);
        }
        usuarioAutenticado = null;
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/login?faces-redirect=true";
    }

    public static class Usuario implements Serializable {
        private static final long serialVersionUID = 1L;

        private String nombre;
        private String username;
        private String password;
        private String email;

        public Usuario(String nombre, String username, String password) {
            this(nombre, username, password, "");
        }

        public Usuario(String nombre, String username, String password, String email) {
            this.nombre = nombre;
            this.username = username;
            this.password = password;
            this.email = email;
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

        public String getEmail() {
            return email;
        }
    }

    public void onChange() {
        System.out.println("On change: " + value);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "You have selected: " + value, null));
    }
}