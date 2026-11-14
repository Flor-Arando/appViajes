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

    /* Campos del formulario de pago (validados en el servidor). */
    private String titular;
    private String numeroTarjeta;
    private String vencimiento;
    private String cvv;
    private String email;
    private boolean terminos;
    private String emailTransferencia;
    private boolean confirmoTransferencia;

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

        // Sin sesión no se cobra nada ni se añade al historial: se pide login.
        if (!isLogueado()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_WARN,
                    "Inicia sesión para pagar",
                    "Necesitas una cuenta para completar la reserva."));
            // El diálogo de login se abre solo al repintar pago.xhtml.
            return;
        }

        metodoPago = metodo;
        referencia = "MT-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase(Locale.ROOT);

        // Registra la compra en el historial del usuario autenticado.
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

        // El diálogo de pago realizado se abre solo: pago.xhtml lo pinta
        // con visible="#{pagoBean.pagoRealizado}" al recargar la página.
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

    /* ¿Hay un usuario con sesión iniciada? Si no, no se permite pagar. */
    public boolean isLogueado() {
        return usuarioBean != null && usuarioBean.isLogged();
    }

    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    public String getVencimiento() {
        return vencimiento;
    }

    public void setVencimiento(String vencimiento) {
        this.vencimiento = vencimiento;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isTerminos() {
        return terminos;
    }

    public void setTerminos(boolean terminos) {
        this.terminos = terminos;
    }

    public String getEmailTransferencia() {
        return emailTransferencia;
    }

    public void setEmailTransferencia(String emailTransferencia) {
        this.emailTransferencia = emailTransferencia;
    }

    public boolean isConfirmoTransferencia() {
        return confirmoTransferencia;
    }

    public void setConfirmoTransferencia(boolean confirmoTransferencia) {
        this.confirmoTransferencia = confirmoTransferencia;
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
