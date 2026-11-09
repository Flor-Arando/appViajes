package es.fempa.modelo;


public class DetallePaquete {

    private PaqueteViaje paquete;
    private String imagen;
    private String[] fotos;
    private String hotel;
    private String[] incluidos;
    private String[] itinerario;

    public PaqueteViaje getPaquete() {
        return paquete;
    }

    public void setPaquete(PaqueteViaje paquete) {
        this.paquete = paquete;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String[] getFotos() {
        return fotos;
    }

    public void setFotos(String[] fotos) {
        this.fotos = fotos;
    }

    public String getHotel() {
        return hotel;
    }

    public void setHotel(String hotel) {
        this.hotel = hotel;
    }

    public String[] getIncluidos() {
        return incluidos;
    }

    public void setIncluidos(String[] incluidos) {
        this.incluidos = incluidos;
    }

    public String[] getItinerario() {
        return itinerario;
    }

    public void setItinerario(String[] itinerario) {
        this.itinerario = itinerario;
    }
}
