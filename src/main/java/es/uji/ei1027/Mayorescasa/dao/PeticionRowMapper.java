package es.uji.ei1027.Mayorescasa.dao;

import es.uji.ei1027.Mayorescasa.model.Peticion;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class PeticionRowMapper implements RowMapper<Peticion> {
    public Peticion mapRow(ResultSet rs, int RowNum) throws SQLException {
        Peticion peticion = new Peticion();
        peticion.setCod_pet(rs.getString("cod_pet"));
        peticion.setTiposervicio(rs.getString("tiposervicio"));
        peticion.setPrecioservicio(rs.getDouble("precioservicio"));
        peticion.setLinea(rs.getInt(("linea")));
        peticion.setFechaaceptada(rs.getDate("fechaaceptada"));
        peticion.setFecharechazada(rs.getDate(("fecharechazada")));
        peticion.setFechafinal(rs.getDate("fechafinal"));
        peticion.setComentarios(rs.getString("comentarios"));
        peticion.setUsuario_ben(rs.getString("usuario_ben"));
        return peticion;
    }
}
