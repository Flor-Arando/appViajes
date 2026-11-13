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
        } else {
            detalle = new DetallePaquete();
        }
    }

    public DetallePaquete getDetalle() {
        return detalle;
    }
}
