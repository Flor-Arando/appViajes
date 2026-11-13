package es.fempa;

import es.fempa.modelo.PaqueteViaje;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Catálogo de paquetes de viaje de toda la aplicación.
 *
 * @ApplicationScoped: los cambios que haga el administrador (crear, editar,
 * borrar) se ven en el dashboard y la búsqueda de inmediato, mientras la
 * aplicación esté arrancada.
 */
@Named("paquetesStore")
@ApplicationScoped
public class PaquetesStore implements Serializable {

    private static final long serialVersionUID = 1L;

    private final List<PaqueteViaje> paquetes = new ArrayList<>();
    private long siguienteId = 1L;

    public PaquetesStore() {
        // Catálogo inicial de ejemplo.
        add(new PaqueteViaje(null, "París romántico", "París, Francia",
                "Vuelo, hotel 4★ en el centro y desayuno incluido. Paseo en barco por el Sena.",
                "https://images.unsplash.com/photo-1502602898657-3e91760cbb34?auto=format&fit=crop&w=1200&h=800&q=80",
                749, "5 días / 4 noches", 4.8));
        add(new PaqueteViaje(null, "Roma imperial", "Roma, Italia",
                "Vuelo, hotel 4★ y visita guiada al Coliseo con entrada sin colas.",
                "https://images.unsplash.com/photo-1552832230-c0197dd311b5?auto=format&fit=crop&w=1200&h=800&q=80",
                529, "4 días / 3 noches", 4.7));
        add(new PaqueteViaje(null, "Nueva York esencial", "Nueva York, EE. UU.",
                "Vuelo directo, hotel en Manhattan y pase turístico de 3 días.",
                "https://images.unsplash.com/photo-1496442226666-8d4d0e62e6e9?auto=format&fit=crop&w=1200&h=800&q=80",
                1249, "7 días / 6 noches", 4.9));
        add(new PaqueteViaje(null, "Tokio y Monte Fuji", "Tokio, Japón",
                "Vuelo, hotel 4★, JR Pass de 7 días y excursión al Monte Fuji.",
                "https://images.unsplash.com/photo-1540959733332-eab4deabeeaf?auto=format&fit=crop&w=1200&h=800&q=80",
                1899, "9 días / 8 noches", 4.9));
        add(new PaqueteViaje(null, "Bali, isla de los dioses", "Bali, Indonesia",
                "Villa con piscina privada, desayuno y traslados incluidos.",
                "https://images.unsplash.com/photo-1537996194471-e657df975ab4?auto=format&fit=crop&w=1200&h=800&q=80",
                1599, "10 días / 9 noches", 4.8));
        add(new PaqueteViaje(null, "Maldivas en calma", "Maldivas",
                "Bungalow sobre el agua en régimen de todo incluido y snorkel.",
                "https://images.unsplash.com/photo-1514282401047-d79a71a590e8?auto=format&fit=crop&w=1200&h=800&q=80",
                2199, "8 días / 7 noches", 5.0));
        add(new PaqueteViaje(null, "Lisboa con encanto", "Lisboa, Portugal",
                "Vuelo, hotel boutique y ruta de los miradores con guía local.",
                "https://images.unsplash.com/photo-1555881400-74d7acaacd8b?auto=format&fit=crop&w=1200&h=800&q=80",
                349, "3 días / 2 noches", 4.6));
        add(new PaqueteViaje(null, "Estambul entre dos mundos", "Estambul, Turquía",
                "Vuelo, hotel 4★, crucero por el Bósforo y visita a Santa Sofía.",
                "https://images.unsplash.com/photo-1524231757912-21f4fe3a7200?auto=format&fit=crop&w=1200&h=800&q=80",
                689, "5 días / 4 noches", 4.7));
    }

    private static final int TAMANO_OFERTAS = 4;

    public List<PaqueteViaje> getPaquetes() {
        return paquetes;
    }

    /** Las primeras ofertas que se muestran en el carrusel del dashboard. */
    public List<PaqueteViaje> getOfertas() {
        int fin = Math.min(TAMANO_OFERTAS, paquetes.size());
        return new ArrayList<>(paquetes.subList(0, fin));
    }

    /** El resto de paquetes que se muestran como destinos destacados. */
    public List<PaqueteViaje> getTopDestinos() {
        int inicio = Math.min(TAMANO_OFERTAS, paquetes.size());
        return new ArrayList<>(paquetes.subList(inicio, paquetes.size()));
    }

    public void add(PaqueteViaje paquete) {
        if (paquete == null) {
            return;
        }
        if (paquete.getId() == null) {
            paquete.setId(siguienteId++);
        } else if (paquete.getId() >= siguienteId) {
            siguienteId = paquete.getId() + 1;
        }
        paquetes.add(paquete);
    }

    public void update(PaqueteViaje paquete) {
        if (paquete == null || paquete.getId() == null) {
            return;
        }
        for (int i = 0; i < paquetes.size(); i++) {
            if (paquete.getId().equals(paquetes.get(i).getId())) {
                paquetes.set(i, paquete);
                return;
            }
        }
    }

    public boolean eliminar(Long id) {
        if (id == null) {
            return false;
        }
        return paquetes.removeIf(p -> id.equals(p.getId()));
    }

    public PaqueteViaje buscar(Long id) {
        if (id == null) {
            return null;
        }
        for (PaqueteViaje p : paquetes) {
            if (id.equals(p.getId())) {
                return p;
            }
        }
        return null;
    }
}
