package es.uji.ei1027.Mayorescasa.controller;

import es.uji.ei1027.Mayorescasa.dao.ContratoDao;
import es.uji.ei1027.Mayorescasa.model.Contrato;
import es.uji.ei1027.Mayorescasa.model.Empresa;
import es.uji.ei1027.Mayorescasa.model.Peticion;
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
import java.util.Date;
import java.util.List;
import java.util.Random;

@Controller
@RequestMapping("/contrato")
public class ContratoController {

    private ContratoDao contratoDao;

    @Autowired
    public void setContratoDao(ContratoDao contratoDao) {
        this.contratoDao=contratoDao;
    }

    @RequestMapping("/list")
    public String listcontratos(Model model) {
        model.addAttribute("contratos", contratoDao.getContratos());
        System.out.println(contratoDao.getContratos());
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
        return "contrato/add";
    }

    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("contrato") Contrato contrato,
                                   BindingResult bindingResult) {
        ContratoValidator contratoValidator = new ContratoValidator();
        contratoValidator.validate(contrato, bindingResult);
        if (bindingResult.hasErrors()) {
            System.out.println("Error al añadir el contrato");
            return "contrato/add";
        }
        List<Empresa>lista=contratoDao.getEmpresas();
        List<Peticion>listaPet=contratoDao.getPeticiones();
        List<String> usuEmpresas = new ArrayList<>();
        for(Empresa empresa:lista) usuEmpresas.add(empresa.getUsuario());
        if(usuEmpresas.contains(contrato.getEmpresa())) {
            contrato.setCodcontrato(aleatorio()+"CNT");
            contratoDao.addContrato(contrato);
            Date fecha = new Date();
            System.out.println(contrato.getTiposervicio());
            System.out.println("*****************************");
            if (contrato.getTiposervicio().equals("Limpieza")){
                contratoDao.updateAdd(fecha,null,200,contrato.getCodcontrato()); }
            if (contrato.getTiposervicio().equals("Cattering")){
                contratoDao.updateAdd(fecha,null,300,contrato.getCodcontrato()); }
            if (contrato.getTiposervicio().equals("Sanitario")){
                contratoDao.updateAdd(fecha,null,150,contrato.getCodcontrato()); }
            return "contrato/add";
        }
        return "contrato/existe";
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

