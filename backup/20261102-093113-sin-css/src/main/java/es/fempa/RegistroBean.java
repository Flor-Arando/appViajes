package es.fempa;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

import java.io.Serializable;

@ViewScoped
@Named
public class RegistroBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String userName;
    private String nombre;
    private String password;

    public RegistroBean() {
    }

    /**
     * Registro simulado: valida los campos y muestra el resultado.
     * Todavía no persiste el usuario en ninguna base de datos.
     */
    public void registrar() {
        if (esVacio(userName) || esVacio(nombre) || esVacio(password)) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Datos incompletos",
                            "Usuario, nombre y contraseña son obligatorios."));
            return;
        }

        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Cuenta creada",
                        "La cuenta de " + nombre + " se ha creado correctamente. Ya puedes iniciar sesión."));

        userName = null;
        nombre = null;
        password = null;
    }

    private boolean esVacio(String valor) {
        return valor == null || valor.trim().isEmpty();
    }

    public String getUsername() {
        return userName;
    }

    public void setUsername(String username) {
        this.userName = username;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
