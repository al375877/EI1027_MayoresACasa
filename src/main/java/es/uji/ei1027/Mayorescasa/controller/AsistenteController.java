package es.uji.ei1027.Mayorescasa.controller;

        import es.uji.ei1027.Mayorescasa.dao.AsistenteDao;
        import es.uji.ei1027.Mayorescasa.model.Asistente;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Controller;
        import org.springframework.ui.Model;
        import org.springframework.validation.BindingResult;
        import org.springframework.web.bind.annotation.ModelAttribute;
        import org.springframework.web.bind.annotation.PathVariable;
        import org.springframework.web.bind.annotation.RequestMapping;
        import org.springframework.web.bind.annotation.RequestMethod;

        import javax.servlet.http.HttpSession;
        import java.util.ArrayList;
        import java.util.List;

@Controller
@RequestMapping("/asistente")
public class AsistenteController {

    private AsistenteDao asistenteDao;

    @Autowired
    public void setAsistenteDao(AsistenteDao asistenteDao) {
        this.asistenteDao=asistenteDao;
    }

    // Operaciones: Crear, listar, actualizar, borrar

    @RequestMapping("/list")
    public String listasistentes(Model model) {
        model.addAttribute("asistentes", asistenteDao.getAsistentes());
        return "asistente/list";
    }
    //Llamada de la peticion add
    @RequestMapping(value="/add")
    public String addasistente(Model model) {
        model.addAttribute("asistente", new Asistente());
        List<String> generoList = new ArrayList<>();
        generoList.add("Femenini");
        generoList.add("Masculino");
        model.addAttribute("generoList", generoList);
        return "asistente/add";
    }

    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("asistente") Asistente asistente,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "asistente/add";
        asistenteDao.addAsistente(asistente);
        return "redirect:list";
    }

    @RequestMapping(value="/update/{usuario}", method = RequestMethod.GET)
    public String editasistente(Model model, @PathVariable String usuario) {
        model.addAttribute("asistente", asistenteDao.getAsistente(usuario));
        return "asistentes/update";
    }

    @RequestMapping(value="/update", method = RequestMethod.POST)
    public String processUpdateSubmit(
            @ModelAttribute("asistente") Asistente asistente,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "asistente/update";
        asistenteDao.updateAsistente(asistente);
        return "redirect:list";
    }

    @RequestMapping(value="/delete/{usuario}")
    public String processDelete(@PathVariable String usuario) {
        asistenteDao.deleteAsistente(usuario);
        return "redirect:../list";
    }

    @RequestMapping("/index")
    public String index(HttpSession session, Model model) {
        return "asistente/index";
    }

    @RequestMapping("/solicitar")
    public String solicitar(Model model) {
        model.addAttribute("asistentes", asistenteDao.getAsistentes());
        return "asistente/solicitar";
    }

    @RequestMapping("/contacto")
    public String contacto(HttpSession session, Model model) {
        return "asistente/contacto";
    }



}
