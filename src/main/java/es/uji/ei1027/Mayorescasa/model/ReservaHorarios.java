package es.uji.ei1027.Mayorescasa.model;

import java.util.Date;

public class ReservaHorarios {
    private String usuario_ben, usuario_vol, tipo;
    private int diasemana;
    private Date fechainicial, fechafinal;

    @Override
    public String toString() {
        return "reservaHorarios{" +
                "usuario_ben='" + usuario_ben + '\'' +
                ", usuario_vol='" + usuario_vol + '\'' +
                ", tipo='" + tipo + '\'' +
                ", diasemana=" + diasemana +
                ", fechainicial=" + fechainicial +
                ", fechafinal=" + fechafinal +
                '}';
    }

    public String getUsuario_ben() {
        return usuario_ben;
    }

    public void setUsuario_ben(String usuario_ben) {
        this.usuario_ben = usuario_ben;
    }

    public String getUsuario_vol() {
        return usuario_vol;
    }

    public void setUsuario_vol(String usuario_vol) {
        this.usuario_vol = usuario_vol;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getDiasemana() {
        return diasemana;
    }

    public void setDiasemana(int diasemana) {
        this.diasemana = diasemana;
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
}
