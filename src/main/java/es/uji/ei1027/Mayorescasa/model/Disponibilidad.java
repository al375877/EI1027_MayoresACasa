package es.uji.ei1027.Mayorescasa.model;

import java.util.Date;

public class Disponibilidad {
    private String usuario_ben;
    private String usuario_vol;
    private String comentario;

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }


    private Date fechainicial//el día de hoy
            , fechafinal;//el día


    @Override
    public String toString() {
        return "reservaHorarios{" +
                "usuario_ben='" + usuario_ben + '\'' +
                ", usuario_vol='" + usuario_vol + '\'' +
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
