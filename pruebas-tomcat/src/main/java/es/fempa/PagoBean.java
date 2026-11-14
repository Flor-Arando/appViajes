package es.fempa;

import es.fempa.modelo.DetallePaquete;
import es.fempa.modelo.PaqueteViaje;
import es.fempa.modelo.Reserva;
import es.fempa.servicio.DetallePaqueteServicio;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.PrimeFaces;

import java.io.Serializable;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Locale;
import java.util.UUID;

/**
 * Bean de la pantalla de pago (pago.xhtml).
 *
 * Recupera el paquete y el número de viajeros que el usuario eligió en
 * detallePaquete.xhtml. Al confirmar el pago (tarjeta o transferencia) simula
 * el cobro, registra la reserva en el historial del usuario y avisa del éxito
 * con un mensaje y un diálogo.
 */
@Named("pagoBean")
@ViewScoped
public class PagoBean implements Serializable {

    private static final long serialVersionUID = 1L;

    /* Datos adicionales del paquete (hotel, incluidos, fotos...) */
    @Inject
    private DetallePaqueteServicio detalleServicio;

    @Inject
    private UsuarioBean usuarioBean;

    private DetallePaquete detalle = new DetallePaquete();
    private int viajeros = 1;
    private String metodoPago = "";
    private String referencia = "";
    private boolean pagoRealizado;

    /**
     * Lee el paquete y el nº de viajeros guardados en sesión por
     * "Comprar paquete" y completa la ficha con DetallePaqueteServicio.
     */
    @PostConstruct
    public void init() {
        Object seleccionado = FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap().get("paqueteSeleccionado");

        if (seleccionado instanceof PaqueteViaje paquete) {
            DetallePaquete datosAdicionales = detalleServicio.buscarPorId(paquete.getId().intValue());
            detalle = (datosAdicionales != null) ? datosAdicionales : new DetallePaquete();
            detalle.setPaquete(paquete);

            Object guardados = FacesContext.getCurrentInstance()
                    .getExternalContext().getSessionMap().get("viajerosSeleccionados");
            if (guardados instanceof Integer numero) {
                viajeros = Math.max(1, numero);
            }
        }
    }

    /* Acciones de los dos métodos de pago. */
    public void pagarConTarjeta() {
        registrarPago("Tarjeta");
    }

    public void pagarConTransferencia() {
        registrarPago("Transferencia bancaria");
    }

    /**
     * Punto común del pago: genera la referencia, registra la reserva en el
     * historial del usuario autenticado y activa el diálogo de confirmación.
     */
    private void registrarPago(String metodo) {
        if (detalle.getPaquete() == null) {
            return;
        }

        metodoPago = metodo;
        referencia = "MT-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase(Locale.ROOT);

        // Registra la compra en el historial del usuario (si hay sesión iniciada).
        UsuarioBean.Usuario usuario = usuarioBean.getUsuarioAutenticado();
        if (usuario != null) {
            Reserva reserva = new Reserva(detalle.getPaquete(), LocalDate.now(), viajeros);
            reserva.setEstado("CONFIRMADA");
            usuario.addReserva(reserva);
        }

        pagoRealizado = true;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                FacesMessage.SEVERITY_INFO,
                "Pago realizado con éxito",
                "Pago simulado correctamente."));

        // Abre la ventana de pago realizado al terminar la petición AJAX.
        // No se usa el atributo oncomplete porque el botón no se vuelve a
        // renderizar y su expresión se quedaría con el valor inicial (false).
        PrimeFaces.current().executeScript("PF('dialogoPago').show()");
    }

    public DetallePaquete getDetalle() {
        return detalle;
    }

    public int getViajeros() {
        return viajeros;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public String getReferencia() {
        return referencia;
    }

    public boolean isPagoRealizado() {
        return pagoRealizado;
    }

    /* Precio por persona ya formateado. */
    public String getPrecioPersonaFormateado() {
        return detalle.getPaquete() == null ? "" : detalle.getPaquete().getPrecioFormateado();
    }

    /* Importe total (precio por persona × viajeros) ya formateado en euros. */
    public String getTotalFormateado() {
        if (detalle.getPaquete() == null) {
            return "";
        }
        NumberFormat formato = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("es-ES"));
        formato.setMaximumFractionDigits(0);
        return formato.format(detalle.getPaquete().getPrecio() * viajeros);
    }
}
