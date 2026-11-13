package es.fempa;

import es.fempa.modelo.PaqueteViaje;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;

/**
 * Gestión de paquetes de viaje para el panel de administración.
 * Sólo debe usarse cuando UsuarioBean.isAdmin() es true; además cada
 * acción vuelve a comprobarlo por seguridad.
 */
@Named("adminPaquetesBean")
@SessionScoped
public class AdminPaquetesBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private PaquetesStore paquetesStore;

    @Inject
    private UsuarioBean usuarioBean;

    /** Paquete en edición (null = formulario de alta). */
    private PaqueteViaje edicion;
    private Long idEdicion;

    public List<PaqueteViaje> getPaquetes() {
        return paquetesStore.getPaquetes();
    }

    public boolean isModoEdicion() {
        return idEdicion != null;
    }

    public PaqueteViaje getEdicion() {
        return edicion;
    }

    /** Prepara el formulario para crear un paquete nuevo. */
    public void nuevo() {
        edicion = new PaqueteViaje();
        edicion.setValoracion(4.5);
        idEdicion = null;
    }

    /** Prepara el formulario para editar un paquete existente. */
    public void editar(PaqueteViaje paquete) {
        if (paquete == null) {
            return;
        }
        // Copia para no modificar el original hasta guardar.
        PaqueteViaje copia = new PaqueteViaje(
                paquete.getId(), paquete.getTitulo(), paquete.getDestino(),
                paquete.getDescripcion(), paquete.getImagen(),
                paquete.getPrecio(), paquete.getDuracion(), paquete.getValoracion());
        this.edicion = copia;
        this.idEdicion = paquete.getId();
    }

    public void guardar() {
        if (!comprobarAdmin()) {
            return;
        }
        if (edicion == null) {
            return;
        }
        FacesContext context = FacesContext.getCurrentInstance();

        if (esVacio(edicion.getTitulo()) || esVacio(edicion.getDestino())
                || esVacio(edicion.getDuracion())) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "Título, destino y duración son obligatorios.", null));
            return;
        }
        if (edicion.getPrecio() <= 0) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "El precio debe ser mayor que 0.", null));
            return;
        }

        if (isModoEdicion()) {
            paquetesStore.update(edicion);
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Paquete actualizado: " + edicion.getTitulo(), null));
        } else {
            edicion.setId(null);
            paquetesStore.add(edicion);
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Paquete creado: " + edicion.getTitulo(), null));
        }
        cancelar();
    }

    public void eliminar(PaqueteViaje paquete) {
        if (!comprobarAdmin() || paquete == null) {
            return;
        }
        if (paquetesStore.eliminar(paquete.getId())) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "Paquete eliminado: " + paquete.getTitulo(), null));
        }
    }

    public void cancelar() {
        edicion = null;
        idEdicion = null;
    }

    private boolean comprobarAdmin() {
        if (!usuarioBean.isAdmin()) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Acceso restringido al administrador.", null));
            return false;
        }
        return true;
    }

    private boolean esVacio(String s) {
        return s == null || s.trim().isEmpty();
    }
}
