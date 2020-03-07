package es.uji.ei1027.Mayorescasa.dao;
import es.uji.ei1027.Mayorescasa.model.Beneficiario;
import es.uji.ei1027.Mayorescasa.model.Compañia;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
public class CompañiaRowMapper  implements RowMapper<Compañia>{
    public Compañia mapRow(ResultSet rs, int RowNum) throws SQLException{
        Compañia compañia = new Compañia();
        compañia.setNombre(rs.getString("nombre"));
        compañia.setCif(rs.getString("cif"));
        compañia.setUsuario(rs.getString("usuario"));
        compañia.setContraseña(rs.getString("contraseña"));
        compañia.setEmail(rs.getString("email"));
        compañia.setDireccion(rs.getString("direccion"));
        compañia.setNumero_telefono(rs.getInt("numero_telefono"));
        compañia.setPersona_contacto(rs.getString("persona_contacto"));
        compañia.setServicio(rs.getString("servicio"));
        return compañia;

    }
}
