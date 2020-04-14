package es.uji.ei1027.Mayorescasa.model;

import java.sql.Date;

public class Beneficiario {
    private String nombre;
    private String dni;
    private String genero;
    private int edad;
    private String usuario;
    private String contraseña;
    private String email;
    private String direccion;
    private String usuario_asis;
    private String tipodieta;
    private int telefono;


    //@DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date fecha_nacimiento;


    public String getUsuario_asis() {
        return usuario_asis;
    }

    public void setUsuario_asis(String usuario_asis) {
        this.usuario_asis = usuario_asis;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Date getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(Date fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public int getNumero_telefono() {

        return telefono;

    }

    public void setNumero_telefono(int telefono) {
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return "Beneficiario{" +
                "nombre='" + nombre + '\'' +
                ", dni='" + dni + '\'' +
                ", genero='" + genero + '\'' +
                ", edad=" + edad +
                ", usuario='" + usuario + '\'' +
                ", contraseña='" + contraseña + '\'' +
                ", email='" + email + '\'' +
                ", direccion='" + direccion + '\'' +
                ", usuario_asis='" + usuario_asis + '\'' +
                ", fecha_nacimiento=" + fecha_nacimiento +
                ", telefono=" + telefono +
                ", tipodieta="+tipodieta+
                '}';
    }

    public String getTipoDieta() {

        return tipodieta;
    }

    public void setTipoDieta(String tipodieta) {
        this.tipodieta = tipodieta;
    }
}
