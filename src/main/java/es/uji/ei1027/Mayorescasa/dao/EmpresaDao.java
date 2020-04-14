package es.uji.ei1027.Mayorescasa.dao;
import es.uji.ei1027.Mayorescasa.model.Empresa;
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
                empresa.getNumero_telefono(),empresa.getServicio());
    }

    //BORRAMOS empresa
    public void deleteEmpresa(String empresa) {
        jdbcTemplate.update("DELETE FROM empresa WHERE usuario=?",empresa);
    }

    //ACTUALIZAMOS empresa (No se actualiza usuario y dni por claves primaria)
    public void updateEmpresa(Empresa empresa){
        jdbcTemplate.update("UPDATE empresa SET nombre=?, cif=?, persona_contacto=?, contraseña=?, " +
                        "email=?, direccion=?, telefono=? ,tipoServicio=?" +
                        "WHERE usuario=? ",
                empresa.getNombre(),empresa.getCif(), empresa.getPersona_contacto(),
                empresa.getContraseña(), empresa.getEmail(),empresa.getDireccion(),
                empresa.getNumero_telefono(),empresa.getServicio(), empresa.getUsuario());
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

}
