package es.fempa;

import es.fempa.modelo.DetallePaquete;
import es.fempa.modelo.PaqueteViaje;
import es.fempa.servicio.DetallePaqueteServicio;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named("detallePaqueteBean")
@RequestScoped
public class DetallePaqueteBean {

    @Inject
    private DetallePaqueteServicio detalleServicio;

    private DetallePaquete detalle;

    @PostConstruct
    public void cargarPaquete() {
        // Se lee sin borrar: en el postback de "Comprar paquete" el bean se crea
        // de nuevo y necesita recuperar el paquete para poder redirigir al pago.
        Object seleccionado = FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap().get("paqueteSeleccionado");

        if (seleccionado instanceof PaqueteViaje) {
            PaqueteViaje paquete = (PaqueteViaje) seleccionado;
            DetallePaquete datosAdicionales = detalleServicio.buscarPorId(paquete.getId().intValue());

            if (datosAdicionales != null) {
                detalle = datosAdicionales;
            } else {
                detalle = new DetallePaquete();
            }
            detalle.setPaquete(paquete);
        } else {
            detalle = new DetallePaquete();
        }
    }

    /**
     * Abre la pantalla de pago con el paquete que se está viendo.
     * Vuelve a guardar el paquete en la sesión para que PagoBean lo recupere.
     */
    public String irAPago() {
        if (detalle == null || detalle.getPaquete() == null) {
            return null;
        }
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
                .put("paqueteSeleccionado", detalle.getPaquete());
        return "/pago.xhtml?faces-redirect=true";
    }

    public DetallePaquete getDetalle() {
        return detalle;
    }
}
