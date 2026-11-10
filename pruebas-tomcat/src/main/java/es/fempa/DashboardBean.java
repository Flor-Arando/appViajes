package es.fempa;

import es.fempa.modelo.Ciudad;
import es.fempa.modelo.PaqueteViaje;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import java.util.stream.Collectors;
import java.time.temporal.ChronoUnit;
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
@SessionScoped
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

    @Inject
    private PaquetesBean paquetesBean;
    private List<PaquetesBean.Paquete> paquetesEncontrados = new ArrayList<>();

    private String paisSeleccionado = "";

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
    public String buscar() {

        // Comprobar que se ha seleccionado al menos un país.
        if (paisSeleccionado == null || paisSeleccionado.isBlank()) {

            anadirMensaje(
                    FacesMessage.SEVERITY_WARN,
                    "Búsqueda incompleta",
                    "Selecciona al menos un país de destino."
            );

            return null;
        }

        // Comprobar las fechas.
        if (rangoFechas == null
                || rangoFechas.size() < 2
                || rangoFechas.get(0) == null
                || rangoFechas.get(1) == null) {

            anadirMensaje(
                    FacesMessage.SEVERITY_WARN,
                    "Búsqueda incompleta",
                    "Selecciona una fecha de salida y una fecha de regreso."
            );

            return null;
        }

        LocalDate fechaSalida = rangoFechas.get(0);
        LocalDate fechaRegreso = rangoFechas.get(1);

        // Comprobar que el rango de fechas sea válido.
        if (fechaRegreso.isBefore(fechaSalida)
                || fechaRegreso.isEqual(fechaSalida)) {

            anadirMensaje(
                    FacesMessage.SEVERITY_WARN,
                    "Fechas incorrectas",
                    "La fecha de regreso debe ser posterior a la fecha de salida."
            );

            return null;
        }

        // Calcular la duración disponible.
        long diasDisponibles = ChronoUnit.DAYS.between(
                fechaSalida,
                fechaRegreso
        );

        // Obtener los paquetes que coinciden con la búsqueda.
        paquetesEncontrados = paquetesBean.getTodosLosPaquetes()
                .stream()

                // Filtrar por el país seleccionado.
                .filter(paquete ->
                        paisSeleccionado.equalsIgnoreCase(paquete.getPais())
                )

                // Filtrar por duración.
                .filter(paquete ->
                        paquete.getDuracion() <= diasDisponibles
                )

                .collect(Collectors.toList());

        // Ir a la página de resultados.
        return "busqueda?faces-redirect=true";
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

    public List<PaquetesBean.Paquete> getPaquetesEncontrados() {
        return paquetesEncontrados;
    }

    public String getFechaSalidaFormateada() {

        if (rangoFechas == null
                || rangoFechas.isEmpty()
                || rangoFechas.get(0) == null) {
            return "";
        }

        return rangoFechas.get(0).format(FORMATO_FECHA);
    }

    public String getFechaVueltaFormateada() {

        if (rangoFechas == null
                || rangoFechas.size() < 2
                || rangoFechas.get(1) == null) {
            return "";
        }

        return rangoFechas.get(1).format(FORMATO_FECHA);
    }

    public String getPaisSeleccionado() {
        return paisSeleccionado;
    }

    public void setPaisSeleccionado(String paisSeleccionado) {
        this.paisSeleccionado = paisSeleccionado;
    }

    public List<String> getPaisesDisponibles() {
        return paquetesBean.getPaisesDisponibles();
    }

    public String getPaisSeleccionadoTexto() {
        if (paisSeleccionado == null || paisSeleccionado.isBlank()) {
            return "—";
        }

        return paisSeleccionado;
    }

    public void reservar(PaquetesBean.Paquete paquete) {

        if (paquete == null) {
            return;
        }

        anadirMensaje(
                FacesMessage.SEVERITY_INFO,
                "Reserva",
                "Has seleccionado \"" + paquete.getNombre()
                        + "\" por " + paquete.getPrecio()
                        + ". La reserva online estará disponible próximamente."
        );
    }
}
