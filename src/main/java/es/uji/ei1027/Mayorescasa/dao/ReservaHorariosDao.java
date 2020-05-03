package es.uji.ei1027.Mayorescasa.dao;

import es.uji.ei1027.Mayorescasa.model.Disponibilidad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class ReservaHorariosDao {
    private JdbcTemplate jdbcTemplate;

    //Obtenermos el jbcTemplate a partir del Data Source
    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    //AÑADIMOS ReservaHorarios
    public void addReservaHorarios(Disponibilidad disponibilidad) {
        jdbcTemplate.update("INSERT INTO ReservaHorarios VALUES (?,?,?,?,?,?)",
                disponibilidad.getUsuario_ben(), disponibilidad.getUsuario_vol(),
                disponibilidad.getDiasemana(), disponibilidad.getTipo(),
                disponibilidad.getFechainicial(), disponibilidad.getFechafinal()
        );

    }

    //BORRAMOS ReservaHorarios
    public void deleteReservaHorarios(String usuario_ben) {
        jdbcTemplate.update("DELETE FROM ReservaHorarios WHERE usuario_ben=?", usuario_ben);
    }

    //ACTUALIZAMOS ReservaHorarios
    public void updateReservaHorarios(Disponibilidad disponibilidad) {
        jdbcTemplate.update("UPDATE reservaHorarios SET usaurio_vol=?, diasemana=?, tipo=?, fechainicial=?, fechafinal=? WHERE usuario_ben ",
                disponibilidad.getUsuario_vol(),
                disponibilidad.getDiasemana(), disponibilidad.getTipo(),
                disponibilidad.getFechainicial(), disponibilidad.getFechafinal(),
                disponibilidad.getUsuario_ben()

        );
    }

    public Disponibilidad getReservaHorarios(String cod) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM ReservaHorarios WHERE cod=?", new ReservaHorariosRowMapper(), cod);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    //LISTAMOS ReservaHorarios
    public List<Disponibilidad> getReservaHorarios() {
        try {
            return jdbcTemplate.query("SELECT * FROM ReservaHorarios", new
                    ReservaHorariosRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<Disponibilidad>();
        }
    }
}
