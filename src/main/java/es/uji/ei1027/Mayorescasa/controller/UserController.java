package es.uji.ei1027.Mayorescasa.controller;

import javax.servlet.http.HttpSession;

import es.uji.ei1027.Mayorescasa.dao.UsuarioDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import es.uji.ei1027.Mayorescasa.dao.UsuarioDao;
import es.uji.ei1027.Mayorescasa.model.UserDetails;

@Controller
@RequestMapping("user")
public class UserController {
    private UsuarioDao usuarioDao;

    @Autowired
    public void setSociDao(UsuarioDao usuarioDao) {
        this.usuarioDao = usuarioDao;
    }

    @RequestMapping("/list")
    public String listSocis(HttpSession session, Model model) {
        if (session.getAttribute("user") == null) {
            UserDetails user = new UserDetails();
            model.addAttribute("user", user);
            //session.setAttribute("nextUrl", "beneficiario/index");
            return "login";
        }
//        }else {
//            UserDetails user = (UserDetails) session.getAttribute("user");
//            session.setAttribute("autorizado",user.getAutorizado());
//        }
        //model.addAttribute("users", usuarioDao.listAllUsers());
        UserDetails user = (UserDetails) session.getAttribute("user");
        if (user.getAutorizado()=="admin")
            return "cas/index";
        return "beneficiario/index";
    }

    @RequestMapping("/index")
    public String index(HttpSession session, Model model) {
        return "user/index";
    }
}

