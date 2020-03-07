package es.uji.ei1027.Mayorescasa.dao;
import es.uji.ei1027.Mayorescasa.model.Compañia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository  //En Spring los DAOs van anotados con @Repository
public class CompañiaDao {
    private JdbcTemplate jdbcTemplate;

    //Obtenermos el jbcTemplate a partir del Data Source
    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate=new JdbcTemplate(dataSource);
    }
    //AÑADIMOS Compañia
    public void addBeneficiario(Compañia compañia) {
        jdbcTemplate.update("INSERT INTO compañia VALUES (?,?,?,?,?,?,?,?,?)",
                compañia.getNombre(),compañia.getCif(),compañia.getPersona_contacto(),
                compañia.getUsuario(),compañia.getContraseña(),compañia.getEmail(),compañia.getDireccion(),
                compañia.getNumero_telefono(),compañia.getServicio());
    }

    //BORRAMOS compañia
    public void deleteVoluntario(String compañia) {
        jdbcTemplate.update("DELETE FROM compañia WHERE usuario=?",compañia);
    }

    //ACTUALIZAMOS compañia (No se actualiza usuario y dni por claves primaria)
    public void updateCompañia(Compañia compañia){
        jdbcTemplate.update("UPDATE beneficiario SET nombre=?, cif=?, persona_contacto=?, contraseña=?, " +
                        "email=?, direccion=?, numero_telefono=? ,servicio=?" +
                        "WHERE usuario=? ",
                compañia.getNombre(),compañia.getCif(), compañia.getPersona_contacto(),
                compañia.getContraseña(), compañia.getEmail(),compañia.getDireccion(),
                compañia.getNumero_telefono(),compañia.getServicio(), compañia.getUsuario());
    }

    public Compañia getCompañia (String compañia ){
        try{
            return jdbcTemplate.queryForObject("SELECT * FROM compañia WHERE usuario=?", new CompañiaRowMapper(),compañia);
        }
        catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    //LISTAMOS compañia
    public List<Compañia> getCompañia() {
        try{
            return jdbcTemplate.query("SELECT * FROM compañia", new
                    CompañiaRowMapper());
        }
        catch (EmptyResultDataAccessException e){
            return  new ArrayList<Compañia>();
        }
    }

}
