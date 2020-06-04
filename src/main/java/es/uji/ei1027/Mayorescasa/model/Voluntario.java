package es.uji.ei1027.Mayorescasa.model;

import java.util.Date;

public class Voluntario  extends Usuario {

    private String dni, hobbies, estado, visible;

    private String dias_semana;

    @Override
    public String getDni() {
        return dni;
    }

    @Override
    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getHobbies() {
        return hobbies;
    }

    public void setHobbies(String hobbies) {
        this.hobbies = hobbies;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getVisible() {
        return visible;
    }

    public void setVisible(String visible) {
        this.visible = visible;
    }




    public String getDias_semana() {
        return dias_semana;
    }
    public void setDias_semana(String dias){
        this.dias_semana=dias;
    }
}
