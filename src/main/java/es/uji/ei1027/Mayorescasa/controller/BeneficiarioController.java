package es.uji.ei1027.Mayorescasa.controller;

import es.uji.ei1027.Mayorescasa.dao.AsistenteDao;
import es.uji.ei1027.Mayorescasa.dao.FacturaDao;
import es.uji.ei1027.Mayorescasa.dao.UsuarioDao;
import es.uji.ei1027.Mayorescasa.model.Beneficiario;
import es.uji.ei1027.Mayorescasa.model.Factura;
import es.uji.ei1027.Mayorescasa.model.Usuario;
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

    @RequestMapping("/index")
    public String index(HttpSession session, Model model) {
        return "beneficiario/index";
    }

    @RequestMapping(value="/contacta")
    public String asistente(HttpSession session, Model model) {
        Usuario user= (Usuario) session.getAttribute("user");
        String dniBen=user.getDni();
        Beneficiario ben = usuarioDao.getBeneficiario(dniBen);
        System.out.println(ben.getAsistente());
        model.addAttribute("asistentes", usuarioDao.getAsistenteBenef(ben.getAsistente()));
        return "beneficiario/contacta";
    }

    @RequestMapping("/info")
    public String info(HttpSession session, Model model) {
        return "beneficiario/info";
    }
    @RequestMapping(value="/info", method= RequestMethod.POST)
    public String processAddSubmit() {
        return "beneficiario/info2";
    }

    @RequestMapping("/info2")
    public String info2(HttpSession session, Model model) {
        return "beneficiario/info2";
    }

    @RequestMapping("/pedirRegistro")
    public String pedirRegistro(Model model) {
        return "beneficiario/pedirRegistro";
    }

    @RequestMapping("/list")
    public String listusuarios(Model model) {
        model.addAttribute("usuarios", usuarioDao.getBeneficiarios());
        return "beneficiario/list";
    }

    @RequestMapping(value = "/update/{usuario}", method = RequestMethod.GET)
    public String editusuario(Model model, @PathVariable String usuario) {
        model.addAttribute("usuario", usuarioDao.getUsuario(usuario));
        return "usuarios/update";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String processUpdateSubmit(
            @ModelAttribute("usuario") Usuario usuario,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "usuario/update";
        usuarioDao.updateUsuario(usuario);
        return "redirect:list";
    }

    @RequestMapping(value = "/delete/{usuario}")
    public String processDelete(@PathVariable String usuario) {
        usuarioDao.deleteUsuario(usuario);
        return "redirect:../list";
    }

    //Llamada de la usuario add
    @RequestMapping(value="/add")
    public String addbeneficiario(Model model) {
        model.addAttribute("usuario", new Usuario());
        List<String> generoList = new ArrayList<>();
        generoList.add("Mujer");
        generoList.add("Hombre");
        model.addAttribute("generoList", generoList);
        return "beneficiario/add";
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
        usuarioDao.addBeneficiario(usuario.getDni(),usuario.getTipodieta());
        //Añadimos factura vacía
        usuarioDao.addFactura(aleatorio(),null,0.0,usuario.getNombre(),usuario.getDni());
        return "redirect:../beneficiario/list";
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



