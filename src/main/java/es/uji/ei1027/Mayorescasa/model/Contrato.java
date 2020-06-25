package es.uji.ei1027.Mayorescasa.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

public class Contrato {
    private String empresa, codcontrato, tiposervicio, dias_semana;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate fechainicial, fechafinal;
    private double preciounidad;
    private LocalTime horainicial, horafinal;

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

    public LocalTime getHorainicial() {
        return horainicial;
    }

    public void setHorainicial(LocalTime horainicial) {
        this.horainicial = horainicial;
    }

    public LocalTime getHorafinal() {
        return horafinal;
    }

    public void setHorafinal(LocalTime horafinal) {
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

    public LocalDate getFechainicial() {
        return fechainicial;
    }

    public void setFechainicial(LocalDate fechainicial) {
        this.fechainicial = fechainicial;
    }

    public LocalDate getFechafinal() {
        return fechafinal;
    }

    public void setFechafinal(LocalDate fechafinal) {
        this.fechafinal = fechafinal;
    }

    public double getPreciounidad() {
        return preciounidad;
    }

    public void setPreciounidad(double preciounidad) {
        this.preciounidad = preciounidad;
    }
}
