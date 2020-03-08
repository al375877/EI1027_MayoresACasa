package es.uji.ei1027.Mayorescasa.dao;

import es.uji.ei1027.Mayorescasa.model.Peticion;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class PeticionRowMapper implements RowMapper<Peticion> {
    public Peticion mapRow(ResultSet rs, int RowNum) throws SQLException {
        Peticion peticion = new Peticion();
        peticion.setCod(rs.getString("cod"));
        peticion.setTipoServicio(rs.getString("Tipo servicio"));
        peticion.setEstado(rs.getString("Estado"));
        peticion.setFecha_inicio(rs.getDate("Fecha inicio"));
        peticion.setFecha_fin(rs.getDate("Fecha fin"));
        peticion.setFecha_aceptado(rs.getDate("Fecha aceptado"));
        peticion.setFecha_rechazado(rs.getDate("Fecha rechazado"));
        peticion.setComentarios(rs.getString("Comentarios"));
        peticion.setUsuario_ben(rs.getString("Usuario_ben"));

        return peticion;
    }
}
