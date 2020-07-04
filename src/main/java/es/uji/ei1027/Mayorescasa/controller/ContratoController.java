package es.uji.ei1027.Mayorescasa.controller;

import es.uji.ei1027.Mayorescasa.dao.ContratoDao;
import es.uji.ei1027.Mayorescasa.dao.EmpresaDao;
import es.uji.ei1027.Mayorescasa.model.Contrato;
import es.uji.ei1027.Mayorescasa.model.Empresa;
import es.uji.ei1027.Mayorescasa.model.Peticion;
import es.uji.ei1027.Mayorescasa.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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
import java.util.Random;

@Controller
@RequestMapping("/contrato")
public class ContratoController {
    @Autowired
    private ContratoDao contratoDao;
    @Autowired
    EmpresaDao  empresaDao;
    @Autowired
    public void setContratoDao(ContratoDao contratoDao) {
        this.contratoDao=contratoDao;
    }

    @RequestMapping("/list")
    public String listcontratos(HttpSession session, Model model) {
        Usuario user= (Usuario) session.getAttribute("user");
        try{
            if(user.getTipoUsuario().equals("casManager") || user.getTipoUsuario().equals("casCommitee")) {
                model.addAttribute("contratos", contratoDao.getContratos());
                if(user.getTipoUsuario().equals("casManager")){
                    model.addAttribute("tipoUser", "casManager");
                }
                return "contrato/list";
            }
        } catch (Exception e){
            return "error/error";
        }
        return "error/error";
    }

    @RequestMapping("/eliminar")
    public String eliminarcontratos(HttpSession session, Model model) {
        Usuario user= (Usuario) session.getAttribute("user");
        try{
            if(user.getTipoUsuario().equals("casManager")) {
                model.addAttribute("contratos", contratoDao.getContratos());
                if(user.getTipoUsuario().equals("casManager")){
                    model.addAttribute("tipoUser", "casManager");
                }
                return "contrato/eliminar";
            }
        } catch (Exception e){
            return "error/error";
        }
        return "error/error";
    }

    @RequestMapping(value="/delete/{codcontrato}")
    public String processDelete(@PathVariable String codcontrato) {
        contratoDao.finalizarContrato(codcontrato);
        contratoDao.finalizarServicios(codcontrato);
        return "redirect:../finalizado/" + codcontrato;
    }

    @RequestMapping("/finalizado/{codcontrato}")
    public String eliminado(Model model, @PathVariable String codcontrato) {
        model.addAttribute("contrato", contratoDao.getContratoFinalizado(codcontrato));
        return "contrato/finalizado";
    }

    @RequestMapping("/listPasados")
    public String listPasados(HttpSession session, Model model) {
        Usuario user= (Usuario) session.getAttribute("user");
        try{
            if(user.getTipoUsuario().equals("casManager") || user.getTipoUsuario().equals("casCommitee")) {
                model.addAttribute("listPasados", contratoDao.getPasados());
                if(user.getTipoUsuario().equals("casManager")){
                    model.addAttribute("tipoUser", "casManager");
                }
                return "contrato/listPasados";
            }
        } catch (Exception e){
            return "error/error";
        }
        return "error/error";
    }

    //Llamada de la contrato add
    @RequestMapping(value="/add")
    public String addcontrato(HttpSession session, Model model) {
        Usuario user= (Usuario) session.getAttribute("user");
        try{
            if(!user.getTipoUsuario().equals("casManager")) return "error/error";

            model.addAttribute("contrato", new Contrato());
            List<Empresa> empresas = empresaDao.getEmpresas();
            List<String> usersEmpresas = new ArrayList<>();
            for (Empresa empresa : empresas) {
                usersEmpresas.add(empresa.getNombre());
            }
            model.addAttribute("empresas", usersEmpresas);
            model.addAttribute("contratosInfo", contratoDao.getContratos());
            model.addAttribute("empresasInfo", empresaDao.getEmpresas());
            return "contrato/add";
        } catch (Exception e) {
            return "error/error";
        }
    }

    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("contrato") Contrato contrato, BindingResult bindingResult) {
        ContratoValidator contratoValidator = new ContratoValidator();
        contratoValidator.validate(contrato, bindingResult);
        if (bindingResult.hasErrors()) {
            System.out.println("Error al añadir el contrato");
            return "contrato/add";
        }
        Contrato existeCont = contratoDao.getContrato(contrato.getEmpresa());
        if (existeCont!=null) return "contrato/existe";
        contrato.setCodcontrato(aleatorio()+"CNT");
        contrato.setTiposervicio(contratoDao.getServicio(contrato.getEmpresa()).getTiposervicio());
        contratoDao.addContrato(contrato);
        Empresa empresa = contratoDao.getEmpresa(contrato.getEmpresa());
        System.out.println("");
        System.out.println("");
        System.out.println("EMAIL ENVIADO");
        System.out.println("*************************************************************************");
        System.out.println("Correo destinatario: " +empresa.getCont_mail() + "\n"+
                "Correo del que envia: mayoresEnCasa@gva.es\n" +
                "Estimado/a señor/a "+ empresa.getContacto() + ":");
        System.out.println("Desde MAYORES EN CASA le comunicamos que se ha añadido un contrato a su empresa.");
        System.out.println("Si usted no está de acuerdo con esto, o no entiende este email, por favor pongase");
        System.out.println("en contacto con nosotros respondiendo a este correo o al numero de tlf: 920 14 58 74");
        System.out.println("");
        System.out.println("Gracias por su colaboración.\n");
        System.out.println("Mayores en casa.\n");
        System.out.println("*************************************************************************");
        return "contrato/contAñadido";
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

