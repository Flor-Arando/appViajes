package es.fempa.modelo;

import java.io.Serializable;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.UUID;

/**
 * Reserva de un paquete realizada por un usuario.
 * Se guarda dentro de cada Usuario para componer su historial de viajes.
 */
public class Reserva implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final DateTimeFormatter FORMATO_FECHA =
            DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.forLanguageTag("es-ES"));

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
    public LocalDate getFechaReserva() { return fechaReserva; }    public int getViajeros() { return viajeros; }
    public void setViajeros(int v) { this.viajeros = Math.max(1, v); }
    public String getEstado() { return estado; }
    public void setEstado(String e) { this.estado = e; }

    public String getTitulo() { return paquete.getTitulo(); }
    public String getDestino() { return paquete.getDestino(); }
    public String getPrecioFormateado() { return paquete.getPrecioFormateado(); }
    public String getFechaReservaTexto() { return fechaReserva.format(FORMATO_FECHA); }

    /** Precio total de la reserva: precio por persona × número de viajeros. */
    public double getTotal() { return paquete.getPrecio() * viajeros; }

    /** Total formateado en euros. */
    public String getTotalFormateado() {
        NumberFormat formato = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("es-ES"));
        formato.setMaximumFractionDigits(0);
        return formato.format(getTotal());
    }
}
