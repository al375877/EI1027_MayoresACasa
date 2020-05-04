package es.uji.ei1027.Mayorescasa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/beneficiario")
public class BeneficiarioController {

    @RequestMapping("/index")
    public String index(HttpSession session, Model model) {
        return "beneficiario/index";
    }

    @RequestMapping("/pedirRegistro")
    public String pedirRegistro(Model model) {
        return "beneficiario/pedirRegistro";
    }



}



