package es.uji.ei1027.Mayorescasa.controller;

import es.uji.ei1027.Mayorescasa.dao.AsistenteDao;
import es.uji.ei1027.Mayorescasa.dao.FacturaDao;
import es.uji.ei1027.Mayorescasa.dao.UsuarioDao;
import es.uji.ei1027.Mayorescasa.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.UsesSunMisc;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Controller
@RequestMapping("/beneficiario")
public class BeneficiarioController {

    private UsuarioDao usuarioDao;

    @Autowired
    public void setUsuarioDao(UsuarioDao usuarioDao) {
        this.usuarioDao=usuarioDao;
    }

    @RequestMapping(value = "/index")
    public String index(HttpSession session, Model model) {
        Usuario user= (Usuario) session.getAttribute("user");
        try{
            if(user.getTipoUsuario().equals("Beneficiario")) {
                model.addAttribute("perfil", usuarioDao.getUsuario(user.getUsuario()));
                return "beneficiario/index";
            }
        } catch (Exception e){
            return "error/error";
        }
        return "error/error";
    }

    @RequestMapping(value = "/info2")
    public String info2(HttpSession session, Model model) {
        return "beneficiario/info2";
    }

    @RequestMapping(value="/contacta")
    public String asistente(HttpSession session, Model model) {
        Usuario user= (Usuario) session.getAttribute("user");
        try{
            String dniBen=user.getDni();
            Beneficiario ben = usuarioDao.getBeneficiario(dniBen);
            model.addAttribute("asistentes", usuarioDao.getAsistenteBenef(ben.getAsistente()));
            return "beneficiario/contacta";
        } catch (Exception e){
            return "error/error";
        }
    }

    @RequestMapping("/info")
    public String info(HttpSession session, Model model) {
        return "beneficiario/info";
    }
//    @RequestMapping(value="/info", method= RequestMethod.POST)
//    public String processAddSubmit() {
//        return "beneficiario/info2";
//    }

    @RequestMapping("/list")
    public String listusuarios(HttpSession session, Model model) {
        Usuario user= (Usuario) session.getAttribute("user");
        try{
            if(user.getTipoUsuario().equals("casManager") || user.getTipoUsuario().equals("casCommitee") || user.getTipoUsuario().equals("casVolunteer")) {
                model.addAttribute("usuarios", usuarioDao.getBeneficiarios());
                return "beneficiario/list";
            }
        } catch (Exception e){
            return "error/error";
        }
        return "error/error";
    }

