package es.uji.ei1027.Mayorescasa.dao;

import es.uji.ei1027.Mayorescasa.model.Factura;
import es.uji.ei1027.Mayorescasa.model.Lineas_fac;
import es.uji.ei1027.Mayorescasa.model.Peticion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
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
        jdbcTemplate.update("INSERT INTO Peticion VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)",
                peticion.getCod_pet(), peticion.getTiposervicio(), peticion.getPrecioservicio(), peticion.getLinea(),
                peticion.getFechaaceptada(), peticion.getFecharechazada(), peticion.getFechafinal(),peticion.getEstado(), peticion.getComentarios(),
                peticion.getDni_ben(),peticion.getBeneficiario(),peticion.getCodcontrato(),peticion.getEmpresa()
        );

    }

    //BORRAMOS Peticion
    public void deletePeticion(String cod) {
        jdbcTemplate.update("DELETE FROM Peticion WHERE cod_pet=?", cod);
    }

    //ACTUALIZAMOS Peticion
    public void updatePeticion(Peticion peticion) {
        jdbcTemplate.update("UPDATE peticion SET tiposervicio=?, precioservicio=?, linea=?, fechaaceptada=?, fecharechazada=?," +
                        "fechafinal=?, estado=?,comentarios=?, dni_ben=?, beneficiario=?,codcontrato=?,empresa=? WHERE cod_pet=? ",
                peticion.getTiposervicio(), peticion.getPrecioservicio(), peticion.getLinea(),
                peticion.getFechaaceptada(), peticion.getFecharechazada(), peticion.getFechafinal(), peticion.getEstado(),
                peticion.getComentarios(), peticion.getDni_ben(),peticion.getBeneficiario(), peticion.getCodcontrato(),peticion.getEmpresa(),peticion.getCod_pet()
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
    public List<Peticion> getPeticionesResueltas() {
        try {
            return jdbcTemplate.query("SELECT * FROM Peticion WHERE estado='Aceptada' OR estado='Rechazada' ", new
                    PeticionRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<Peticion>();
        }
    }

    //LISTAMOS Peticion
    public List<Peticion> getPeticionesPropias(String dni) {
        try {
            return jdbcTemplate.query("SELECT * FROM Peticion WHERE dni_ben=?", new
                    PeticionRowMapper(), dni);
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<Peticion>();
        }
    }

    public boolean consultaPeticion(String dni,String tipoPeticion){
        Peticion peticion;
        try {
             peticion = jdbcTemplate.queryForObject("SELECT * FROM Peticion WHERE dni_ben=? and tiposervicio=?",
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

    public Factura getFactura(String dni_ben) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM Factura WHERE dni_ben=?", new FacturaRowMapper(), dni_ben);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public Factura getFacturaCod(String cod_fac) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM Factura WHERE cod_fac=?", new FacturaRowMapper(), cod_fac);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    //LISTAMOS lineas_fac
    public List<Lineas_fac> getLineas_fac( String cod_fac) {
        try{
            return jdbcTemplate.query("SELECT * FROM lineas_fac WHERE cod_fac=?", new
                    Lineas_facRowMapper(), cod_fac);
        }
        catch (EmptyResultDataAccessException e){
            return  new ArrayList<Lineas_fac>();
        }
    }

    //LISTAMOS Facturas
    public List<Factura> getFacturas() {
        try {
            return jdbcTemplate.query("SELECT * FROM Factura", new
                    FacturaRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<Factura>();
        }
    }
    //ACTUALIZAMOS Factura
    public void updateFactura(double precio, String concepto, String cod_fac) {
        jdbcTemplate.update("UPDATE Factura SET preciototal=?, concepto=? WHERE cod_fac=?",
                precio, concepto, cod_fac);
    }

}
