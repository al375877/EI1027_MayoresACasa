package es.uji.ei1027.Mayorescasa.controller;

import es.uji.ei1027.Mayorescasa.dao.*;
import es.uji.ei1027.Mayorescasa.model.*;
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
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@Controller
@RequestMapping("/peticion")
public class PeticionController {
    @Autowired
    private PeticionDao peticionDao;
    @Autowired
    private ContratoDao contratoDao;
    @Autowired
    private EmpresaDao empresaDao;
    @Autowired
    public void setPeticionDao(PeticionDao peticionDao) {
        this.peticionDao = peticionDao;
    }

    @RequestMapping("/list")
    public String listpeticiones(Model model, HttpSession session) {
        Usuario user= (Usuario) session.getAttribute("user");
        try{
            if(user.getTipoUsuario().equals("casCommitee")) {
                model.addAttribute("map", peticionDao.getPeticionesPendientes());
                model.addAttribute("contratos", contratoDao.getContratos());
                return "peticion/list";
            }
        } catch (Exception e){
            return "error/error";
        }
        return "error/error";
    }

    @RequestMapping("/listAcepRech")
    public String listAcepRech(Model model, HttpSession session) {
        Usuario user= (Usuario) session.getAttribute("user");
        try{
            if(user.getTipoUsuario().equals("casCommitee")) {
                model.addAttribute("aceptadas",peticionDao.getPeticionesAceptadas());
                model.addAttribute("rechazadas",peticionDao.getPeticionesRechazadas());
                return "peticion/listAcepRech";
            }
        } catch (Exception e){
            return "error/error";
        }
        return "error/error";
    }

