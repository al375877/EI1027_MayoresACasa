package es.uji.ei1027.Mayorescasa.dao;

import es.uji.ei1027.Mayorescasa.model.Beneficiario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository  //En Spring los DAOs van anotados con @Repository
public class BeneficiarioDao {

    private JdbcTemplate jdbcTemplate;

    //Obtenermos el jbcTemplate a partir del Data Source
    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate=new JdbcTemplate(dataSource);
    }
    //AÑADIMOS BENEFICIARIO
    public void addBeneficiario(Beneficiario beneficiario) {
        jdbcTemplate.update("INSERT INTO Beneficiario VALUES (?,?,?,?,?,?,?,?,?,?,?)",
                beneficiario.getNombre(),beneficiario.getDni(),beneficiario.getGenero(),
                beneficiario.getEdad(),beneficiario.getUsuario(),beneficiario.getContraseña(),
                beneficiario.getEmail(),beneficiario.getDireccion(),beneficiario.getUsuario_asis(),
                beneficiario.getFecha_nacimiento(),beneficiario.getNumero_telefono());
    }

    //BORRAMOS BENEFICIARIO
    public void deleteBeneficiario(String beneficiario) {
        jdbcTemplate.update("DELETE FROM Beneficiario WHERE usuario=?",beneficiario);
    }

    //ACTUALIZAMOS BENEFICIARIO (No se actualiza usuario y dni por claves primaria)
    public void updateBeneficiario(Beneficiario beneficiario){
        jdbcTemplate.update("UPDATE beneficiario SET nombre=?, genero=?, edad=?, contraseña=?, " +
                        "email=?, direccion=?, usuario_asis=?, fecha_nacimiento=?, numero_telefono=? " +
                        "WHERE usuario=? ",
                beneficiario.getNombre(),beneficiario.getGenero(), beneficiario.getEdad(),
                beneficiario.getContraseña(), beneficiario.getEmail(),beneficiario.getDireccion(),
                beneficiario.getUsuario_asis(), beneficiario.getFecha_nacimiento(),
                beneficiario.getNumero_telefono(), beneficiario.getUsuario());
    }

    public Beneficiario getBeneficiario (String usuario ){
        try{
            return jdbcTemplate.queryForObject("SELECT * FROM beneficiario WHERE usuario=?", new
                    BeneficiarioRowMapper(),usuario);
        }
        catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    //LISTAMOS BENEFICIARIOS
    public List<Beneficiario> getBeneficiarios() {
        try{
            return jdbcTemplate.query("SELECT * FROM beneficiario", new
                    BeneficiarioRowMapper());
        }
        catch (EmptyResultDataAccessException e){
            return  new ArrayList<Beneficiario>();
        }
    }



}
