package es.uji.ei1027.Mayorescasa.controller;



import es.uji.ei1027.Mayorescasa.dao.UsuarioDao;
import es.uji.ei1027.Mayorescasa.model.UserDetails;
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
    @Override
    public boolean supports(Class<?> cls) {
        return Usuario.class.isAssignableFrom(cls);
    }
    @Override
    public void validate(Object obj, Errors errors) {
        // Exercici: Afegeix codi per comprovar que
        // l'usuari i la contrasenya no estiguen buits
        UserDetails usuario= (UserDetails) obj;
        if( usuario.getUsername().trim().equals("")) {

            errors.rejectValue("username", "obligatori", "Falta introducir un valor");
        }
        if(usuario.getPassword().trim().equals(""))
            errors.rejectValue("password","obligatori","Falta introducir un valor");
    }
}

@Controller
public class LoginController {
    @Autowired
    private UsuarioDao usuarioDao;

    //llamada al metodo login
    @RequestMapping("/login")
    public String login(Model model) {
        model.addAttribute("user", new UserDetails());
        return "login";
    }
    //respuesta al login
    @RequestMapping(value="/login", method=RequestMethod.POST)
    public String checkLogin(@ModelAttribute("user") UserDetails user,
                             BindingResult bindingResult, HttpSession session) {

        UserValidator userValidator = new UserValidator();

        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "login";
        }
        // Comprova que el login siga correcte
        // intentant carregar les dades de l'usuari
        Usuario realUser = usuarioDao.getUsuario(user.getUsername());
        if (user == null) {
            bindingResult.rejectValue("password", "badpw", "Contrasenya incorrecta");
            return "login";
        }
        // Autenticats correctament.
        // Guardem les dades de l'usuari autenticat a la sessió
        session.setAttribute("user", realUser);
        System.out.println("tipo usuario="+realUser.getTipoUsuario());
        session.setAttribute("tipo",realUser.getTipoUsuario());


        return "redirect:/"+realUser.getTipoUsuario().toLowerCase()+"/index";
    }


    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
