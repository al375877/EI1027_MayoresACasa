package es.uji.ei1027.Mayorescasa.controller;


import es.uji.ei1027.Mayorescasa.dao.EmpresaDao;
import es.uji.ei1027.Mayorescasa.dao.UsuarioDao;
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

import javax.sound.midi.SysexMessage;
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

    @RequestMapping(value = "/update/{usuario}", method = RequestMethod.GET)
    public String editempresa(Model model, @PathVariable String usuario) {
        System.out.println(usuario);
        model.addAttribute("empresa", empresaDao.getEmpresa(usuario));
        return "empresa/update";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String processUpdateSubmit(
            @ModelAttribute("empresa") Empresa empresa,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "empresa/update";
        System.out.println(empresa.getContacto());
        System.out.println();
        System.out.println(empresa.getUsuario());
        empresaDao.updateEmpresa(empresa);
        return "redirect:list";
    }

    @RequestMapping("/list")
    public String listEmpresas(Model model) {
        model.addAttribute("empresas", empresaDao.getEmpresas());
        return "empresa/list";
    }

    @RequestMapping("/unilist/{usuario}")
    public String unilistEmpresas(Model model, @PathVariable String usuario) {
        model.addAttribute("empresa", empresaDao.getEmpresa(usuario));
        return "empresa/unilist";
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
        empresaDao.addUsuario(empresa.getNombre(),empresa.getUsuario(), empresa.getContraseña(), empresa.getEmail(), empresa.getDireccion(),
                empresa.getCif(), null, null, empresa.getTelefono(), "Empresa", null);
        System.out.println("");
        System.out.println("");
        System.out.println("EMAIL ENVIADO");
        System.out.println("*************************************************************************");
        System.out.println("Correo destinatario: " +empresaDao.getUsuarioDni(empresa.getCif()).getEmail()+ "\n"+
                "Correo del que envia: mayoresEnCasa@gva.es\n" +
                "Estimado/a señor/a "+ empresa.getContacto() + ":");
        System.out.println("Desde MAYORES EN CASA le comunicamos que ha sido registrada su empresa. Debe tener");
        System.out.println("en cuenta que este usuario caduca a los 30 días, lo podrá cambiar en 2 días");
        System.out.println("Su usuario y contraseña son:  ");
        System.out.println("   Usuario: " + empresa.getUsuario());
        System.out.println("   Contraseña: " + empresa.getContraseña());
        System.out.println("Gracias por su colaboración.\n");
        System.out.println("Mayores en casa.\n");
        System.out.println("*************************************************************************");
        return "redirect:../empresa/list";
    }

    @RequestMapping(value="/delete/{usuario}")
    public String processDelete(@PathVariable String usuario) {
        empresaDao.deleteEmpresa(usuario);
        empresaDao.deleteUsuario(usuario);
        return "redirect:../list";
    }

    @RequestMapping("/index")
    public String index(Model model) {
        return "empresa/index";
    }






}