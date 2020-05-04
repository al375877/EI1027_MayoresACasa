package es.uji.ei1027.Mayorescasa.controller;



import es.uji.ei1027.Mayorescasa.dao.UsuarioDao;
import es.uji.ei1027.Mayorescasa.model.Usuario;
import es.uji.ei1027.Mayorescasa.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

class UserValidator implements Validator {
    @Autowired
    private UsuarioDao usuarioDao;
    @Override
    public boolean supports(Class<?> cls) {
        return Usuario.class.isAssignableFrom(cls);
    }
    @Override
    public void validate(Object obj, Errors errors) {
        // Exercici: Afegeix codi per comprovar que
        // l'usuari i la contrasenya no estiguen buits
        Usuario usuario= (Usuario) obj;


        if( usuario.getUsuario().trim().equals("")) {
            errors.rejectValue("usuario", "obligatori", "Falta introducir un valor");
        }
        if(usuario.getContraseña().trim().equals(""))
            errors.rejectValue("contraseña","obligatori","Falta introducir un valor");
    }
}

@Controller
public class LoginController {
    @Autowired
    private UsuarioDao usuarioDao;

    //llamada al metodo login
    @RequestMapping("/login")
    public String login(Model model) {
        model.addAttribute("user", new Usuario());
        return "login";
    }
    //respuesta al login
    @RequestMapping(value="/login", method=RequestMethod.POST)
    public String checkLogin(@ModelAttribute("user") Usuario user,
                             BindingResult bindingResult, HttpSession session) {

        UserValidator userValidator = new UserValidator();

        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "login";
        }
        // Comprova que el login siga correcte
        // intentant carregar les dades de l'usuari
        user = usuarioDao.getUsuario(user.getUsuario());
        if (user == null) {
            bindingResult.rejectValue("usuario", "badpw", "Usuario incorrecto");
            return "login";
        }
        // Autenticats correctament.
        // Guardem les dades de l'usuari autenticat a la sessió
        session.setAttribute("user", user);
        System.out.println("tipo usuario="+user.getTipoUsuario());
        session.setAttribute("tipo",user.getTipoUsuario());


        return "redirect:/"+user.getTipoUsuario().toLowerCase()+"/index";
    }


    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
