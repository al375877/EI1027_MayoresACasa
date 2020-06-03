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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/voluntario")
public class VoluntarioController {

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
        model.addAttribute("usuarios", usuarioDao.getUsuarios());
        return "voluntario/list";
    }
    //Llamada de la peticion add
    @RequestMapping(value="/add")
    public String addvoluntario(Model model) {
        model.addAttribute("usuario", new Usuario());
        List<String> generoList = new ArrayList<>();
        generoList.add("Femenino");
        generoList.add("Masculino");
        model.addAttribute("generoList", generoList);
        return "voluntario/add";
    }

    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("usuario") Usuario usuario,
                                   BindingResult bindingResult) {
        VoluntarioValidator nadadorValidator = new VoluntarioValidator();
        nadadorValidator.validate(usuario, bindingResult);
        if (bindingResult.hasErrors()) {
            System.out.println("Error al añadir el voluntario");
            return "voluntario/add";
        }
        usuario.setTipoUsuario("Voluntario");
        usuarioDao.addUsuario(usuario);
        usuarioDao.addVoluntario(usuario.getDni(),"No añadido");
        return "redirect:../login";
    }

    @RequestMapping("/index")
    public String index(HttpSession session, Model model) {
        return "voluntario/index";
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
