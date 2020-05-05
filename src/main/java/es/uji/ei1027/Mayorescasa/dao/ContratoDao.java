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
import java.util.Date;
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
        jdbcTemplate.update("INSERT INTO contrato VALUES (?,?,?,?,?,?,?)",
                contrato.getEmpresa(),contrato.getCodcontrato(),
                contrato.getTiposervicio(),contrato.getFechafinal(),contrato.getFechafinal(),
                contrato.getCantidadservicios(),contrato.getPreciounidad()
        );
    }

    //BORRAMOS Contrato
    public void deleteContrato(String codcontrato) {
        jdbcTemplate.update("DELETE FROM contrato WHERE codcontrato=?", codcontrato);
    }

    //ACTUALIZAMOS Contrato (No se actualiza usuario y dni por claves primaria)
    public void updateContrato(Contrato contrato){
        jdbcTemplate.update("UPDATE contrato SET codcontrato=?, tiposervicio=?, " +
                        "fechainicial=?, fechafinal=?, cantidadservicios=?, preciounidad=?" +
                        "WHERE empresa=? ",
                contrato.getCodcontrato(),
                contrato.getTiposervicio(),contrato.getFechafinal(),contrato.getFechafinal(),
                contrato.getCantidadservicios(),contrato.getPreciounidad(), contrato.getEmpresa()
        );

    }
    //ACTUALIZAR FECHAS
    public void  updateAdd(Date fechaInicial, Date fechaFinal, int preciounidad, String codcontrato){
        jdbcTemplate.update("UPDATE contrato SET fechainicial=?, fechafinal=?, preciounidad=? WHERE codcontrato=? ",
                fechaInicial, fechaFinal, preciounidad, codcontrato);
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
    public List<Contrato> getContratos() {
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

