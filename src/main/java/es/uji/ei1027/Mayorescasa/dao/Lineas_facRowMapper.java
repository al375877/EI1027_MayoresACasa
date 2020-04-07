package es.uji.ei1027.Mayorescasa.dao;

import es.uji.ei1027.Mayorescasa.model.Lineas_fac;
import es.uji.ei1027.Mayorescasa.model.Peticion;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Lineas_facRowMapper implements RowMapper<Lineas_fac> {
    public Lineas_fac mapRow(ResultSet rs, int RowNum) throws SQLException {
        Lineas_fac lineas_fac = new Lineas_fac();
        lineas_fac.setCod_fac(rs.getString("Codigo de factura"));
        lineas_fac.setCod_pet(rs.getString("Codigo de peticion"));
        lineas_fac.setLinea(rs.getInt("Linea"));
        lineas_fac.setTiposervicio(rs.getString("Tipo de servicio"));
        lineas_fac.setPrecioservicio(rs.getDouble("Precision"));
        return lineas_fac;
    }

}
