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
                        "/resources/images/paris.svg",
                        749,
                        "5 días / 4 noches",
                        4.8),
                new PaqueteViaje(2L,
                        "Roma imperial",
                        "Roma, Italia",
                        "Vuelo, hotel 4★ y visita guiada al Coliseo con entrada sin colas.",
                        "/resources/images/roma.svg",
                        529,
                        "4 días / 3 noches",
                        4.7),
                new PaqueteViaje(3L,
                        "Nueva York esencial",
                        "Nueva York, EE. UU.",
                        "Vuelo directo, hotel en Manhattan y pase turístico de 3 días.",
                        "/resources/images/nueva-york.svg",
                        1249,
                        "7 días / 6 noches",
                        4.9),
                new PaqueteViaje(4L,
                        "Tokio y Monte Fuji",
                        "Tokio, Japón",
                        "Vuelo, hotel 4★, JR Pass de 7 días y excursión al Monte Fuji.",
                        "/resources/images/tokio.svg",
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
                        "/resources/images/bali.svg",
                        1599,
                        "10 días / 9 noches",
                        4.8),
                new PaqueteViaje(6L,
                        "Maldivas en calma",
                        "Maldivas",
                        "Bungalow sobre el agua en régimen de todo incluido y snorkel.",
                        "/resources/images/maldivas.svg",
                        2199,
                        "8 días / 7 noches",
                        5.0),
                new PaqueteViaje(7L,
                        "Lisboa con encanto",
                        "Lisboa, Portugal",
                        "Vuelo, hotel boutique y ruta de los miradores con guía local.",
                        "/resources/images/lisboa.svg",
                        349,
                        "3 días / 2 noches",
                        4.6),
                new PaqueteViaje(8L,
                        "Estambul entre dos mundos",
                        "Estambul, Turquía",
                        "Vuelo, hotel 4★, crucero por el Bósforo y visita a Santa Sofía.",
                        "/resources/images/estambul.svg",
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
    public void reservar(PaqueteViaje paquete) {
        if (paquete == null) {
            return;
        }
        anadirMensaje(FacesMessage.SEVERITY_INFO,
                "Reserva",
                "Has seleccionado \"" + paquete.getTitulo() + "\" por "
                        + paquete.getPrecioFormateado()
                        + ". La reserva online estará disponible próximamente.");
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
