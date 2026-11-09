package es.fempa;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

/** Datos y filtro de los paquetes que muestra el carrusel. */
@Named
@SessionScoped
public class PaquetesBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Set<String> CATEGORIAS = Set.of("todos", "circuito", "familia", "aventura");

    private String categoria = "todos";
    private final List<Paquete> paquetes = List.of(
            new Paquete("Nilo y Abu Simbel", "Egipto", 8,
                    "Madrid, Barcelona, Valencia", "El Cairo, Luxor, Asuán", "512€", "aventura",
                    "https://images.unsplash.com/photo-1710069441986-82759c8a200c?auto=format&fit=crop&w=1200&q=85",
                    "Pirámides de Guiza y la Gran Esfinge en Egipto"),
            new Paquete("Minicrucero a Roma", "Italia", 6,
                    "Barcelona", "Roma, Civitavecchia", "550€", "circuito",
                    "https://images.unsplash.com/photo-1552832230-c0197dd311b5?auto=format&fit=crop&w=900&q=85",
                    "Vista del Coliseo de Roma al atardecer"),
            new Paquete("París soñado con Disneyland", "Francia", 5,
                    "Madrid", "París, Disneyland Paris", "543€", "familia",
                    "https://images.unsplash.com/photo-1503919005314-30d93d07d823?auto=format&fit=crop&w=900&q=85",
                    "Castillo de cuento en un parque temático de París"),
            new Paquete("Costa Rica esencial", "Costa Rica", 9,
                    "Madrid, Barcelona, Valencia", "San José, Arenal, Monteverde", "1.619€", "aventura",
                    "https://images.unsplash.com/photo-1692721653135-1d99288f79fc?auto=format&fit=crop&w=1200&q=85",
                    "Volcán Arenal rodeado de bosque tropical en Costa Rica"),
            new Paquete("Nápoles y la Costa Amalfitana", "Italia", 6,
                    "Madrid", "Nápoles, Sorrento, Costa Amalfitana", "835€", "circuito",
                    "https://images.unsplash.com/photo-1516483638261-f4dbaf036963?auto=format&fit=crop&w=1200&q=85",
                    "Pueblo costero colorido en la costa italiana"),
            new Paquete("Iconos de Japón", "Japón", 11,
                    "Madrid", "Osaka, Kioto, Tokio", "3.398€", "aventura",
                    "https://images.unsplash.com/photo-1685476938177-0f140dec746f?auto=format&fit=crop&w=1200&q=85",
                    "Túnel de torii rojos en Kioto, Japón"),
            new Paquete("Bangkok y norte de Tailandia", "Tailandia", 10,
                    "Madrid, Barcelona", "Bangkok, Phitsanulok, Chiang Mai", "1.629€", "aventura",
                    "https://images.unsplash.com/photo-1759770273833-45ee906c7219?auto=format&fit=crop&w=1200&q=85",
                    "Templo ornamentado del Gran Palacio de Bangkok"),
            new Paquete("Esencia de Sudáfrica", "Sudáfrica", 8,
                    "Madrid", "Johannesburgo, Kruger, Ciudad del Cabo", "2.040€", "aventura",
                    "https://images.unsplash.com/photo-1770553128604-f4e5abef28ab?auto=format&fit=crop&w=1200&q=85",
                    "Casas de colores del barrio Bo-Kaap en Ciudad del Cabo"),
            new Paquete("Vietnam clásico", "Vietnam", 12,
                    "Madrid, Barcelona", "Hanói, bahía de Ha Long, Hue, Hoi An", "1.825€", "circuito",
                    "https://images.unsplash.com/photo-1748102288847-b2607862c249?auto=format&fit=crop&w=1200&q=85",
                    "Islas de piedra caliza en la bahía de Ha Long"),
            new Paquete("Bali esencial", "Indonesia", 12,
                    "Madrid", "Ubud, Bedugul, Candi Dasa", "1.935€", "familia",
                    "https://images.unsplash.com/photo-1537996194471-e657df975ab4?auto=format&fit=crop&w=1200&q=85",
                    "Templo balinés junto al agua"),
            new Paquete("Capitales imperiales de China", "China", 10,
                    "Madrid", "Pekín, Xi'an, Shanghái", "2.585€", "circuito",
                    "https://images.unsplash.com/photo-1508804185872-d7badad00f7d?auto=format&fit=crop&w=1200&q=85",
                    "Gran Muralla China entre las montañas"),
            new Paquete("Egipto básico", "Egipto", 8,
                    "Barcelona, Madrid, Málaga, Valencia", "El Cairo, crucero por el Nilo", "545€", "circuito",
                    "https://images.unsplash.com/photo-1710069441986-82759c8a200c?auto=format&fit=crop&w=1200&q=85",
                    "Pirámides de Guiza y la Gran Esfinge en Egipto")
    );

    public void seleccionar(String categoria) {
        if (CATEGORIAS.contains(categoria)) {
            this.categoria = categoria;
        }
    }

    public List<Paquete> getPaquetesFiltrados() {
        return paquetes.stream()
                .filter(paquete -> "todos".equals(categoria) || paquete.getCategoria().equals(categoria))
                .toList();
    }

    public String getCategoria() {
        return categoria;
    }

    public List<Paquete> getTodosLosPaquetes() {
        return paquetes;
    }

    public static class Paquete implements Serializable {
        private static final long serialVersionUID = 1L;

        private final String nombre;
        private final String pais;
        private final int duracion;
        private final String salidas;
        private final String visitando;
        private final String precio;
        private final String categoria;
        private final String imagen;
        private final String imagenAlt;

        public Paquete(String nombre, String pais, int duracion,
                       String salidas, String visitando, String precio, String categoria,
                       String imagen, String imagenAlt) {
            this.nombre = nombre;
            this.pais = pais;
            this.duracion = duracion;
            this.salidas = salidas;
            this.visitando = visitando;
            this.precio = precio;
            this.categoria = categoria;
            this.imagen = imagen;
            this.imagenAlt = imagenAlt;
        }

        public String getNombre() { return nombre; }
        public String getPais() { return pais; }
        public int getDuracion() { return duracion; }
        public String getSalidas() { return salidas; }
        public String getVisitando() { return visitando; }
        public String getPrecio() { return precio; }
        public String getCategoria() { return categoria; }
        public String getImagen() { return imagen; }
        public String getImagenAlt() { return imagenAlt; }
    }
    // Obtiene automáticamente los países disponibles en los paquetes.
    public List<String> getPaisesDisponibles() {
        return paquetes.stream()
                .map(Paquete::getPais)
                .distinct()
                .sorted()
                .toList();
    }
}
