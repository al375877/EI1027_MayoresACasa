package es.uji.ei1027.Mayorescasa.dao;
import es.uji.ei1027.Mayorescasa.model.Empresa;
import es.uji.ei1027.Mayorescasa.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository  //En Spring los DAOs van anotados con @Repository
public class EmpresaDao {
    private JdbcTemplate jdbcTemplate;

    //Obtenermos el jbcTemplate a partir del Data Source
    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate=new JdbcTemplate(dataSource);
    }
    //AÑADIMOS Empresa
    public void addEmpresa(Empresa empresa) {
        jdbcTemplate.update("INSERT INTO empresa VALUES (?,?,?,?,?,?,?,?,?)",
                empresa.getNombre(),empresa.getCif(),empresa.getPersona_contacto(),
                empresa.getUsuario(),empresa.getContraseña(),empresa.getEmail(),empresa.getDireccion(),
                empresa.getTelefono(),empresa.getTiposervicio());
    }

    //BORRAMOS empresa
    public void deleteEmpresa(String empresa) {
        jdbcTemplate.update("DELETE FROM empresa WHERE usuario=?",empresa);
    }

    //ACTUALIZAMOS empresa (No se actualiza usuario y dni por claves primaria)
    public void updateEmpresa(Empresa empresa){
        jdbcTemplate.update("UPDATE empresa SET nombre=?, cif=?, persona_contacto=?, contraseña=?, " +
                        "email=?, direccion=?, telefono=? ,tiposervicio=?" +
                        "WHERE usuario=? ",
                empresa.getNombre(),empresa.getCif(), empresa.getPersona_contacto(),
                empresa.getContraseña(), empresa.getEmail(),empresa.getDireccion(),
                empresa.getTelefono(),empresa.getTiposervicio(), empresa.getUsuario());
    }

    public Empresa getEmpresa (String empresa ){
        try{
            return jdbcTemplate.queryForObject("SELECT * FROM empresa WHERE usuario=?", new EmpresaRowMapper(),empresa);
        }
        catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    //LISTAMOS empresa
    public List<Empresa> getEmpresas() {
        try{
            return jdbcTemplate.query("SELECT * FROM empresa", new
                    EmpresaRowMapper());
        }
        catch (EmptyResultDataAccessException e){
            return  new ArrayList<Empresa>();
        }
    }

    //AÑADIMOS Usuario
    public void addUsuario(String nombre, String usuario, String contraseña, String email, String direccion,
    String dni, String genero, String nacimiento, int telefono, String tipoUsuario, String tipoDieta) {
        jdbcTemplate.update("INSERT INTO Usuario VALUES (?,?,?,?,?,?,?,?,?,?,?)",
                nombre, usuario, contraseña, email, direccion, dni, genero, nacimiento, telefono, tipoUsuario, tipoDieta );
    }

    public Usuario getUsuarioDni (String dni ){
        try{
            return jdbcTemplate.queryForObject("SELECT * FROM usuario WHERE dni=?", new UsuarioRowMapper(), dni);
        }
        catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    //BORRAMOS Usuario
    public void deleteUsuario(String usuario) {
        jdbcTemplate.update("DELETE FROM Usuario WHERE usuario=?", usuario);
    }

}
