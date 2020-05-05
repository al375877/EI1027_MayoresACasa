package es.uji.ei1027.Mayorescasa.dao;
import es.uji.ei1027.Mayorescasa.model.Contrato;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ContratoRowMapper implements RowMapper<Contrato>{
    public Contrato mapRow(ResultSet rs, int RowNum) throws SQLException {
        Contrato contrato = new Contrato();
        contrato.setEmpresa(rs.getString("empresa"));
        contrato.setCod_pet(rs.getString("cod_pet"));
        contrato.setcodcontrato(rs.getString("codcontrato"));
        contrato.settiposervicio(rs.getString("tiposervicio"));
        contrato.setfechainicial(rs.getDate("fechainicial"));
        contrato.setfechafinal(rs.getDate("fechafinal"));
        contrato.setcantidadservicios(rs.getInt("cantidadservicios"));
        contrato.setpreciounidad(rs.getDouble("preciounidad"));
        return contrato;

    }
}
