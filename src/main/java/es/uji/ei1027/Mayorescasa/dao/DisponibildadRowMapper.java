package es.uji.ei1027.Mayorescasa.dao;

import es.uji.ei1027.Mayorescasa.model.Disponibilidad;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DisponibildadRowMapper implements RowMapper<Disponibilidad> {
    public Disponibilidad mapRow(ResultSet rs, int RowNum) throws SQLException {
        Disponibilidad disponibilidad = new Disponibilidad();
        disponibilidad.setUsuario_ben(rs.getString("dni_ben"));
        disponibilidad.setUsuario_vol(rs.getString("dni_vol"));
        disponibilidad.setFechainicial(rs.getDate("fechainicial"));
        disponibilidad.setFechafinal(rs.getDate("fechafinal"));
        disponibilidad.setComentario(rs.getString("comentario"));
        disponibilidad.setEstado(rs.getString("estado"));
        return disponibilidad;
    }
}
