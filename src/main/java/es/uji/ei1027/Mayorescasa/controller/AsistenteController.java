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

    @RequestMapping("/solicitar")
    public String solicitar(Model model) {
        model.addAttribute("asistentes", asistenteDao.getAsistentes());
        return "asistente/solicitar";
    }

}
