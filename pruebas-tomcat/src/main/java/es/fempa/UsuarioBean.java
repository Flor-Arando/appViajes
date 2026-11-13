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
                // Guarda el usuario en la sesión: es lo que hace estar "logueado".
                this.usuarioActual = usuario;
                // También lo mantiene para getUsuarioAutenticado()/eliminarCuenta().
                usuarioAutenticado = usuario.getUsername();
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
        this.usuarioAutenticado = null;
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

    public String getNombreEdicion() {
        // Inicialización perezosa: si aún no se ha cargado, toma el del usuario.
        if (nombreEdicion == null) {
            Usuario u = getUsuarioAutenticado();
            return u != null ? u.getNombre() : "";
        }
        return nombreEdicion;
    }
    public void setNombreEdicion(String nombreEdicion) { this.nombreEdicion = nombreEdicion; }

    public String getEmailEdicion() {
        if (emailEdicion == null) {
            Usuario u = getUsuarioAutenticado();
            return u != null ? u.getEmail() : "";
        }
        return emailEdicion;
    }
    public void setEmailEdicion(String emailEdicion) { this.emailEdicion = emailEdicion; }

    public String getPasswordActual() { return passwordActual; }
    public void setPasswordActual(String passwordActual) { this.passwordActual = passwordActual; }

    public String getPasswordNueva() { return passwordNueva; }
    public void setPasswordNueva(String passwordNueva) { this.passwordNueva = passwordNueva; }

    // Campos editables del perfil (se rellenan al cargar la página).
    private String nombreEdicion;
    private String emailEdicion;
    private String passwordActual;
    private String passwordNueva;

    // Precarga los campos editables con los datos del usuario autenticado.
    public void prepararEdicion() {
        Usuario usuario = getUsuarioAutenticado();
        if (usuario != null) {
            nombreEdicion = usuario.getNombre();
            emailEdicion = usuario.getEmail();
        }
    }

    // Guarda los cambios del perfil validándolos en el servidor.
    public String actualizarPerfil() {
        FacesContext context = FacesContext.getCurrentInstance();
        Usuario usuario = getUsuarioAutenticado();

        if (usuario == null) {
            return "/login?faces-redirect=true";
        }

        if (nombreEdicion == null || nombreEdicion.trim().isEmpty()) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "El nombre no puede estar vacío.", null));
            return null;
        }

        if (emailEdicion == null || !emailEdicion.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "Escribe un correo electrónico válido.", null));
            return null;
        }

        // Cambio de contraseña opcional: exige la actual y una nueva válida.
        if (passwordNueva != null && !passwordNueva.isEmpty()) {
            if (passwordActual == null || !passwordActual.equals(usuario.getPassword())) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "La contraseña actual no es correcta.", null));
                return null;
            }
            if (!passwordNueva.matches("^(?=.*[A-Z])(?=.*\\d).{8,}$")) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                        "La nueva contraseña debe tener 8 caracteres, una mayúscula y un número.", null));
                return null;
            }
            usuario.setPassword(passwordNueva);
        }

        usuario.setNombre(nombreEdicion.trim());
        usuario.setEmail(emailEdicion.trim().toLowerCase(Locale.ROOT));

        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Perfil actualizado correctamente.", null));
        prepararEdicion();
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

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    public void onChange() {
        System.out.println("On change: " + value);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "You have selected: " + value, null));
    }
}