package es.uji.ei1027.Mayorescasa.dao;
import es.uji.ei1027.Mayorescasa.model.Asistente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;


@Repository  //En Spring los DAOs van anotados con @Repository
public class AsistenteDao {
    private JdbcTemplate jdbcTemplate;

    //Obtenermos el jbcTemplate a partir del Data Source
    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate=new JdbcTemplate(dataSource);
    }
    //AÑADIMOS Asistente
    public void addAsistente(Asistente asistente) {
        jdbcTemplate.update("INSERT INTO Asistente VALUES (?,?,?,?,?,?,?)",
                asistente.getNombre(),asistente.getDni(),asistente.getUsuario(),asistente.getContraseña(),
                asistente.getEmail(),asistente.getBeneficiario(), asistente.getTelefono());
    }

    //BORRAMOS Asistente
    public void deleteAsistente(String asistente) {
        jdbcTemplate.update("DELETE FROM Asistente WHERE usuario=?",asistente);
    }

    //ACTUALIZAMOS Asistente (No se actualiza usuario y dni por claves primaria)
    public void updateAsistente(Asistente asistente){
        jdbcTemplate.update("UPDATE Asistente SET nombre=?, dni=?, " +
                        "contraseña=?, email=?, beneficiario=?, telefono=? " +
                        "WHERE usuario=? ",
                asistente.getNombre(),asistente.getDni(),asistente.getContraseña(),
                asistente.getEmail(),asistente.getBeneficiario(), asistente.getTelefono(),
                asistente.getUsuario());
    }
    public Asistente getAsistente (String usuario ){
        try{
            return jdbcTemplate.queryForObject("SELECT * FROM asistente WHERE usuario=?", new AsistenteRowMapper(),usuario);
        }
        catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    //LISTAMOS Asistente
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
