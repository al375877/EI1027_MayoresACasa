package es.uji.ei1027.Mayorescasa.dao;

import es.uji.ei1027.Mayorescasa.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Date;
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
    public void addBeneficiario(String nombre, String dni, String tipoDieta) {
        jdbcTemplate.update("INSERT INTO Beneficiario VALUES (?,?,?,?)",
                nombre, dni, tipoDieta, null);
    }

    //ACTUALIZAMOS Beneficiario
    public void updateBeneficiario(Beneficiario beneficiario) {
        jdbcTemplate.update("UPDATE Beneficiario SET asistente=? WHERE dni=?",
                beneficiario.getAsistente(), beneficiario.getDni());

    }

    //BORRAMOS Usuario
    public void deleteUsuario(String usuario) {
        jdbcTemplate.update("DELETE FROM Usuario WHERE usuario=?", usuario);
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
    public List<Voluntario> getVoluntariosVisibles() {
        try{
            return jdbcTemplate.query("SELECT * FROM voluntario where visible='si'", new
                    VoluntarioRowMapper());
        }
        catch (EmptyResultDataAccessException e){
            return  new ArrayList<Voluntario>();
        }
    }
    public String getVisibilidad(String dni) {
        Voluntario voluntario;
        try{
            voluntario= jdbcTemplate.queryForObject("SELECT * FROM voluntario where dni=?", new
                    VoluntarioRowMapper(),dni);

            return voluntario.getVisible();
        }
        catch (EmptyResultDataAccessException e){
            return  null;
        }
    }

    //Get Beneficiario
    public Beneficiario getBeneficiario (String dni ){
        try{
            return jdbcTemplate.queryForObject("SELECT * FROM beneficiario WHERE dni=?", new BeneficiarioRowMapper(), dni);
        }
        catch (EmptyResultDataAccessException e){
            return null;
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

    public void setVisibilidad(String dniVol,String visible){
        jdbcTemplate.update("UPDATE Voluntario SET visible=? WHERE dni=?",
                visible, dniVol);

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

    public List<Usuario> getUsuariosVoluntarios() {
        try{
            return jdbcTemplate.query("SELECT * FROM usuario WHERE tipoUsuario='Voluntario'", new
                    UsuarioRowMapper());
        }
        catch (EmptyResultDataAccessException e){
            return  new ArrayList<Usuario>();
        }
    }

    public Voluntario getVoluntario(String dni){
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM voluntario WHERE dni=?", new VoluntarioRowMapper(), dni);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public void updateEstadoVoluntario(Voluntario voluntario){
        jdbcTemplate.update("UPDATE Voluntario SET estado=? WHERE dni = ?", voluntario.getEstado(), voluntario.getDni());
    }

    //AÑADIMOS Factura
    public void addFactura(String codigo, Date fecha, double precio, String concepto, String dniBen) {
        jdbcTemplate.update("INSERT INTO Factura VALUES (?,?,?,?,?)",
                codigo, fecha, precio, concepto, dniBen);
    }

    public List<Asistente> getAsistenteBenef (String nombre ){
        try{
            System.out.println(nombre);
            return jdbcTemplate.query("SELECT * FROM asistente WHERE nombre=?", new AsistenteRowMapper(), nombre);
        }
        catch (EmptyResultDataAccessException e){
            return new ArrayList<Asistente>();
        }
    }

    //LISTAMOS asistentes
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
