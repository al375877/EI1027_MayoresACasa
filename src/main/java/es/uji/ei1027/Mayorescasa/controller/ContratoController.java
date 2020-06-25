package es.uji.ei1027.Mayorescasa.controller;

import es.uji.ei1027.Mayorescasa.dao.ContratoDao;
import es.uji.ei1027.Mayorescasa.dao.EmpresaDao;
import es.uji.ei1027.Mayorescasa.model.Contrato;
import es.uji.ei1027.Mayorescasa.model.Empresa;
import es.uji.ei1027.Mayorescasa.model.Peticion;
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
    public String listcontratos(Model model) {
        model.addAttribute("contratos", contratoDao.getContratos());
        return "contrato/list";
    }

    @RequestMapping(value="/delete/{codcontrato}")
    public String processDelete(@PathVariable String codcontrato) {
        contratoDao.deleteContrato(codcontrato);
        return "redirect:../list";
    }

    @RequestMapping("/existe")
    public String index(HttpSession session, Model model) {
        return "contrato/existe";
    }

    //Llamada de la contrato add
    @RequestMapping(value="/add")
    public String addcontrato(Model model) {
        model.addAttribute("contrato", new Contrato());
        List<Empresa> empresas=empresaDao.getEmpresas();
        List<String> usersEmpresas=new ArrayList<>();
        for(Empresa empresa: empresas){
            usersEmpresas.add(empresa.getNombre());
        }
        model.addAttribute("empresas",usersEmpresas);
        return "contrato/add";
    }

    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("contrato") Contrato contrato, BindingResult bindingResult) {
        ContratoValidator contratoValidator = new ContratoValidator();
        contratoValidator.validate(contrato, bindingResult);
        if (bindingResult.hasErrors()) {
            System.out.println("Error al añadir el contrato");
            return "contrato/add";
        }
        if(contratoDao.existeServicio(contrato.getEmpresa(),contrato.getTiposervicio())) {
            contrato.setCodcontrato(aleatorio()+"CNT");
            contratoDao.addContrato(contrato);
        } else {
            return "contrato/existe";
        }
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

    @RequestMapping("/contAñadiddo")
    public String contAñadiddo(Model model) {
        return "contrato/contAñadiddo";
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

