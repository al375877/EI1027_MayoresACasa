package es.uji.ei1027.Mayorescasa.dao;

import es.uji.ei1027.Mayorescasa.model.Servicio;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ServicioRowMapper implements RowMapper<Servicio> {
    public Servicio mapRow(ResultSet rs, int RowNum) throws SQLException{
        Servicio servicio = new Servicio();
        servicio.setBeneficiario(rs.getString("beneficiario"));
        servicio.setDni(rs.getString("dni"));
        servicio.setTiposervicio(rs.getString("tiposervicio"));
        servicio.setTipodieta(rs.getString("tipodieta"));
        servicio.setDireccion(rs.getString("direccion"));
        servicio.setTelefono(rs.getInt("telefono"));
        servicio.setComentarios(rs.getString("comentarios"));
        return servicio;
    }
}
