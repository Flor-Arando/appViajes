package es.fempa;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class PagoBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private String titular;
    private String numero;
    private String vencimiento;
    private String cvv;
    private String email;
    private boolean aceptaCondiciones;
    private boolean confirmaTransferencia;

    // Comprueba los datos mínimos y muestra un mensaje en la página.
    public void pagar() {
        if (titular == null || titular.isBlank()
                || numero == null || numero.isBlank()
                || vencimiento == null || vencimiento.isBlank()
                || cvv == null || cvv.isBlank()
                || email == null || email.isBlank()
                || !aceptaCondiciones) {
            mensaje(FacesMessage.SEVERITY_WARN,
                    "Completa los datos de la tarjeta y acepta las condiciones.");
            return;
        }

        mensaje(FacesMessage.SEVERITY_INFO,
                "Pago realizado correctamente (modo demostración).");
    }

    // Confirma la transferencia sin procesar datos bancarios reales.
    public void confirmarTransferencia() {
        if (email == null || email.isBlank() || !confirmaTransferencia) {
            mensaje(FacesMessage.SEVERITY_WARN,
                    "Indica tu correo y confirma la transferencia.");
            return;
        }

        mensaje(FacesMessage.SEVERITY_INFO,
                "Transferencia pendiente de verificación.");
    }

    private void mensaje(FacesMessage.Severity severity, String texto) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(severity, texto, null));
    }

    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getVencimiento() {
        return vencimiento;
    }

    public void setVencimiento(String vencimiento) {
        this.vencimiento = vencimiento;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isAceptaCondiciones() {
        return aceptaCondiciones;
    }

    public void setAceptaCondiciones(boolean aceptaCondiciones) {
        this.aceptaCondiciones = aceptaCondiciones;
    }

    public boolean isConfirmaTransferencia() {
        return confirmaTransferencia;
    }

    public void setConfirmaTransferencia(boolean confirmaTransferencia) {
        this.confirmaTransferencia = confirmaTransferencia;
    }
}
