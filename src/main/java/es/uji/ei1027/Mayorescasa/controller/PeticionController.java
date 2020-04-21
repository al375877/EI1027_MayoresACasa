package es.uji.ei1027.Mayorescasa.controller;

import es.uji.ei1027.Mayorescasa.dao.PeticionDao;
import es.uji.ei1027.Mayorescasa.model.Peticion;
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
@RequestMapping("/peticion")
public class PeticionController {

    private PeticionDao peticionDao;

    @Autowired
    public void setPeticionDao(PeticionDao peticionDao) {
        this.peticionDao=peticionDao;
    }

    // Operaciones: Crear, listar, actualizar, borrar

    @RequestMapping("/list")
    public String listpeticiones(Model model) {
        model.addAttribute("peticiones", peticionDao.getPeticiones());
        return "peticion/list";
    }
    //Llamada de la peticion add
    @RequestMapping(value="/add")
    public String addpeticion(Model model) {
        model.addAttribute("peticion", new Peticion());
        List<String> generoList = new ArrayList<>();
        generoList.add("Femenini");
        generoList.add("Masculino");
        model.addAttribute("generoList", generoList);
        return "peticion/add";
    }

    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("peticion") Peticion peticion,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "peticion/add";
        peticionDao.addPeticion(peticion);
        return "redirect:list";
    }

    @RequestMapping(value="/update/{usuario}", method = RequestMethod.GET)
    public String editpeticion(Model model, @PathVariable String usuario) {
        model.addAttribute("peticion", peticionDao.getPeticion(usuario));
        return "peticiones/update";
    }

    @RequestMapping(value="/update", method = RequestMethod.POST)
    public String processUpdateSubmit(
            @ModelAttribute("peticion") Peticion peticion,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "peticion/update";
        peticionDao.updatePeticion(peticion);
        return "redirect:list";
    }

    @RequestMapping(value="/delete/{usuario}")
    public String processDelete(@PathVariable String usuario) {
        peticionDao.deletePeticion(usuario);
        return "redirect:../list";
    }

    @RequestMapping("/servicios")
    public String servicios(Model model) {
        model.addAttribute("peticiones", peticionDao.getPeticiones());
        return "peticion/servicios";
    }



}
