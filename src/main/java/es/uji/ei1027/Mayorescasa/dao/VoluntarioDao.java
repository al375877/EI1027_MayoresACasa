package es.uji.ei1027.Mayorescasa.dao;

import es.uji.ei1027.Mayorescasa.model.Voluntario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository  //En Spring los DAOs van anotados con @Repository
public class VoluntarioDao {
    private JdbcTemplate jdbcTemplate;

    //Obtenermos el jbcTemplate a partir del Data Source
    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate=new JdbcTemplate(dataSource);
    }
    //AÑADIMOS Voluntario
    public void addVoluntario(Voluntario voluntario) {
        jdbcTemplate.update("INSERT INTO Voluntario VALUES (?,?,?,?,?,?,?,?,?,?)",
                voluntario.getNombre(),voluntario.getDni(),voluntario.getGenero(),
                voluntario.getUsuario(),voluntario.getContraseña(),
                voluntario.getEmail(),voluntario.getDireccion(),
                voluntario.getFecha_nacimiento(),voluntario.getNumero_telefono(),voluntario.getHobbie());
    }

    //BORRAMOS Voluntario
    public void deleteVoluntario(String voluntario) {
        jdbcTemplate.update("DELETE FROM Voluntario WHERE usuario=?",voluntario);
    }

    //ACTUALIZAMOS Voluntario (No se actualiza usuario y dni por claves primaria)
    public void updateVoluntario(Voluntario voluntario){
        jdbcTemplate.update("UPDATE voluntario SET nombre=?, genero=?, contraseña=?, " +
                        "email=?, direccion=?, fechanacimiento=?, telefono=? ,hobbies=?" +
                        "WHERE usuario=? ",
                voluntario.getNombre(),voluntario.getGenero(),
                voluntario.getContraseña(), voluntario.getEmail(),voluntario.getDireccion(),
                voluntario.getFecha_nacimiento(),
                voluntario.getNumero_telefono(),voluntario.getHobbie(), voluntario.getUsuario());
    }

    public Voluntario getVoluntario (String voluntario ){
        try{
            return jdbcTemplate.queryForObject("SELECT * FROM voluntario WHERE usuario=?", new VoluntarioRowMapper(),voluntario);
        }
        catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    //LISTAMOS Voluntario
    public List<Voluntario> getVoluntario() {
        try{
            return jdbcTemplate.query("SELECT * FROM voluntario", new
                    VoluntarioRowMapper());
        }
        catch (EmptyResultDataAccessException e){
            return  new ArrayList<Voluntario>();
        }
    }
}
