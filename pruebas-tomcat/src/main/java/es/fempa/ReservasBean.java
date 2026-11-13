package es.fempa;

import es.fempa.modelo.PaqueteViaje;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

/**
 * Bean de las reservas del usuario autenticado.
 * Vive en la sesión: al cerrar sesión se vacía, igual que el login.
 */
@Named("reservasBean")
@SessionScoped
public class ReservasBean implements Serializable {

    private static final long serialVersionUID = 1L;

    public static class Reserva implements Serializable {
        private static final long serialVersionUID = 1L;

        private final String codigo;
        private final PaqueteViaje paquete;
        private final LocalDate fechaReserva;
        private int viajeros;
        private String estado; // CONFIRMADA | PENDIENTE

        public Reserva(PaqueteViaje paquete, LocalDate fechaReserva, int viajeros) {
            this.codigo = UUID.randomUUID().toString().substring(0, 8).toUpperCase(Locale.ROOT);
            this.paquete = paquete;
            this.fechaReserva = fechaReserva;
            this.viajeros = Math.max(1, viajeros);
            this.estado = "PENDIENTE";
        }

        public String getCodigo() { return codigo; }
        public PaqueteViaje getPaquete() { return paquete; }
        public LocalDate getFechaReserva() { return fechaReserva; }
        public int getViajeros() { return viajeros; }
        public void setViajeros(int v) { this.viajeros = Math.max(1, v); }
        public String getEstado() { return estado; }
        public void setEstado(String e) { this.estado = e; }

        /** Título del paquete reservado. */
        public String getTitulo() { return paquete.getTitulo(); }
        /** Destino del paquete reservado. */
        public String getDestino() { return paquete.getDestino(); }
        /** Precio formateado del paquete. */
        public String getPrecioFormateado() { return paquete.getPrecioFormateado(); }
        /** Fecha de reserva como texto legible. */
        public String getFechaReservaTexto() {
            return fechaReserva.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        }
    }

    private List<Reserva> reservas = new ArrayList<>();

    @PostConstruct
    public void init() {
        // Datos de ejemplo para poder ver la tabla funcionando.
        PaqueteViaje paris = new PaqueteViaje(1L, "París romántico", "París, Francia",
                "Vuelo, hotel 4★ en el centro y desayuno incluido.", "", 749, "5 días / 4 noches", 4.8);
        PaqueteViaje tokio = new PaqueteViaje(4L, "Tokio y Monte Fuji", "Tokio, Japón",
                "Vuelo, hotel 4★, JR Pass de 7 días y excursión al Monte Fuji.", "", 1899, "9 días / 8 noches", 4.9);
        reservas.add(new Reserva(paris, LocalDate.now().minusDays(3), 2));
        reservas.add(new Reserva(tokio, LocalDate.now().minusDays(1), 1));
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    /** Total de viajeros sumados en todas las reservas. */
    public int getTotalViajeros() {
        return reservas.stream().mapToInt(Reserva::getViajeros).sum();
    }
}
