package es.uji.ei1027.Mayorescasa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cas")
public class CasController {

    @RequestMapping("/index")
    public String index(Model model) {
        return "cas/index";
    }
}
