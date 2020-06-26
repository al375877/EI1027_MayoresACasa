package es.uji.ei1027.Mayorescasa.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

public class Peticion {
    private String cod_pet;
    private String tiposervicio;
    private String comentarios;
    private String dni_ben;
    private String beneficiario;
    private String estado;
    private String codcontrato;
    private String empresa;
    private int linea;
    private double precioservicio;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
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
    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    public String getCodcontrato() {
        return codcontrato;
    }

    public void setCodcontrato(String codcontrato) {
        this.codcontrato = codcontrato;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
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
