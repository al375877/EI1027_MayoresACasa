package es.uji.ei1027.Mayorescasa.dao;
import es.uji.ei1027.Mayorescasa.model.Contrato;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ContratoRowMapper implements RowMapper<Contrato>{
    public Contrato mapRow(ResultSet rs, int RowNum) throws SQLException {
        Contrato contrato = new Contrato();
        contrato.setEmpresa(rs.getString("empresa"));
        contrato.setCodcontrato(rs.getString("codcontrato"));
        contrato.setTiposervicio(rs.getString("tiposervicio"));
        contrato.setFechainicial(rs.getDate("fechainicial"));
        contrato.setFechafinal(rs.getDate("fechafinal"));
        contrato.setCantidadservicios(rs.getInt("cantidadservicios"));
        contrato.setPreciounidad(rs.getDouble("preciounidad"));
        return contrato;

    }
}
