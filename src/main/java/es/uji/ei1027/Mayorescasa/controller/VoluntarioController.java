package es.uji.ei1027.Mayorescasa.controller;

import es.uji.ei1027.Mayorescasa.dao.DisponibilidadDao;
import es.uji.ei1027.Mayorescasa.dao.UsuarioDao;
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
@RequestMapping("/voluntario")
public class VoluntarioController {
    @Autowired
    private UsuarioDao usuarioDao;

    @Autowired
    public void setUsuarioDao(UsuarioDao usuarioDao) {

        this.usuarioDao=usuarioDao;
    }
    @Autowired
    DisponibilidadDao disponibilidadDao;

    @RequestMapping("/list")
    public String listusuarios(HttpSession session, Model model) {
        Usuario user= (Usuario) session.getAttribute("user");
        try{
            if(user.getTipoUsuario().equals("casVolunteer")) {
                model.addAttribute("usuarios", usuarioDao.getVoluntariosUsers());
                return "voluntario/list";
            }
        } catch (Exception e) {
            return "error/error";
        }
        return "error/error";
    }

    @RequestMapping("/registros")
    public String registros(HttpSession session, Model model) {
        Usuario user= (Usuario) session.getAttribute("user");
        try{
            if(user.getTipoUsuario().equals("casVolunteer")) {
                model.addAttribute("usersVol", usuarioDao.getVoluntariosPendientes());
                model.addAttribute("usersVolA", usuarioDao.getVoluntariosAceptados());
                model.addAttribute("usersVolR", usuarioDao.getVoluntariosRechazados());
                return "voluntario/registros";
            }
        } catch (Exception e) {
            return "error/error";
        }
        return "error/error";
    }

