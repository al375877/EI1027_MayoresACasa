package es.uji.ei1027.Mayorescasa.dao;
import es.uji.ei1027.Mayorescasa.model.Contrato;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ContratoRowMapper implements RowMapper<Contrato>{
    public Contrato mapRow(ResultSet rs, int RowNum) throws SQLException {
        Contrato contrato = new Contrato();
        contrato.setEmpresa(rs.getString("Empresa"));
        contrato.setCod_pet(rs.getString("Codigo de peticion"));
        contrato.setCodContrato(rs.getString("Codigo de Contrato"));
        contrato.setTipoServicio(rs.getString("Tipo servicio"));
        contrato.setFechaInicial(rs.getDate("Fecha inicial"));
        contrato.setFechaFinal(rs.getDate("Fecha final"));
        contrato.setCantidadservicios(rs.getInt("Cantidad de Servicio"));
        contrato.setPrecioUnidad(rs.getDouble("Precio unidad"));
        return contrato;

    }
}
