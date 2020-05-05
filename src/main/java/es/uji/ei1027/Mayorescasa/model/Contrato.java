package es.uji.ei1027.Mayorescasa.model;

import java.util.Date;

public class Contrato {
    private String empresa, codcontrato, tiposervicio;
    private Date fechainicial, fechafinal;
    private int cantidadservicios;
    private double preciounidad;

    @Override
    public String toString() {
        return "Contrato{" +
                "empresa='" + empresa + '\'' +
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

    public String getCodcontrato() {
        return codcontrato;
    }

    public void setCodcontrato(String codcontrato) {
        this.codcontrato = codcontrato;
    }

    public String getTiposervicio() {
        return tiposervicio;
    }

    public void setTiposervicio(String tiposervicio) {
        this.tiposervicio = tiposervicio;
    }

    public Date getFechainicial() {
        return fechainicial;
    }

    public void setFechainicial(Date fechainicial) {
        this.fechainicial = fechainicial;
    }

    public Date getFechafinal() {
        return fechafinal;
    }

    public void setFechafinal(Date fechafinal) {
        this.fechafinal = fechafinal;
    }

    public int getCantidadservicios() {
        return cantidadservicios;
    }

    public void setCantidadservicios(int cantidadservicios) {
        this.cantidadservicios = cantidadservicios;
    }

    public double getPreciounidad() {
        return preciounidad;
    }

    public void setPreciounidad(double preciounidad) {
        this.preciounidad = preciounidad;
    }
}
