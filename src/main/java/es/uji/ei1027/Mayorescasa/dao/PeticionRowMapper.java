package es.uji.ei1027.Mayorescasa.dao;

import es.uji.ei1027.Mayorescasa.model.Peticion;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class PeticionRowMapper implements RowMapper<Peticion> {
    public Peticion mapRow(ResultSet rs, int RowNum) throws SQLException {
        Peticion peticion = new Peticion();
        peticion.setCod_pet(rs.getString("Codigo"));
        peticion.setTiposervicio(rs.getString("Tipo servicio"));
        peticion.setPrecioservicio(rs.getDouble("Precio servicio"));
        peticion.setLinea(rs.getInt(("Linea")));
        peticion.setFechaaceptada(rs.getDate("Fecha aceptada"));
        peticion.setFecharechazada(rs.getDate(("Fecha rechazada")));
        peticion.setFechafinal(rs.getDate("Fecha final"));
        peticion.setComentarios(rs.getString("Comentario"));
        peticion.setUsuario_ben(rs.getString("Usuario beneficiario"));
        return peticion;
    }
}
