package es.fempa;
import es.fempa.modelo.DetallePaquete;
import es.fempa.modelo.PaqueteViaje;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;



@Named("detallePaqueteBean")
@RequestScoped
public class DetallePaqueteBean {

    private DetallePaquete detalle;

    @PostConstruct
    public void cargarPaquete() {
        detalle = new DetallePaquete();

        Object seleccionado = FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap().remove("paqueteSeleccionado");

        if (seleccionado instanceof PaqueteViaje) {
            PaqueteViaje paquete = (PaqueteViaje) seleccionado;
            detalle.setPaquete(paquete);
            cargarDatosAdicionales(paquete.getId().intValue());
        }
    }

    private void cargarDatosAdicionales(int id) {
        if (id == 1) {
            detalle.setImagen("https://images.unsplash.com/photo-1502602898657-3e91760cbb34?auto=format&fit=crop&w=1800&q=85");
            detalle.setFotos(new String[] {
                "https://images.unsplash.com/photo-1752887634161-9cb2c287d3f6?auto=format&fit=crop&w=1400&q=85",
                "https://images.unsplash.com/photo-1503917988258-f87a78e3c995?auto=format&fit=crop&w=1400&q=85",
                "https://images.unsplash.com/photo-1499856871958-5b9627545d1a?auto=format&fit=crop&w=1400&q=85"
            });
            detalle.setHotel("Hotel 4 estrellas en el centro de París");
            detalle.setIncluidos(new String[] {"Vuelos de ida y vuelta", "4 noches con desayuno", "Paseo en barco por el Sena"});
            detalle.setItinerario(new String[] {"Día 1: llegada y paseo por el centro", "Día 2: Île de la Cité y barrio Latino", "Día 3: Louvre y jardines", "Día 4: Montmartre y paseo por el Sena", "Día 5: desayuno y regreso"});
            
        } else if (id == 2) {
            detalle.setImagen("https://images.unsplash.com/photo-1572319805438-61eeede8e74d?auto=format&fit=crop&w=1800&q=85");
            detalle.setFotos(new String[] {
                "https://images.unsplash.com/photo-1468435005930-958f6107dccd?auto=format&fit=crop&w=1400&q=85",
                "https://images.unsplash.com/photo-1525874684015-58379d421a52?auto=format&fit=crop&w=1400&q=85",
                "https://images.unsplash.com/photo-1791639307440-2879519ec0a3?auto=format&fit=crop&w=1400&q=85"
            });
            detalle.setHotel("Hotel 4★ en una zona céntrica y bien comunicada");
            detalle.setIncluidos(new String[] {"Vuelos de ida y vuelta", "3 noches en hotel 4★", "Visita guiada al Coliseo con entrada sin colas"});
            detalle.setItinerario(new String[] {
                "Día 1 · Llegada y primer paseo: traslado al hotel y recorrido tranquilo por Piazza Navona, el Panteón y Campo de' Fiori.",
                "Día 2 · La Roma antigua: visita guiada incluida al Coliseo y paseo por el Foro Romano y el monte Palatino.",
                "Día 3 · Vaticano y Trastevere: descubre la Plaza de San Pedro y termina la tarde entre las calles y plazas de Trastevere.",
                "Día 4 · Despedida: desayuno, salida del hotel y traslado al aeropuerto para el vuelo de regreso."
            });
        } else if (id == 3) {
            detalle.setImagen("https://images.unsplash.com/photo-1602196426252-abb226dcd1b4?auto=format&fit=crop&w=1800&q=85");
            detalle.setFotos(new String[] {
                "https://images.unsplash.com/photo-1640290970426-fe6a132df9fc?auto=format&fit=crop&w=1400&q=85",
                "https://images.unsplash.com/photo-1770461846361-be22b53359dc?auto=format&fit=crop&w=1400&q=85",
                "https://images.unsplash.com/photo-1770872571594-de1f3202337d?auto=format&fit=crop&w=1400&q=85"
            });
            detalle.setHotel("Hotel 4 estrellas en Manhattan");
            detalle.setIncluidos(new String[] {"Vuelos de ida y vuelta", "6 noches en Manhattan", "Pase turístico de 3 días"});
            detalle.setItinerario(new String[] {"Día 1: llegada a Manhattan", "Día 2: Midtown y mirador", "Día 3: Central Park y museos", "Días 4 y 5: barrios de Nueva York", "Días 6 y 7: tiempo libre y regreso"});
        } else if (id == 4) {
            detalle.setImagen("https://images.unsplash.com/photo-1540959733332-eab4deabeeaf?auto=format&fit=crop&w=1800&q=85");
            detalle.setFotos(new String[] {
                "https://images.unsplash.com/photo-1773841040156-0df2a89b7560?auto=format&fit=crop&w=1400&q=85",
                "https://images.unsplash.com/photo-1493976040374-85c8e12f0c0e?auto=format&fit=crop&w=1400&q=85",
                "https://images.unsplash.com/photo-1526481280693-3bfa7568e0f3?auto=format&fit=crop&w=1400&q=85"
            });
            detalle.setHotel("Hotel 4 estrellas en Shinjuku, Tokio");
            detalle.setIncluidos(new String[] {"Vuelos de ida y vuelta", "8 noches de hotel", "JR Pass y excursión al Monte Fuji"});
            detalle.setItinerario(new String[] {"Días 1 y 2: llegada y barrios de Tokio", "Día 3: excursión al Monte Fuji", "Días 4 y 5: Kioto y sus templos", "Días 6 y 7: Osaka", "Días 8 y 9: Tokio y regreso"});
        } else if (id == 5) {
            detalle.setImagen("https://images.unsplash.com/photo-1537996194471-e657df975ab4?auto=format&fit=crop&w=1800&q=85");
            detalle.setFotos(new String[] {
                "https://images.unsplash.com/photo-1557093793-d149a38a1be8?auto=format&fit=crop&w=1400&q=85",
                "https://images.unsplash.com/photo-1539367628448-4bc5c9d171c8?auto=format&fit=crop&w=1400&q=85",
                "https://images.unsplash.com/photo-1537996194471-e657df975ab4?auto=format&fit=crop&w=1400&q=85"
            });
            detalle.setHotel("Villa privada con piscina en Bali");
            detalle.setIncluidos(new String[] {"Vuelos de ida y vuelta", "9 noches en villa", "Desayuno y traslados"});
            detalle.setItinerario(new String[] {"Día 1: llegada a Bali", "Días 2 a 4: Ubud y sus templos", "Días 5 a 7: arrozales y costa", "Días 8 a 10: descanso y regreso"});
        } else if (id == 6) {
            detalle.setImagen("https://images.unsplash.com/photo-1698879714255-9b5d2953ed46?auto=format&fit=crop&w=1800&q=85");
            detalle.setFotos(new String[] {
                "https://images.unsplash.com/photo-1614505241498-80a3ec936595?auto=format&fit=crop&w=1400&q=85",
                "https://images.unsplash.com/photo-1514282401047-d79a71a590e8?auto=format&fit=crop&w=1400&q=85",
                "https://images.unsplash.com/photo-1573843981267-be1999ff37cd?auto=format&fit=crop&w=1400&q=85"
            });
            detalle.setHotel("Bungalow sobre el agua en Maldivas");
            detalle.setIncluidos(new String[] {"Vuelos de ida y vuelta", "7 noches en bungalow", "Todo incluido y snorkel"});
            detalle.setItinerario(new String[] {"Día 1: llegada y traslado en lancha", "Días 2 y 3: playa y snorkel", "Días 4 a 6: descanso y actividades acuáticas", "Días 7 y 8: mañana libre y regreso"});
        } else if (id == 7) {
            detalle.setImagen("https://images.unsplash.com/photo-1682447448492-682010151fc5?auto=format&fit=crop&w=1800&q=85");
            detalle.setFotos(new String[] {
                "https://images.unsplash.com/photo-1759352586318-207e03630eec?auto=format&fit=crop&w=1400&q=85",
                "https://images.unsplash.com/photo-1555881400-74d7acaacd8b?auto=format&fit=crop&w=1400&q=85",
                "https://images.unsplash.com/photo-1555881400-69dd4b130386?auto=format&fit=crop&w=1400&q=85"
            });
            detalle.setHotel("Hotel boutique en el centro de Lisboa");
            detalle.setIncluidos(new String[] {"Vuelos de ida y vuelta", "2 noches en hotel boutique", "Ruta guiada por los miradores"});
            detalle.setItinerario(new String[] {"Día 1: Baixa, Chiado y paseo junto al Tajo", "Día 2: Alfama y ruta de los miradores", "Día 3: desayuno y regreso"});
        } else if (id == 8) {
            detalle.setImagen("https://images.unsplash.com/photo-1524231757912-21f4fe3a7200?auto=format&fit=crop&w=1800&q=85");
            detalle.setFotos(new String[] {
                "https://images.unsplash.com/photo-1772550018917-506fcaa1848f?auto=format&fit=crop&w=1400&q=85",
                "https://images.unsplash.com/photo-1541432901042-2d8bd64b4a9b?auto=format&fit=crop&w=1400&q=85",
                "https://images.unsplash.com/photo-1524231757912-21f4fe3a7200?auto=format&fit=crop&w=1400&q=85"
            });
            detalle.setHotel("Hotel 4 estrellas en Sultanahmet, Estambul");
            detalle.setIncluidos(new String[] {"Vuelos de ida y vuelta", "4 noches en hotel", "Crucero por el Bósforo y visita a Santa Sofía"});
            detalle.setItinerario(new String[] {"Día 1: llegada y paseo por Sultanahmet", "Día 2: Santa Sofía y Mezquita Azul", "Día 3: Gran Bazar y crucero por el Bósforo", "Días 4 y 5: tiempo libre y regreso"});
        }
    }

    public DetallePaquete getDetalle() {
        return detalle;
    }
}
