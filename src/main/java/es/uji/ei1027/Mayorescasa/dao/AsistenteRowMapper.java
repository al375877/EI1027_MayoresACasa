

package es.uji.ei1027.Mayorescasa.dao;
import es.uji.ei1027.Mayorescasa.model.Asistente;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public final class AsistenteRowMapper implements RowMapper<Asistente> {
    public Asistente mapRow(ResultSet rs, int RowNum) throws SQLException{
        Asistente asistente = new Asistente();
        asistente.setNombre(rs.getString("nombre"));
        asistente.setDni(rs.getString("dni"));
        asistente.setUsuario(rs.getString("usuario"));
        asistente.setContraseña(rs.getString("contraseña"));
        asistente.setEmail(rs.getString("email"));
        asistente.setTelefono(rs.getInt("telefono"));

        return asistente;

    }
}
