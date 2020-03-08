package es.uji.ei1027.Mayorescasa.dao;

import es.uji.ei1027.Mayorescasa.model.Peticion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class PeticionDao {
    private JdbcTemplate jdbcTemplate;

    //Obtenermos el jbcTemplate a partir del Data Source
    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    //AÑADIMOS Peticion
    public void addPeticion(Peticion peticion) {
        jdbcTemplate.update("INSERT INTO Peticion VALUES (?,?,?,?,?,?,?,?,?)",
                peticion.getCod(), peticion.getTipoServicio(), peticion.getEstado(),
                peticion.getFecha_inicio(), peticion.getFecha_fin(), peticion.getFecha_aceptado(),
                peticion.getFecha_rechazado(), peticion.getComentarios(), peticion.getUsuario_ben());
    }

    //BORRAMOS Peticion
    public void deletePeticion(String cod) {
        jdbcTemplate.update("DELETE FROM Peticion WHERE cod=?", cod);
    }

    //ACTUALIZAMOS Peticion
    public void updatePeticion(Peticion peticion) {
        jdbcTemplate.update("UPDATE peticon SET tipoServicio=?, estado=?, fecha_inicio=?, " +
                        "fecha_fin=?, fecha_aceptado=?, fecha_rechazado=?, comentarios=?," +
                        "usuario_ben=? WHERE cod=?",
                peticion.getTipoServicio(), peticion.getEstado(),
                peticion.getFecha_inicio(), peticion.getFecha_fin(), peticion.getFecha_aceptado(),
                peticion.getFecha_rechazado(), peticion.getComentarios(), peticion.getUsuario_ben(),
                peticion.getCod()
        );
    }

    public Peticion getPeticion(String cod) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM Peticion WHERE cod=?", new PeticionRowMapper(), cod);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    //LISTAMOS Peticion
    public List<Peticion> getPeticion() {
        try {
            return jdbcTemplate.query("SELECT * FROM Peticion", new
                    PeticionRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<Peticion>();
        }
    }
}
