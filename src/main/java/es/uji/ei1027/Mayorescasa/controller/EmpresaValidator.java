package es.uji.ei1027.Mayorescasa.controller;

import es.uji.ei1027.Mayorescasa.model.Empresa;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class EmpresaValidator implements Validator {
    @Override
    public boolean supports(Class<?> cls) {
        return Empresa.class.equals(cls);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Empresa empresa = (Empresa)obj;
        if (empresa.getNombre().length()<4)
            errors.rejectValue("nombre", "obligatorio",
                    " Introduce un valor valido (Mayor que 4 letras)");
        if (empresa.getUsuario().trim().equals(""))
            errors.rejectValue("usuario", "obligatorio",
                    " Hay que introducir un valor");
        if (empresa.getContacto().length()<12)
            errors.rejectValue("contacto", "obligatorio",
                    " Introduce un valor valido (Nombre y Apellidos)");
        if (empresa.getTelefono()<600000000)
            errors.rejectValue("telefono", "obligatorio",
                    "No es un número válido");
        if (empresa.getCif().trim().length()!=9)
            errors.rejectValue("cif", "obligatorio",
                    " Introduce un cif valido");
        if (empresa.getContraseña().trim().equals(""))
            errors.rejectValue("contraseña", "obligatorio",
                    " Hay que introducir un valor");
        if (!empresa.getEmail().trim().contains("@") || !empresa.getEmail().trim().contains("."))
            errors.rejectValue("email", "obligatorio",
                    " Introduce un email valido");
        if (empresa.getTiposervicio().trim().equals(""))
            errors.rejectValue("tiposervicio", "obligatorio",
                    " Hay que introducir un valor");
        if (empresa.getCont_nif().trim().length()!=9)
            errors.rejectValue("cont_nif", "obligatorio",
                    " Introduce un nif valido");
        if (!empresa.getCont_mail().trim().contains("@") || !empresa.getEmail().trim().contains("."))
            errors.rejectValue("cont_mail", "obligatorio",
                    " Introduce un email valido");
        if (empresa.getCont_tlf()<600000000)
            errors.rejectValue("cont_tlf", "obligatorio",
                    "No es un número válido");

    }
}
