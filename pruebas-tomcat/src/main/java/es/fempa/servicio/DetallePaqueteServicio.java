package es.fempa.servicio;

import es.fempa.modelo.DetallePaquete;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class DetallePaqueteServicio implements Serializable {

    private static final long serialVersionUID = 1L;

    private final Map<Integer, DetallePaquete> catalogoDetalles = new HashMap<>();

    @PostConstruct
    public void init() {
        catalogoDetalles.put(1, crearDetalle(
            "https://images.unsplash.com/photo-1502602898657-3e91760cbb34?auto=format&fit=crop&w=1800&q=85",
            new String[] {
                "https://images.unsplash.com/photo-1752887634161-9cb2c287d3f6?auto=format&fit=crop&w=1400&q=85",
                "https://images.unsplash.com/photo-1503917988258-f87a78e3c995?auto=format&fit=crop&w=1400&q=85",
                "https://images.unsplash.com/photo-1499856871958-5b9627545d1a?auto=format&fit=crop&w=1400&q=85"
            },
            "Hotel 4 estrellas en el centro de París",
            new String[] {"Vuelos de ida y vuelta", "4 noches con desayuno", "Paseo en barco por el Sena"},
            new String[] {"Día 1: llegada y paseo por el centro", "Día 2: Île de la Cité y barrio Latino", "Día 3: Louvre y jardines", "Día 4: Montmartre y paseo por el Sena", "Día 5: desayuno y regreso"}
        ));

        catalogoDetalles.put(2, crearDetalle(
            "https://images.unsplash.com/photo-1572319805438-61eeede8e74d?auto=format&fit=crop&w=1800&q=85",
            new String[] {
                "https://images.unsplash.com/photo-1468435005930-958f6107dccd?auto=format&fit=crop&w=1400&q=85",
                "https://images.unsplash.com/photo-1525874684015-58379d421a52?auto=format&fit=crop&w=1400&q=85",
                "https://images.unsplash.com/photo-1791639307440-2879519ec0a3?auto=format&fit=crop&w=1400&q=85"
            },
            "Hotel 4 estrellas en una zona céntrica y bien comunicada",
            new String[] {"Vuelos de ida y vuelta", "3 noches en hotel 4★", "Visita guiada al Coliseo con entrada sin colas"},
            new String[] {
                "Día 1 · Llegada y primer paseo: traslado al hotel y recorrido tranquilo por Piazza Navona, el Panteón y Campo de' Fiori.",
                "Día 2 · La Roma antigua: visita guiada incluida al Coliseo y paseo por el Foro Romano y el monte Palatino.",
                "Día 3 · Vaticano y Trastevere: descubre la Plaza de San Pedro y termina la tarde entre las calles y plazas de Trastevere.",
                "Día 4 · Despedida: desayuno, salida del hotel y traslado al aeropuerto para el vuelo de regreso."
            }
        ));

        catalogoDetalles.put(3, crearDetalle(
            "https://images.unsplash.com/photo-1602196426252-abb226dcd1b4?auto=format&fit=crop&w=1800&q=85",
            new String[] {
                "https://images.unsplash.com/photo-1640290970426-fe6a132df9fc?auto=format&fit=crop&w=1400&q=85",
                "https://images.unsplash.com/photo-1770461846361-be22b53359dc?auto=format&fit=crop&w=1400&q=85",
                "https://images.unsplash.com/photo-1770872571594-de1f3202337d?auto=format&fit=crop&w=1400&q=85"
            },
            "Hotel 4 estrellas en Manhattan",
            new String[] {"Vuelos de ida y vuelta", "6 noches en Manhattan", "Pase turístico de 3 días"},
            new String[] {"Día 1: llegada a Manhattan", "Día 2: Midtown y mirador", "Día 3: Central Park y museos", "Días 4 y 5: barrios de Nueva York", "Días 6 y 7: tiempo libre y regreso"}
        ));

        catalogoDetalles.put(4, crearDetalle(
            "https://images.unsplash.com/photo-1540959733332-eab4deabeeaf?auto=format&fit=crop&w=1800&q=85",
            new String[] {
                "https://images.unsplash.com/photo-1773841040156-0df2a89b7560?auto=format&fit=crop&w=1400&q=85",
                "https://images.unsplash.com/photo-1493976040374-85c8e12f0c0e?auto=format&fit=crop&w=1400&q=85",
                "https://images.unsplash.com/photo-1526481280693-3bfa7568e0f3?auto=format&fit=crop&w=1400&q=85"
            },
            "Hotel 4 estrellas en Shinjuku, Tokio",
            new String[] {"Vuelos de ida y vuelta", "8 noches de hotel", "JR Pass y excursión al Monte Fuji"},
            new String[] {"Días 1 y 2: llegada y barrios de Tokio", "Día 3: excursión al Monte Fuji", "Días 4 y 5: Kioto y sus templos", "Días 6 y 7: Osaka", "Días 8 y 9: Tokio y regreso"}
        ));

        catalogoDetalles.put(5, crearDetalle(
            "https://images.unsplash.com/photo-1537996194471-e657df975ab4?auto=format&fit=crop&w=1800&q=85",
            new String[] {
                "https://images.unsplash.com/photo-1557093793-d149a38a1be8?auto=format&fit=crop&w=1400&q=85",
                "https://images.unsplash.com/photo-1539367628448-4bc5c9d171c8?auto=format&fit=crop&w=1400&q=85",
                "https://images.unsplash.com/photo-1537996194471-e657df975ab4?auto=format&fit=crop&w=1400&q=85"
            },
            "Villa privada con piscina en Bali",
            new String[] {"Vuelos de ida y vuelta", "9 noches en villa", "Desayuno y traslados"},
            new String[] {"Día 1: llegada a Bali", "Días 2 a 4: Ubud y sus templos", "Días 5 a 7: arrozales y costa", "Días 8 a 10: descanso y regreso"}
        ));

        catalogoDetalles.put(6, crearDetalle(
            "https://images.unsplash.com/photo-1698879714255-9b5d2953ed46?auto=format&fit=crop&w=1800&q=85",
            new String[] {
                "https://images.unsplash.com/photo-1614505241498-80a3ec936595?auto=format&fit=crop&w=1400&q=85",
                "https://images.unsplash.com/photo-1514282401047-d79a71a590e8?auto=format&fit=crop&w=1400&q=85",
                "https://images.unsplash.com/photo-1573843981267-be1999ff37cd?auto=format&fit=crop&w=1400&q=85"
            },
            "Bungalow sobre el agua en Maldivas",
            new String[] {"Vuelos de ida y vuelta", "7 noches en bungalow", "Todo incluido y snorkel"},
            new String[] {"Día 1: llegada y traslado en lancha", "Días 2 y 3: playa y snorkel", "Días 4 a 6: descanso y actividades acuáticas", "Días 7 y 8: mañana libre y regreso"}
        ));

        catalogoDetalles.put(7, crearDetalle(
            "https://images.unsplash.com/photo-1682447448492-682010151fc5?auto=format&fit=crop&w=1800&q=85",
            new String[] {
                "https://images.unsplash.com/photo-1759352586318-207e03630eec?auto=format&fit=crop&w=1400&q=85",
                "https://images.unsplash.com/photo-1555881400-74d7acaacd8b?auto=format&fit=crop&w=1400&q=85",
                "https://images.unsplash.com/photo-1555881400-69dd4b130386?auto=format&fit=crop&w=1400&q=85"
            },
            "Hotel boutique en el centro de Lisboa",
            new String[] {"Vuelos de ida y vuelta", "2 noches en hotel boutique", "Ruta guiada por los miradores"},
            new String[] {"Día 1: Baixa, Chiado y paseo junto al Tajo", "Día 2: Alfama y ruta de los miradores", "Día 3: desayuno y regreso"}
        ));

        catalogoDetalles.put(8, crearDetalle(
            "https://images.unsplash.com/photo-1524231757912-21f4fe3a7200?auto=format&fit=crop&w=1800&q=85",
            new String[] {
                "https://images.unsplash.com/photo-1772550018917-506fcaa1848f?auto=format&fit=crop&w=1400&q=85",
                "https://images.unsplash.com/photo-1541432901042-2d8bd64b4a9b?auto=format&fit=crop&w=1400&q=85",
                "https://images.unsplash.com/photo-1524231757912-21f4fe3a7200?auto=format&fit=crop&w=1400&q=85"
            },
            "Hotel 4 estrellas en Sultanahmet, Estambul",
            new String[] {"Vuelos de ida y vuelta", "4 noches en hotel", "Crucero por el Bósforo y visita a Santa Sofía"},
            new String[] {"Día 1: llegada y paseo por Sultanahmet", "Día 2: Santa Sofía y Mezquita Azul", "Día 3: Gran Bazar y crucero por el Bósforo", "Días 4 y 5: tiempo libre y regreso"}
        ));
    }

    private DetallePaquete crearDetalle(String imagen, String[] fotos, String hotel, String[] incluidos, String[] itinerario) {
        DetallePaquete d = new DetallePaquete();
        d.setImagen(imagen);
        d.setFotos(fotos);
        d.setHotel(hotel);
        d.setIncluidos(incluidos);
        d.setItinerario(itinerario);
        return d;
    }

    public DetallePaquete buscarPorId(int id) {
        return catalogoDetalles.get(id);
    }
}
