package es.uji.ei1027.Mayorescasa.model;

import java.util.Date;

public class Asistente {

   private String nombre,dni,genero,contraseña,usuario,email, direccion;
   private int edad,numero_telefono;
   private  Date fecha_nacimiento;

    public String getNombre() {
        return nombre;
    }

    public String getDni() {
        return dni;
    }

    public String getGenero() {
        return genero;
    }

    public String getContraseña() {
        return contraseña;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getEmail() {
        return email;
    }

    public String getDireccion() {
        return direccion;
    }

    public int getEdad() {
        return edad;
    }

    public int getNumero_telefono() {
        return numero_telefono;
    }

    public Date getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public void setNumero_telefono(int numero_telefono) {
        this.numero_telefono = numero_telefono;
    }

    public void setFecha_nacimiento(Date fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    @Override
    public String toString() {
        return "Asistente{" +
                "nombre='" + nombre + '\'' +
                ", dni='" + dni + '\'' +
                ", genero='" + genero + '\'' +
                ", contraseña='" + contraseña + '\'' +
                ", usuario='" + usuario + '\'' +
                ", email='" + email + '\'' +
                ", direccion='" + direccion + '\'' +
                ", edad=" + edad +
                ", numero_telefono=" + numero_telefono +
                ", fecha_nacimiento=" + fecha_nacimiento +
                '}';
    }
}
