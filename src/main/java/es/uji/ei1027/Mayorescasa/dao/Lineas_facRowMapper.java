package es.uji.ei1027.Mayorescasa.dao;

import es.uji.ei1027.Mayorescasa.model.Lineas_fac;
import es.uji.ei1027.Mayorescasa.model.Peticion;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Lineas_facRowMapper implements RowMapper<Lineas_fac> {
    public Lineas_fac mapRow(ResultSet rs, int RowNum) throws SQLException {
        Lineas_fac lineas_fac = new Lineas_fac();
        lineas_fac.setCod_fac(rs.getString("cod_fac"));
        lineas_fac.setCod_pet(rs.getString("cod_pet"));
        lineas_fac.setLinea(rs.getInt("linea"));
        lineas_fac.setTiposervicio(rs.getString("tiposervicio"));
        lineas_fac.setPrecioservicio(rs.getDouble("precioservicio"));
        return lineas_fac;
    }

}
