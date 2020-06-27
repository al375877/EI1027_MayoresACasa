package es.uji.ei1027.Mayorescasa.model;

public class Beneficiario extends Usuario {
    private String nombre;
    private String dni;
    private String tipodieta;
    private String asistente;

    public String getAsistente() {
        return asistente;
    }

    public void setAsistente(String asistente) {
        this.asistente = asistente;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    @Override
    public String getNombre() {
        return nombre;
    }

    @Override
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipodieta() {
        return tipodieta;
    }

    public void setTipodieta(String tipodieta) {
        this.tipodieta = tipodieta;
    }
}
