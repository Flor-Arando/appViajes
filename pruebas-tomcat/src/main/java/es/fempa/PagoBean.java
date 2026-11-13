package es.fempa;

import es.fempa.modelo.DetallePaquete;
import es.fempa.modelo.PaqueteViaje;
import es.fempa.servicio.DetallePaqueteServicio;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.PrimeFaces;

import java.io.Serializable;
import java.util.Locale;
import java.util.UUID;

/**
 * Bean de la pantalla de pago (pago.xhtml).
 *
 * Recupera el paquete que el usuario eligió en detallePaquete.xhtml y, cuando
 * se confirma el pago (tarjeta o transferencia), simula el cobro y avisa del
 * éxito con un mensaje y un diálogo.
 *
 * El alta en el historial de vuelos todavía no se hace en este paso.
 */
@Named("pagoBean")
@ViewScoped
public class PagoBean implements Serializable {

    private static final long serialVersionUID = 1L;

    /* Datos adicionales del paquete (hotel, incluidos, fotos...) */
    @Inject
    private DetallePaqueteServicio detalleServicio;

    private DetallePaquete detalle = new DetallePaquete();
    private String metodoPago = "";
    private String referencia = "";
    private boolean pagoRealizado;

    /**
     * Lee el paquete guardado en sesión por "Comprar paquete".
     * Se usa DetallePaqueteServicio para completar la ficha del paquete.
     */
    @PostConstruct
    public void init() {
        Object seleccionado = FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap().get("paqueteSeleccionado");

        if (seleccionado instanceof PaqueteViaje paquete) {
            DetallePaquete datosAdicionales = detalleServicio.buscarPorId(paquete.getId().intValue());
            detalle = (datosAdicionales != null) ? datosAdicionales : new DetallePaquete();
            detalle.setPaquete(paquete);
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
     * Punto común del pago: genera la referencia, guarda el vuelo en el
     * historial y activa el diálogo de confirmación.
     */
    private void registrarPago(String metodo) {
        if (detalle.getPaquete() == null) {
            return;
        }

        metodoPago = metodo;
        referencia = "MT-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase(Locale.ROOT);

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

    public String getMetodoPago() {
        return metodoPago;
    }

    public String getReferencia() {
        return referencia;
    }

    public boolean isPagoRealizado() {
        return pagoRealizado;
    }

    /* Importe total ya formateado en euros para la vista. */
    public String getTotalFormateado() {
        return detalle.getPaquete() == null ? "" : detalle.getPaquete().getPrecioFormateado();
    }
}
