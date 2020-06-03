package es.uji.ei1027.Mayorescasa.controller;

import es.uji.ei1027.Mayorescasa.dao.EmpresaDao;
import es.uji.ei1027.Mayorescasa.model.Contrato;
import es.uji.ei1027.Mayorescasa.model.Empresa;
import es.uji.ei1027.Mayorescasa.model.Peticion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class PeticionValidador implements Validator {
    @Autowired
    EmpresaDao empresaDao;

    @Override
    public boolean supports(Class<?> cls) {
        return Contrato.class.equals(cls);
        // o, si volguérem tractar també les subclasses:
        // return Contrato.class.isAssignableFrom(cls);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Peticion peticion = (Peticion) obj;

        if (peticion.getComentarios().trim().equals(""))
            errors.rejectValue("comentarios", "obligatorio",
                    " Hay que introducir un valor");

    }
}
