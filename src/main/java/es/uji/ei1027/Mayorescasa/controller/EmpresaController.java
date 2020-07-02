package es.uji.ei1027.Mayorescasa.controller;


import es.uji.ei1027.Mayorescasa.dao.EmpresaDao;
import es.uji.ei1027.Mayorescasa.dao.UsuarioDao;
import es.uji.ei1027.Mayorescasa.model.Contrato;
import es.uji.ei1027.Mayorescasa.model.Empresa;
import es.uji.ei1027.Mayorescasa.model.Peticion;
import es.uji.ei1027.Mayorescasa.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import javax.sound.midi.SysexMessage;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/empresa")
public class EmpresaController {

    private EmpresaDao empresaDao;

    @Autowired
    public void setEmpresaDao(EmpresaDao empresaDao) {
        this.empresaDao=empresaDao;
    }

    // Operaciones: Crear, listar, actualizar, borrar
    @RequestMapping("/info")
    public String infoEmpresas(Model model) {
        return "empresa/info";
    }

    @RequestMapping(value = "/update/{usuario}", method = RequestMethod.GET)
    public String editempresa(HttpSession session, Model model, @PathVariable String usuario) {
        Usuario user= (Usuario) session.getAttribute("user");
        try{
            if(user.getTipoUsuario().equals("casManager") || user.getTipoUsuario().equals("Empresa")) {
                model.addAttribute("empresa", empresaDao.getEmpresa(usuario));
                return "empresa/update";
            }
        } catch (Exception e) {
            return "error/error";
        }
        return "error/error";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String processUpdateSubmit(HttpSession session,
            @ModelAttribute("empresa") Empresa empresa, BindingResult bindingResult) {
        Usuario user= (Usuario) session.getAttribute("user");
        if (bindingResult.hasErrors())
            return "empresa/update";
        empresaDao.updateEmpresa(empresa);
        if(user.getTipoUsuario().equals("casManager")) return "redirect:list";
        return "redirect:unilist/" + user.getUsuario();
    }

    @RequestMapping("/list")
    public String listEmpresas(HttpSession session, Model model) {
        Usuario user= (Usuario) session.getAttribute("user");
        try{
            if(user.getTipoUsuario().equals("casManager") || user.getTipoUsuario().equals("casCommitee") || user.getTipoUsuario().equals("casVolunteer")) {
                model.addAttribute("empresas", empresaDao.getEmpresas());
                return "empresa/list";
            }
        } catch (Exception e) {
            return "error/error";
        }
        return "error/error";
    }

    @RequestMapping("/unilist/{usuario}")
    public String unilistEmpresas(HttpSession session, Model model, @PathVariable String usuario) {
        Usuario user= (Usuario) session.getAttribute("user");
        try{
            if(user.getTipoUsuario().equals("casManager") || user.getTipoUsuario().equals("Empresa")) {
                model.addAttribute("empresa", empresaDao.getEmpresa(usuario));
                if(user.getTipoUsuario().equals("casManager")){
                    model.addAttribute("tipoUser", "casCommitee");
                }
                return "empresa/unilist";
            }
        } catch (Exception e) {
            return "error/error";
        }
        return "error/error";
    }
    //Llamada de la peticion add
    @RequestMapping(value="/add")
    public String addEmpresa(HttpSession session, Model model) {
        Usuario user= (Usuario) session.getAttribute("user");
        try{
            if(user.getTipoUsuario().equals("casManager")) {
                model.addAttribute("empresa", new Empresa());
                return "empresa/add";
            }
        } catch (Exception e) {
            return "error/error";
        }
        return "error/error";
    }

    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("empresa") Empresa empresa,
                                   BindingResult bindingResult) {
        EmpresaValidator emrpesaValidator = new EmpresaValidator();
        emrpesaValidator.validate(empresa, bindingResult);
        if (bindingResult.hasErrors())
            return "empresa/add";
        if (empresaDao.existe(empresa.getUsuario(),empresa.getNombre(),empresa.getCif())!=null){
            return "empresa/empExiste";
        }
        empresaDao.addEmpresa(empresa);
        empresaDao.addUsuario(empresa.getNombre(),empresa.getUsuario(), empresa.getContraseña(), empresa.getEmail(), empresa.getDireccion(),
                empresa.getCif(), null, null, empresa.getTelefono(), "Empresa", null);
        System.out.println("");
        System.out.println("");
        System.out.println("EMAIL ENVIADO");
        System.out.println("*************************************************************************");
        System.out.println("Correo destinatario: " +empresa.getCont_mail() + "\n"+
                "Correo del que envia: mayoresEnCasa@gva.es\n" +
                "Estimado/a señor/a "+ empresa.getContacto() + ":");
        System.out.println("Desde MAYORES EN CASA le comunicamos que ha sido registrada su empresa.");
        System.out.println("Le mandamos un usuario y una contraseña provisional, para que pueda acceder a la web.");
        System.out.println("Debe tener en cuenta que este usuario caduca a los 30 días.");
        System.out.println("Su usuario y contraseña son:  ");
        System.out.println("   Usuario: " + empresa.getUsuario());
        System.out.println("   Contraseña: " + empresa.getContraseña());
        System.out.println("Gracias por su colaboración.\n");
        System.out.println("Mayores en casa.\n");
        System.out.println("*************************************************************************");
        return "empresa/empRegistrada";
    }

