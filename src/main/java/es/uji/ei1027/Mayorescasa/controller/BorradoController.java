package es.uji.ei1027.Mayorescasa.controller;

import es.uji.ei1027.Mayorescasa.model.Usuario;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/borrado")
public class BorradoController {

    @RequestMapping("/index")
    public String index(HttpSession session) {
        try{
            return "borrado/index";
        } catch (Exception ex){
            return "error/error";
        }
    }

}
