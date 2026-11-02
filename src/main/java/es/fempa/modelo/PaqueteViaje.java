package es.fempa.modelo;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;

/**
 * POJO que representa una ficha de viaje (oferta o destino destacado)
 * que se muestra en las tarjetas del dashboard.
 */
public class PaqueteViaje implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String titulo;
    private String destino;
    private String descripcion;
    private String imagen;
    private double precio;
    private String duracion;
    private double valoracion;

    public PaqueteViaje() {
    }

    public PaqueteViaje(Long id, String titulo, String destino, String descripcion,
                        String imagen, double precio, String duracion, double valoracion) {
        this.id = id;
        this.titulo = titulo;
        this.destino = destino;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.precio = precio;
        this.duracion = duracion;
        this.valoracion = valoracion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public double getValoracion() {
        return valoracion;
    }

    public void setValoracion(double valoracion) {
        this.valoracion = valoracion;
    }

    /**
     * Precio formateado en euros para mostrarlo directamente en la tarjeta.
     */
    public String getPrecioFormateado() {
        NumberFormat formato = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("es-ES"));
        formato.setMaximumFractionDigits(0);
        return formato.format(precio);
    }

    /**
     * Valoración con un decimal (por ejemplo 4,8).
     */
    public String getValoracionTexto() {
        return String.format(Locale.forLanguageTag("es-ES"), "%.1f", valoracion);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaqueteViaje)) {
            return false;
        }
        PaqueteViaje that = (PaqueteViaje) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "PaqueteViaje{" + "id=" + id + ", titulo='" + titulo + '\'' + '}';
    }
}
