package es.uji.ei1027.Mayorescasa.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class Disponibilidad {
    private String usuario_ben;
    private String usuario_vol;
    private String comentario;
    private String estado;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date fechainicial//el día de hoy
            ,fechafinal;//el día que deciden de estar asiganados


    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }






    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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