    @RequestMapping(value="/delete/{usuario}")
    public String processDelete(@PathVariable String usuario) {
        Empresa empresa = empresaDao.getEmpresa(usuario);
        List<Contrato> lista= empresaDao.buscaContrato(empresa.getNombre());
        if (lista.size()==0){
            empresaDao.deleteEmpresa(usuario);
            empresaDao.deleteUsuario(usuario);
        } else {
            return "empresa/contActivo";
        }
        return "redirect:../list";
    }

    @RequestMapping(value="/misContratosActivo")
    public String misContratos(HttpSession session, Model model) {
        Usuario user= (Usuario) session.getAttribute("user");
        try{
            if(user.getTipoUsuario().equals("Empresa")) {
                Date fecha = new Date();
                model.addAttribute("contratos", empresaDao.getContratoActivo(user.getNombre(), fecha));
                return "empresa/misContratosActivo";
            }
        } catch (Exception e) {
            return "error/error";
        }
        return "error/error";
    }

    @RequestMapping(value="/misServiciosActivo")
    public String misServiciosActivo(HttpSession session, Model model) {
        Usuario user= (Usuario) session.getAttribute("user");
        try{
            if(user.getTipoUsuario().equals("Empresa")) {
                model.addAttribute("servicios", empresaDao.getServiciosActivo(user.getNombre()));
                return "empresa/misServiciosActivo";
            }
        } catch (Exception e) {
            return "error/error";
        }
        return "error/error";
    }

    @RequestMapping(value="/misContratosPasados")
    public String misContratosPasados(HttpSession session, Model model) {
        Usuario user= (Usuario) session.getAttribute("user");
        try{
            if(user.getTipoUsuario().equals("Empresa")) {
                Date fecha = new Date();
                model.addAttribute("pasados", empresaDao.getContratoPasado(user.getNombre(), fecha));
                return "empresa/misContratosPasados";
            }
        } catch (Exception e) {
            return "error/error";
        }
        return "error/error";
    }

    @RequestMapping(value="/misDatos")
    public String misDatos(HttpSession session, Model model) {
        Usuario user= (Usuario) session.getAttribute("user");
        try{
            if(user.getTipoUsuario().equals("Empresa")) {
                model.addAttribute("datos", empresaDao.getEmpresa(user.getUsuario()));
                return "empresa/misDatos";
            }
        } catch (Exception e) {
            return "error/error";
        }
        return "error/error";
    }

    @RequestMapping("/index")
    public String index(HttpSession session, Model model) {
        Usuario user= (Usuario) session.getAttribute("user");
        try{
            if(user.getTipoUsuario().equals("Empresa")) {
                model.addAttribute("perfil", empresaDao.getUsuario(user.getUsuario()));
                return "empresa/index";
            }
        } catch (Exception e) {
            return "error/error";
        }
        return "error/error";
    }

    @RequestMapping("/contactaCas")
    public String contactaCas(HttpSession session, Model model) {
        Usuario user= (Usuario) session.getAttribute("user");
        try{
            if(user.getTipoUsuario().equals("Empresa")) {
                model.addAttribute("datos", empresaDao.getCasManager());
                return "empresa/contactaCas";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "error/error";
        }
        return "error/error";
    }

    @RequestMapping(value = "/addComentario", method = RequestMethod.POST)
    public String limpieza(@ModelAttribute("comment") String comentario, HttpSession session) {
        Usuario user= (Usuario) session.getAttribute("user");
        try{
            if (comentario==null) comentario="";
            Usuario cas = empresaDao.getCasManager();
            System.out.println("");
            System.out.println("");
            System.out.println("EMAIL ENVIADO");
            System.out.println("*************************************************************************");
            System.out.println("Correo destinatario: " +cas.getEmail() + "\n"+
                    "Correo del que envia: mayoresEnCasa@gva.es\n" +
                    "Estimado/a señor/a "+ cas.getNombre() + ":");
            System.out.println("La empresa " + user.getNombre() + " tiene la siguiente duda: \n");
            System.out.println(comentario + "\n");
            System.out.println("");
            System.out.println("Mayores en casa.\n");
            System.out.println("*************************************************************************");
            return "empresa/enviado";
        } catch (Exception e){
            return "error/error";
        }
    }

    @RequestMapping("/enviado")
    public String enviado(HttpSession session, Model model) {
        Usuario user= (Usuario) session.getAttribute("user");
        try{
            if(user.getTipoUsuario().equals("Empresa")) {
                return "empresa/enviado";
            }
        } catch (Exception e) {
            return "error/error";
        }
        return "error/error";
    }

}