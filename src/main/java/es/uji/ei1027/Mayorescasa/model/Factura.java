package es.uji.ei1027.Mayorescasa.model;

import java.util.Date;

public class Factura {
    private String cod_fac;
    private Date fecha;
    private float precio;
    private String concepto;
    private String usuBen;

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public String getUsuBen() {
        return usuBen;
    }

    public void setUsuBen(String usuBen) {
        this.usuBen = usuBen;
    }

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
