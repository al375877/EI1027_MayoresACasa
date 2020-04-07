package es.uji.ei1027.Mayorescasa.dao;

import es.uji.ei1027.Mayorescasa.model.ReservaHorarios;
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
    public void addReservaHorarios(ReservaHorarios reservaHorarios) {
        jdbcTemplate.update("INSERT INTO ReservaHorarios VALUES (?,?,?,?,?,?)",
                reservaHorarios.getUsuario_ben(),reservaHorarios.getUsuario_vol(),
                reservaHorarios.getDiasemana(),reservaHorarios.getTipo(),
                reservaHorarios.getFechainicial(),reservaHorarios.getFechafinal()
        );

    }

    //BORRAMOS ReservaHorarios
    public void deleteReservaHorarios(String usuario_ben) {
        jdbcTemplate.update("DELETE FROM ReservaHorarios WHERE usuario_ben=?", usuario_ben);
    }

    //ACTUALIZAMOS ReservaHorarios
    public void updateReservaHorarios(ReservaHorarios reservaHorarios) {
        jdbcTemplate.update("UPDATE reservaHorarios SET usaurio_vol=?, diasemana=?, tipo=?, fechainicial=?, fechafinal=? WHERE usuario_ben ",
                reservaHorarios.getUsuario_vol(),
                reservaHorarios.getDiasemana(),reservaHorarios.getTipo(),
                reservaHorarios.getFechainicial(),reservaHorarios.getFechafinal(),
                reservaHorarios.getUsuario_ben()

        );
    }

    public ReservaHorarios getReservaHorarios(String cod) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM ReservaHorarios WHERE cod=?", new ReservaHorariosRowMapper(), cod);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    //LISTAMOS ReservaHorarios
    public List<ReservaHorarios> getReservaHorarios() {
        try {
            return jdbcTemplate.query("SELECT * FROM ReservaHorarios", new
                    ReservaHorariosRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<ReservaHorarios>();
        }
    }
}
