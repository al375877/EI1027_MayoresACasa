package es.uji.ei1027.Mayorescasa.dao;

import es.uji.ei1027.Mayorescasa.model.Lineas_fac;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class Lineas_facDao {
    private JdbcTemplate jdbcTemplate;

    //Obtenermos el jbcTemplate a partir del Data Source
    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate=new JdbcTemplate(dataSource);
    }
    //AÑADIMOS Lineas_fac
    public void addLineas_fac(Lineas_fac lineas_fac) {
        jdbcTemplate.update("INSERT INTO lineas_fac VALUES (?,?,?,?,?)",
                lineas_fac.getCod_fac(),lineas_fac.getCod_pet(),lineas_fac.getLinea(),
                lineas_fac.getTiposervicio(),lineas_fac.getPrecioservicio()
        );
    }

    //BORRAMOS Lineas_fac
    public void deleteLineas_fac(String cod_fac) {
        jdbcTemplate.update("DELETE FROM lineas_fac WHERE cod_fac=?", cod_fac);
    }

    //ACTUALIZAMOS Lineas_fac (No se actualiza usuario y dni por claves primaria)
    public void updateLineas_fac(Lineas_fac lineas_fac){
        jdbcTemplate.update("UPDATE lineas_fac SET cod_pet=?, linea=?, tiposervicio=?" +
                        "precioservicio=? WHERE cod_fac=? "
                ,lineas_fac.getCod_pet(),lineas_fac.getLinea(),
                lineas_fac.getTiposervicio(),lineas_fac.getPrecioservicio(), lineas_fac.getCod_fac()

        );

    }

    public Lineas_fac getLineas_fac (String lineas_fac ){
        try{
            return jdbcTemplate.queryForObject("SELECT * FROM lineas_fac WHERE cod_fac=?", new Lineas_facRowMapper(),lineas_fac);
        }
        catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    //LISTAMOS lineas_fac
    public List<Lineas_fac> getLineas_fac() {
        try{
            return jdbcTemplate.query("SELECT * FROM lineas_fac", new
                    Lineas_facRowMapper());
        }
        catch (EmptyResultDataAccessException e){
            return  new ArrayList<Lineas_fac>();
        }
    }
}
