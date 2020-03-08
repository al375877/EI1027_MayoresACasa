package es.uji.ei1027.Mayorescasa.dao;

import es.uji.ei1027.Mayorescasa.model.Factura;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class FacturaDao {
    private JdbcTemplate jdbcTemplate;

    //Obtenermos el jbcTemplate a partir del Data Source
    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    //AÑADIMOS Factura
    public void addFactura(Factura factura) {
        jdbcTemplate.update("INSERT INTO Factura VALUES (?,?,?)",
                factura.getCod_fac(), factura.getFecha(), factura.getPrecio());
    }

    //BORRAMOS Factura
    public void deleteFactura(String cod_fac) {
        jdbcTemplate.update("DELETE FROM Factura WHERE cod_fac=?", cod_fac);
    }

    //ACTUALIZAMOS Factura
    public void updateFactura(Factura factura) {
        jdbcTemplate.update("UPDATE Factura SET fecha=?, precio=? WHERE cod_fac=?",
                factura.getFecha(), factura.getPrecio(), factura.getCod_fac());
    }

    public Factura getFactura(String cod_fac) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM Factura WHERE cod_fac=?", new FacturaRowMapper(), cod_fac);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    //LISTAMOS Asistente
    public List<Factura> getFactura() {
        try {
            return jdbcTemplate.query("SELECT * FROM Factura", new
                    FacturaRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<Factura>();
        }
    }
}
