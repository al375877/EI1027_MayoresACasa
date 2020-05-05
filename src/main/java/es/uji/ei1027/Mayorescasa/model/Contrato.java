package es.uji.ei1027.Mayorescasa.model;

import java.util.Date;

public class Contrato {
    private String empresa, cod_pet, codcontrato, tiposervicio;
    private Date fechainicial, fechafinal;
    private int cantidadservicios;
    private double preciounidad;

    @Override
    public String toString() {
        return "Contrato{" +
                "empresa='" + empresa + '\'' +
                ", cod_pet='" + cod_pet + '\'' +
                ", codcontrato='" + codcontrato + '\'' +
                ", tiposervicio='" + tiposervicio + '\'' +
                ", fechainicial=" + fechainicial +
                ", fechafinal=" + fechafinal +
                ", cantidadservicios=" + cantidadservicios +
                ", preciounidad=" + preciounidad +
                '}';
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

    public String getcodcontrato() {
        return codcontrato;
    }

    public void setcodcontrato(String codcontrato) {
        this.codcontrato = codcontrato;
    }

    public String gettiposervicio() {
        return tiposervicio;
    }

    public void settiposervicio(String tiposervicio) {
        this.tiposervicio = tiposervicio;
    }

    public Date getfechainicial() {
        return fechainicial;
    }

    public void setfechainicial(Date fechainicial) {
        this.fechainicial = fechainicial;
    }

    public Date getfechafinal() {
        return fechafinal;
    }

    public void setfechafinal(Date fechafinal) {
        this.fechafinal = fechafinal;
    }

    public int getcantidadservicios() {
        return cantidadservicios;
    }

    public void setcantidadservicios(int cantidadservicios) {
        this.cantidadservicios = cantidadservicios;
    }

    public double getpreciounidad() {
        return preciounidad;
    }

    public void setpreciounidad(double preciounidad) {
        this.preciounidad = preciounidad;
    }
}
