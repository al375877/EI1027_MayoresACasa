package es.uji.ei1027.Mayorescasa.controller;


import es.uji.ei1027.Mayorescasa.dao.EmpresaDao;
import es.uji.ei1027.Mayorescasa.model.Empresa;
import es.uji.ei1027.Mayorescasa.model.Usuario;
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

    @RequestMapping("/list")
    public String listEmpresas(Model model) {
        model.addAttribute("empresas", empresaDao.getEmpresas());
        return "empresa/list";
    }
    //Llamada de la peticion add
    @RequestMapping(value="/add")
    public String addEmpresa(Model model) {
        model.addAttribute("empresa", new Empresa());
        return "empresa/add";
    }

    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("empresa") Empresa empresa,
                                   BindingResult bindingResult) {
        EmpresaValidator emrpesaValidator = new EmpresaValidator();
        emrpesaValidator.validate(empresa, bindingResult);
        if (bindingResult.hasErrors())
            return "empresa/add";
        empresaDao.addEmpresa(empresa);
        return "redirect:../login";
    }

    @RequestMapping(value="/update/{usuario}", method = RequestMethod.GET)
    public String editaEmpresa(Model model, @PathVariable String usuario) {
        model.addAttribute("empresa", empresaDao.getEmpresa(usuario));
        return "empresa/update";
    }

    @RequestMapping(value="/update", method = RequestMethod.POST)
    public String processUpdateSubmit(
            @ModelAttribute("empresa") Empresa empresa,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "empresa/update";
        empresaDao.updateEmpresa(empresa);
        return "redirect:list";
    }

    @RequestMapping(value="/delete/{usuario}")
    public String processDelete(@PathVariable String usuario) {
        empresaDao.deleteEmpresa(usuario);
        return "redirect:../list";
    }




}