    @RequestMapping(value = "/update/{codigo}", method = RequestMethod.GET)
    public String editpeticion(HttpSession session, Model model, @PathVariable String codigo) {
        Usuario user= (Usuario) session.getAttribute("user");
        try{
            if(user.getTipoUsuario().equals("casCommitee")) {
                List<Contrato> contratos = contratoDao.getContratos();
                List<String> usersEmpresas = new ArrayList<>();
                for (Contrato contrato : contratos) {
                    usersEmpresas.add(contrato.getEmpresa());
                }
                model.addAttribute("empresas", usersEmpresas);
                model.addAttribute("peticion", peticionDao.getPeticion(codigo));
                model.addAttribute("contratos", contratoDao.getContratos());
                return "peticion/update";
            }
        } catch (Exception e){
            return "error/error";
        }
        return "error/error";
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

    @RequestMapping("/servicios")
    public String servicios(HttpSession session, Model model) {
        Usuario user= (Usuario) session.getAttribute("user");
        try{
            if(user.getTipoUsuario().equals("Beneficiario")) {
                return "peticion/servicios";
            }
        } catch (Exception e){
            return "error/error";
        }
        return "error/error";
    }

    @RequestMapping(value = "/addComentarioL", method = RequestMethod.POST)
    public String limpieza(@ModelAttribute("comment") String comentario, HttpSession session) {
        try{
            if (comentario==null) comentario="";
            Usuario user = (Usuario) session.getAttribute("user");
            Peticion pet = new Peticion();
            pet.setCod_pet(aleatorio() + "LIMP");
            pet.setTiposervicio("Limpieza");
            pet.setDni_ben(user.getDni());
            pet.setBeneficiario(user.getNombre());
            pet.setPrecioservicio(7);
            pet.setComentarios(comentario);
            pet.setEstado("Pendiente");
            boolean existe= peticionDao.consultaPeticion(pet.getDni_ben(),pet.getTiposervicio());
            if(existe){
                System.out.println("Ya solicitado");
                return "peticion/existe";
            }else{
                peticionDao.addPeticion(pet);
                session.setAttribute("existeL",true);
                return "peticion/solicitada";
            }
        } catch (Exception e){
            return "error/error";
        }
    }

    @RequestMapping(value = "/addComentarioC", method = RequestMethod.POST)
    public String catering(@ModelAttribute("comment") String comentario, HttpSession session) {
        try{
            if (comentario==null) comentario="";
            Usuario user= (Usuario) session.getAttribute("user");
            Peticion pet = new Peticion();
            pet.setCod_pet(aleatorio()  + "CAT");
            pet.setTiposervicio("Catering");
            pet.setDni_ben(user.getDni());
            pet.setBeneficiario(user.getNombre());
            pet.setPrecioservicio(6);
            pet.setComentarios(comentario);
            pet.setEstado("Pendiente");
            boolean existe= peticionDao.consultaPeticion(pet.getDni_ben(),pet.getTiposervicio());
            if(existe){
                System.out.println("Ya solicitado");
                return "peticion/existe";
            }else{
                peticionDao.addPeticion(pet);
                session.setAttribute("existeC",true);
                return "peticion/solicitada";
            }
        } catch (Exception e){
            return "error/error";
        }
    }

    @RequestMapping(value = "/addComentarioS", method = RequestMethod.POST)
    public String sanitario(@ModelAttribute("comment") String comentario, HttpSession session) {
        try{
            if (comentario==null) comentario="";
            Usuario user= (Usuario) session.getAttribute("user");
            Peticion pet = new Peticion();
            pet.setCod_pet(aleatorio()  + "SAN");
            pet.setTiposervicio("Sanitario");
            pet.setDni_ben(user.getDni());
            pet.setBeneficiario(user.getNombre());
            pet.setPrecioservicio(5);
            pet.setComentarios(comentario);
            pet.setEstado("Pendiente");
            boolean existe= peticionDao.consultaPeticion(pet.getDni_ben(),pet.getTiposervicio());
            if(existe){
                System.out.println("Ya solicitado");
                return "peticion/existe";
            }else{
                peticionDao.addPeticion(pet);
                session.setAttribute("existeS",true);
                return "peticion/solicitada";
            }
        } catch (Exception e){
            return "error/error";
        }
    }

    @RequestMapping(value = "/aceptar/{cod_pet}", method = RequestMethod.GET)
    public String aceptarPeticion(@PathVariable("cod_pet") String cod_pet, HttpSession session, Model model) {
        try{
            Peticion pet = peticionDao.getPeticion(cod_pet);
            String empresa = pet.getEmpresa();
            String servicio = pet.getTiposervicio();
            Contrato contrato=peticionDao.getContratoE(empresa,servicio);
            if (contrato!=null){
                Date fecha = new Date();
                peticionDao.updateEstadoFechaEmpresa("Aceptada",fecha,empresa,pet.getCod_pet(),contrato.getCodcontrato());
                //********************************************************************
                System.out.println("");
                System.out.println("");
                System.out.println("EMAIL EMPRESA ENVIADO");
                System.out.println("*************************************************************************");
                Empresa emp = peticionDao.getEmpresa(empresa);
                System.out.println("Correo destinatario: "+ emp.getCont_mail() + ", " + emp.getEmail() + "\n" +
                        "Correo del que envia: mayoresEnCasa@gva.es\n" +
                        " \n " +
                        "Empresa, "+emp.getNombre()+", tiene un nuevo encargo para don/doña, "+pet.getBeneficiario()+".\n" +
                        "Gracias por la ayuda");
                System.out.println("*************************************************************************");
                //********************************************************************
                String pattern = "dd MMMM yyyy";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                String date = simpleDateFormat.format(pet.getFechafinal());
                System.out.println(date);
                System.out.println("");
                System.out.println("");
                System.out.println("EMAIL CLIENTE ENVIADO");
                System.out.println("*************************************************************************");
                Usuario usu = peticionDao.getUsuario(pet.getDni_ben());
                System.out.println("Correo destinatario: "+ usu.getEmail() + "\n" +
                        "Correo del que envia: mayoresEnCasa@gva.es\n" +
                        " \n " +
                        "Sr/a, "+usu.getNombre()+", su solicitud de servicio ha sido aceptada. \n " +
                        "La empresa que le ofrecera el servicio sera:" + pet.getEmpresa() +".\n" +
                        "Su servicio finaliza el " + date +".\n" +
                        "Puede consultar el estado de su peticion en su perfil de usuario.\n" +
                        "Gracias por elegir Mayores en casa");
                System.out.println("*************************************************************************");
                return "redirect:../list";
            } else {
                System.out.println("Sin contrato");
                return "contrato/sinContrato";
            }
        } catch (Exception e){
            return "error/error";
        }

    }

    @RequestMapping(value="/rechazar/{cod}")
    public String rechazarPeticion(@PathVariable String cod, Model model) {
        try{
            Peticion pet;
            pet = peticionDao.getPeticion(cod);
            Date fecha = new Date();
            peticionDao.updateEstadoFecha("Rechazada", fecha, pet.getCod_pet());
            //********************************************************************
            System.out.println("");
            System.out.println("");
            System.out.println("EMAIL CLIENTE ENVIADO");
            System.out.println("*************************************************************************");
            Usuario usu = peticionDao.getUsuario(pet.getDni_ben());
            System.out.println("Correo destinatario: "+ usu.getEmail() + "\n" +
                    "Correo del que envia: mayoresEnCasa@gva.es\n" +
                    " \n " +
                    "Sr/a, "+usu.getNombre()+", su solicitud de servicio ha sido RECHAZADA. \n " +
                    "Debido a que no tenemos empresas que ofrezcan el servico solicitado con sus restricciones"+".\n" +
                    "Puede consultar el estado de su peticion en su perfil de usuario.\n" +
                    "Gracias por elegir Mayores en casa");
            System.out.println("*************************************************************************");
            return "redirect:../list";
        } catch (Exception e){
            return "error/error";
        }
    }

    @RequestMapping("/misPeticiones")
    public String misPeticiones(HttpSession session, Model model) {
        Usuario user= (Usuario) session.getAttribute("user");
        try {
            if (user.getTipoUsuario().equals("Beneficiario")) {
                model.addAttribute("peticiones", peticionDao.getPeticionesPropiasPendientes(user.getDni()));
                model.addAttribute("petAceptadas", peticionDao.getPeticionesPropiasAceptadas(user.getDni()));
                model.addAttribute("petRechazadas", peticionDao.getPeticionesPropiasRechazadas(user.getDni()));
                return "peticion/misPeticiones";
            }
        } catch (Exception e){
            return "error/error";
        }
        return "error/error";
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
