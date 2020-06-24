package es.uji.ei1027.Mayorescasa.model;

import java.sql.Time;
import java.util.Date;

public class Contrato {
    private String empresa, codcontrato, tiposervicio, dias_semana;
    private Date fechainicial, fechafinal;
    private double preciounidad;
    private Time horainicial, horafinal;

    public String getDias_semana() {
        return dias_semana;
    }

    public void setDias_semana(String dias_semana) {
        this.dias_semana = dias_semana;
    }

    @Override
    public String toString() {
        return "Contrato{" +
                "empresa='" + empresa + '\'' +
                ", codcontrato='" + codcontrato + '\'' +
                ", tiposervicio='" + tiposervicio + '\'' +
                ", dias_semana='" + dias_semana + '\'' +
                ", fechainicial=" + fechainicial +
                ", fechafinal=" + fechafinal +
                ", preciounidad=" + preciounidad +
                ", horainicial=" + horainicial +
                ", horafinal=" + horafinal +
                '}';
    }

    public Time getHorainicial() {
        return horainicial;
    }

    public void setHorainicial(Time horainicial) {
        this.horainicial = horainicial;
    }

    public Time getHorafinal() {
        return horafinal;
    }

    public void setHorafinal(Time horafinal) {
        this.horafinal = horafinal;
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

    public double getPreciounidad() {
        return preciounidad;
    }

    public void setPreciounidad(double preciounidad) {
        this.preciounidad = preciounidad;
    }
}
