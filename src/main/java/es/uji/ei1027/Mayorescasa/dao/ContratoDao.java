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
        jdbcTemplate.update("INSERT INTO contrato VALUES (?,?,?,?,?,?,?,?,?)",
                contrato.getEmpresa(),contrato.getCodcontrato(),
                contrato.getTiposervicio(),contrato.getFechainicial(),contrato.getFechafinal(),
                contrato.getPreciounidad(),contrato.getDias_semana(),contrato.getHorainicial(),
                contrato.getHorafinal()
        );
    }

    //BORRAMOS Contrato
    public void deleteContrato(String codcontrato) {
        jdbcTemplate.update("DELETE FROM contrato WHERE codcontrato=?", codcontrato);
    }

    //ACTUALIZAMOS Contrato (No se actualiza usuario y dni por claves primaria)
    public void updateContrato(Contrato contrato){
        jdbcTemplate.update("UPDATE contrato SET codcontrato=?, tiposervicio=?, " +
                        "fechainicial=?, fechafinal=?, preciounidad=?, dias_semana=?, " +
                        "horainicial=?, horafinal=? WHERE empresa=? ",
                contrato.getCodcontrato(), contrato.getTiposervicio(),contrato.getFechainicial(),
                contrato.getFechafinal(), contrato.getPreciounidad(),contrato.getDias_semana(),
                contrato.getHorainicial(), contrato.getHorafinal(), contrato.getEmpresa()
        );

    }

    public Contrato getContrato (String cod ){
        try{
            return jdbcTemplate.queryForObject("SELECT * FROM contrato WHERE codcontrato=?", new ContratoRowMapper(),cod);
        }
        catch (EmptyResultDataAccessException e){
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
    //Existe Servicio-Empresa
    public boolean existeServicio(String nombre, String servicio){
        List<String> empServicios= new ArrayList<>();
        try{
            List<Empresa> empresas=jdbcTemplate.query("SELECT * FROM empresa WHERE nombre=?", new
                    EmpresaRowMapper(), nombre);
            for(Empresa empresa : empresas){
                empServicios.add(empresa.getTiposervicio());
            }
            if (empServicios.contains(servicio)) return true;
        }
        catch (EmptyResultDataAccessException e){
            return  false;
        }
        return false;
    }

    //get a partir de una empresa
    public Contrato getContratoE (String empresa, String servicio ){
        try{
            return jdbcTemplate.queryForObject("SELECT * FROM contrato WHERE empresa=? AND fechafinal>CURRENT_DATE AND tiposervicio=?", new ContratoRowMapper(),empresa,servicio);
        }
        catch (EmptyResultDataAccessException e){
            return null;
        }
    }
    //LISTAMOS contrato
    public List<Contrato> getContratos() {
        try{
            return jdbcTemplate.query("SELECT * FROM contrato WHERE fechafinal>=CURRENT_DATE", new
                    ContratoRowMapper());
        }
        catch (EmptyResultDataAccessException e){
            return  new ArrayList<Contrato>();
        }
    }
    //LISTAMOS contrato
    public List<Contrato> getPasados() {
        try{
            return jdbcTemplate.query("SELECT * FROM contrato WHERE fechafinal<CURRENT_DATE ORDER BY fechafinal DESC", new
                    ContratoRowMapper());
        }
        catch (EmptyResultDataAccessException e){
            return  new ArrayList<Contrato>();
        }
    }

    //LISTAMOS empresa por categoria
    public List<String> getEmpresasC(String categoria) {
        List<String> empresasNombre= new ArrayList<>();

        try{
            List<Empresa> empresas=jdbcTemplate.query("SELECT * FROM empresa where tiposervicio=?", new
                    EmpresaRowMapper(),categoria);
            for(Empresa empresa : empresas){
                empresasNombre.add(empresa.getNombre());

            }
            return empresasNombre;
        }
        catch (EmptyResultDataAccessException e){
            return  empresasNombre;
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

