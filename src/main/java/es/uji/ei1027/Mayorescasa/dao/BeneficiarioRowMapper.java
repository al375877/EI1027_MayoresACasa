package es.uji.ei1027.Mayorescasa.dao;

import es.uji.ei1027.Mayorescasa.model.Beneficiario;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BeneficiarioRowMapper implements  RowMapper<Beneficiario> {
    public Beneficiario mapRow(ResultSet rs, int RowNum) throws SQLException {
        Beneficiario ben = new Beneficiario();
        ben.setNombre(rs.getString("nombre"));
        ben.setDni(rs.getString("dni"));
        ben.setAsistente(rs.getString("asistente"));
        ben.setTipodieta(rs.getString("tipodieta"));
        return ben;

    }
}
