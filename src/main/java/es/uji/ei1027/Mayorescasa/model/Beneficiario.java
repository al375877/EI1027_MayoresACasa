package es.uji.ei1027.Mayorescasa.model;

import java.sql.Date;

public class Beneficiario extends Usuario {
    private String dni;
    private String tipodieta;

    @Override
    public String getDni() {
        return dni;
    }

    @Override
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
