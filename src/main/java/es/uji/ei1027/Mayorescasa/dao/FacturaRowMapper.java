package es.uji.ei1027.Mayorescasa.dao;

import es.uji.ei1027.Mayorescasa.model.Factura;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class FacturaRowMapper implements RowMapper<Factura> {
    public Factura mapRow(ResultSet rs, int RowNum) throws SQLException {
        Factura factura = new Factura();
        factura.setCod_fac(rs.getString("codigo factura"));
        factura.setFecha(rs.getDate("fecha"));
        factura.setPrecio(rs.getFloat("precio"));
        return factura;
    }
}