    //Llamada de la peticion add
    @RequestMapping(value="/add")
    public String addvoluntario(HttpSession session, Model model) {
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("voluntario", new Voluntario());
        List<String> generoList = new ArrayList<>();
        generoList.add("Femenino");
        generoList.add("Masculino");
        model.addAttribute("generoList", generoList);
        return "voluntario/add";
    }

    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("voluntario") Voluntario voluntario,
                                   BindingResult bindingResult) {
        VoluntarioValidator voluntarioValidator = new VoluntarioValidator();
        voluntarioValidator.validate(voluntario, bindingResult);
        if (bindingResult.hasErrors()) {
            System.out.println("Error al añadir el voluntario");
            return "voluntario/add";
        }
        voluntario.setTipoUsuario("Voluntario");
        try {
            usuarioDao.addUsuario(voluntario);
            usuarioDao.addVoluntario(voluntario.getDni(), voluntario.getHobbies(), voluntario.getDias_semana(), "no", "Pendiente");
        } catch (Exception e){
            e.printStackTrace();
            return "error/existe";
        }
        return "redirect:../voluntario/altaEnviada";
    }

    @RequestMapping(value = "/rechazar/{dni}")
    public String rechazarVoluntario(@PathVariable String dni){
        Voluntario voluntario = usuarioDao.getVoluntario(dni);
        voluntario.setEstado("Rechazado");
        usuarioDao.updateEstadoVoluntario(voluntario);
        System.out.println("");
        System.out.println("");
        System.out.println("EMAIL ENVIADO");
        System.out.println("*************************************************************************");
        System.out.println("Correo destinatario: " +usuarioDao.getUsuarioDni(dni).getEmail()+ "\n"+
                "Correo del que envia: mayoresEnCasa@gva.es\n" +
                "Estimado/a señor/a "+usuarioDao.getUsuarioDni(dni).getNombre()+ " desde mayores en casa le comunicamos que tu solicitud para ser voluntario ha sido "+ voluntario.getEstado()+".\n" +
                "Gracias por su colaboración.\n");
        System.out.println("*************************************************************************");

        return "redirect:../registros";
    }

    @RequestMapping(value = "/aceptar/{dni}")
    public String aceptarVoluntario(@PathVariable String dni){
        Voluntario voluntario = usuarioDao.getVoluntario(dni);
        voluntario.setEstado("Aceptado");
        usuarioDao.updateEstadoVoluntario(voluntario);
        System.out.println("");
        System.out.println("");
        System.out.println("EMAIL ENVIADO");
        System.out.println("*************************************************************************");
        System.out.println("Correo destinatario: " +usuarioDao.getUsuarioDni(dni).getEmail()+ "\n"+
                "Correo del que envia: mayoresEnCasa@gva.es\n" +
                "Estimado/a señor/a "+usuarioDao.getUsuarioDni(dni).getNombre()+ " desde mayores en casa le comunicamos que tu solicitud para ser voluntario ha sido "+ voluntario.getEstado()+".\n" +
                "Gracias por su colaboración.\n");

        return "redirect:../registros";
    }

    @RequestMapping(value ="/delete/{dni}")
    public String eliminarVoluntario(@PathVariable String dni){
        if(!usuarioDao.consultarDisponibilidad(dni).isEmpty()) return "voluntario/errorRel";
        if(!usuarioDao.consultarDisponibilidadPendiente(dni).isEmpty()) {
            usuarioDao.deleteDisponibilidad(dni);
        }
        usuarioDao.deleteVoluntario(dni);
        usuarioDao.deleteUsuario(dni);
        return "voluntario/eliminado";
    }

    @RequestMapping("/altaEnviada")
    public String altaEnviada(HttpSession session, Model model) {
        Usuario user= (Usuario) session.getAttribute("user");
        return "voluntario/altaEnviada";
    }

    @RequestMapping(value = "/index")
    public String index(HttpSession session, Model model) {
        Usuario user= (Usuario) session.getAttribute("user");
        try{
            if(user.getTipoUsuario().equals("Voluntario")) {
                model.addAttribute("perfil", usuarioDao.getUsuario(user.getUsuario()));
                List<Disponibilidad> listaAcep= disponibilidadDao.consultaBeneficiariosAceptado(user.getDni());
                Map<Usuario,Disponibilidad> mapAcep=new HashMap<>();
                for(Disponibilidad dis:listaAcep){
                    mapAcep.put(usuarioDao.getUsuarioDni(dis.getUsuario_ben()),dis);
                }
                model.addAttribute("beneficiariosA",mapAcep);
                return "voluntario/index";
            }
        } catch (Exception e) {
            return "error/error";
        }
        return "error/error";
    }

    @RequestMapping("/perfil")
    public String perfil(HttpSession session, Model model) {
        Usuario user= (Usuario) session.getAttribute("user");
        try{
            if(user.getTipoUsuario().equals("Voluntario")) {
                if(usuarioDao.getVoluntario(user.getDni()).getEstado().equals("Pendiente")) return "voluntario/pendiente";
                Voluntario vol=usuarioDao.getVoluntario(user.getDni());
                String visible=vol.getVisible();
                String estado=vol.getEstado();
                String usuario=vol.getUsuario();
                model.addAttribute("usuario",usuario);
                model.addAttribute("visibilidad",visible);
                model.addAttribute("estado",estado);
                return "voluntario/perfil";
            }
        } catch (Exception e) {
            return "error/error";
        }
        return "error/error";
    }


    @RequestMapping("/solicitar")
    public String solicitar(Model model,HttpSession session) {
        Usuario user= (Usuario) session.getAttribute("user");
        try{
            if(user.getTipoUsuario().equals("Beneficiario")) {
                List<Voluntario> voluntariosDisponibles=usuarioDao.getVoluntariosVisibles();
                List<Disponibilidad> asignados= disponibilidadDao.consultaDisponibilidad(user.getDni());
                List<TempUsuarioComentario> listaTemporal= new ArrayList<>();

                //si ya tiene uno de los voluntarios asignado, y está aceptada, lo elimina de los disponibles.
                if (asignados.size()>0) {
                    for(Disponibilidad dis:asignados) {
                   for(Voluntario vol:voluntariosDisponibles) {
                                if ( vol.getDni().equals(dis.getUsuario_vol())) {
                                    voluntariosDisponibles.remove(vol);
                                    break;//solo hace el break en el for de dentro
                                }
                            }
                        //si está aceptada, la añado a la lista de asignados
                        if (dis.getEstado().equals("Aceptada")){
                            TempUsuarioComentario temporal = new TempUsuarioComentario();
                            temporal.setUsuario(usuarioDao.getUsuarioDni(dis.getUsuario_vol()));
                            temporal.setComentario(disponibilidadDao.getComentario(dis.getUsuario_vol(),dis.getUsuario_ben()));
                            listaTemporal.add(temporal);
                        }
                    }
                }
                if(listaTemporal.size()>0){
                    model.addAttribute("temp",listaTemporal);
                }
                if(voluntariosDisponibles.size()>0){
                    model.addAttribute("voluntarios", voluntariosDisponibles);
                }

                return "voluntario/solicitar";
            }
        } catch (Exception e) {
            return "error/error";
        }
        return "error/error";
    }


    @RequestMapping(value="/solicitar", method= RequestMethod.POST)
    public String setDisponibildad(@ModelAttribute("dniV") String dniV,@ModelAttribute("comment") String comment, HttpSession session,Model model){

        Usuario user= (Usuario) session.getAttribute("user");
        List<TempUsuarioComentario> listaTemporal= new ArrayList<>();
        List<Voluntario> voluntariosDisponibles=usuarioDao.getVoluntariosVisibles();

        //añado la relacion entre ben y vol
        Disponibilidad disponibilidad=new Disponibilidad();
        disponibilidad.setUsuario_ben(user.getDni());
        disponibilidad.setUsuario_vol(dniV);
        Date fecha = new Date();
        disponibilidad.setFechainicial(fecha);
        disponibilidad.setComentario(comment);
        disponibilidad.setEstado("Pendiente");
        disponibilidadDao.addDisponibilidad(disponibilidad);

        //recojo los voluntarios asignados al beneficiario
        List<Disponibilidad> asignados= disponibilidadDao.consultaDisponibilidad(user.getDni());
        session.setAttribute("volAsignados",asignados);

        //si ya tiene uno de los voluntarios asignado, lo elimina de los disponibles.
        for(Disponibilidad dis:asignados) {
                for(Voluntario vol:voluntariosDisponibles) {
                    if ( vol.getDni().equals(dis.getUsuario_vol())) {
                        voluntariosDisponibles.remove(vol);
                        break;//solo hace el break en el for de dentro
                    }
                }
            //si está aceptada, la añado a la lista de asignados
            if (dis.getEstado().equals("Aceptada")){
                TempUsuarioComentario temporal = new TempUsuarioComentario();
                temporal.setUsuario(usuarioDao.getUsuarioDni(dis.getUsuario_vol()));
                temporal.setComentario(disponibilidadDao.getComentario(dis.getUsuario_vol(), dis.getUsuario_ben()));
                listaTemporal.add(temporal);
            }
        }
        if(listaTemporal.size()>0){
            model.addAttribute("temp",listaTemporal);
        }
        if(voluntariosDisponibles.size()>0){
            model.addAttribute("voluntarios", voluntariosDisponibles);
        }
        return "voluntario/solicitar";
    }
    @RequestMapping(value="/rechazarBen/{dni}", method = RequestMethod.GET)
    public String rechazarBeneficiario( @PathVariable("dni") String dniBen, HttpSession session) {

            Usuario user=(Usuario)  session.getAttribute("user");
            Disponibilidad dis=disponibilidadDao.getDisponibilidad(dniBen,user.getDni());

            //si ya estaba aceptada, se finaliza
            if(dis.getEstado().equals("Aceptada")){
                dis.setEstado("Finalizada");
                Date fecha = new Date();
                dis.setFechafinal(fecha);
                disponibilidadDao.finalizarDis(dis);
            }else if(dis.getEstado().equals("Pendiente")){
                dis.setEstado("Rechazada");
                disponibilidadDao.updateEstado(dis);
            }
            System.out.println("");
            System.out.println("");
            System.out.println("EMAIL ENVIADO");
            System.out.println("*************************************************************************");
            System.out.println("Correo destinatario:"+usuarioDao.getUsuarioDni(dniBen).getEmail()+"\n" +
                    "Correo del que envia: mayoresEnCasa@gva.es\n" +
                    "Don/Doña "+usuarioDao.getUsuarioDni(dniBen).getNombre()+", lamentamos comunicarle que el voluntario/a"+user.getNombre()+" por motivos personales no podrá seguir prestano servicio. \n" +
                    "Esperemos que esto no le cause problemas y pueda encontrar otro voluntario rápido con nuestra aplicación");


        return "redirect:../beneficiarios";
    }

    @RequestMapping(value="/aceptarBen/{dni}", method = RequestMethod.GET)
    public String aceptarBeneficiario( @PathVariable("dni") String dniBen,HttpSession session, Model model) {

        Usuario user=(Usuario)  session.getAttribute("user");
        Disponibilidad dis=disponibilidadDao.getDisponibilidad(dniBen,user.getDni());
        //si ya está finaliza o rechaza, no puedes volver a aceptarla
        if(dis.getEstado().equals("Pendiente")){
            dis.setEstado("Aceptada");
            disponibilidadDao.updateEstado(dis);

        }
        System.out.println("");
        System.out.println("");
        System.out.println("EMAIL ENVIADO");
        System.out.println("*************************************************************************");
        System.out.println("Correo destinatario: "+usuarioDao.getUsuarioDni(dniBen).getEmail()+"\n" +
                "Correo del que envia: mayoresEnCasa@gva.es\n" +
                "Don/Doña "+usuarioDao.getUsuarioDni(dniBen).getNombre()+", le confirmamos que el voluntario que solicitaste, "+user.getNombre()+",  ha aceptado su petición.\n" +
                "Esperemos que sea de su agrado y podais construir una buena relación.");

        return "redirect:../beneficiarios";
    }
    @RequestMapping(value="/deleteBen/{dni}", method = RequestMethod.GET)
    public String deleteBeneficiario( @PathVariable("dni") String dniBen,HttpSession session) {

        Usuario user=(Usuario)  session.getAttribute("user");
        Disponibilidad dis=disponibilidadDao.getDisponibilidad(dniBen,user.getDni());
        disponibilidadDao.deleteDisponibilidad(dis);
        return "redirect:../beneficiarios";
    }


    @RequestMapping("/beneficiarios")
    public String beneficiarios(Model model,HttpSession session) {
        Usuario user= (Usuario) session.getAttribute("user");
        try{
            if(user.getTipoUsuario().equals("Voluntario")) {
                //lista de disponilidades de los beneficiarios
                List<Disponibilidad> listaDis= disponibilidadDao.consultaBeneficiariosPendiente(user.getDni());

                //mapa de key usuario y valor la disponibilidad
                Map<Usuario,Disponibilidad> map=new HashMap<>();
                for(Disponibilidad dis:listaDis){
                    map.put(usuarioDao.getUsuarioDni(dis.getUsuario_ben()),dis);
                }
                model.addAttribute("map",map);

                List<Disponibilidad> listaAcep= disponibilidadDao.consultaBeneficiariosAceptado(user.getDni());
                Map<Usuario,Disponibilidad> mapAcep=new HashMap<>();
                for(Disponibilidad dis:listaAcep){
                    mapAcep.put(usuarioDao.getUsuarioDni(dis.getUsuario_ben()),dis);
                }
                model.addAttribute("beneficiariosA",mapAcep);

                List<Disponibilidad> listaRech= disponibilidadDao.consultaBeneficiariosRechazado(user.getDni());
                Map<Usuario,Disponibilidad> mapRech=new HashMap<>();
                for(Disponibilidad dis:listaRech){
                    mapRech.put(usuarioDao.getUsuarioDni(dis.getUsuario_ben()),dis);
                }
                model.addAttribute("beneficiariosR",mapRech);

                List<Disponibilidad> listaFinal= disponibilidadDao.consultaBeneficiariosFinalizado(user.getDni());
                Map<Usuario,Disponibilidad> mapFinal=new HashMap<>();
                for(Disponibilidad dis:listaFinal){
                    mapFinal.put(usuarioDao.getUsuarioDni(dis.getUsuario_ben()),dis);
                }
                model.addAttribute("beneficiariosF",mapFinal);

                return "voluntario/beneficiarios";
            }
        } catch (Exception e) {
            return "error/error";
        }
        return "error/error";
    }

    @RequestMapping("/quitarVisibilidad")
    public String quitarVisibilidad(Model model,HttpSession session) {
        Usuario user= (Usuario) session.getAttribute("user");
        try{
            if(user.getTipoUsuario().equals("Voluntario")) {
                usuarioDao.setVisibilidad(user.getDni(),"no");
                return "redirect:./perfil";
            }
        } catch (Exception e) {
            return "error/error";
        }
        return "error/error";
    }

    @RequestMapping("/ponerVisibilidad")
    public String ponerVisibilidad(Model model,HttpSession session) {
        Usuario user= (Usuario) session.getAttribute("user");
        try{
            if(user.getTipoUsuario().equals("Voluntario")) {
                usuarioDao.setVisibilidad(user.getDni(),"si");
                return "redirect:./perfil";
            }
        } catch (Exception e) {
            return "error/error";
        }
        return "error/error";
    }

    @RequestMapping("/baja")
    public String baja(Model model,HttpSession session) {

        Usuario user=(Usuario) session.getAttribute("user");
        Voluntario actual=usuarioDao.getVoluntario(user.getDni());
        actual.setEstado("Baja");
        usuarioDao.updateEstadoVoluntario(actual);
        usuarioDao.setVisibilidad(user.getDni(),"no");
        List<Disponibilidad> listaDisp=disponibilidadDao.consultaBeneficiarios(user.getDni());
        for(Disponibilidad disp:listaDisp){
            Date fecha=new Date();
            disp.setFechafinal(fecha);
            disp.setEstado("Finalizada");
            disponibilidadDao.finalizarDis(disp);
        }

        return "redirect:./perfil";
    }
    @RequestMapping("/alta")
    public String alta(Model model,HttpSession session) {
        Usuario user=(Usuario) session.getAttribute("user");
        Voluntario actual=usuarioDao.getVoluntario(user.getDni());
        actual.setEstado("Aceptado");
        usuarioDao.updateEstadoVoluntario(actual);
        usuarioDao.setVisibilidad(user.getDni(),"si");
        return "redirect:./perfil";
    }

    @RequestMapping( value="/rechazarVol/{dni}", method = RequestMethod.GET)
    public String finalizarConVoluntario( @PathVariable("dni") String dniVol,HttpSession session){
        Usuario user=(Usuario) session.getAttribute("user");
        Disponibilidad dis=disponibilidadDao.getDisponibilidad(user.getDni(),dniVol);
        Date fecha=new Date();
        dis.setFechafinal(fecha);
        dis.setEstado("Finalizada");
        disponibilidadDao.finalizarDis(dis);
        System.out.println("");
        System.out.println("");
        System.out.println("EMAIL ENVIADO");
        System.out.println("*************************************************************************");
        System.out.println("Correo destinatario:"+usuarioDao.getUsuarioDni(dniVol).getEmail()+"\n" +
                "Correo del que envia: mayoresEnCasa@gva.es\n" +
                "Estimado/a "+usuarioDao.getUsuarioDni(dniVol).getNombre()+", lamentamos comunicarle que el beneficiario/a "+user.getNombre()+", por motivos personales ha decido que no quiere que vuelvas.\n" +
                " Puedes consultar tus peticiones y elegir otro beneficiario al que ayudar. Gracias por la colaboración.\n");
        return "redirect:../solicitar";
    }

    //Llamada de la peticion add
    @RequestMapping(value="/addCas")
    public String addCasvoluntario(HttpSession session, Model model) {
        Usuario user= (Usuario) session.getAttribute("user");
        try{
            if(user.getTipoUsuario().equals("casVolunteer")) {
                model.addAttribute("usuario", new Usuario());
                model.addAttribute("voluntario", new Voluntario());
                List<String> generoList = new ArrayList<>();
                generoList.add("Femenino");
                generoList.add("Masculino");
                model.addAttribute("generoList", generoList);
                return "voluntario/addCas";
            }
        } catch (Exception e) {
            return "error/error";
        }
        return "error/error";
    }
    @RequestMapping(value="/addCas", method= RequestMethod.POST)
    public String processAddSubmitCas(@ModelAttribute("voluntario") Voluntario voluntario,
                                   BindingResult bindingResult) {
        VoluntarioValidator voluntarioValidator = new VoluntarioValidator();
        voluntarioValidator.validate(voluntario, bindingResult);
        if (bindingResult.hasErrors()) {
            System.out.println("Error al añadir el voluntario por CAS");
            return "voluntario/addCas";
        }
        voluntario.setTipoUsuario("Voluntario");
        usuarioDao.addUsuario(voluntario);
        usuarioDao.addVoluntario(voluntario.getDni(),voluntario.getHobbies(),voluntario.getDias_semana(),"no","Aceptado");
        return "redirect:../voluntario/list";
    }

    @RequestMapping("/unilist/{usuario}")
    public String unilistEmpresas(HttpSession session, Model model, @PathVariable String usuario) {
        Usuario user= (Usuario) session.getAttribute("user");
        try{
            if(user.getTipoUsuario().equals("casVolunteer")) {
                model.addAttribute("user", usuarioDao.getUsuario(usuario));
                Usuario useri = usuarioDao.getUsuario(usuario);
                model.addAttribute("vol", usuarioDao.getVoluntario(useri.getDni()));
                return "voluntario/unilist";
            }
        } catch (Exception e) {
            return "error/error";
        }
        return "error/error";
    }

    @RequestMapping("/unilistPen/{usuario}")
    public String unilistEmpresasPen(HttpSession session, Model model, @PathVariable String usuario) {
        Usuario user= (Usuario) session.getAttribute("user");
        try{
            if(user.getTipoUsuario().equals("casVolunteer")) {
                model.addAttribute("user", usuarioDao.getUsuario(usuario));
                Usuario useri = usuarioDao.getUsuario(usuario);
                model.addAttribute("vol", usuarioDao.getVoluntario(useri.getDni()));
                return "voluntario/unilistPen";
            }
        } catch (Exception e) {
            return "error/error";
        }
        return "error/error";
    }

    @RequestMapping("/unilistPropio/{usuario}")
    public String unilistEmpresasPropio(HttpSession session, Model model, @PathVariable String usuario) {
        Usuario user= (Usuario) session.getAttribute("user");
        try{
            if(user.getTipoUsuario().equals("Voluntario")) {
                model.addAttribute("user", usuarioDao.getUsuario(usuario));
                Usuario useri = usuarioDao.getUsuario(usuario);
                model.addAttribute("vol", usuarioDao.getVoluntario(useri.getDni()));
                return "voluntario/unilistPropio";
            }
        } catch (Exception e) {
            return "error/error";
        }
        return "error/error";
    }

    @RequestMapping(value = "/update/{usuario}", method = RequestMethod.GET)
    public String editusuario(HttpSession session, Model model, @PathVariable String usuario) {
        Usuario user= (Usuario) session.getAttribute("user");
        try{
            if(user.getTipoUsuario().equals("casVolunteer") || user.getTipoUsuario().equals("Voluntario")) {
                model.addAttribute("usuario", usuarioDao.getUsuario(usuario));
                return "voluntario/update";
            }
        } catch (Exception e) {
            return "error/error";
        }
        return "error/error";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String processUpdateSubmit(HttpSession session,
            @ModelAttribute("usuario") Usuario usuario, BindingResult bindingResult) {
        Usuario user= (Usuario) session.getAttribute("user");
        if (bindingResult.hasErrors())
            return "voluntario/update";
        usuario.setTipoUsuario("Voluntario");
        usuarioDao.updateUsuario(usuario);
        if(user.getTipoUsuario().equals("casVolunteer")) return "redirect:list";
        return "redirect:unilistPropio/" + user.getUsuario();
    }

    @RequestMapping(value = "/updatePropio/{dni}", method = RequestMethod.GET)
    public String editVoluntario(HttpSession session, Model model, @PathVariable String dni) {
        Usuario user= (Usuario) session.getAttribute("user");
        try{
            if(user.getTipoUsuario().equals("Voluntario")) {
                model.addAttribute("voluntario", usuarioDao.getVoluntario(dni));
                return "voluntario/updatePropio";
            }
        } catch (Exception e) {
            return "error/error";
        }
        return "error/error";
    }

    @RequestMapping(value = "/updatePropio", method = RequestMethod.POST)
    public String processUpdateSubmitVoluntario(HttpSession session,
            @ModelAttribute("voluntario") Voluntario voluntario,
            BindingResult bindingResult) {
        Usuario user= (Usuario) session.getAttribute("user");
        if (bindingResult.hasErrors())
            return "voluntario/updatePropio";
        usuarioDao.updateVoluntario(voluntario);
        return "redirect:unilistPropio/" + user.getUsuario();
    }

    @RequestMapping("/contactaCas")
    public String contactaCas(HttpSession session, Model model) {
        Usuario user= (Usuario) session.getAttribute("user");
        try{
            if(user.getTipoUsuario().equals("Voluntario")) {
                model.addAttribute("datos", usuarioDao.getCasVolunteer());
                return "voluntario/contactaCas";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "error/error";
        }
        return "error/error";
    }

    @RequestMapping(value = "/addComentario", method = RequestMethod.POST)
    public String limpieza(@ModelAttribute("comment") String comentario, HttpSession session) {
        Usuario user= (Usuario) session.getAttribute("user");
        try{
            if (comentario==null) comentario="";
            Usuario cas = usuarioDao.getCasVolunteer();
            System.out.println("");
            System.out.println("");
            System.out.println("EMAIL ENVIADO");
            System.out.println("*************************************************************************");
            System.out.println("Correo destinatario: " +cas.getEmail() + "\n"+
                    "Correo del que envia: mayoresEnCasa@gva.es\n" +
                    "Estimado/a señor/a "+ cas.getNombre() + ":");
            System.out.println("El voluntario " + user.getNombre() + " tiene la siguiente duda: \n");
            System.out.println(comentario + "\n");
            System.out.println("");
            System.out.println("Mayores en casa.\n");
            System.out.println("*************************************************************************");
            return "voluntario/enviado";
        } catch (Exception e){
            return "error/error";
        }
    }

    @RequestMapping("/enviado")
    public String enviado(HttpSession session, Model model) {
        Usuario user= (Usuario) session.getAttribute("user");
        try{
            if(user.getTipoUsuario().equals("Empresa")) {
                return "voluntario/enviado";
            }
        } catch (Exception e) {
            return "error/error";
        }
        return "error/error";
    }

    @RequestMapping("/activas")
    public String activas(HttpSession session, Model model) {
        Usuario user= (Usuario) session.getAttribute("user");
        try{
            if(user.getTipoUsuario().equals("Voluntario")) {
                List<Disponibilidad> listaAcep= disponibilidadDao.consultaBeneficiariosAceptado(user.getDni());
                Map<Usuario,Disponibilidad> mapAcep=new HashMap<>();
                for(Disponibilidad dis:listaAcep){
                    mapAcep.put(usuarioDao.getUsuarioDni(dis.getUsuario_ben()),dis);
                }
                model.addAttribute("beneficiariosA",mapAcep);

                List<Disponibilidad> listaRech= disponibilidadDao.consultaBeneficiariosRechazado(user.getDni());
                Map<Usuario,Disponibilidad> mapRech=new HashMap<>();
                for(Disponibilidad dis:listaRech){
                    mapRech.put(usuarioDao.getUsuarioDni(dis.getUsuario_ben()),dis);
                }
                model.addAttribute("beneficiariosR",mapRech);

                List<Disponibilidad> listaFinal= disponibilidadDao.consultaBeneficiariosFinalizado(user.getDni());
                Map<Usuario,Disponibilidad> mapFinal=new HashMap<>();
                for(Disponibilidad dis:listaFinal){
                    mapFinal.put(usuarioDao.getUsuarioDni(dis.getUsuario_ben()),dis);
                }
                model.addAttribute("beneficiariosF",mapFinal);
                return "voluntario/activas";
            }
        } catch (Exception e) {
            return "error/error";
        }
        return "error/error";
    }


}
