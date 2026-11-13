package es.fempa;

import es.fempa.modelo.DetallePaquete;
import es.fempa.modelo.PaqueteViaje;
import es.fempa.modelo.Reserva;
import es.fempa.servicio.DetallePaqueteServicio;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Locale;

/**
 * Detalle de un paquete seleccionado, con selector de viajeros y compra.
 * Se mantiene en la sesión para conservar el paquete y el nº de viajeros
 * mientras el usuario ajusta la compra.
 */
@Named("detallePaqueteBean")
@SessionScoped
public class DetallePaqueteBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private DetallePaqueteServicio detalleServicio;

    @Inject
    private UsuarioBean usuarioBean;

    private DetallePaquete detalle;
    private int viajeros = 1;

    @PostConstruct
    public void cargarPaquete() {
        Object seleccionado = FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap().remove("paqueteSeleccionado");

        if (seleccionado instanceof PaqueteViaje) {
            PaqueteViaje paquete = (PaqueteViaje) seleccionado;
            DetallePaquete datosAdicionales = detalleServicio.buscarPorId(paquete.getId().intValue());

            if (datosAdicionales != null) {
                detalle = datosAdicionales;
            } else {
                detalle = new DetallePaquete();
            }
            detalle.setPaquete(paquete);
            viajeros = 1;
        } else {
            detalle = new DetallePaquete();
        }
    }

    public DetallePaquete getDetalle() {
        return detalle;
    }

    public int getViajeros() {
        return viajeros;
    }

    public void setViajeros(int viajeros) {
        this.viajeros = Math.max(1, viajeros);
    }

    /** Precio total = precio por persona × número de viajeros. */
    public double getPrecioTotal() {
        if (detalle == null || detalle.getPaquete() == null) {
            return 0d;
        }
        return detalle.getPaquete().getPrecio() * viajeros;
    }

    public String getPrecioTotalFormateado() {
        NumberFormat formato = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("es-ES"));
        formato.setMaximumFractionDigits(0);
        return formato.format(getPrecioTotal());
    }

    /**
     * Compra el paquete para el número de viajeros elegido y lo añade
     * al historial del usuario autenticado.
     */
    public String comprar() {
        FacesContext context = FacesContext.getCurrentInstance();

        UsuarioBean.Usuario usuario = usuarioBean.getUsuarioAutenticado();
        if (usuario == null) {
            return "/login?faces-redirect=true";
        }
        if (detalle == null || detalle.getPaquete() == null) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "No hay ningún paquete seleccionado.", null));
            return null;
        }

        Reserva reserva = new Reserva(detalle.getPaquete(), LocalDate.now(), viajeros);
        reserva.setEstado("CONFIRMADA");
        usuario.addReserva(reserva);

        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Reserva confirmada",
                "Has reservado \"" + detalle.getPaquete().getTitulo() + "\" para "
                        + viajeros + " viajero(s) por " + getPrecioTotalFormateado() + "."));

        return "/misReservas?faces-redirect=true";
    }
}
