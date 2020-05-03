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
import java.util.Random;

@Controller
@RequestMapping("/peticion")
public class PeticionController {

    private PeticionDao peticionDao;
    private int codigo;
    private List<String> beneficiarios= new ArrayList<>();

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
        return "peticion/servicios";
    }

    @RequestMapping("/limpieza")
    public String limpieza(Model model) {
        codigo++;
        Peticion pet = new Peticion();
        pet.setCod_pet(aleatorio() + "LIMP");
        pet.setTiposervicio("LIMPIEZA");
        pet.setDni_ben("54781268Q"); //Retocar
        pet.setLinea(codigo);
        pet.setPrecioservicio(200);
        pet.setComentarios("Peticion esperando aprobacion");
        if (beneficiarios.contains(pet.getDni_ben() + "LIMP")){
            return "peticion/existe";
        } else {
            beneficiarios.add(pet.getDni_ben() + "LIMP");
            peticionDao.addPeticion(pet);
            return "peticion/servicios";
        }

    }

    @RequestMapping("/cattering")
    public String cattering(Model model) {
        codigo++;
        Peticion pet = new Peticion();
        pet.setCod_pet(aleatorio()  + "CATT");
        pet.setTiposervicio("54781268Q");
        pet.setDni_ben("jesus"); //Retocar
        pet.setLinea(codigo);
        pet.setPrecioservicio(300);
        pet.setComentarios("Peticion esperando aprobacion");
        if (beneficiarios.contains(pet.getDni_ben()+ "CATT")){
            return "peticion/existe";
        } else {
            beneficiarios.add(pet.getDni_ben()+ "CATT");
            peticionDao.addPeticion(pet);
            return "peticion/servicios";
        }
    }

    @RequestMapping("/sanitario")
    public String sanitario(Model model) {
        codigo++;
        Peticion pet = new Peticion();
        pet.setCod_pet(aleatorio()  + "SAN");
        pet.setTiposervicio("54781268Q");
        pet.setDni_ben("jesus"); //Retocar
        pet.setLinea(codigo);
        pet.setPrecioservicio(150);
        if (beneficiarios.contains(pet.getDni_ben()+"SAN")){
            return "peticion/existe";
        } else {
            beneficiarios.add(pet.getDni_ben()+"SAN");
            peticionDao.addPeticion(pet);
            return "peticion/servicios";
        }
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
