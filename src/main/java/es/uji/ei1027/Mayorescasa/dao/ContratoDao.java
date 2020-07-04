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

@Repository
public class ContratoDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate=new JdbcTemplate(dataSource);
    }
    //AÑADIMOS Contrato
    public void addContrato(Contrato contrato) {
        jdbcTemplate.update("INSERT INTO contrato VALUES (?,?,?,?,?,?,?,?,?)",
                contrato.getEmpresa(),contrato.getCodcontrato(),
                contrato.getTiposervicio(),contrato.getFechainicial(),contrato.getFechafinal(),
                contrato.getPreciounidad(),contrato.getDias_semana(),contrato.getHorainicial(),
                contrato.getHorafinal()
        );
    }
    //Obtener servicio
    public Empresa getServicio(String nomEmpresa){
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM empresa WHERE nombre=?", new EmpresaRowMapper(), nomEmpresa );
        } catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    //Get Empresa
    public Empresa getEmpresa (String empresa ){
        try{
            return jdbcTemplate.queryForObject("SELECT * FROM empresa WHERE nombre=?", new EmpresaRowMapper(), empresa);
        }
        catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    //Busca contrato
    public Contrato getContrato(String empresa){
        try{
            return jdbcTemplate.queryForObject("SELECT * FROM contrato WHERE empresa=? AND fechafinal>=CURRENT_DATE", new ContratoRowMapper(), empresa);
        }
        catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    //LISTAMOS contrato
    public List<Contrato> getContratos() {
        try{
            return jdbcTemplate.query("SELECT * FROM contrato WHERE fechafinal>CURRENT_DATE", new
                    ContratoRowMapper());
        }
        catch (EmptyResultDataAccessException e){
            return  new ArrayList<Contrato>();
        }
    }
    //LISTAMOS contrato
    public List<Contrato> getPasados() {
        try{
            return jdbcTemplate.query("SELECT * FROM contrato WHERE fechafinal<=CURRENT_DATE ORDER BY fechafinal DESC", new
                    ContratoRowMapper());
        }
        catch (EmptyResultDataAccessException e){
            return  new ArrayList<Contrato>();
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

    //Finalizar contrato
    public void finalizarContrato(String codigo){
        jdbcTemplate.update("UPDATE Contrato SET fechafinal=CURRENT_DATE WHERE codcontrato=?",codigo);
    }

    //Finalizar servicios
    public void finalizarServicios(String codigo){
        jdbcTemplate.update("UPDATE Peticion SET fechafinal=CURRENT_DATE, estado='Finalizada' WHERE codcontrato=?",codigo);
    }

    public Contrato getContratoFinalizado(String codigo) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM contrato WHERE codcontrato=?", new ContratoRowMapper(), codigo);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }




}

