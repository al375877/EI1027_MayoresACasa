package es.uji.ei1027.Mayorescasa.controller;

import es.uji.ei1027.Mayorescasa.model.Usuario;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/casmanager")
public class CasManagerController {

    @RequestMapping("/index")
    public String index(HttpSession session) {
        Usuario user= (Usuario) session.getAttribute("user");
        try{
            if(user.getTipoUsuario().equals("casManager")){
                System.out.println("Usuario correcto");
                return "casmanager/index";
            }
        } catch (Exception ex){
            System.out.println("Acceso prohibido");
            return "error/error";
        }
        return "error/error";
    }
}
