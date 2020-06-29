package es.uji.ei1027.Mayorescasa.dao;
import es.uji.ei1027.Mayorescasa.model.Asistente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;


@Repository
public class AsistenteDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate=new JdbcTemplate(dataSource);
    }

    public List<Asistente> getAsistentes() {
        try{
            return jdbcTemplate.query("SELECT * FROM asistente", new
                    AsistenteRowMapper());
        }
        catch (EmptyResultDataAccessException e){
            return  new ArrayList<Asistente>();
        }
    }
}
