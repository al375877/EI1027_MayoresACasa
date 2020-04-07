package es.uji.ei1027.Mayorescasa.dao;

import es.uji.ei1027.Mayorescasa.model.ReservaHorarios;
import es.uji.ei1027.Mayorescasa.model.Voluntario;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReservaHorariosRowMapper implements RowMapper<ReservaHorarios> {
    public ReservaHorarios mapRow(ResultSet rs, int RowNum) throws SQLException {
        ReservaHorarios reservaHorarios = new ReservaHorarios();
        reservaHorarios.setUsuario_ben(rs.getString("Usuario beneficiario"));
        reservaHorarios.setUsuario_vol(rs.getString("Usuario voluntario"));
        reservaHorarios.setDiasemana(rs.getInt("Dia de la semana"));
        reservaHorarios.setTipo(rs.getString("Tipo"));
        reservaHorarios.setFechainicial(rs.getDate("Fecha Inicial"));
        reservaHorarios.setFechafinal(rs.getDate("Fecha final"));
        return reservaHorarios;
    }
}
