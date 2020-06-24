package es.uji.ei1027.Mayorescasa.model;

public class Empresa {
    String nombre,cif,contacto,usuario,contraseña,email,direccion,tiposervicio, cont_nif, cont_mail;
    int cont_tlf, telefono;

    public String getNombre() {
        return nombre;
    }

    public String getCont_nif() {
        return cont_nif;
    }

    public void setCont_nif(String cont_nif) {
        this.cont_nif = cont_nif;
    }

    public int getCont_tlf() {
        return cont_tlf;
    }

    public void setCont_tlf(int cont_tlf) {
        this.cont_tlf = cont_tlf;
    }

    public String getCont_mail() {
        return cont_mail;
    }

    public void setCont_mail(String cont_mail) {
        this.cont_mail = cont_mail;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCif() {
        return cif;
    }

    public void setCif(String cif) {
        this.cif = cif;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
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

    public String getTiposervicio() {
        return tiposervicio;
    }

    public void setTiposervicio(String tiposervicio) {
        this.tiposervicio = tiposervicio;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return "Empresa{" +
                "nombre='" + nombre + '\'' +
                ", cif='" + cif + '\'' +
                ", contacto='" + contacto + '\'' +
                ", usuario='" + usuario + '\'' +
                ", contraseña='" + contraseña + '\'' +
                ", email='" + email + '\'' +
                ", direccion='" + direccion + '\'' +
                ", tiposervicio='" + tiposervicio + '\'' +
                ", cont_nif='" + cont_nif + '\'' +
                ", cont_tlf='" + cont_tlf + '\'' +
                ", cont_mail='" + cont_mail + '\'' +
                ", telefono=" + telefono +
                '}';
    }
}
