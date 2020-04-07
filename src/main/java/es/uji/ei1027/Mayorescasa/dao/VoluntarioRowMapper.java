package es.uji.ei1027.Mayorescasa.dao;
import es.uji.ei1027.Mayorescasa.model.Beneficiario;
import es.uji.ei1027.Mayorescasa.model.Voluntario;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public final class VoluntarioRowMapper implements RowMapper<Voluntario>  {
    public Voluntario mapRow(ResultSet rs, int RowNum) throws SQLException{
        Voluntario voluntario = new Voluntario();
        voluntario.setNombre(rs.getString("nombre"));
        voluntario.setDni(rs.getString("dni"));
        voluntario.setGenero(rs.getString("genero"));
        voluntario.setUsuario(rs.getString("usuario"));
        voluntario.setContraseña(rs.getString("contraseña"));
        voluntario.setEmail(rs.getString("email"));
        voluntario.setDireccion(rs.getString("direccion"));
        voluntario.setFecha_nacimiento(rs.getDate("fecha_nacimiento"));
        voluntario.setNumero_telefono(rs.getInt("telefono"));
        voluntario.setHobbie(rs.getString("hobbie"));
        return voluntario;

    }
}
