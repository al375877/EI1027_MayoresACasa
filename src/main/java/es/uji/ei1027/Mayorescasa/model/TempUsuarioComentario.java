package es.uji.ei1027.Mayorescasa.model;

import java.util.List;

public class TempUsuarioComentario {


    public Usuario usuario;
    public String comentario;
    public String nombre,email, direccion;
    public int telefono;

    public String getComentario() {
        return comentario;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public String getDireccion() {
        return direccion;
    }

    public int getTelefono() {
        return telefono;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario user){
        this.usuario=user;
        setNombre();
        setDireccion();
        setTelefono();
        setEmail();
    }
    public void setComentario(String comentario){
        this.comentario=comentario;
    }
    public void setNombre(){
        this.nombre=usuario.getNombre();
    }
    public void  setEmail(){
       this.email=usuario.getEmail();
    }
    public void  setDireccion(){
        this.direccion=usuario.getDireccion();
    }
    public void  setTelefono(){
        this.telefono=usuario.getTelefono();
    }
}
