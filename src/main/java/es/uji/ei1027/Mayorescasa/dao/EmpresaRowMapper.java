package es.uji.ei1027.Mayorescasa.dao;
import es.uji.ei1027.Mayorescasa.model.Empresa;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
public class EmpresaRowMapper implements RowMapper<Empresa>{
    public Empresa mapRow(ResultSet rs, int RowNum) throws SQLException{
        Empresa empresa = new Empresa();
        empresa.setNombre(rs.getString("nombre"));
        empresa.setCif(rs.getString("cif"));
        empresa.setUsuario(rs.getString("usuario"));
        empresa.setContraseña(rs.getString("contraseña"));
        empresa.setEmail(rs.getString("email"));
        empresa.setDireccion(rs.getString("direccion"));
        empresa.setNumero_telefono(rs.getInt("telefono"));
        empresa.setPersona_contacto(rs.getString("persona_contacto"));
        empresa.setServicio(rs.getString("servicio"));
        return empresa;

    }
}
