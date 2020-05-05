package es.uji.ei1027.Mayorescasa.model;

import java.util.Date;

public class Peticion {
    private String cod_pet, tiposervicio, comentarios, dni_ben, beneficiario;
    private int linea;
    private double precioservicio;
    private Date fechaaceptada,fecharechazada,fechafinal;

    @Override
    public String toString() {
        return "Peticion{" +
                "cod_pet='" + cod_pet + '\'' +
                ", tiposervicio='" + tiposervicio + '\'' +
                ", comentarios='" + comentarios + '\'' +
                ", dni_ben='" + dni_ben + '\'' +
                ", linea=" + linea +
                ", precioservicio=" + precioservicio +
                ", fechaaceptada=" + fechaaceptada +
                ", fecharechazada=" + fecharechazada +
                ", fechafinal=" + fechafinal +
                '}';
    }

    public String getCod_pet() {
        return cod_pet;
    }

    public void setCod_pet(String cod_pet) {
        this.cod_pet = cod_pet;
    }

    public String getTiposervicio() {
        return tiposervicio;
    }

    public void setTiposervicio(String tiposervicio) {
        this.tiposervicio = tiposervicio;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public String getDni_ben() {
        return dni_ben;
    }

    public void setDni_ben(String usuario_ben) {
        this.dni_ben = usuario_ben;
    }

    public int getLinea() {
        return linea;
    }

    public void setLinea(int linea) {
        this.linea = linea;
    }

    public double getPrecioservicio() {
        return precioservicio;
    }

    public void setPrecioservicio(double precioservicio) {
        this.precioservicio = precioservicio;
    }

    public Date getFechaaceptada() {
        return fechaaceptada;
    }

    public void setFechaaceptada(Date fechaaceptada) {
        this.fechaaceptada = fechaaceptada;
    }

    public Date getFecharechazada() {
        return fecharechazada;
    }

    public void setFecharechazada(Date fecharechazada) {
        this.fecharechazada = fecharechazada;
    }

    public Date getFechafinal() {
        return fechafinal;
    }

    public void setFechafinal(Date fechafinal) {
        this.fechafinal = fechafinal;
    }

    public String getBeneficiario() {
        return beneficiario;
    }

    public void setBeneficiario(String beneficiario) {
        this.beneficiario = beneficiario;
    }
}
