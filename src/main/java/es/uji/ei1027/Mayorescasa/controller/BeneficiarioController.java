package es.uji.ei1027.Mayorescasa.controller;

import es.uji.ei1027.Mayorescasa.dao.DatosBeneficiario;
import es.uji.ei1027.Mayorescasa.model.Asistente;
import es.uji.ei1027.Mayorescasa.model.Beneficiario;
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
@RequestMapping("/beneficiario")
public class BeneficiarioController {

    @RequestMapping("/index")
    public String index(HttpSession session, Model model) {
        return "beneficiario/index";
    }

    @RequestMapping(value = "/pedirRegistro", method=RequestMethod.POST)
    public String pedirRegistro(@ModelAttribute(value="foo") DatosBeneficiario foo) {
        return "beneficiario/pedirRegistro";
    }
    @RequestMapping(value = "/showForm", method=RequestMethod.GET)
    public void showForm(Model model) {
        DatosBeneficiario foo = new DatosBeneficiario();
        foo.setNombre("bar");

        model.addAttribute("foo", foo);
        System.out.println(foo.getNombre());
    }

}



