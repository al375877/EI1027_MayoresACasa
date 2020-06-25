package es.uji.ei1027.Mayorescasa.controller;


import es.uji.ei1027.Mayorescasa.dao.EmpresaDao;
import es.uji.ei1027.Mayorescasa.dao.UsuarioDao;
import es.uji.ei1027.Mayorescasa.model.Contrato;
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

    @RequestMapping("/index")
    public String index(Model model) {
        return "empresa/index";
    }

    @RequestMapping("/contActivo")
    public String contActivo(Model model) {
        return "empresa/contActivo";
    }

    @RequestMapping("/empRegistrada")
    public String empRegistrada(Model model) {
        return "empresa/empRegistrada";
    }

    @RequestMapping("/empExiste")
    public String empExiste(Model model) {
        return "empresa/empExiste";
    }








}