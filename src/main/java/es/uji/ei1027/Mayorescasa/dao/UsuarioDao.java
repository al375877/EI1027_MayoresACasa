package es.uji.ei1027.Mayorescasa.dao;

import es.uji.ei1027.Mayorescasa.model.Usuario;
import es.uji.ei1027.Mayorescasa.model.Voluntario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository  //En Spring los DAOs van anotados con @Repository
public class UsuarioDao {
    private JdbcTemplate jdbcTemplate;

    //Obtenermos el jbcTemplate a partir del Data Source
    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate=new JdbcTemplate(dataSource);
    }
    //AÑADIMOS Usuario
    public void addUsuario(Usuario usuario) {
        jdbcTemplate.update("INSERT INTO Usuario VALUES (?,?,?,?,?,?,?,?,?,?,?)",
                usuario.getNombre(),usuario.getUsuario(),usuario.getContraseña(),
                usuario.getEmail(),usuario.getDireccion(),usuario.getDni(),
                usuario.getGenero(),usuario.getNacimiento(),usuario.getTelefono(),
                usuario.getTipoUsuario(),usuario.getTipodieta());
    }
    //AÑADIMOS Voluntario
    public void addVoluntario(String dni, String hobbies, String dias_semana) {
        jdbcTemplate.update("INSERT INTO Voluntario VALUES (?,?,?)",
                dni,hobbies,dias_semana);
    }

    //AÑADIMOS Beneficiario
    public void addBeneficiario(String dni, String tipoDieta, String registro) {
        jdbcTemplate.update("INSERT INTO Beneficiario VALUES (?,?,?)",
                dni, tipoDieta, registro);
    }

    //ACTUALIZAMOS Beneficiario
    public void updateBeneficiario(String dni, String registro) {
        jdbcTemplate.update("UPDATE Beneficiario SET registro=? WHERE dni=?",
                registro, dni);
    }

    //BORRAMOS Usuario
    public void deleteUsuario(String dni) {
        jdbcTemplate.update("DELETE FROM Usuario WHERE dni=?", dni);
    }

    //ACTUALIZAMOS Usuario
    public void updateUsuario(Usuario usuario){
        jdbcTemplate.update("UPDATE Usuario SET nombre=?, usuario=?," +
                        "contraseña=?, email=?, direccion=?, genero=?, " +
                        "nacimiento=?, telefono=?, tipousuario=?, tipodieta=? WHERE dni=?",
                usuario.getNombre(),usuario.getUsuario(),usuario.getContraseña(),
                usuario.getEmail(),usuario.getDireccion(),usuario.getGenero(),
                usuario.getNacimiento(),usuario.getTelefono(), usuario.getTipoUsuario(),
                usuario.getTipodieta(),usuario.getDni());
    }
    public Usuario getUsuario (String name ){
        try{
            return jdbcTemplate.queryForObject("SELECT * FROM usuario WHERE usuario=?", new UsuarioRowMapper(), name);
        }
        catch (EmptyResultDataAccessException e){
            return null;
        }
    }
    public Usuario getUsuarioDni (String dni ){
        try{
            return jdbcTemplate.queryForObject("SELECT * FROM usuario WHERE dni=?", new UsuarioRowMapper(), dni);
        }
        catch (EmptyResultDataAccessException e){
            return null;
        }
    }
    //LISTAMOS Voluntarios
    public List<Voluntario> getVoluntarios() {
        try{
           return jdbcTemplate.query("SELECT * FROM voluntario", new
                    VoluntarioRowMapper());
        }
        catch (EmptyResultDataAccessException e){
            return  new ArrayList<Voluntario>();
        }
    }

    //LISTAMOS Beneficiarios
    public List<Usuario> getBeneficiarios() {
        try{
            return jdbcTemplate.query("SELECT * FROM usuario WHERE tipousuario=?", new
                    UsuarioRowMapper(), "Beneficiario");
        }
        catch (EmptyResultDataAccessException e){
            return  new ArrayList<Usuario>();
        }
    }

    public void setDisponibilidad(String dniVol){
        jdbcTemplate.update("UPDATE Voluntario SET esta_disponible=? WHERE dni=?",
                "no", dniVol);

    }

    //LISTAMOS Usuario
    public List<Usuario> getUsuarios() {
        try{
            return jdbcTemplate.query("SELECT * FROM usuario", new
                    UsuarioRowMapper());
        }
        catch (EmptyResultDataAccessException e){
            return  new ArrayList<Usuario>();
        }
    }
}
