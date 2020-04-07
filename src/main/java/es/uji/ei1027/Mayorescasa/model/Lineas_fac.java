package es.uji.ei1027.Mayorescasa.model;

public class Lineas_fac {
    private String cod_fac, cod_pet, tiposervicio;
    private int linea;
    private double precioservicio;

    @Override
    public String toString() {
        return "Lineas_fac{" +
                "cod_fac='" + cod_fac + '\'' +
                ", cod_pet='" + cod_pet + '\'' +
                ", tiposervicio='" + tiposervicio + '\'' +
                ", linea=" + linea +
                ", precioservicio=" + precioservicio +
                '}';
    }

    public String getCod_fac() {
        return cod_fac;
    }

    public void setCod_fac(String cod_fac) {
        this.cod_fac = cod_fac;
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
}
