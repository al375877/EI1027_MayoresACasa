package es.uji.ei1027.Mayorescasa.dao;


import es.uji.ei1027.Mayorescasa.model.Voluntario;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class VoluntarioRowMapper implements RowMapper<Voluntario> {
    public Voluntario mapRow(ResultSet rs, int RowNum) throws SQLException {
        Voluntario vol = new Voluntario();
        vol.setDni(rs.getString("dni"));
        vol.setHobbies(rs.getString("hobbies"));
        vol.setDias_semana(rs.getString("dias_semana"));
        return vol;

    }
}