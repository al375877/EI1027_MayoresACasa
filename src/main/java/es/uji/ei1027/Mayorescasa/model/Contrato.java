package es.uji.ei1027.Mayorescasa.model;

import java.util.Date;

public class Contrato {
    private String empresa, cod_pet, codContrato, tipoServicio;
    private Date fechaInicial, fechaFinal;
    private int cantidadservicios;
    private double precioUnidad;

    @Override
    public String toString() {
        return "Contrato{" +
                "empresa='" + empresa + '\'' +
                ", cod_pet='" + cod_pet + '\'' +
                ", codContrato='" + codContrato + '\'' +
                ", tipoServicio='" + tipoServicio + '\'' +
                ", fechaInicial=" + fechaInicial +
                ", fechaFinal=" + fechaFinal +
                ", cantidadservicios=" + cantidadservicios +
                ", precioUnidad=" + precioUnidad +
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

    public String getCodContrato() {
        return codContrato;
    }

    public void setCodContrato(String codContrato) {
        this.codContrato = codContrato;
    }

    public String getTipoServicio() {
        return tipoServicio;
    }

    public void setTipoServicio(String tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    public Date getFechaInicial() {
        return fechaInicial;
    }

    public void setFechaInicial(Date fechaInicial) {
        this.fechaInicial = fechaInicial;
    }

    public Date getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public int getCantidadservicios() {
        return cantidadservicios;
    }

    public void setCantidadservicios(int cantidadservicios) {
        this.cantidadservicios = cantidadservicios;
    }

    public double getPrecioUnidad() {
        return precioUnidad;
    }

    public void setPrecioUnidad(double precioUnidad) {
        this.precioUnidad = precioUnidad;
    }
}
