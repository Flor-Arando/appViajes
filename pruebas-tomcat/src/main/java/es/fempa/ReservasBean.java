package es.fempa;

import es.fempa.modelo.DetallePaquete;
import es.fempa.modelo.Reserva;
import es.fempa.servicio.DetallePaqueteServicio;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Acceso al historial de reservas del usuario autenticado.
 * Las reservas viven dentro de cada Usuario, así que este bean sólo
 * expone las del usuario en sesión. Es @ViewScoped para conservar la
 * reserva seleccionada mientras se muestra su detalle en el diálogo.
 */
@Named("reservasBean")
@ViewScoped
public class ReservasBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private UsuarioBean usuarioBean;

    @Inject
    private DetallePaqueteServicio detalleServicio;

    /* Reserva abierta en el diálogo de detalle y su ficha completa. */
    private Reserva seleccionada;
    private DetallePaquete detalleSeleccionado;

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

    /**
     * Prepara el detalle de una reserva concreta: guarda la reserva y carga
     * la ficha completa del paquete (hotel, incluidos, itinerario, fotos...).
     */
    public void seleccionar(Reserva reserva) {
        this.seleccionada = reserva;
        if (reserva != null && reserva.getPaquete() != null) {
            DetallePaquete datos = detalleServicio.buscarPorId(reserva.getPaquete().getId().intValue());
            detalleSeleccionado = (datos != null) ? datos : new DetallePaquete();
            detalleSeleccionado.setPaquete(reserva.getPaquete());
        } else {
            detalleSeleccionado = null;
        }
    }

    public Reserva getSeleccionada() {
        return seleccionada;
    }

    public DetallePaquete getDetalleSeleccionado() {
        return detalleSeleccionado;
    }
}
