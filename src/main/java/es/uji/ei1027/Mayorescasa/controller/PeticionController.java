package es.uji.ei1027.Mayorescasa.controller;

import es.uji.ei1027.Mayorescasa.dao.PeticionDao;
import es.uji.ei1027.Mayorescasa.model.Peticion;
import es.uji.ei1027.Mayorescasa.model.Usuario;
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
@RequestMapping("/peticion")
public class PeticionController {

    private PeticionDao peticionDao;
    private int codigo;
    private List<String> beneficiarios = new ArrayList<>();

    @Autowired
    public void setPeticionDao(PeticionDao peticionDao) {
        this.peticionDao = peticionDao;
    }

    // Operaciones: Crear, listar, actualizar, borrar

    @RequestMapping("/list")
    public String listpeticiones(Model model) {
        model.addAttribute("peticiones", peticionDao.getPeticiones());
        return "peticion/list";
    }

    //Llamada de la peticion add
    @RequestMapping(value = "/add")
    public String addpeticion(Model model) {
        model.addAttribute("peticion", new Peticion());
        return "peticion/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("peticion") Peticion peticion,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "peticion/add";
        peticionDao.addPeticion(peticion);
        return "redirect:list";
    }

    @RequestMapping(value = "/update/{usuario}", method = RequestMethod.GET)
    public String editpeticion(Model model, @PathVariable String usuario) {
        model.addAttribute("peticion", peticionDao.getPeticion(usuario));
        return "peticiones/update";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String processUpdateSubmit(
            @ModelAttribute("peticion") Peticion peticion,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "peticion/update";
        peticionDao.updatePeticion(peticion);
        return "redirect:list";
    }

    @RequestMapping(value = "/delete/{usuario}")
    public String processDelete(@PathVariable String usuario) {
        peticionDao.deletePeticion(usuario);
        return "redirect:../list";
    }

    @RequestMapping("/servicios")
    public String servicios(Model model) {
        return "peticion/servicios";
    }

    @RequestMapping("/limpieza")
    public void limpieza(HttpSession session, Model model) {

        Usuario user = (Usuario) session.getAttribute("user");
        codigo++;
        Peticion pet = new Peticion();
        pet.setCod_pet(aleatorio() + "LIMP");
        pet.setTiposervicio("LIMPIEZA");
        pet.setDni_ben(user.getDni());
        pet.setLinea(codigo);
        pet.setPrecioservicio(200);
        pet.setComentarios("Peticion esperando aprobacion");

        if (beneficiarios.contains(pet.getDni_ben() + "LIMP")) {

            System.out.println("----------HE PUTO ENTRADO PERO NO HAGO NADA------------------");
            session.setAttribute("existe",true);

        } else {
            beneficiarios.add(pet.getDni_ben()+"LIMP");
            peticionDao.addPeticion(pet);
            session.setAttribute("existe",false);
        }

    }



    @RequestMapping("/cattering")
    public void cattering(HttpSession session, Model model) {
        Usuario user =new Usuario();
        user= (Usuario) session.getAttribute("user");
        codigo++;
        Peticion pet = new Peticion();
        pet.setCod_pet(aleatorio()  + "CATT");
        pet.setTiposervicio("CATTERING");
        pet.setDni_ben(user.getDni()); //Retocar
        pet.setLinea(codigo);
        pet.setPrecioservicio(300);
        pet.setComentarios("Peticion esperando aprobacion");
        if (beneficiarios.contains(pet.getDni_ben()+ "CATT")){
            model.addAttribute("model", "#existe");
        } else {
            beneficiarios.add(pet.getDni_ben()+ "CATT");
            peticionDao.addPeticion(pet);
            model.addAttribute("model", "#noexiste");
        }

    }

    @RequestMapping("/sanitario")
    public void sanitario(HttpSession session, Model model) {
        Usuario user =new Usuario();
        user= (Usuario) session.getAttribute("user");
        codigo++;
        Peticion pet = new Peticion();
        pet.setCod_pet(aleatorio()  + "SAN");
        pet.setTiposervicio("SANITARIO");
        pet.setDni_ben(user.getDni()); //Retocar
        pet.setLinea(codigo);
        pet.setPrecioservicio(150);
        if (beneficiarios.contains(pet.getDni_ben()+"SAN")){
            model.addAttribute("model", "#existe");
        } else {
            beneficiarios.add(pet.getDni_ben()+"SAN");
            peticionDao.addPeticion(pet);
            model.addAttribute("model", "#noexiste");
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

    @RequestMapping(value="/aceptar/{cod}")
    public String aceptarPeticion(@PathVariable String cod) {
        Peticion pet;
        pet = peticionDao.getPeticion(cod);
        Date fecha = new Date();
        System.out.println(fecha);
        pet.setFechaaceptada(fecha);
        pet.setComentarios("Peticion ACEPTADA");
        peticionDao.updatePeticion(pet);
        return "redirect:../list";
    }

    @RequestMapping(value="/rechazar/{cod}")
    public String rechazarPeticion(@PathVariable String cod) {
        Peticion pet;
        pet = peticionDao.getPeticion(cod);
        Date fecha = new Date();
        System.out.println(fecha);
        pet.setFecharechazada(fecha);
        pet.setComentarios("Peticion RECHAZADA");
        peticionDao.updatePeticion(pet);
        return "redirect:../list";
    }

    @RequestMapping("/misPeticiones")
    public String misPeticiones(HttpSession session, Model model) {
        Usuario user =new Usuario();
        user= (Usuario) session.getAttribute("user");
        model.addAttribute("peticiones", peticionDao.getPeticionesPropias(user.getDni()));
        return "peticion/misPeticiones";
    }

}
