package es.fempa;

import es.fempa.modelo.Ciudad;
import es.fempa.modelo.PaqueteViaje;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import org.primefaces.model.ResponsiveOption;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Bean de la página de inicio (dashboard) de la agencia de viajes.
 *
 * Carga las ciudades del buscador, las ofertas y los destinos destacados,
 * y atiende las acciones de búsqueda y reserva (de momento simuladas).
 */
@Named("dashboardBean")
@ViewScoped
public class DashboardBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final DateTimeFormatter FORMATO_FECHA =
            DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.forLanguageTag("es-ES"));

    private List<LocalDate> rangoFechas;
    private Ciudad ciudadSeleccionada;

    private List<Ciudad> ciudades;
    private List<PaqueteViaje> ofertas;
    private List<PaqueteViaje> topDestinos;
    private List<ResponsiveOption> opcionesResponsive;

    @PostConstruct
    public void init() {
        cargarCiudades();
        cargarOfertas();
        cargarTopDestinos();
        cargarOpcionesResponsive();
    }

    private void cargarCiudades() {
        ciudades = new ArrayList<>(List.of(
                new Ciudad("Madrid"),
                new Ciudad("Barcelona"),
                new Ciudad("Valencia"),
                new Ciudad("Sevilla"),
                new Ciudad("Bilbao"),
                new Ciudad("Málaga"),
                new Ciudad("Alicante"),
                new Ciudad("Zaragoza")
        ));
    }

    private void cargarOfertas() {
        ofertas = new ArrayList<>(List.of(
                new PaqueteViaje(1L,
                        "París romántico",
                        "París, Francia",
                        "Vuelo, hotel 4★ en el centro y desayuno incluido. Paseo en barco por el Sena.",
                        "https://images.unsplash.com/photo-1502602898657-3e91760cbb34?auto=format&fit=crop&w=1200&h=800&q=80",
                        749,
                        "5 días / 4 noches",
                        4.8),
                new PaqueteViaje(2L,
                        "Roma imperial",
                        "Roma, Italia",
                        "Vuelo, hotel 4★ y visita guiada al Coliseo con entrada sin colas.",
                        "https://images.unsplash.com/photo-1552832230-c0197dd311b5?auto=format&fit=crop&w=1200&h=800&q=80",
                        529,
                        "4 días / 3 noches",
                        4.7),
                new PaqueteViaje(3L,
                        "Nueva York esencial",
                        "Nueva York, EE. UU.",
                        "Vuelo directo, hotel en Manhattan y pase turístico de 3 días.",
                        "https://images.unsplash.com/photo-1496442226666-8d4d0e62e6e9?auto=format&fit=crop&w=1200&h=800&q=80",
                        1249,
                        "7 días / 6 noches",
                        4.9),
                new PaqueteViaje(4L,
                        "Tokio y Monte Fuji",
                        "Tokio, Japón",
                        "Vuelo, hotel 4★, JR Pass de 7 días y excursión al Monte Fuji.",
                        "https://images.unsplash.com/photo-1540959733332-eab4deabeeaf?auto=format&fit=crop&w=1200&h=800&q=80",
                        1899,
                        "9 días / 8 noches",
                        4.9)
        ));
    }

    private void cargarTopDestinos() {
        topDestinos = new ArrayList<>(List.of(
                new PaqueteViaje(5L,
                        "Bali, isla de los dioses",
                        "Bali, Indonesia",
                        "Villa con piscina privada, desayuno y traslados incluidos.",
                        "https://images.unsplash.com/photo-1537996194471-e657df975ab4?auto=format&fit=crop&w=1200&h=800&q=80",
                        1599,
                        "10 días / 9 noches",
                        4.8),
                new PaqueteViaje(6L,
                        "Maldivas en calma",
                        "Maldivas",
                        "Bungalow sobre el agua en régimen de todo incluido y snorkel.",
                        "https://images.unsplash.com/photo-1514282401047-d79a71a590e8?auto=format&fit=crop&w=1200&h=800&q=80",
                        2199,
                        "8 días / 7 noches",
                        5.0),
                new PaqueteViaje(7L,
                        "Lisboa con encanto",
                        "Lisboa, Portugal",
                        "Vuelo, hotel boutique y ruta de los miradores con guía local.",
                        "https://images.unsplash.com/photo-1555881400-74d7acaacd8b?auto=format&fit=crop&w=1200&h=800&q=80",
                        349,
                        "3 días / 2 noches",
                        4.6),
                new PaqueteViaje(8L,
                        "Estambul entre dos mundos",
                        "Estambul, Turquía",
                        "Vuelo, hotel 4★, crucero por el Bósforo y visita a Santa Sofía.",
                        "https://images.unsplash.com/photo-1524231757912-21f4fe3a7200?auto=format&fit=crop&w=1200&h=800&q=80",
                        689,
                        "5 días / 4 noches",
                        4.7)
        ));
    }

    private void cargarOpcionesResponsive() {
        opcionesResponsive = List.of(
                new ResponsiveOption("992px", 2, 1),
                new ResponsiveOption("640px", 1, 1)
        );
    }

    /**
     * El buscador todavía no está conectado a ningún servicio:
     * únicamente devuelve un mensaje informativo al usuario.
     */
    public void buscar() {
        StringBuilder mensaje = new StringBuilder("El buscador estará disponible próximamente.");

        if (ciudadSeleccionada != null) {
            mensaje.append(" Destino seleccionado: ").append(ciudadSeleccionada.getNombre()).append('.');
        }
        if (rangoFechas != null && !rangoFechas.isEmpty()) {
            if (rangoFechas.size() >= 2 && rangoFechas.get(1) != null) {
                mensaje.append(" Fechas: ")
                        .append(rangoFechas.get(0).format(FORMATO_FECHA))
                        .append(" - ")
                        .append(rangoFechas.get(1).format(FORMATO_FECHA))
                        .append('.');
            } else if (rangoFechas.get(0) != null) {
                mensaje.append(" Fecha de salida: ")
                        .append(rangoFechas.get(0).format(FORMATO_FECHA))
                        .append('.');
            }
        }

        anadirMensaje(FacesMessage.SEVERITY_INFO, "Buscador", mensaje.toString());
    }

    /**
     * Acción provisional del botón "Reservar" de cada tarjeta.
     */
    public String reservar(PaqueteViaje paquete) {
        if (paquete == null) {
            return null;
        }
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
                .put("paqueteSeleccionado", paquete);
        return "/detallePaquete.xhtml?faces-redirect=true";
    }

    private void anadirMensaje(FacesMessage.Severity severidad, String titulo, String detalle) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severidad, titulo, detalle));
    }

    public List<LocalDate> getRangoFechas() {
        return rangoFechas;
    }

    public void setRangoFechas(List<LocalDate> rangoFechas) {
        this.rangoFechas = rangoFechas;
    }

    public Ciudad getCiudadSeleccionada() {
        return ciudadSeleccionada;
    }

    public void setCiudadSeleccionada(Ciudad ciudadSeleccionada) {
        this.ciudadSeleccionada = ciudadSeleccionada;
    }

    public List<Ciudad> getCiudades() {
        return ciudades;
    }

    public List<PaqueteViaje> getOfertas() {
        return ofertas;
    }

    public List<PaqueteViaje> getTopDestinos() {
        return topDestinos;
    }

    public List<ResponsiveOption> getOpcionesResponsive() {
        return opcionesResponsive;
    }
}
