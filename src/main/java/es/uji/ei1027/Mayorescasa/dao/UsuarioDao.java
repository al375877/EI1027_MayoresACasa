package es.uji.ei1027.Mayorescasa.dao;

import es.uji.ei1027.Mayorescasa.model.Usuario;
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
        jdbcTemplate.update("INSERT INTO Usuario VALUES (?,?,?,?,?,?,?,?,?,?)",
                usuario.getNombre(),usuario.getUsuario(),usuario.getContraseña(),
                usuario.getEmail(),usuario.getDireccion(),usuario.getDni(),
                usuario.getGenero(),usuario.getNacimiento(),usuario.getTelefono(),
                usuario.getTipoUsuario());
    }
    //AÑADIMOS Voluntario
    public void addVoluntario(String dni, String hobbies) {
        jdbcTemplate.update("INSERT INTO Voluntario VALUES (?,?)",
                dni,hobbies);
    }
//
//    //BORRAMOS Usuario
//    public void deleteUsuario(String usuario) {
//        jdbcTemplate.update("DELETE FROM Usuario WHERE usuario=?",usuario);
//    }
//
//    //ACTUALIZAMOS Usuario (No se actualiza usuario y dni por claves primaria)
//    public void updateUsuario(Usuario usuario){
//        jdbcTemplate.update("UPDATE Usuario SET nombre=?, dni=?, " +
//                        "contraseña=?, email=?, beneficiario=?, telefono=? " +
//                        "WHERE usuario=? ",
//                usuario.getNombre(),usuario.getDni(),usuario.getContraseña(),
//                usuario.getMail(),usuario.getBeneficiario(), usuario.getTelefono(),
//                usuario.getUsuario());
//    }
    public Usuario getUsuario (String name ){
        try{
            return jdbcTemplate.queryForObject("SELECT * FROM usuario WHERE usuario=?", new UsuarioRowMapper(), name);
        }
        catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    //LISTAMOS Voluntarios
    public List<Usuario> getVoluntarios() {
        try{
            return jdbcTemplate.query("SELECT * FROM usuario WHERE tipousuario=?", new
                    UsuarioRowMapper(), "Voluntario");
        }
        catch (EmptyResultDataAccessException e){
            return  new ArrayList<Usuario>();
        }
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
