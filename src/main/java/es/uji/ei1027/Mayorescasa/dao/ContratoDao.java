package es.uji.ei1027.Mayorescasa.dao;

import es.uji.ei1027.Mayorescasa.model.Contrato;
import es.uji.ei1027.Mayorescasa.model.Empresa;
import es.uji.ei1027.Mayorescasa.model.Peticion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository  //En Spring los DAOs van anotados con @Repository
public class ContratoDao {
    private JdbcTemplate jdbcTemplate;

    //Obtenermos el jbcTemplate a partir del Data Source
    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate=new JdbcTemplate(dataSource);
    }
    //AÑADIMOS Contrato
    public void addContrato(Contrato contrato) {
        jdbcTemplate.update("INSERT INTO contrato VALUES (?,?,?,?,?,?,?,?)",
                contrato.getEmpresa(),contrato.getCod_pet(),contrato.getcodcontrato(),
                contrato.gettiposervicio(),contrato.getfechafinal(),contrato.getfechafinal(),
                contrato.getcantidadservicios(),contrato.getpreciounidad()
        );
    }

    //BORRAMOS Contrato
    public void deleteContrato(String empresa) {
        jdbcTemplate.update("DELETE FROM contrato WHERE empresa=?", empresa);
    }

    //ACTUALIZAMOS Contrato (No se actualiza usuario y dni por claves primaria)
    public void updateContrato(Contrato contrato){
        jdbcTemplate.update("UPDATE contrato SET cod_pet=?, codcontrato=?, tiposervicio=?, " +
                        "fechainicial=?, fechafinal=?, cantidadservicios=?, preciounidad=?" +
                        "WHERE empresa=? ",
                contrato.getCod_pet(),contrato.getcodcontrato(),
                contrato.gettiposervicio(),contrato.getfechafinal(),contrato.getfechafinal(),
                contrato.getcantidadservicios(),contrato.getpreciounidad(), contrato.getEmpresa()
        );

    }

    public Contrato getContrato (String contrato ){
        try{
            return jdbcTemplate.queryForObject("SELECT * FROM contrato WHERE usuario=?", new ContratoRowMapper(),contrato);
        }
        catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    //LISTAMOS contrato
    public List<Contrato> getContrato() {
        try{
            return jdbcTemplate.query("SELECT * FROM contrato", new
                    ContratoRowMapper());
        }
        catch (EmptyResultDataAccessException e){
            return  new ArrayList<Contrato>();
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
    //LISTAMOS Peticion
    public List<Peticion> getPeticiones() {
        try {
            return jdbcTemplate.query("SELECT * FROM Peticion", new
                    PeticionRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<Peticion>();
        }
    }


}

