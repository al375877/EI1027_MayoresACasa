package es.uji.ei1027.Mayorescasa.model;

import java.util.Date;

public class Voluntario {
    private String nombre,dni,genero,contraseña,usuario,email, direccion,hobbie;
    private int numero_telefono;
    private Date fecha_nacimiento;

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

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
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

    public String getHobbie() {
        return hobbie;
    }

    public void setHobbie(String hobbie) {
        this.hobbie = hobbie;
    }

    public int getNumero_telefono() {
        return numero_telefono;
    }

    public void setNumero_telefono(int numero_telefono) {
        this.numero_telefono = numero_telefono;
    }

    public Date getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(Date fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    @Override
    public String toString() {
        return "Voluntario{" +
                "nombre='" + nombre + '\'' +
                ", dni='" + dni + '\'' +
                ", genero='" + genero + '\'' +
                ", contraseña='" + contraseña + '\'' +
                ", usuario='" + usuario + '\'' +
                ", email='" + email + '\'' +
                ", direccion='" + direccion + '\'' +
                ", hobbie='" + hobbie + '\'' +
                ", numero_telefono=" + numero_telefono +
                ", fecha_nacimiento=" + fecha_nacimiento +
                '}';
    }
}
