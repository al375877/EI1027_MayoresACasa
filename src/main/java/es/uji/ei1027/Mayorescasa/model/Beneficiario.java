package es.uji.ei1027.Mayorescasa.model;

import java.sql.Date;

public class Beneficiario extends UsuarioGeneral{
    private String genero;
    private String dni;
    private int edad;
    private String usuario_asis;
    private String tipodieta;

    //@DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date fecha_nacimiento;

    public String getUsuario_asis() {
        return usuario_asis;
    }

    public void setUsuario_asis(String usuario_asis) {
        this.usuario_asis = usuario_asis;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }


    public Date getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(Date fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public String getTipoDieta() {
        return tipodieta;
    }

    public void setTipoDieta(String tipodieta) {
        this.tipodieta = tipodieta;
    }
    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }
    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }


}
