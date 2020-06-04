package es.uji.ei1027.Mayorescasa.controller;

import es.uji.ei1027.Mayorescasa.dao.DisponibilidadDao;
import es.uji.ei1027.Mayorescasa.dao.UsuarioDao;
import es.uji.ei1027.Mayorescasa.model.Disponibilidad;
import es.uji.ei1027.Mayorescasa.model.TempUsuarioComentario;
import es.uji.ei1027.Mayorescasa.model.Usuario;
import es.uji.ei1027.Mayorescasa.model.Voluntario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
@RequestMapping("/voluntario")
public class VoluntarioController {
    @Autowired
    private UsuarioDao usuarioDao;

    @Autowired
    public void setUsuarioDao(UsuarioDao usuarioDao) {

        this.usuarioDao=usuarioDao;
    }
    @Autowired
    DisponibilidadDao disponibilidadDao;

    // Operaciones: Crear, listar, actualizar, borrar

    @RequestMapping("/list")
    public String listvoluntarios(Model model) {

        List<Usuario> listUser =  usuarioDao.getUsuariosVoluntarios();
        Map<Usuario, String> map = new HashMap();
        for (Usuario user:listUser){
            map.put(user, usuarioDao.getVoluntario(user.getDni()).getEstado());
            System.out.println(usuarioDao.getVoluntario(user.getDni()).getEstado());
        }
        model.addAttribute("map",map);

        return "voluntario/list";
    }
    //Llamada de la peticion add
    @RequestMapping(value="/add")
    public String addvoluntario(Model model) {
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("voluntario", new Voluntario());
        List<String> generoList = new ArrayList<>();
        generoList.add("Femenino");
        generoList.add("Masculino");
        model.addAttribute("generoList", generoList);
        return "voluntario/add";
    }

    @RequestMapping(value = "/rechazar/{dni}")
    public String rechazarVoluntario(@PathVariable String dni){
        Voluntario voluntario = usuarioDao.getVoluntario(dni);
        voluntario.setEstado("Rechazado");
        usuarioDao.updateEstadoVoluntario(voluntario);
        return "redirect:../list";
    }

    @RequestMapping(value = "/aceptar/{dni}")
    public String aceptarVoluntario(@PathVariable String dni){
        Voluntario voluntario = usuarioDao.getVoluntario(dni);
        voluntario.setEstado("Aceptado");
        usuarioDao.updateEstadoVoluntario(voluntario);
        return "redirect:../list";
    }

    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("voluntario") Voluntario voluntario,
                                   BindingResult bindingResult) {
        VoluntarioValidator nadadorValidator = new VoluntarioValidator();
        nadadorValidator.validate(voluntario, bindingResult);
        if (bindingResult.hasErrors()) {
            System.out.println("Error al añadir el voluntario");
            return "voluntario/add";
        }
        voluntario.setTipoUsuario("Voluntario");
        usuarioDao.addUsuario(voluntario);


        usuarioDao.addVoluntario(voluntario.getDni(),voluntario.getHobbies(),voluntario.getDias_semana());
        return "redirect:../login";
    }

    @RequestMapping("/index")
    public String index(HttpSession session, Model model) {
        return "voluntario/index";
    }
    @RequestMapping("/perfil")
    public String perfil(HttpSession session, Model model) {
        return "voluntario/perfil";
    }


    @RequestMapping("/solicitar")
    public String solicitar(Model model,HttpSession session) {
        List<Voluntario> voluntariosDisponibles=usuarioDao.getVoluntarios();
        List<Usuario> asignados= (List<Usuario>) session.getAttribute("volAsignados");
        List<TempUsuarioComentario> listaTemporal= new ArrayList<>();
        //si ya tiene uno de los voluntarios asignado, lo elimina de los disponibles.
        System.out.println("SIZE:"+asignados.size());
        if (asignados.size()>0) {
            for(Usuario asignado:asignados) {
                for(Voluntario vol:voluntariosDisponibles) {
                    if (vol.getDni().equals(asignado.getDni())) {
                        voluntariosDisponibles.remove(vol);
                        break;//solo hace el break en el for de dentro
                    }
                }
                TempUsuarioComentario temporal = new TempUsuarioComentario();
                temporal.setUsuario(asignado);
                temporal.setComentario(disponibilidadDao.getComentario(asignado.getDni()));
                listaTemporal.add(temporal);
            }

            model.addAttribute("temp",listaTemporal);
        }
        if(voluntariosDisponibles.size()>0){
            model.addAttribute("voluntarios", voluntariosDisponibles);
        }

        return "voluntario/solicitar";
    }


    @RequestMapping(value="/solicitar", method= RequestMethod.POST)
    public String setDisponibildad(@ModelAttribute("dniV") String dniV,@ModelAttribute("comment") String comment, HttpSession session,Model model){

        Usuario user= (Usuario) session.getAttribute("user");
        List<TempUsuarioComentario> listaTemporal= new ArrayList<>();
        List<Voluntario> voluntariosDisponibles=usuarioDao.getVoluntarios();

        //añado la relacion entre ben y vol
        Disponibilidad disponibilidad=new Disponibilidad();
        disponibilidad.setUsuario_ben(user.getDni());
        disponibilidad.setUsuario_vol(dniV);
        Date fecha = new Date();
        disponibilidad.setFechainicial(fecha);
        disponibilidad.setComentario(comment);
        disponibilidadDao.addDisponibilidad(disponibilidad);

        //recojo los voluntarios asignados al beneficiario
        List<Usuario> asignados= disponibilidadDao.consultaDisponibilidad(user.getDni());
        session.setAttribute("volAsignados",asignados);



        //si ya tiene uno de los voluntarios asignado, lo elimina de los disponibles.
        for(Usuario asignado:asignados) {
            for(Voluntario vol:voluntariosDisponibles) {
                if(vol.getDni().equals(asignado.getDni())){
                    voluntariosDisponibles.remove(vol);
                    break;//solo hace el break en el for de dentro
                }
            }
            TempUsuarioComentario temporal = new TempUsuarioComentario();
            temporal.setUsuario(asignado);
            temporal.setComentario(disponibilidadDao.getComentario(asignado.getDni()));
            listaTemporal.add(temporal);

        }
        model.addAttribute("temp",listaTemporal);

        if(voluntariosDisponibles.size()>0){
            model.addAttribute("voluntarios", voluntariosDisponibles);
        }
        return "voluntario/solicitar";
    }
}
