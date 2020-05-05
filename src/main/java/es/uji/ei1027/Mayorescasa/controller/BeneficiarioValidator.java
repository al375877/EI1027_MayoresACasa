package es.uji.ei1027.Mayorescasa.controller;

import es.uji.ei1027.Mayorescasa.model.Usuario;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class BeneficiarioValidator implements Validator {
    @Override
    public boolean supports(Class<?> cls) {
        return Usuario.class.equals(cls);
        // o, si volguérem tractar també les subclasses:
        // return Usuario.class.isAssignableFrom(cls);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Usuario usuario = (Usuario)obj;
        if (usuario.getNombre().trim().equals(""))
            errors.rejectValue("nombre", "obligatorio",
                    " Hay que introducir un valor");
        // Afegeix ací la validació per a Edat > 18 anys
        if (usuario.getNacimiento()>1955)
            errors.rejectValue("nacimiento", "obligatorio",
                    " Mayor de 65 años (o cumplirlos este año)");
        if (usuario.getDni().trim().equals(""))
            errors.rejectValue("dni", "obligatorio",
                    " Hay que introducir un valor");
        if (usuario.getTelefono()<600000000)
            errors.rejectValue("telefono", "obligatorio",
                    " Hay que introducir un valor");
        if (usuario.getUsuario().trim().equals(""))
            errors.rejectValue("usuario", "obligatorio",
                    " Hay que introducir un valor");
        if (usuario.getContraseña().trim().equals(""))
            errors.rejectValue("contraseña", "obligatorio",
                    " Hay que introducir un valor");
        if (usuario.getEmail().trim().equals(""))
            errors.rejectValue("email", "obligatorio",
                    " Hay que introducir un valor");
        if (usuario.getDireccion().trim().equals(""))
            errors.rejectValue("direccion", "obligatorio",
                    " Hay que introducir un valor");


    }
}