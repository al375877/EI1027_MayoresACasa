package es.uji.ei1027.Mayorescasa.controller;

import es.uji.ei1027.Mayorescasa.dao.UsuarioDao;
import es.uji.ei1027.Mayorescasa.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/voluntario")
public class VoluntarioController {

    private UsuarioDao usuarioDao;

    @Autowired
    public void setUsuarioDao(UsuarioDao usuarioDao) {
        this.usuarioDao=usuarioDao;
    }

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

//    @RequestMapping(value="/update/{usuario}", method = RequestMethod.GET)
//    public String editvoluntario(Model model, @PathVariable String usuario) {
//        model.addAttribute("voluntario", usuarioDao.getVoluntario(usuario));
//        return "voluntarios/update";
//    }
//
//    @RequestMapping(value="/update", method = RequestMethod.POST)
//    public String processUpdateSubmit(
//            @ModelAttribute("voluntario") Voluntario voluntario,
//            BindingResult bindingResult) {
//        if (bindingResult.hasErrors())
//            return "voluntario/update";
//        usuarioDao.updateVoluntario(voluntario);
//        return "redirect:list";
//    }
//
//    @RequestMapping(value="/delete/{usuario}")
//    public String processDelete(@PathVariable String usuario) {
//        usuarioDao.deleteVoluntario(usuario);
//        return "redirect:../list";
//    }

    @RequestMapping("/index")
    public String index(HttpSession session, Model model) {
        return "voluntario/index";
    }

    @RequestMapping("/solicitar")
    public String solicitar(Model model) {
        model.addAttribute("usuarios", usuarioDao.getVoluntarios());
        return "voluntario/solicitar";
    }

}
