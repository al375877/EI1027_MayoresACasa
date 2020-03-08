package es.uji.ei1027.Mayorescasa.model;

import java.util.Date;

public class Factura {
    private String cod_fac;
    private Date fecha;
    private float precio;

    public String getCod_fac() {
        return cod_fac;
    }

    public void setCod_fac(String cod_fac) {
        this.cod_fac = cod_fac;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return "Factura{" +
                "cod_fac='" + cod_fac + '\'' +
                ", fecha=" + fecha +
                ", precio=" + precio +
                '}';
    }
}