    @RequestMapping(value = "/update/{usuario}", method = RequestMethod.GET)
    public String editusuario(HttpSession session, Model model, @PathVariable String usuario) {
        Usuario user= (Usuario) session.getAttribute("user");
        try{
            if (!user.getTipoUsuario().equals("casCommitee")) return "error/error";
            model.addAttribute("usuario", usuarioDao.getUsuario(usuario));
            return "beneficiario/update";
        } catch (Exception e){
            return "error/error";
        }
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String processUpdateSubmit(
            @ModelAttribute("usuario") Usuario usuario,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "beneficiario/update";
        usuario.setTipoUsuario("Beneficiario");
        usuarioDao.updateUsuario(usuario);
        return "redirect:list";
    }

    @RequestMapping(value = "/delete/{usuario}")
    public String processDelete(@PathVariable String usuario) {
        usuarioDao.deleteUsuario(usuario);
        return "redirect:../list";
    }

    @RequestMapping(value = "/updateAsis/{dni}", method = RequestMethod.GET)
    public String usuarioAsis(HttpSession session, Model model, @PathVariable String dni) {
        Usuario user= (Usuario) session.getAttribute("user");
        try{
            if(user.getTipoUsuario().equals("casCommitee")) {
                List<Asistente> asistentes = usuarioDao.getAsistentes();
                List<String> usersAsis = new ArrayList<>();
                for (Asistente asistente : asistentes) {
                    usersAsis.add(asistente.getNombre());
                }
                model.addAttribute("dni", usuarioDao.getBeneficiario(dni));
                model.addAttribute("asistentes", usuarioDao.getAsistentes());
                model.addAttribute("asistentesSel", usersAsis);
                return "beneficiario/updateAsis";
            }
        } catch (Exception e){
            return "error/error";
        }
        return "error/error";
    }
    @RequestMapping(value = "/updateAsis", method = RequestMethod.POST)
    public String processUpdateSubmitAsis(
            @ModelAttribute("dni") Beneficiario beneficiario,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "beneficiario/update";
        try {
            usuarioDao.updateBeneficiario(beneficiario);
        } catch (Exception e){
            System.out.println("Error añadir asistente");
            return "beneficiario/errorAsis";
        }
        return "redirect:list";
    }

    @RequestMapping("/unilist/{usuario}")
    public String unilistEmpresas(HttpSession session, Model model, @PathVariable String usuario) {
        Usuario user= (Usuario) session.getAttribute("user");
        try{
            if (user.getTipoUsuario().equals("casCommitee") || user.getTipoUsuario().equals("casVolunteer") || user.getTipoUsuario().equals("Beneficiario")) {
                model.addAttribute("user", usuarioDao.getUsuario(usuario));
                String dniBen = usuarioDao.getUsuario(usuario).getDni();
                Beneficiario beneficiario = usuarioDao.getBeneficiario(dniBen);
                try {
                    model.addAttribute("asistenteUni", usuarioDao.getAsistenteBenef(beneficiario.getAsistente()));
                } catch (Exception e) {
                    System.out.println("Sin asistente");
                }
            }
            return "beneficiario/unilist";
        } catch (Exception e){
            return "error/error";
        }
    }

    @RequestMapping(value="/add")
    public String addbeneficiario(HttpSession session, Model model) {
        Usuario user= (Usuario) session.getAttribute("user");
        try{
            if (user.getTipoUsuario().equals("casCommitee")) {
                model.addAttribute("usuario", new Usuario());
                List<String> generoList = new ArrayList<>();
                generoList.add("Mujer");
                generoList.add("Hombre");
                model.addAttribute("generoList", generoList);
                return "beneficiario/add";
            }
        } catch (Exception e){
            return "error/error";
        }
        return "error/error";
    }

    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("usuario") Usuario usuario,
                                   BindingResult bindingResult) {
        BeneficiarioValidator nadadorValidator = new BeneficiarioValidator();
        nadadorValidator.validate(usuario, bindingResult);
        if (bindingResult.hasErrors()) {
            System.out.println("Error al añadir el beneficiario");
            return "beneficiario/add";
        }
        usuario.setTipoUsuario("Beneficiario");
        usuarioDao.addUsuario(usuario);
        usuarioDao.addBeneficiario(usuario.getNombre(),usuario.getDni(),usuario.getTipodieta());
        //Añadimos factura vacía
        usuarioDao.addFactura(aleatorio(),null,0.0,usuario.getNombre(),usuario.getDni());
        return "redirect:../beneficiario/list";
    }

    @RequestMapping("/atras")
    public String atras(Model model, HttpSession session) {
        Usuario user= (Usuario) session.getAttribute("user");
        if (user.getTipoUsuario().equals("casVolunteer")) return "casvolunteer/index";
        if (user.getTipoUsuario().equals("casCommitee")) return "cascommitee/index";
        return "/";
    }

    private String aleatorio(){
        Random aleatorio = new Random();
        String alfa = "ABCDEFGHIJKLMNOPQRSTVWXYZ";
        String cadena = "";
        int numero;
        int forma;
        forma=(int)(aleatorio.nextDouble() * alfa.length()-1+0);
        numero=(int)(aleatorio.nextDouble() * 99+100);
        cadena=cadena+alfa.charAt(forma)+numero;
        return cadena;
    }
}



