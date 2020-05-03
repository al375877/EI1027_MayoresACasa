package es.uji.ei1027.Mayorescasa.dao;

import es.uji.ei1027.Mayorescasa.model.Disponibilidad;
import es.uji.ei1027.Mayorescasa.model.Usuario;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioRowMapper implements RowMapper<Usuario> {
    public Usuario mapRow(ResultSet rs, int RowNum) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setNombre(rs.getString("nombre"));
        usuario.setDni(rs.getString("dni"));
        usuario.setUsuario(rs.getString("usuario"));
        usuario.setContraseña(rs.getString("contraseña"));
        usuario.setEmail(rs.getString("email"));
        usuario.setDireccion(rs.getString("direccion"));
        usuario.setNumero_telefono(rs.getInt("telefono"));
        usuario.setFechaNacimiento(rs.getDate("fechanacimiento"));
        usuario.setGenero(rs.getString("genero"));
        usuario.setTipoUsuario(rs.getString("tipousuario"));

        return usuario;

    }
}

