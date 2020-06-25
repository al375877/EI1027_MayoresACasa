package es.uji.ei1027.Mayorescasa.controller;

import es.uji.ei1027.Mayorescasa.model.Empresa;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class EmpresaValidator implements Validator {
    @Override
    public boolean supports(Class<?> cls) {
        return Empresa.class.equals(cls);
        // o, si volguérem tractar també les subclasses:
        // return Empresa.class.isAssignableFrom(cls);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Empresa empresa = (Empresa)obj;
        if (empresa.getNombre().trim().equals(""))
            errors.rejectValue("nombre", "obligatorio",
                    " Hay que introducir un valor");
        // Afegeix ací la validació per a Edat > 18 anys
        if (empresa.getUsuario().trim().equals(""))
            errors.rejectValue("usuario", "obligatorio",
                    " Hay que introducir un valor");
        if (empresa.getContacto().trim().equals(""))
            errors.rejectValue("contacto", "obligatorio",
                    " Hay que introducir un valor");
        if (empresa.getTelefono()<600000000)
            errors.rejectValue("telefono", "obligatorio",
                    "No es un número válido");
        if (empresa.getCif().trim().equals(""))
            errors.rejectValue("cif", "obligatorio",
                    " Hay que introducir un valor");
        if (empresa.getContraseña().trim().equals(""))
            errors.rejectValue("contraseña", "obligatorio",
                    " Hay que introducir un valor");
        if (empresa.getEmail().trim().equals(""))
            errors.rejectValue("email", "obligatorio",
                    " Hay que introducir un valor");
        if (empresa.getTiposervicio().trim().equals(""))
            errors.rejectValue("tiposervicio", "obligatorio",
                    " Hay que introducir un valor");
        if (empresa.getCont_nif().trim().equals(""))
            errors.rejectValue("cont_nif", "obligatorio",
                    " Hay que introducir un valor");
        if (empresa.getCont_mail().trim().equals(""))
            errors.rejectValue("cont_mail", "obligatorio",
                    " Hay que introducir un valor");
        if (empresa.getCont_tlf()<600000000)
            errors.rejectValue("cont_tlf", "obligatorio",
                    "No es un número válido");

    }
}
