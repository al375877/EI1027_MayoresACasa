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
public class PeticionDao {
    private JdbcTemplate jdbcTemplate;

    //Obtenermos el jbcTemplate a partir del Data Source
    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate=new JdbcTemplate(dataSource);
    }

    //AÑADIMOS Peticion
    public void addPeticion(Peticion peticion) {
        jdbcTemplate.update("INSERT INTO Peticion VALUES (?,?,?,?,?,?,?,?,?,?,?,?)",
                peticion.getCod_pet(), peticion.getTiposervicio(), peticion.getPrecioservicio(),
                peticion.getFechaaceptada(), peticion.getFecharechazada(), peticion.getFechafinal(),peticion.getEstado(), peticion.getComentarios(),
                peticion.getDni_ben(),peticion.getBeneficiario(),peticion.getCodcontrato(),peticion.getEmpresa()
        );

    }

    //ACTUALIZAMOS Peticion
    public void updatePeticion(Peticion peticion) {
        jdbcTemplate.update("UPDATE peticion SET fechafinal=?, comentarios=?, empresa=? WHERE cod_pet=? ",
                peticion.getFechafinal(), peticion.getComentarios(), peticion.getEmpresa(), peticion.getCod_pet()
        );
    }
    public void updateEstadoFechaEmpresa(String estado, Date fecha, String empresa, String codigo, String codcontrato) {
        jdbcTemplate.update("UPDATE peticion SET estado=?, fechaaceptada=?, empresa=?, codcontrato=? WHERE cod_pet=? ", estado, fecha, empresa, codcontrato, codigo
        );
    }
    public void updateEstadoFecha(String estado, Date fecha, String codigo) {
        jdbcTemplate.update("UPDATE peticion SET estado=?, fecharechazada=? WHERE cod_pet=? ", estado, fecha, codigo
        );
    }

    public Peticion getPeticion(String cod) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM Peticion WHERE cod_pet=?", new PeticionRowMapper(), cod);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    //LISTAMOS Peticion Pendientes
    public List<Peticion> getPeticionesPendientes() {
        try {
            return jdbcTemplate.query("SELECT * FROM Peticion WHERE estado='Pendiente'", new
                    PeticionRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<Peticion>();
        }
    }

    //LISTAMOS Peticion Resueltas
    public List<Peticion> getPeticionesAceptadas() {
        try {
            return jdbcTemplate.query("SELECT * FROM Peticion WHERE estado='Aceptada'", new
                    PeticionRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<Peticion>();
        }
    }
    public List<Peticion> getPeticionesRechazadas() {
        try {
            return jdbcTemplate.query("SELECT * FROM Peticion WHERE estado='Rechazada'", new
                    PeticionRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<Peticion>();
        }
    }

    //LISTAMOS Peticion
    public List<Peticion> getPeticionesPropiasPendientes(String dni) {
        try {
            return jdbcTemplate.query("SELECT * FROM Peticion WHERE dni_ben=? AND estado='Pendiente'", new
                    PeticionRowMapper(), dni);
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<Peticion>();
        }
    }
    public List<Peticion> getPeticionesPropiasAceptadas(String dni) {
        try {
            return jdbcTemplate.query("SELECT * FROM Peticion WHERE dni_ben=? AND estado='Aceptada'", new
                    PeticionRowMapper(), dni);
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<Peticion>();
        }
    }
    public List<Peticion> getPeticionesPropiasRechazadas(String dni) {
        try {
            return jdbcTemplate.query("SELECT * FROM Peticion WHERE dni_ben=? AND (estado='Rechazada' OR estado='Finalizada')", new
                    PeticionRowMapper(), dni);
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<Peticion>();
        }
    }

    public boolean consultaPeticion(String dni,String tipoPeticion){
        Peticion peticion;
        try {
             peticion = jdbcTemplate.queryForObject("SELECT * FROM Peticion WHERE dni_ben=? and tiposervicio=? and (estado='Pendiente' or estado='Aceptada')",
                    new PeticionRowMapper(), dni, tipoPeticion);
        }catch (EmptyResultDataAccessException e){
            peticion=null;
        }
        return peticion!=null;
    }

    public void addLineas_fac(String codFac, String codPet, int linea, String tipoServicio, double precioServicio) {
        jdbcTemplate.update("INSERT INTO lineas_fac VALUES (?,?,?,?,?)",
               codFac, codPet, linea, tipoServicio, precioServicio
        );
    }

    public Empresa getEmpresa (String nombre ){
        try{
            return jdbcTemplate.queryForObject("SELECT * FROM empresa WHERE nombre=?", new EmpresaRowMapper(), nombre);
        }
        catch (EmptyResultDataAccessException e){
            return null;
        }
    }
    public Usuario getUsuario (String dni ){
        try{
            return jdbcTemplate.queryForObject("SELECT * FROM usuario WHERE dni=?", new UsuarioRowMapper(), dni);
        }
        catch (EmptyResultDataAccessException e){
            return null;
        }
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

}
