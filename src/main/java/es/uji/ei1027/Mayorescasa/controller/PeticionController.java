package es.uji.ei1027.Mayorescasa.controller;

import es.uji.ei1027.Mayorescasa.dao.*;
import es.uji.ei1027.Mayorescasa.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
@RequestMapping("/peticion")
public class PeticionController {
    @Autowired
    private PeticionDao peticionDao;
    private int codigo;
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
        //mapa para enlazar la peticion con las empresas de ese tipo de servicio
        Map<Peticion, List<String>> peticionEmpresas= new HashMap();

        for(Peticion pet:peticionDao.getPeticionesPendientes()){
            List<String> empresas= contratoDao.getEmpresasC(pet.getTiposervicio());
            peticionEmpresas.put(pet,empresas);
            }
        model.addAttribute("map",peticionEmpresas);
        return "peticion/list";
    }

    @RequestMapping("/listAcepRech")
    public String listAcepRech(Model model, HttpSession session) {
        Usuario user= (Usuario) session.getAttribute("user");
        model.addAttribute("listAcepRech",peticionDao.getPeticionesResueltas());
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

    @RequestMapping(value = "/addComentarioL", method = RequestMethod.POST)
    public String limpieza(@ModelAttribute("comment") String comentario, HttpSession session) {
        if (comentario==null) comentario="";
        Usuario user = (Usuario) session.getAttribute("user");
        codigo++;

        Peticion pet = new Peticion();
        pet.setCod_pet(aleatorio() + "LIMP");
        pet.setTiposervicio("Limpieza");
        pet.setDni_ben(user.getDni());
        pet.setBeneficiario(user.getNombre());
        pet.setLinea(codigo);
        pet.setPrecioservicio(168);
        pet.setComentarios(comentario);
        pet.setEstado("Pendiente");

        boolean existe= (boolean) session.getAttribute("existeL");
        if(existe){

            return "peticion/existe";
        }else{
            peticionDao.addPeticion(pet);
            session.setAttribute("existeL",true);
            return "peticion/solicitada";
        }
    }


    @RequestMapping(value = "/addComentarioC", method = RequestMethod.POST)
    public String cattering(@ModelAttribute("comment") String comentario, HttpSession session) {
        if (comentario==null) comentario="";
        Usuario user= (Usuario) session.getAttribute("user");
        codigo++;
        Peticion pet = new Peticion();
        pet.setCod_pet(aleatorio()  + "CATT");
        pet.setTiposervicio("Cattering");
        pet.setDni_ben(user.getDni());
        pet.setBeneficiario(user.getNombre());
        pet.setLinea(codigo);
        pet.setPrecioservicio(336);
        pet.setComentarios(comentario);
        pet.setEstado("Pendiente");

        boolean existe= (boolean) session.getAttribute("existeC");
        if(existe){

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
        codigo++;
        Peticion pet = new Peticion();
        pet.setCod_pet(aleatorio()  + "SAN");
        pet.setTiposervicio("Sanitario");
        pet.setDni_ben(user.getDni());
        pet.setBeneficiario(user.getNombre());
        pet.setLinea(codigo);
        pet.setPrecioservicio(40);
        pet.setComentarios(comentario);
        pet.setEstado("Pendiente");

        boolean existe= (boolean) session.getAttribute("existeS");
        if(existe){

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
    public String aceptarPeticion(@ModelAttribute("cod") String cod,@ModelAttribute("empresa") String empresa) {

        Contrato contrato=contratoDao.getContratoE(empresa);
        Peticion pet;
        Factura fac;
        pet = peticionDao.getPeticion(cod);
        if(pet.getEstado().equals("Pendiente")){
            try{
                pet.setCodcontrato(contrato.getCodcontrato());
                Date fecha = new Date();
                pet.setFechaaceptada(fecha);
                pet.setEmpresa(empresa);
                pet.setEstado("Aceptada");
                peticionDao.updatePeticion(pet);
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
                System.out.println("Correo destinatario: "+empresaDao.getEmpresa(empresa).getEmail()+"\n" +
                        "Correo del que envia: mayoresEnCasa@gva.es\n" +
                        "Empresa, "+empresaDao.getEmpresa(empresa).getNombre()+", tiene un nuevo encargo para don/doña, "+pet.getBeneficiario()+".\n" +
                        "Gracias por la ayuda");
                return "redirect:list";
            }catch (NullPointerException e){
                return "redirect:../contrato/add";
            }

        }

        return "redirect:list";
    }

    @RequestMapping(value="/rechazar/{cod}")
    public String rechazarPeticion(@PathVariable String cod, Model model) {
        Peticion pet;
        pet = peticionDao.getPeticion(cod);
        if(pet.getEstado().equals("Pendiente")){
            Date fecha = new Date();
            System.out.println(fecha);
            pet.setFecharechazada(fecha);
            pet.setEstado("Rechazada");
            peticionDao.updatePeticion(pet);
        }
        return "redirect:../list";
    }

    @RequestMapping("/misPeticiones")
    public String misPeticiones(HttpSession session, Model model) {
        Usuario user= (Usuario) session.getAttribute("user");
        model.addAttribute("peticiones", peticionDao.getPeticionesPropias(user.getDni()));
        return "peticion/misPeticiones";
    }

    @RequestMapping("/info")
    public String limpieza(Model model) {
        return "peticion/info";
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
