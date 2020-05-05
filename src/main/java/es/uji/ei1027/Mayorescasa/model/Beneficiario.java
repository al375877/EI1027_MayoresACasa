package es.uji.ei1027.Mayorescasa.model;

public class Beneficiario extends Usuario {
    private String dni;
    private String tipodieta;
    private String registro;

    public String getRegistro() {
        return registro;
    }

    public void setRegistro(String registro) {
        this.registro = registro;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getTipodieta() {
        return tipodieta;
    }

    public void setTipodieta(String tipodieta) {
        this.tipodieta = tipodieta;
    }
}
