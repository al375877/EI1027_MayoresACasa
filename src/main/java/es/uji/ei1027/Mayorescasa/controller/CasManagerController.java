package es.uji.ei1027.Mayorescasa.controller;

import es.uji.ei1027.Mayorescasa.dao.UsuarioDao;
import es.uji.ei1027.Mayorescasa.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/casmanager")
public class CasManagerController {

    @Autowired
    private UsuarioDao usuarioDao;

    @Autowired
    public void setUsuarioDao(UsuarioDao usuarioDao) {

        this.usuarioDao=usuarioDao;
    }

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

    @RequestMapping("/unilist/{usuario}")
    public String unilistEmpresasPropio(HttpSession session, Model model, @PathVariable String usuario) {
        Usuario user= (Usuario) session.getAttribute("user");
        try{
            if(user.getTipoUsuario().equals("casManager") || user.getTipoUsuario().equals("casCommitee") || user.getTipoUsuario().equals("casVolunteer")) {
                model.addAttribute("user", usuarioDao.getUsuario(usuario));
                return "casmanager/unilist";
            }
        } catch (Exception e) {
            return "error/error";
        }
        return "error/error";
    }
}
