package es.uji.ei1027.Mayorescasa.dao;

import es.uji.ei1027.Mayorescasa.model.Disponibilidad;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReservaHorariosRowMapper implements RowMapper<Disponibilidad> {
    public Disponibilidad mapRow(ResultSet rs, int RowNum) throws SQLException {
        Disponibilidad disponibilidad = new Disponibilidad();
        disponibilidad.setUsuario_ben(rs.getString("Usuario beneficiario"));
        disponibilidad.setUsuario_vol(rs.getString("Usuario voluntario"));
        disponibilidad.setDiasemana(rs.getInt("Dia de la semana"));
        disponibilidad.setTipo(rs.getString("Tipo"));
        disponibilidad.setFechainicial(rs.getDate("Fecha Inicial"));
        disponibilidad.setFechafinal(rs.getDate("Fecha final"));
        return disponibilidad;
    }
}
