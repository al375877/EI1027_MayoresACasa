package es.uji.ei1027.Mayorescasa.controller;

import es.uji.ei1027.Mayorescasa.dao.BeneficiarioDao;
import es.uji.ei1027.Mayorescasa.model.Beneficiario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/beneficiario")
public class BeneficiarioController {

    private BeneficiarioDao beneficiarioDao;

    @Autowired
    public void setBeneficiarioDao(BeneficiarioDao beneficiarioDao) {
        this.beneficiarioDao=beneficiarioDao;
    }

    // Operaciones: Crear, listar, actualizar, borrar

    @RequestMapping("/list")
    public String listbeneficiarios(Model model) {
        model.addAttribute("beneficiarios", beneficiarioDao.getBeneficiarios());
        return "beneficiario/list";
    }
//Llamada de la peticion add
    @RequestMapping(value="/add")
    public String addbeneficiario(Model model) {
        model.addAttribute("beneficiario", new Beneficiario());
        List<String> generoList = new ArrayList<>();
        generoList.add("Femenini");
        generoList.add("Masculino");
        model.addAttribute("generoList", generoList);
        return "beneficiario/add";
    }

    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("beneficiario") Beneficiario beneficiario,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "beneficiario/add";
        beneficiarioDao.addBeneficiario(beneficiario);
        return "redirect:list";
    }

    @RequestMapping(value="/update/{usuario}", method = RequestMethod.GET)
    public String editbeneficiario(Model model, @PathVariable String usuario) {
        model.addAttribute("beneficiario", beneficiarioDao.getBeneficiario(usuario));
        return "beneficiario/update";
    }

    @RequestMapping(value="/update", method = RequestMethod.POST)
    public String processUpdateSubmit(
            @ModelAttribute("beneficiario") Beneficiario beneficiario,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "beneficiario/update";
        beneficiarioDao.updateBeneficiario(beneficiario);
        return "redirect:list";
    }

    @RequestMapping(value="/delete/{usuario}")
    public String processDelete(@PathVariable String usuario) {
        beneficiarioDao.deleteBeneficiario(usuario);
        return "redirect:../list";
    }



}
