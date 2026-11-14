package es.fempa;

import es.fempa.modelo.DetallePaquete;
import es.fempa.modelo.PaqueteViaje;
import es.fempa.servicio.DetallePaqueteServicio;
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

    @Inject
    private DetallePaqueteServicio detalleServicio;

    /** Paquete en edición (null = formulario de alta). */
    private PaqueteViaje edicion;
    private Long idEdicion;

    /* Ficha ampliada del paquete (se edita como texto, una línea por elemento). */
    private String hotel;
    private String incluidosTexto;
    private String itinerarioTexto;
    private String imagenDetalle;
    private String fotosTexto;

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
        hotel = null;
        incluidosTexto = null;
        itinerarioTexto = null;
        imagenDetalle = null;
        fotosTexto = null;
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

        // Carga la ficha ampliada existente (si la hay) en los campos de texto.
        DetallePaquete detalle = (paquete.getId() != null)
                ? detalleServicio.buscarPorId(paquete.getId().intValue())
                : null;
        if (detalle != null) {
            hotel = detalle.getHotel();
            incluidosTexto = unirLineas(detalle.getIncluidos());
            itinerarioTexto = unirLineas(detalle.getItinerario());
            imagenDetalle = detalle.getImagen();
            fotosTexto = unirLineas(detalle.getFotos());
        } else {
            hotel = null;
            incluidosTexto = null;
            itinerarioTexto = null;
            imagenDetalle = null;
            fotosTexto = null;
        }
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
            guardarDetalle(edicion.getId());
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Paquete actualizado: " + edicion.getTitulo(), null));
        } else {
            edicion.setId(null);
            paquetesStore.add(edicion);
            guardarDetalle(edicion.getId());
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Paquete creado: " + edicion.getTitulo(), null));
        }
        cancelar();
    }

    /** Guarda la ficha ampliada (hotel, incluidos, itinerario y fotos). */
    private void guardarDetalle(Long id) {
        if (id == null) {
            return;
        }
        String[] incluidos = separarLineas(incluidosTexto);
        String[] itinerario = separarLineas(itinerarioTexto);
        String[] fotos = separarLineas(fotosTexto);

        boolean vacio = esVacio(hotel) && incluidos.length == 0
                && itinerario.length == 0 && fotos.length == 0 && esVacio(imagenDetalle);
        if (vacio) {
            detalleServicio.eliminar(id.intValue());
            return;
        }

        DetallePaquete detalle = new DetallePaquete();
        detalle.setHotel(hotel == null ? "" : hotel.trim());
        detalle.setIncluidos(incluidos);
        detalle.setItinerario(itinerario);
        detalle.setFotos(fotos);
        detalle.setImagen(esVacio(imagenDetalle) ? edicion.getImagen() : imagenDetalle.trim());
        detalleServicio.guardarPorId(id.intValue(), detalle);
    }

    /** Une un array en texto, una línea por elemento. */
    private String unirLineas(String[] valores) {
        return (valores == null) ? "" : String.join("\n", valores);
    }

    /** Separa el texto en líneas, quitando vacías y espacios sobrantes. */
    private String[] separarLineas(String texto) {
        if (esVacio(texto)) {
            return new String[0];
        }
        return texto.lines()
                .map(String::trim)
                .filter(l -> !l.isEmpty())
                .toArray(String[]::new);
    }

    public void eliminar(PaqueteViaje paquete) {
        if (!comprobarAdmin() || paquete == null) {
            return;
        }
        if (paquetesStore.eliminar(paquete.getId())) {
            if (paquete.getId() != null) {
                detalleServicio.eliminar(paquete.getId().intValue());
            }
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "Paquete eliminado: " + paquete.getTitulo(), null));
        }
    }

    public void cancelar() {
        edicion = null;
        idEdicion = null;
        hotel = null;
        incluidosTexto = null;
        itinerarioTexto = null;
        imagenDetalle = null;
        fotosTexto = null;
    }

    public String getHotel() {
        return hotel;
    }

    public void setHotel(String hotel) {
        this.hotel = hotel;
    }

    public String getIncluidosTexto() {
        return incluidosTexto;
    }

    public void setIncluidosTexto(String incluidosTexto) {
        this.incluidosTexto = incluidosTexto;
    }

    public String getItinerarioTexto() {
        return itinerarioTexto;
    }

    public void setItinerarioTexto(String itinerarioTexto) {
        this.itinerarioTexto = itinerarioTexto;
    }

    public String getImagenDetalle() {
        return imagenDetalle;
    }

    public void setImagenDetalle(String imagenDetalle) {
        this.imagenDetalle = imagenDetalle;
    }

    public String getFotosTexto() {
        return fotosTexto;
    }

    public void setFotosTexto(String fotosTexto) {
        this.fotosTexto = fotosTexto;
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
