package es.fempa;

import es.fempa.modelo.Reserva;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Acceso al historial de reservas del usuario autenticado.
 * Las reservas viven dentro de cada Usuario, así que este bean sólo
 * expone las del usuario en sesión.
 */
@Named("reservasBean")
@RequestScoped
public class ReservasBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private UsuarioBean usuarioBean;

    public List<Reserva> getReservas() {
        UsuarioBean.Usuario usuario = usuarioBean.getUsuarioAutenticado();
        if (usuario == null) {
            return new ArrayList<>();
        }
        return usuario.getReservas();
    }

    /** Total de viajeros sumados en todas sus reservas. */
    public int getTotalViajeros() {
        return getReservas().stream().mapToInt(Reserva::getViajeros).sum();
    }
}
