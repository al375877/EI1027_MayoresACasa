package es.uji.ei1027.Mayorescasa.controller;

import es.uji.ei1027.Mayorescasa.model.Contrato;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ContratoValidator implements Validator {
    @Override
    public boolean supports(Class<?> cls) {
        return Contrato.class.equals(cls);
        // o, si volguérem tractar també les subclasses:
        // return Contrato.class.isAssignableFrom(cls);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Contrato contrato = (Contrato)obj;
        if (contrato.getEmpresa().trim().equals(""))
            errors.rejectValue("empresa", "obligatorio",
                    " Hay que introducir un valor");
        if (contrato.getCantidadservicios()<1)
            errors.rejectValue("cantidadservicios", "obligatorio",
                    " Hay que introducir un valor");
    }
}
