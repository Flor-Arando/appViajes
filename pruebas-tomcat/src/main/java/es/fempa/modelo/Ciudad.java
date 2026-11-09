package es.fempa.modelo;

import java.io.Serializable;
import java.util.Objects;

/**
 * POJO que representa una ciudad de origen o destino disponible
 * en el buscador de la agencia de viajes.
 */
public class Ciudad implements Serializable {

    private static final long serialVersionUID = 1L;

    private String nombre;

    public Ciudad() {
    }

    public Ciudad(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Ciudad)) {
            return false;
        }
        Ciudad ciudad = (Ciudad) o;
        return Objects.equals(nombre, ciudad.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre);
    }

    @Override
    public String toString() {
        return nombre;
    }
}
