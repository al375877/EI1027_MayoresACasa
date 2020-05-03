package es.uji.ei1027.Mayorescasa.dao;

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
        jdbcTemplate.update("INSERT INTO Peticion VALUES (?,?,?,?,?,?,?,?,?)",
                peticion.getCod_pet(), peticion.getTiposervicio(), peticion.getPrecioservicio(), peticion.getLinea(),
                peticion.getFechaaceptada(), peticion.getFecharechazada(), peticion.getFechafinal(), peticion.getComentarios(),
                peticion.getDni_ben()
        );

    }

    //BORRAMOS Peticion
    public void deletePeticion(String cod) {
        jdbcTemplate.update("DELETE FROM Peticion WHERE cod_pet=?", cod);
    }

    //ACTUALIZAMOS Peticion
    public void updatePeticion(Peticion peticion) {
        jdbcTemplate.update("UPDATE peticion SET tiposervicio=?, precioservicio=?, linea=?, fechaaceptada=?, fecharechazada=?," +
                        "fechafinal=?, comentarios=?, dni_ben=? WHERE cod_pet=? ",
                peticion.getTiposervicio(), peticion.getPrecioservicio(), peticion.getLinea(),
                peticion.getFechaaceptada(), peticion.getFecharechazada(), peticion.getFechafinal(), peticion.getComentarios(),
                peticion.getDni_ben(),peticion.getCod_pet()
        );
    }

    public Peticion getPeticion(String cod) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM Peticion WHERE cod_pet=?", new PeticionRowMapper(), cod);
        } catch (EmptyResultDataAccessException e) {
            return null;
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
