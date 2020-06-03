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
        jdbcTemplate.update("INSERT INTO Disponibilidad VALUES (?,?,?,?)",
               disponibilidad.getUsuario_ben(),disponibilidad.getUsuario_vol(),disponibilidad.getFechainicial(),disponibilidad.getComentario());

    }
//si existe una relacion entre el usuario beneficiario y cualquier voluntario, los devuelve
    public List<Usuario> consultaDisponibilidad(String dni){
        try{
            List<Disponibilidad>lista= jdbcTemplate.query("SELECT * FROM Disponibilidad WHERE dni_ben=?",
                    new DisponibildadRowMapper(), dni);

            ArrayList<Usuario> listaDnis= new ArrayList<>();

            for(Disponibilidad dis : lista){
                listaDnis.add(usuarioDao.getUsuarioDni(dis.getUsuario_vol()));
                System.out.println("DNI--------VOLUNTARIO________ASIGNADO----------"+usuarioDao.getUsuarioDni(dis.getUsuario_vol()).getDni());
            }
            return listaDnis;

        }catch (EmptyResultDataAccessException e){
            return new ArrayList<Usuario>();
        }

    }
    public String getComentario(String dniV){
        String comentario="";
        try{
            Disponibilidad disponibilidad= jdbcTemplate.queryForObject("SELECT * FROM Disponibilidad WHERE dni_vol=?",
                    new DisponibildadRowMapper(), dniV);
            comentario=disponibilidad.getComentario();
            return comentario;

        }catch (EmptyResultDataAccessException e){
            return comentario;
        }

    }


}
