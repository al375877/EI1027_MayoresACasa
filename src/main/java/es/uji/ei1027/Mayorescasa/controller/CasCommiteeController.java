package es.uji.ei1027.Mayorescasa.controller;

import es.uji.ei1027.Mayorescasa.model.Usuario;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/cascommitee")
public class CasCommiteeController {

    @RequestMapping("/index")
    public String index(HttpSession session) {
        Usuario user= (Usuario) session.getAttribute("user");
        try{
            if(user.getTipoUsuario().equals("casCommitee")){
                System.out.println("Usuario correcto");
                return "cascommitee/index";
            }
        } catch (Exception ex){
            System.out.println("Acceso prohibido");
            return "error/error";
        }
        return "error/error";
    }
}
