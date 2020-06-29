package es.uji.ei1027.Mayorescasa.dao;

import es.uji.ei1027.Mayorescasa.model.Disponibilidad;
import es.uji.ei1027.Mayorescasa.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository  //En Spring los DAOs van anotados con @Repository
public class DisponibilidadDao {
    private JdbcTemplate jdbcTemplate;

    //Obtenermos el jbcTemplate a partir del Data Source
    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate=new JdbcTemplate(dataSource);
    }
    @Autowired
    UsuarioDao usuarioDao;

    public void addDisponibilidad(Disponibilidad disponibilidad){
        jdbcTemplate.update("INSERT INTO Disponibilidad VALUES (?,?,?,?,?)",
               disponibilidad.getUsuario_ben(),disponibilidad.getUsuario_vol(),disponibilidad.getFechainicial(),disponibilidad.getComentario(),disponibilidad.getEstado());

    }
    public void updateEstado(Disponibilidad disponibilidad){
        jdbcTemplate.update("UPDATE Disponibilidad  set estado=? where dni_vol=? AND dni_ben=?",
                disponibilidad.getEstado(),disponibilidad.getUsuario_vol(),disponibilidad.getUsuario_ben());
    }
    public void finalizarDis(Disponibilidad disponibilidad){
        jdbcTemplate.update("UPDATE Disponibilidad  set estado=?, fechafinal=? where dni_vol=? AND dni_ben=?",
                disponibilidad.getEstado(),disponibilidad.getFechafinal(),disponibilidad.getUsuario_vol(),disponibilidad.getUsuario_ben());
    }

//si existe una relacion entre el usuario beneficiario y cualquier voluntario, los devuelve. ESTE O NO ACEPTADA esto lo tratare en el controller
    public List<Disponibilidad> consultaDisponibilidad(String dni){
        try{
            List<Disponibilidad>lista= jdbcTemplate.query("SELECT * FROM Disponibilidad WHERE dni_ben=? ",
                    new DisponibildadRowMapper(), dni);

         return lista;



        }catch (EmptyResultDataAccessException e){
            return new ArrayList<>();
        }

    }


    //devuelve una lista de todas las asignaciones de beneficiaros
    public List<Disponibilidad> consultaBeneficiarios(String dni){
        try{
            List<Disponibilidad>lista= jdbcTemplate.query("SELECT * FROM Disponibilidad WHERE dni_vol=?",
                    new DisponibildadRowMapper(), dni);

            return lista;

        }catch (EmptyResultDataAccessException e){
            return new ArrayList<>();
        }
    }

    //devuelve una lista de todas las asignaciones de beneficiaros
    public List<Disponibilidad> consultaBeneficiariosPendiente(String dni){
        try{
            List<Disponibilidad>lista= jdbcTemplate.query("SELECT * FROM Disponibilidad WHERE dni_vol=? AND estado='Pendiente'",
                    new DisponibildadRowMapper(), dni);

            return lista;

        }catch (EmptyResultDataAccessException e){
            return new ArrayList<>();
        }
    }

    //devuelve una lista de todas las asignaciones de beneficiaros aceptados
    public List<Disponibilidad> consultaBeneficiariosAceptado(String dni){
        try{
            List<Disponibilidad>lista= jdbcTemplate.query("SELECT * FROM Disponibilidad WHERE dni_vol=? AND estado='Aceptada'",
                    new DisponibildadRowMapper(), dni);
            return lista;
        }catch (EmptyResultDataAccessException e){
            return new ArrayList<>();
        }
    }

    //devuelve una lista de todas las asignaciones de beneficiaros rechazados
    public List<Disponibilidad> consultaBeneficiariosRechazado(String dni){
        try{
            List<Disponibilidad>lista= jdbcTemplate.query("SELECT * FROM Disponibilidad WHERE dni_vol=? AND estado='Rechazada'",
                    new DisponibildadRowMapper(), dni);
            return lista;
        }catch (EmptyResultDataAccessException e){
            return new ArrayList<>();
        }
    }

    //devuelve una lista de todas las asignaciones de beneficiaros finalizadas
    public List<Disponibilidad> consultaBeneficiariosFinalizado(String dni){
        try{
            List<Disponibilidad>lista= jdbcTemplate.query("SELECT * FROM Disponibilidad WHERE dni_vol=? AND estado='Finalizada'",
                    new DisponibildadRowMapper(), dni);
            return lista;
        }catch (EmptyResultDataAccessException e){
            return new ArrayList<>();
        }
    }

    public String getComentario(String dniV, String dniB){
        String comentario="";
        try{
            Disponibilidad disponibilidad= jdbcTemplate.queryForObject("SELECT * FROM Disponibilidad WHERE dni_vol=? AND dni_ben=?",
                    new DisponibildadRowMapper(), dniV, dniB);
            comentario=disponibilidad.getComentario();
            return comentario;

        }catch (EmptyResultDataAccessException e){
            return comentario;
        }

    }

    public Disponibilidad getDisponibilidad(String dniBen, String dniVol){
        try{
            return jdbcTemplate.queryForObject("SELECT * FROM Disponibilidad WHERE dni_ben=? AND dni_vol=?",
                    new DisponibildadRowMapper(), dniBen, dniVol);


        }catch (EmptyResultDataAccessException e){
            return null;
        }
    }
    //BORRAMOS Peticion
    public void deleteDisponibilidad(Disponibilidad dis) {
        jdbcTemplate.update("DELETE FROM Disponibilidad WHERE dni_ben=? AND dni_vol=?", dis.getUsuario_ben(), dis.getUsuario_vol());
    }


}
