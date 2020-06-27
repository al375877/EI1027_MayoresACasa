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

    // Operaciones: Crear, listar, actualizar, borrar

    @RequestMapping("/list")
    public String listpeticiones(Model model, HttpSession session) {
        Usuario user= (Usuario) session.getAttribute("user");
//        //mapa para enlazar la peticion con las empresas de ese tipo de servicio
//        Map<Peticion, List<String>> peticionEmpresas= new HashMap();
//
//        for(Peticion pet:peticionDao.getPeticionesPendientes()){
//            List<String> empresas= contratoDao.getEmpresasC(pet.getTiposervicio());
//            peticionEmpresas.put(pet,empresas);
//            }
//        model.addAttribute("map",peticionEmpresas);
        model.addAttribute("map",peticionDao.getPeticionesPendientes());
        model.addAttribute("contratos", contratoDao.getContratos());
        return "peticion/list";
    }

    @RequestMapping("/listAcepRech")
    public String listAcepRech(Model model, HttpSession session) {
        Usuario user= (Usuario) session.getAttribute("user");
        model.addAttribute("aceptadas",peticionDao.getPeticionesAceptadas());
        model.addAttribute("rechazadas",peticionDao.getPeticionesRechazadas());
        return "peticion/listAcepRech";
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

    @RequestMapping(value = "/update/{codigo}", method = RequestMethod.GET)
    public String editpeticion(Model model, @PathVariable String codigo) {
        model.addAttribute("peticion", peticionDao.getPeticion(codigo));
        model.addAttribute("contratos", contratoDao.getContratos());
        return "peticion/update";
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

    @RequestMapping(value = "/addComentarioL", method = RequestMethod.POST)
    public String limpieza(@ModelAttribute("comment") String comentario, HttpSession session) {
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
    }


    @RequestMapping(value = "/addComentarioC", method = RequestMethod.POST)
    public String catering(@ModelAttribute("comment") String comentario, HttpSession session) {
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
    }

    @RequestMapping(value = "/addComentarioS", method = RequestMethod.POST)
    public String sanitario(@ModelAttribute("comment") String comentario, HttpSession session) {
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
//        boolean existe= (boolean) session.getAttribute("existeS");
        if(existe){
            System.out.println("Ya solicitado");
            return "peticion/existe";
        }else{
            peticionDao.addPeticion(pet);
            session.setAttribute("existeS",true);
            return "peticion/solicitada";
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
    @RequestMapping(value = "/aceptar", method = RequestMethod.POST)
    public String aceptarPeticion(@ModelAttribute("cod") String cod) {
        Peticion pet = peticionDao.getPeticion(cod);
        String empresa = pet.getEmpresa();
        String servicio = pet.getTiposervicio();
        Contrato contrato=contratoDao.getContratoE(empresa,servicio);
        Factura fac;
        if (contrato!=null){
            pet.setCodcontrato(contrato.getCodcontrato());
            Date fecha = new Date();
            peticionDao.updateEstadoFechaEmpresa("Aceptada",fecha,empresa,pet.getCod_pet());
            //********************************************************************
            fac = peticionDao.getFactura(pet.getDni_ben());
            peticionDao.addLineas_fac(fac.getCod_fac(),pet.getCod_pet(),0,pet.getTiposervicio(),pet.getPrecioservicio());
            System.out.println("************************************************");
            System.out.println("LINEA DE FACTURA AÑADIDA");
            System.out.println("Factura: " + fac.getCod_fac() );
            System.out.println("Cliente: " + pet.getBeneficiario() );
            System.out.println("DNI: " + pet.getDni_ben());
            System.out.println("Precio del servicio: " + pet.getPrecioservicio());
            System.out.println("Tipo de servicio: " + pet.getTiposervicio());
            System.out.println("************************************************");
            peticionDao.updateFactura(fac.getPrecio()+pet.getPrecioservicio(),pet.getBeneficiario(),fac.getCod_fac());
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
            return "redirect:list";
        } else {
            System.out.println("Sin contrato");
            return "contrato/sinContrato";
        }

    }

    @RequestMapping(value="/rechazar/{cod}")
    public String rechazarPeticion(@PathVariable String cod, Model model) {
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
    }

    @RequestMapping("/misPeticiones")
    public String misPeticiones(HttpSession session, Model model) {
        Usuario user= (Usuario) session.getAttribute("user");
        model.addAttribute("peticiones", peticionDao.getPeticionesPropiasPendientes(user.getDni()));
        model.addAttribute("petAceptadas", peticionDao.getPeticionesPropiasAceptadas(user.getDni()));
        model.addAttribute("petRechazadas", peticionDao.getPeticionesPropiasRechazadas(user.getDni()));
        return "peticion/misPeticiones";
    }

    @RequestMapping("/info")
    public String limpieza(Model model) {
        return "peticion/info";
    }

    @RequestMapping("/sinContrato")
    public String sinContrato(Model model) {
        return "contrato/sinContrato";
    }


    @RequestMapping("/facturas")
    public String facturas(Model model) {
        model.addAttribute("facturas", peticionDao.getFacturas());
        return "peticion/facturas";
    }

    @RequestMapping(value = "/genFactura/{cod}")
    public String genFactura(@PathVariable String cod, Model model) {

        Factura fac;
        ArrayList<Lineas_fac> lineas;
        fac = peticionDao.getFacturaCod(cod);
        lineas = (ArrayList) peticionDao.getLineas_fac(cod);
        System.out.println("");
        System.out.println("");
        System.out.println("FACTURA GENERADA");
        System.out.println("****************************************");
        System.out.println("CODIGO DE FACTURA: " + fac.getCod_fac());
        System.out.println("****************************************");
        System.out.println("Cliente: " + fac.getConcepto() + "      DNI: " + fac.getdniBen());
        System.out.println("");
        System.out.println("TIPO DE SERVICIO      PRECIO");
        for (Lineas_fac linea:lineas){
            System.out.println("   " + linea.getTiposervicio() + "          " + linea.getPrecioservicio());
        }
        System.out.println("");
        System.out.println("");
        System.out.println("                      TOTAL: " + fac.getPrecio() + " EUROS");
        System.out.println("****************************************");

        return "redirect:../facturas";
    }


}
