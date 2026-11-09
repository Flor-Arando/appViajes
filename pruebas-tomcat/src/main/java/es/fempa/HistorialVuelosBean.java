package es.fempa;

import jakarta.faces.context.FacesContext;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.io.Serializable;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Named
@ViewScoped
public class HistorialVuelosBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final DateTimeFormatter FECHA = DateTimeFormatter.ofPattern("d 'de' MMMM 'de' yyyy", Locale.forLanguageTag("es-ES"));

    private List<Vuelo> vuelos;
    private String busqueda = "";
    private String continente = "todos";
    private String estado = "todos";
    private String fechaDesde = "";
    private String fechaHasta = "";
    private Vuelo vueloSeleccionado;

    @PostConstruct
    public void inicializar() {
        vuelos = crearVuelos();
    }

    public List<Vuelo> getVuelosFiltrados() {
        String consulta = busqueda == null ? "" : busqueda.trim().toLowerCase(Locale.ROOT);
        return vuelos.stream()
                .filter(v -> consulta.isEmpty() || (v.getDestino() + " " + v.getCiudad() + " " + v.getPais() + " " + v.getAerolinea()).toLowerCase(Locale.ROOT).contains(consulta))
                .filter(v -> "todos".equals(continente) || Objects.equals(continente, v.getContinente()))
                .filter(v -> "todos".equals(estado) || Objects.equals(estado, v.getEstado()))
                .filter(v -> fechaDesde == null || fechaDesde.isBlank() || v.getFecha().compareTo(fechaDesde) >= 0)
                .filter(v -> fechaHasta == null || fechaHasta.isBlank() || v.getFecha().compareTo(fechaHasta) <= 0)
                .toList();
    }

    public void limpiarFiltros() {
        busqueda = "";
        continente = "todos";
        estado = "todos";
        fechaDesde = "";
        fechaHasta = "";
    }

    public void cancelar(Vuelo vuelo) {
        if (vuelo != null && ("pendiente".equals(vuelo.getEstado()) || "confirmado".equals(vuelo.getEstado()))) {
            vuelo.setEstado("cancelado");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_INFO, "Vuelo cancelado", vuelo.getDestino()));
        }
    }

    public void seleccionarContinente(String continente) {
        this.continente = continente;
    }

    public void mostrarDetalles(Vuelo vuelo) {
        vueloSeleccionado = vuelo;
    }

    public void descargar(Vuelo vuelo) throws IOException {
        if (vuelo == null) {
            return;
        }
        String contenido = "Vuelo: " + vuelo.getOrigen() + " → " + vuelo.getDestino()
                + "\nDestino: " + vuelo.getCiudad() + ", " + vuelo.getPais()
                + "\nFecha: " + formatearFecha(vuelo.getFecha())
                + "\nAerolínea: " + vuelo.getAerolinea()
                + "\nEstado: " + vuelo.getEstado()
                + "\nPrecio: " + vuelo.getPrecio() + " €";
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
        String nombre = URLEncoder.encode("itinerario-" + vuelo.getDestino() + ".txt", StandardCharsets.UTF_8).replace("+", "%20");
        response.reset();
        response.setContentType("text/plain; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + nombre);
        response.getOutputStream().write(contenido.getBytes(StandardCharsets.UTF_8));
        response.getOutputStream().flush();
        context.responseComplete();
    }

    public String formatearFecha(String fecha) {
        return fecha == null || fecha.isBlank() ? "Fecha sin confirmar" : LocalDate.parse(fecha).format(FECHA);
    }


    public int getTotalVuelos() { return vuelos.size(); }
    public int getTotalFiltrados() { return getVuelosFiltrados().size(); }
    public boolean isHistorialVacio() { return vuelos.isEmpty(); }

    public String getBusqueda() { return busqueda; }
    public void setBusqueda(String busqueda) { this.busqueda = busqueda; }
    public String getContinente() { return continente; }
    public void setContinente(String continente) { this.continente = continente; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public String getFechaDesde() { return fechaDesde; }
    public void setFechaDesde(String fechaDesde) { this.fechaDesde = fechaDesde; }
    public String getFechaHasta() { return fechaHasta; }
    public void setFechaHasta(String fechaHasta) { this.fechaHasta = fechaHasta; }
    public Vuelo getVueloSeleccionado() { return vueloSeleccionado; }

    private static List<Vuelo> crearVuelos() {
        List<Vuelo> lista = new ArrayList<>();
        lista.add(new Vuelo(1, "Madrid", "Roma", "Roma", "Italia", "europa", "2026-12-15", "confirmado", "Iberia", 180, "https://images.unsplash.com/photo-1552832230-c0197dd311b5?auto=format&fit=crop&w=800&q=80"));
        lista.add(new Vuelo(2, "Madrid", "Tokio", "Tokio", "Japón", "asia", "2027-01-20", "pendiente", "Air Europa", 740, "https://images.unsplash.com/photo-1540959733332-eab4deabeeaf?auto=format&fit=crop&w=800&q=80"));
        lista.add(new Vuelo(3, "Madrid", "Nueva York", "Nueva York", "EE. UU.", "norteamerica", "2026-11-10", "confirmado", "American Airlines", 520, "https://images.unsplash.com/photo-1496442226666-8d4d0e62e6e9?auto=format&fit=crop&w=800&q=80"));
        lista.add(new Vuelo(4, "Madrid", "Cusco", "Cusco", "Perú", "sudamerica", "2026-09-05", "completado", "LATAM Airlines", 680, "https://images.unsplash.com/photo-1526392060635-9d6019884377?auto=format&fit=crop&w=800&q=80"));
        lista.add(new Vuelo(5, "Madrid", "Sídney", "Sídney", "Australia", "oceania", "2027-03-12", "pendiente", "Qatar Airways", 920, "https://images.unsplash.com/photo-1506973035872-a4ec16b8e8d9?auto=format&fit=crop&w=800&q=80"));
        lista.add(new Vuelo(6, "Madrid", "El Cairo", "El Cairo", "Egipto", "africa", "2026-08-18", "cancelado", "Ryanair", 430, "https://images.unsplash.com/photo-1539650116574-8efeb43e2750?auto=format&fit=crop&w=800&q=80"));
        lista.add(new Vuelo(7, "Madrid", "Lisboa", "Lisboa", "Portugal", "europa", "2026-08-19", "completado", "TAP Air Portugal", 95, "https://images.unsplash.com/photo-1555881400-74d7acaacd8b?auto=format&fit=crop&w=800&q=80"));
        lista.add(new Vuelo(8, "Madrid", "París", "París", "Francia", "europa", "2026-03-19", "completado", "Air France", 160, "https://images.unsplash.com/photo-1502602898657-3e91760cbb34?auto=format&fit=crop&w=800&q=80"));
        lista.add(new Vuelo(9, "Madrid", "Ámsterdam", "Ámsterdam", "Países Bajos", "europa", "2025-08-19", "completado", "KLM", 190, "https://images.unsplash.com/photo-1512470876302-972faa2aa9a4?auto=format&fit=crop&w=800&q=80"));
        lista.add(new Vuelo(10, "Madrid", "Tenerife", "Tenerife", "España", "europa", "2024-12-19", "completado", "Iberia Express", 120, "https://images.unsplash.com/photo-1667930578642-42b99abf25f5?auto=format&fit=crop&w=800&q=80"));
        lista.add(new Vuelo(11, "Madrid", "Barcelona", "Barcelona", "España", "europa", "2024-04-19", "completado", "Vueling", 85, "https://images.unsplash.com/photo-1578095172812-dcc191c5aed8?auto=format&fit=crop&w=800&q=80"));
        lista.add(new Vuelo(12, "Madrid", "Londres", "Londres", "Reino Unido", "europa", "2023-10-19", "completado", "Ryanair", 210, "https://images.unsplash.com/photo-1513635269975-59663e0ac1ad?auto=format&fit=crop&w=800&q=80"));
        lista.add(new Vuelo(13, "Madrid", "Nueva York", "Nueva York", "Estados Unidos", "norteamerica", "2022-10-19", "completado", "Iberia", 520, "https://images.unsplash.com/photo-1496442226666-8d4d0e62e6e9?auto=format&fit=crop&w=800&q=80"));
        lista.add(new Vuelo(14, "Madrid", "Sevilla", "Sevilla", "España", "europa", "2021-10-19", "completado", "Vueling", 75, "https://images.unsplash.com/photo-1539037116277-4db20889f2d4?auto=format&fit=crop&w=800&q=80"));
        lista.add(new Vuelo(15, "Madrid", "Ecuador", "Parque Nacional Cotopaxi", "Ecuador", "sudamerica", "2024-06-19", "completado", "Air Europa", 890, "https://images.unsplash.com/photo-1719451015082-ac0c6a18bf73?auto=format&fit=crop&w=800&q=80"));
        return lista;
    }

    public static class Vuelo implements Serializable {
        private static final long serialVersionUID = 1L;
        private final int id;
        private final String origen, destino, ciudad, pais, continente, fecha, aerolinea, imagen;
        private String estado;
        private final int precio;

        public Vuelo(int id, String origen, String destino, String ciudad, String pais, String continente, String fecha, String estado, String aerolinea, int precio, String imagen) {
            this.id = id; this.origen = origen; this.destino = destino; this.ciudad = ciudad; this.pais = pais;
            this.continente = continente; this.fecha = fecha; this.estado = estado; this.aerolinea = aerolinea;
            this.precio = precio; this.imagen = imagen;
        }
        public int getId() { return id; }
        public String getOrigen() { return origen; }
        public String getDestino() { return destino; }
        public String getCiudad() { return ciudad; }
        public String getPais() { return pais; }
        public String getContinente() { return continente; }
        public String getFecha() { return fecha; }
        public String getEstado() { return estado; }
        public void setEstado(String estado) { this.estado = estado; }
        public String getAerolinea() { return aerolinea; }
        public int getPrecio() { return precio; }
        public String getImagen() { return imagen; }
    }
}
