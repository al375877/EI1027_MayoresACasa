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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        List<String> codPets = new ArrayList<>();
        for(Peticion peticion:listaPet) codPets.add(peticion.getCod_pet());
        for(Empresa empresa:lista) usuEmpresas.add(empresa.getUsuario());
//        System.out.println(codPets + contrato.getCod_pet());
//        System.out.println(usuEmpresas + contrato.getEmpresa());
//        System.out.println("*******************************************");
        if((codPets.contains(contrato.getCod_pet())) && (usuEmpresas.contains(contrato.getEmpresa()))) {
            Date fecha = new Date();
            System.out.println(fecha);
            contratoDao.addContrato(contrato);
            contrato.setfechainicial(fecha);
            contratoDao.updateFechas(fecha,null,contrato.getEmpresa());
            return "contrato/add";
        }
        return "contrato/existe";
    }
}

