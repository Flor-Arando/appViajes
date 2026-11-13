package es.fempa;

import es.fempa.modelo.PaqueteViaje;
import es.fempa.modelo.Reserva;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Almacén de usuarios de toda la aplicación.
 *
 * Vive en el ámbito de aplicación (@ApplicationScoped), NO en la sesión:
 * así, al cerrar sesión no se pierden las cuentas registradas. El login
 * sólo marca qué usuario de esta lista está activo en la sesión.
 */
@Named("usuariosStore")
@ApplicationScoped
public class UsuariosStore implements Serializable {

    private static final long serialVersionUID = 1L;

    private final List<UsuarioBean.Usuario> usuarios = new ArrayList<>();

    @PostConstruct
    public void init() {
        UsuarioBean.Usuario admin = new UsuarioBean.Usuario("Administrador", "admin", "1234", "admin@morfeotrip.example");

        // Historial de ejemplo para que la tabla del perfil tenga contenido.
        PaqueteViaje paris = new PaqueteViaje(1L, "París romántico", "París, Francia",
                "Vuelo, hotel 4★ en el centro y desayuno incluido.", "", 749, "5 días / 4 noches", 4.8);
        PaqueteViaje tokio = new PaqueteViaje(4L, "Tokio y Monte Fuji", "Tokio, Japón",
                "Vuelo, hotel 4★, JR Pass de 7 días y excursión al Monte Fuji.", "", 1899, "9 días / 8 noches", 4.9);
        admin.getReservas().add(new Reserva(paris, LocalDate.now().minusDays(3), 2));
        admin.getReservas().add(new Reserva(tokio, LocalDate.now().minusDays(1), 1));

        usuarios.add(admin);
    }

    public List<UsuarioBean.Usuario> getUsuarios() {
        return usuarios;
    }

    public UsuarioBean.Usuario buscar(String username) {
        String normalized = username == null ? "" : username.trim().toLowerCase(Locale.ROOT);
        if (normalized.isEmpty()) {
            return null;
        }
        for (UsuarioBean.Usuario u : usuarios) {
            if (u.getUsername().equalsIgnoreCase(normalized)) {
                return u;
            }
        }
        return null;
    }

    public boolean existe(String username) {
        return buscar(username) != null;
    }

    public void agregar(UsuarioBean.Usuario usuario) {
        if (usuario != null) {
            usuarios.add(usuario);
        }
    }

    public void eliminar(UsuarioBean.Usuario usuario) {
        usuarios.remove(usuario);
    }
}
