package es.uji.ei1027.Mayorescasa.controller;

import es.uji.ei1027.Mayorescasa.dao.VoluntarioDao;
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
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/voluntario")
public class VoluntarioController {

    private VoluntarioDao voluntarioDao;

    @Autowired
    public void setVoluntarioDao(VoluntarioDao voluntarioDao) {
        this.voluntarioDao=voluntarioDao;
    }

    // Operaciones: Crear, listar, actualizar, borrar

    @RequestMapping("/list")
    public String listvoluntarios(Model model) {
        model.addAttribute("voluntarios", voluntarioDao.getVoluntarios());
        return "voluntario/list";
    }
    //Llamada de la peticion add
    @RequestMapping(value="/add")
    public String addvoluntario(Model model) {
        model.addAttribute("voluntario", new Voluntario());
        List<String> generoList = new ArrayList<>();
        generoList.add("Femenini");
        generoList.add("Masculino");
        model.addAttribute("generoList", generoList);
        return "voluntario/add";
    }

    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("voluntario") Voluntario voluntario,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "voluntario/add";
        voluntarioDao.addVoluntario(voluntario);
        return "redirect:list";
    }

    @RequestMapping(value="/update/{usuario}", method = RequestMethod.GET)
    public String editvoluntario(Model model, @PathVariable String usuario) {
        model.addAttribute("voluntario", voluntarioDao.getVoluntario(usuario));
        return "voluntarios/update";
    }

    @RequestMapping(value="/update", method = RequestMethod.POST)
    public String processUpdateSubmit(
            @ModelAttribute("voluntario") Voluntario voluntario,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "voluntario/update";
        voluntarioDao.updateVoluntario(voluntario);
        return "redirect:list";
    }

    @RequestMapping(value="/delete/{usuario}")
    public String processDelete(@PathVariable String usuario) {
        voluntarioDao.deleteVoluntario(usuario);
        return "redirect:../list";
    }

    @RequestMapping("/index")
    public String index(HttpSession session, Model model) {
        return "voluntario/index";
    }

    @RequestMapping("/solicitar")
    public String solicitar(Model model) {
        model.addAttribute("voluntarios", voluntarioDao.getVoluntarios());
        return "voluntario/solicitar";
    }



}
