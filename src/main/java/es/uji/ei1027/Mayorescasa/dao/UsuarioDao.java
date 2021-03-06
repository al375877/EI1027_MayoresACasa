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
        try {
            jdbcTemplate.update("INSERT INTO Usuario VALUES (?,?,?,?,?,?,?,?,?,?,?)",
                    usuario.getNombre(), usuario.getUsuario(), usuario.getContraseña(),
                    usuario.getEmail(), usuario.getDireccion(), usuario.getDni(),
                    usuario.getGenero(), usuario.getNacimiento(), usuario.getTelefono(),
                    usuario.getTipoUsuario(), usuario.getTipodieta());
        } catch (EmptyResultDataAccessException e){
            return;
        }
    }
    //AÑADIMOS Voluntario
    public void addVoluntario(String dni, String hobbies, String dias_semana, String visible, String estado) {
        jdbcTemplate.update("INSERT INTO Voluntario VALUES (?,?,?,?,?)",
                dni, hobbies, dias_semana, estado, visible);
    }

    //Datos CasVolunteer
    public Usuario getCasVolunteer (){
        try{
            return jdbcTemplate.queryForObject("SELECT * FROM usuario WHERE tipoUsuario='casVolunteer'", new UsuarioRowMapper());
        }
        catch (EmptyResultDataAccessException e){
            return null;
        }
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

    //ACTUALIZAMOS Voluntario
    public void updateVoluntario(Voluntario voluntario) {
        jdbcTemplate.update("UPDATE Voluntario SET hobbies=?, dias_semana=? WHERE dni=?",
                voluntario.getHobbies(),voluntario.getDias_semana(),voluntario.getDni());

    }

    //devuelve una lista de todas las asignaciones de beneficiaros finalizadas
    public List<Disponibilidad> consultaBeneficiariosFinalizado(String dni){
        try{
            List<Disponibilidad>lista= jdbcTemplate.query("SELECT * FROM Disponibilidad WHERE dni_ben=? AND estado='Finalizada'",
                    new DisponibildadRowMapper(), dni);
            return lista;
        }catch (EmptyResultDataAccessException e){
            return new ArrayList<>();
        }
    }

    public List<Usuario> consultaBeneficiariosAceptados(String dni){
        try{
            List<Usuario>lista= jdbcTemplate.query("SELECT usuario.* FROM disponibilidad JOIN voluntario ON disponibilidad.dni_vol = voluntario.dni JOIN usuario USING(dni) WHERE disponibilidad.dni_ben=? AND disponibilidad.estado='Aceptada'",
                    new UsuarioRowMapper(), dni);
            return lista;
        }catch (EmptyResultDataAccessException e){
            return new ArrayList<>();
        }
    }

    public List<Voluntario> consultaBeneficiariosPendientes(String dni){
        try{
            List<Voluntario>lista= jdbcTemplate.query("SELECT voluntario.* FROM disponibilidad JOIN voluntario ON disponibilidad.dni_vol = voluntario.dni WHERE disponibilidad.dni_ben=? AND disponibilidad.estado='Pendiente'",
                    new VoluntarioRowMapper(), dni);
            return lista;
        }catch (EmptyResultDataAccessException e){
            return new ArrayList<>();
        }
    }

    //OCULTAMOS Usuario
    public void deleteUsuario(String dni) {
        jdbcTemplate.update("UPDATE Usuario SET tipousuario='Borrado' WHERE dni=?", dni);
    }

    //OCULTAMOS Voluntario
    public void deleteVoluntario(String dni) {
        jdbcTemplate.update("UPDATE Voluntario SET estado='Borrado', visible='no' WHERE dni=?", dni);
    }

    //Finalizamos Servicio
    public List<Peticion> consultarServicio(String dni) {
        try{
            return jdbcTemplate.query("SELECT * FROM peticion WHERE dni_ben=? AND estado='Aceptada'", new PeticionRowMapper(), dni);
        }
        catch (EmptyResultDataAccessException e){
            return  null;
        }
    }
    //OCULTAMOS Disponibilidad
    public void finalizaServicio(String dni) {
        jdbcTemplate.update("UPDATE Peticion SET estado='Finalizada' WHERE dni_ben=?", dni);
    }

    //OCULTAMOS Disponibilidad
    public void deleteDisponibilidad(String dni) {
        jdbcTemplate.update("UPDATE Disponibilidad SET estado='Borrado' WHERE dni_vol=?", dni);
    }

    //OCULTAMOS Disponibilidad
    public void deleteDisponibilidadBen(String dni) {
        jdbcTemplate.update("UPDATE Disponibilidad SET estado='Borrado' WHERE dni_ben=?", dni);
    }

    //Consultar Disponibilidad
    public List<Disponibilidad> consultarDisponibilidad(String dni) {
        try{
            return jdbcTemplate.query("SELECT * FROM disponibilidad WHERE dni_vol=? AND estado='Aceptada'", new DisponibildadRowMapper(), dni);
        }
        catch (EmptyResultDataAccessException e){
            return  null;
        }
    }

    //Consultar Disponibilidad
    public List<Disponibilidad> consultarDisponibilidadBen(String dni) {
        try{
            return jdbcTemplate.query("SELECT * FROM disponibilidad WHERE dni_ben=? AND estado='Aceptada'", new DisponibildadRowMapper(), dni);
        }
        catch (EmptyResultDataAccessException e){
            return  null;
        }
    }


    //Consultar Disponibilidad
    public List<Disponibilidad> consultarDisponibilidadPendiente(String dni) {
        try{
            return jdbcTemplate.query("SELECT * FROM disponibilidad WHERE dni_vol=? AND estado='Pendiente'", new DisponibildadRowMapper(), dni);
        }
        catch (EmptyResultDataAccessException e){
            return  null;
        }
    }

    //Consultar Disponibilidad
    public List<Disponibilidad> consultarDisponibilidadPendienteBen(String dni) {
        try{
            return jdbcTemplate.query("SELECT * FROM disponibilidad WHERE dni_ben=? AND estado='Pendiente'", new DisponibildadRowMapper(), dni);
        }
        catch (EmptyResultDataAccessException e){
            return  null;
        }
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
    public Usuario getUsuario (String usuario ){
        try{
            return jdbcTemplate.queryForObject("SELECT * FROM usuario WHERE usuario=?", new UsuarioRowMapper(), usuario);
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
    public List<Usuario> getVoluntariosUsers() {
        try{
            return jdbcTemplate.query("SELECT * FROM usuario JOIN voluntario USING(dni) WHERE usuario.tipousuario='Voluntario' AND voluntario.estado='Aceptado'", new
                    UsuarioRowMapper());
        }
        catch (EmptyResultDataAccessException e){
            return  new ArrayList<Usuario>();
        }
    }

    public List<Usuario> getVoluntariosPendientes() {
        try{
            return jdbcTemplate.query("SELECT usuario.* FROM voluntario FULL JOIN usuario USING(dni) WHERE voluntario.estado='Pendiente'", new
                    UsuarioRowMapper());
        }
        catch (EmptyResultDataAccessException e){
            return  new ArrayList<Usuario>();
        }
    }

    public List<Usuario> getVoluntariosAceptados() {
        try{
            return jdbcTemplate.query("SELECT usuario.* FROM voluntario FULL JOIN usuario USING(dni) WHERE voluntario.estado='Aceptado'", new
                    UsuarioRowMapper());
        }
        catch (EmptyResultDataAccessException e){
            return  new ArrayList<Usuario>();
        }
    }

    public List<Usuario> getVoluntariosRechazados() {
        try{
            return jdbcTemplate.query("SELECT usuario.* FROM voluntario FULL JOIN usuario USING(dni) WHERE voluntario.estado='Rechazado'", new
                    UsuarioRowMapper());
        }
        catch (EmptyResultDataAccessException e){
            return  new ArrayList<Usuario>();
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
