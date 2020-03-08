package es.uji.ei1027.Mayorescasa.model;

import java.util.Date;

public class Peticion {
    private String cod, tipoServicio, estado, comentarios, usuario_ben;
    private Date fecha_inicio, fecha_fin, fecha_aceptado, fecha_rechazado;

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public String getTipoServicio() {
        return tipoServicio;
    }

    public void setTipoServicio(String tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public String getUsuario_ben() {
        return usuario_ben;
    }

    public void setUsuario_ben(String usuario_ben) {
        this.usuario_ben = usuario_ben;
    }

    public Date getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(Date fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public Date getFecha_fin() {
        return fecha_fin;
    }

    public void setFecha_fin(Date fecha_fin) {
        this.fecha_fin = fecha_fin;
    }

    public Date getFecha_aceptado() {
        return fecha_aceptado;
    }

    public void setFecha_aceptado(Date fecha_aceptado) {
        this.fecha_aceptado = fecha_aceptado;
    }

    public Date getFecha_rechazado() {
        return fecha_rechazado;
    }

    public void setFecha_rechazado(Date fecha_rechazado) {
        this.fecha_rechazado = fecha_rechazado;
    }

    @Override
    public String toString() {
        return "Peticion{" +
                "cod='" + cod + '\'' +
                ", tipoServicio='" + tipoServicio + '\'' +
                ", estado='" + estado + '\'' +
                ", comentarios='" + comentarios + '\'' +
                ", usuario_ben='" + usuario_ben + '\'' +
                ", fecha_inicio=" + fecha_inicio +
                ", fecha_fin=" + fecha_fin +
                ", fecha_aceptado=" + fecha_aceptado +
                ", fecha_rechazado=" + fecha_rechazado +
                '}';
    }
}
